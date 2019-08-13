package plug_checker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import plug_checker.checker.FileParser;

import static plug_checker.constants.Constants.invalidMsgAttribute;

class FileParserTest {

    private static FileParser xslMockParser;
    private static FileParser xsdMockParser;

    @BeforeAll
    static void setUpClass() {
        xslMockParser = new FileParser("D:/demo.xsl");
        xsdMockParser = new FileParser("D:/demo.xsd");
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
    public void testXsdRead(){
        xslMockParser.printDocument();
    }

    @Test
    public void testGetNodeIfExists() {
        Node check = xslMockParser.getNodeWithSelectAttribute(invalidMsgAttribute);
        System.out.println(check.getNodeValue() + check.getNodeName());
    }

    @Test
    public void testGetParentNodeIfExists() {
        Node check = xslMockParser.getParentNodeWithNameAttribute(invalidMsgAttribute);
        if (check.hasAttributes()) {
            NamedNodeMap nodeMap = check.getAttributes();
            for (int i = 0; i < nodeMap.getLength(); i++) {
                Node node = nodeMap.item(i);
                System.out.println(node.getNodeValue() + node.getNodeName());
            }
        }
    }
}
