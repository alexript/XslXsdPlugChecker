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
}
