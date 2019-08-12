package plug_checker.checker;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static plug_checker.constants.Constants.*;

public class Checker {
    private final String xslFilePath;
    private final String xsdFilePath;
    private final FileParser xslParser;
    private FileParser xsdParser;

    public Checker(String xslFilePath, String xdlFilePath) {
        this.xslFilePath = xslFilePath;
        this.xsdFilePath = xdlFilePath;
        this.xslParser = new FileParser(xslFilePath);
    }

    public HashMap<String, String> checkAllEntries() {
        HashMap<String, String> result = new HashMap<>();
        result.put(idFileHtmlLabel, idFilePrefixCheck());
        result.put(checkPrefixHtmlLabel, checkPrefixForLatinSymbols());

        return result;
    }

    private String idFilePrefixCheck() {
        Node current = xslParser.getNodeWithNameAttribute(filePrefixAttribute);
        if (current == null) {
            return "Parameter " + filePrefixAttribute + " not found";
        }

        String xsdPathWithoutDirs = cutDirsFromPath(xsdFilePath);
        String xslParameterValue = current.getTextContent();

        int secondIndex = xsdPathWithoutDirs.indexOf('_', xsdPathWithoutDirs.indexOf('_') + 1);
        if (secondIndex < 0 || secondIndex >= xsdPathWithoutDirs.length()) {
            return "xsd file prefix not found or it's in wrong format";
        }
        String xsdPathPrefixValue = xsdPathWithoutDirs.substring(0, secondIndex);
        if (xsdPathPrefixValue.equals(xslParameterValue)) {
            return "+";
        }
        return "xsl parameter doesn't match with xsd file prefix";
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

    // Returns list of missing alphabets
    private List<String> checkAlphabets(){
        List<String> result = new ArrayList<>();

        Node alphabet = xslParser.getNodeWithNameAttribute(fileAlfavit1Attribute);
        if (alphabet == null) {
            result.add(fileAlfavit1Attribute);
        }
        alphabet = xslParser.getNodeWithNameAttribute(fileAlfavit2Attribute);
        if (alphabet == null) {
            result.add(fileAlfavit2Attribute);
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
