package plug_checker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;
import plug_checker.checker.FileParser;

import static plug_checker.constants.Constants.fileStartTestAttribute;

class FileParserTest {

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
    public void testGetNodeIfExists() {
         Node check = mockParser.getNodeWithTestAttribute(fileStartTestAttribute);
         System.out.println(check.getNodeValue() + check.getNodeName());
    }
}
