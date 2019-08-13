package plug_checker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import plug_checker.checker.FileParser;

import static plug_checker.constants.Constants.idPolFileAttribute;
import static plug_checker.constants.Constants.invalidMsgAttribute;
public class CheckerTest {
    private static FileParser mockParser;

    @BeforeAll
    static void setUpClass() {
        mockParser = new FileParser("D:/demo.xsl");
    }

    @AfterAll
    static void tearDownClass() {
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testGetNodeAttributeValue() {
        Node idPolFileNode = mockParser.getParentNodeWithNameAttribute(idPolFileAttribute);
        if(idPolFileNode == null) {
            System.out.println(idPolFileAttribute + " is not found. ");
            return;
        }
        String result = "";
        if (idPolFileNode.hasAttributes()) {
            NamedNodeMap nodeMap = idPolFileNode.getAttributes();
            for (int i = 0; i < nodeMap.getLength(); i++) {
                Node tempNode = nodeMap.item(i);
                if(tempNode.getNodeName().equals("select")) {
                    System.out.println(tempNode.getNodeValue());
                    result = tempNode.getNodeValue();
                }
            }
        }
        String[] tokenArray = result.split("[,)]");;
        for(String temp : tokenArray){
            System.out.println(temp);
        }
    }

    @Test
    public void testPrefixCodeCheck(){
        int firstSymbolIndex = 17;
        int codeLength = 4;

        String xsdPathWithoutDirs = "NO_BOUCHR8_1_148_00_05_04_01.xsd";
        String codeByXslInfo = xsdPathWithoutDirs.substring(firstSymbolIndex, firstSymbolIndex + codeLength);

        int secondUnderscoreIndex = getSecondIndexOf(xsdPathWithoutDirs, "_");

        String xsdPathPrefixValue = xsdPathWithoutDirs.substring(0, secondUnderscoreIndex);
        int indexByXsdNamePrefix = xsdPathPrefixValue.length() + 7;


        System.out.println("xsd name: " + xsdPathWithoutDirs.substring(indexByXsdNamePrefix, indexByXsdNamePrefix + codeLength));
        System.out.println("xss name: " + codeByXslInfo);

        // TODO using firstSymbolIndex & codeLength extract IFNS code from xsd filename after confirming task details
    }
    private int getSecondIndexOf(String str, String separator) {
        return str.indexOf(separator, str.indexOf(separator) + 1);
    }
}
