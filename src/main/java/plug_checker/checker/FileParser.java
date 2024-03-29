package plug_checker.checker;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.*;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class FileParser {

    private NodeList nodeList;

    public FileParser(String fileName) {
        try {
            File file = new File(fileName);

            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            Document doc = dBuilder.parse(file); // Throws SAXParseException in case of broken schema, then NPE during parsing
            if (doc.hasChildNodes()) {
                this.nodeList = doc.getChildNodes();
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }


    public Node getNodeWithNameAttribute(String nodeName) {
        return getNodeIfExists(this.nodeList, nodeName, "name");
    }

    public Node getNodeWithSelectAttribute(String nodeName) {
        return getNodeIfExists(this.nodeList, nodeName, "select");
    }

    public Node getNodeWithTestAttribute(String nodeName) {
        return getNodeIfExists(this.nodeList, nodeName, "test");
    }
    private Node getNodeIfExists(NodeList nodeList, String nodeName, String attributeName) {
        Node resultNode = null;
        for (int count = 0; count < nodeList.getLength(); count++) {
            Node tempNode = nodeList.item(count);
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                if (tempNode.hasAttributes()) {
                    NamedNodeMap nodeMap = tempNode.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++) {
                        Node node = nodeMap.item(i);
                        if(attributeName.equals(node.getNodeName()) && node.getNodeValue().equals(nodeName)) {
                            resultNode = node;
                            break;
                        }
                    }
                }
                if (tempNode.hasChildNodes()) {
                    Node tempReturnNode = getNodeIfExists(tempNode.getChildNodes(), nodeName, attributeName);
                    if(tempReturnNode != null) {
                        resultNode = tempReturnNode;
                        break;
                    }
                }
            }
        }
        return resultNode;
    }

    public Node getParentNodeWithNameAttribute(String nodeName) {
        return getParentNodeIfExists(this.nodeList, nodeName, "name");
    }

    public Node getParentNodeWithIdAttribute(String nodeId) {
        return getParentNodeIfExists(this.nodeList, nodeId, "id");
    }

    public Node getParentNodeWithTestAttribute(String nodeId) {
        return getParentNodeIfExists(this.nodeList, nodeId, "test");
    }

    private Node getParentNodeIfExists(NodeList nodeList, String nodeName, String attributeName) {
        Node resultNode = null;
        for (int count = 0; count < nodeList.getLength(); count++) {
            Node tempNode = nodeList.item(count);
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                if (tempNode.hasAttributes()) {
                    NamedNodeMap nodeMap = tempNode.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++) {
                        Node node = nodeMap.item(i);
                        if(attributeName.equals(node.getNodeName()) && node.getNodeValue().equals(nodeName)) {
                            resultNode = tempNode;
                            break;
                        }
                    }
                }
                if (tempNode.hasChildNodes()) {
                    Node tempReturnNode = getParentNodeIfExists(tempNode.getChildNodes(), nodeName, attributeName);
                    if(tempReturnNode != null) {
                        resultNode = tempReturnNode;
                        break;
                    }
                }
            }
        }
        return resultNode;
    }

    public Node getNodeByName (String nodeName) {
        return getNodeByName(this.nodeList, nodeName);
    }

    private Node getNodeByName (NodeList nodeList, String nodeName) {
        Node resultNode = null;
        for (int count = 0; count < nodeList.getLength(); count++) {
            Node tempNode = nodeList.item(count);
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
               if(tempNode.getNodeName().equals(nodeName)) {
                   resultNode = tempNode;
               }
                if (tempNode.hasChildNodes()) {
                    Node tempReturnNode = getNodeByName(tempNode.getChildNodes(), nodeName);
                    if(tempReturnNode != null) {
                        resultNode = tempReturnNode;
                        break;
                    }
                }
            }
        }
        return resultNode;
    }

    public void printDocument(){
        printDocument(this.nodeList);
    }

    private void printDocument(NodeList nodeList) {
        for (int count = 0; count < nodeList.getLength(); count++) {
            Node tempNode = nodeList.item(count);
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
                System.out.println("Node Value =" + tempNode.getTextContent());
                if (tempNode.hasAttributes()) {
                    NamedNodeMap nodeMap = tempNode.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++) {
                        Node node = nodeMap.item(i);
                        System.out.println("attr name : " + node.getNodeName());
                        System.out.println("attr value : " + node.getNodeValue());
                    }
                }
                if (tempNode.hasChildNodes()) {
                    printDocument(tempNode.getChildNodes());
                }
                System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
            }

        }

    }
}
