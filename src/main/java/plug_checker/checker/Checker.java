package plug_checker.checker;

import org.w3c.dom.Node;

import java.util.HashMap;

import static plug_checker.constants.Constants.*;

public class Checker {
    // TODO
    // Input: file (String)
    // Returns Map <String, Boolean>, String - field label, Boolean - check result

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

        return result;
    }

    private String idFilePrefixCheck() {
        Node current = xslParser.locateNodeByNameAttribute(filePrefixAttribute);
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

        System.out.println("xslParameterValue: " + xslParameterValue + ", xsdPathPrefixValue: " + xsdPathPrefixValue);
        if (xsdPathPrefixValue.equals(xslParameterValue)) {
            return "+";
        }

        return "xsl parameter doesn't match with xsd file prefix";
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
