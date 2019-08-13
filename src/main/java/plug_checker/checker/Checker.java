package plug_checker.checker;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static plug_checker.constants.Constants.*;

public class Checker {
    private final String xslFilePath;
    private final String xsdFilePath;
    private final FileParser xslParser;
    private final FileParser xsdParser;

    public Checker(String xslFilePath, String xdlFilePath) {
        this.xslFilePath = xslFilePath;
        this.xsdFilePath = xdlFilePath;
        this.xslParser = new FileParser(xslFilePath);
        this.xsdParser = new FileParser(xsdFilePath);
    }

    public HashMap<String, String> checkAllEntries() {
        HashMap<String, String> result = new HashMap<>();
        result.put(idFileHtmlLabel, idFilePrefixCheck());
        result.put(checkPrefixHtmlLabel, checkPrefixForLatinSymbols());
        result.put(checkNecessaryParametersHtmlLabel, checkForNecessaryAttributes());
        result.put(checkIdPolExtractHtmlLabel, checkIdPolFileExtracting());
        result.put(checkINNHtmLabel, checkINNandKPP());

        return result;
    }

    private String idFilePrefixCheck() {
        Node filePrefixNode = xslParser.getParentNodeWithNameAttribute(filePrefixAttribute);
        if (filePrefixNode == null) {
            return "Parameter " + filePrefixAttribute + " not found";
        }

        String xsdPathWithoutDirs = cutDirsFromPath(xsdFilePath);
        String xslParameterValue = filePrefixNode.getTextContent();

        int secondIndex = getSecondIndexOf(xsdPathWithoutDirs, "_");
        if (secondIndex < 0 || secondIndex >= xsdPathWithoutDirs.length()) {
            return "xsd file prefix not found or it's in wrong format";
        }
        String xsdPathPrefixValue = xsdPathWithoutDirs.substring(0, secondIndex);
        if (xsdPathPrefixValue.equals(xslParameterValue)) {
            return "+";
        }
        return "xsl parameter doesn't match with xsd file prefix";
    }

    private int getSecondIndexOf(String str, String separator) {
        return str.indexOf(separator, str.indexOf(separator) + 1);
    }

    private String checkPrefixForLatinSymbols() {
        List<String> missingAlphabets = checkAlphabets();
        String report = "";
        if(missingAlphabets.size() > 0){
            report = String.join(", ", checkAlphabets()) + " not found.";
        }

        Node prefixTest = xslParser.getNodeWithTestAttribute(fileStartTestAttribute);
        if(prefixTest == null) {
            report += " File prefix test not found";
        }

        return report.length() > 0 ? report : "+";
    }

    private String checkForNecessaryAttributes(){
        String report = "";

        Node checkInvalidMsg = xslParser.getParentNodeWithNameAttribute(invalidMsgAttribute);
        if(checkInvalidMsg != null) {
            boolean selectInvalidMsgAttributeIsCorrect = checkNodeValue(checkInvalidMsg, invalidMsgSelectAttribute);
            if(!selectInvalidMsgAttributeIsCorrect) {
                report += "Incorrect " + invalidMsgAttribute + " select attribute. ";
            }
        } else {
            report = invalidMsgAttribute + " is not found. ";
        }

        Node checkIdFile = xslParser.getParentNodeWithNameAttribute(idFileAttribute);
        if(checkIdFile != null) {
            boolean selectIdFileAttributeIsCorrect = checkNodeValue(checkIdFile, idFileSelectAttribute);
            if(!selectIdFileAttributeIsCorrect) {
                report += "Incorrect " + idFileAttribute + " select attribute. ";
            }
        } else {
            report += idFileAttribute + " is not found. ";
        }

        return report.length() > 0 ? report : "+";
    }

    private String checkIdPolFileExtracting(){
        String report = "";

        Node idPolFileNode = xslParser.getParentNodeWithNameAttribute(idPolFileAttribute);
        if(idPolFileNode == null) {
            return idPolFileAttribute + " not found. ";
        }

        Node patternFileNode = xslParser.getParentNodeWithNameAttribute(filePrefixAttribute);
        if(patternFileNode == null) {
            return filePrefixAttribute + " not found. ";
        }

        String idPolExtractor = getNodeAttributeValue(idPolFileNode, "select");
        if(idPolExtractor == null) {
            return idPolFileAttribute + " doesn't have select attribute.";
        }

        String[] tokenArray = idPolExtractor.split("[,)]");
        if(tokenArray.length < 3) {
            return idPolFileAttribute + " select attribute syntax isn't correct";
        }
        String xslParameterValue = patternFileNode.getTextContent();

        int firstSymbolIndex = Integer.parseInt(tokenArray[1]);

        if(xslParameterValue.length() + 7 == firstSymbolIndex) {
            return "+";
        }

        return filePrefixAttribute + " length + 7 doesn't match " + idPolFileAttribute + " select";
    }

    private String checkINNandKPP(){
        String report = "";
        Node innflXsdNode = xsdParser.getNodeWithNameAttribute(INNFLFileAttribute);
        boolean innflExistsInXsd = innflXsdNode != null;

        Node innulXsdNode = xsdParser.getNodeWithNameAttribute(INNULFileAttribute);
        boolean innulExistsInXsd = innulXsdNode != null;

        Node checkKPP = xslParser.getParentNodeWithNameAttribute(KPPFileAttribute);
        if(checkKPP != null) {
            boolean selectKPPAttributeIsCorrect = checkNodeValue(checkKPP, KPPFileSelectAttribute);
            if(!selectKPPAttributeIsCorrect) {
                report += "Incorrect " + KPPFileAttribute + " select attribute. ";
            }
        } else {
            report = KPPFileAttribute + " not found. ";
        }

        Node checkNPUL = xslParser.getParentNodeWithNameAttribute(NPULFileAttribute);
        if(checkNPUL != null) {
            boolean selectNPULAttributeIsCorrect = checkNodeValue(checkNPUL, NPULFileSelectAttribute);
            if(!selectNPULAttributeIsCorrect) {
                report += "Incorrect " + NPULFileAttribute + " select attribute. ";
            }
        } else {
            report += NPULFileAttribute + " is not found. ";
        }

        if(!innflExistsInXsd && !innulExistsInXsd) {
            report += "No " + INNFLFileAttribute + " or " + INNULFileAttribute + " in xsd file. ";
            return report;
        }

        Node innflXslNode = xslParser.getParentNodeWithNameAttribute(INNFLFileAttribute);
        boolean innflExistsInXsl = innflXslNode != null;
        if(innflExistsInXsd != innflExistsInXsl) {
            report += INNFLFileAttribute + " in xsd and xsl doesn't match. ";
        } else {
            if(innflExistsInXsl) {
                boolean selectINNFLAttributeIsCorrect = checkNodeValue(innflXslNode, INNFLFileSelectAttribute);
                if(!selectINNFLAttributeIsCorrect) {
                    report += "Incorrect " + INNFLFileAttribute + " select attribute. ";
                }
            }
        }

        Node innulXslNode = xslParser.getParentNodeWithNameAttribute(INNULFileAttribute);
        boolean innulExistsInXsl = innulXslNode != null;
        if(innulExistsInXsd != innulExistsInXsl) {
            report += INNULFileAttribute + " in xsd and xsl doesn't match. ";
        } else {
            if(innulExistsInXsl) {
                boolean selectINNULAttributeIsCorrect = checkNodeValue(innulXslNode, INNULFileSelectAttribute);

                if(!selectINNULAttributeIsCorrect) {
                    report += "Incorrect " + INNULFileAttribute + " select attribute. ";
                }
            }
        }

        return report.length() > 0 ? report : "+";

    }

    private String getNodeAttributeValue(Node node, String attributeName) {
        if (node.hasAttributes()) {
            NamedNodeMap nodeMap = node.getAttributes();
            for (int i = 0; i < nodeMap.getLength(); i++) {
                Node tempNode = nodeMap.item(i);
                if(tempNode.getNodeName().equals(attributeName)) {
                    return tempNode.getNodeValue();
                }
            }
        }
        return null;
    }

    private boolean checkNodeValue(Node node, String attributeValue) {
        if (node.hasAttributes()) {
            NamedNodeMap nodeMap = node.getAttributes();
            for (int i = 0; i < nodeMap.getLength(); i++) {
                Node tempNode = nodeMap.item(i);
                if(tempNode.getNodeValue().equals(attributeValue)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Returns list of missing alphabets
    private List<String> checkAlphabets(){
        List<String> result = new ArrayList<>();

        Node alphabet = xslParser.getNodeWithNameAttribute(alfavit1Attribute);
        if (alphabet == null) {
            result.add(alfavit1Attribute);
        }
        alphabet = xslParser.getNodeWithNameAttribute(alfavit2Attribute);
        if (alphabet == null) {
            result.add(alfavit2Attribute);
        }

        return result;
    }

    private String cutDirsFromPath(String filePath) {
        String result = filePath;
        int slashIndex = filePath.lastIndexOf('/') + 1;
        if (slashIndex > 0) {
            result = filePath.substring(slashIndex);
        }

        int backSlashIndex = result.lastIndexOf('\\') + 1;
        if (backSlashIndex > 0) {
            result = result.substring(backSlashIndex);
        }

        return result;
    }
}
