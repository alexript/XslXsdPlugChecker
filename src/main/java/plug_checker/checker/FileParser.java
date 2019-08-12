package plug_checker.checker;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.*;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

class FileParser {

    private NodeList nodeList;

    FileParser(String fileName) {
        try {
            File file = new File(fileName);

            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            if (doc.hasChildNodes()) {
                this.nodeList = doc.getChildNodes();
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    Node locateNodeByNameAttribute(String nodeName) {
        return locateNodeByNameAttribute(nodeName, this.nodeList);
    }

    private Node locateNodeByNameAttribute(String nodeName, NodeList nodeList) {
        for (int count = 0; count < nodeList.getLength(); count++) {
            Node tempNode = nodeList.item(count);
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                if (tempNode.hasAttributes()) {
                    NamedNodeMap nodeMap = tempNode.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++) {
                        Node node = nodeMap.item(i);
//                        System.out.println("attr name : " + node.getNodeName());
//                        System.out.println("attr value : " + node.getNodeValue());
                        if ("name".equals(node.getNodeName()) && node.getNodeValue().equals(nodeName)) {
                            System.out.println("Found!");
                            return tempNode;
                        }
                    }
                    if (tempNode.hasChildNodes()) {
                        Node recursiveResult = locateNodeByNameAttribute(nodeName, tempNode.getChildNodes());
                        if (recursiveResult != null) {
                            return recursiveResult;
                        }
                    }
                }
            }
        }
        return null;
    }
}
