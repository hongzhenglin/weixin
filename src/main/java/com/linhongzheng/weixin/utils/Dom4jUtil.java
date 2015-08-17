package com.linhongzheng.weixin.utils;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
public class Dom4jUtil {
    public static List getChilds(Node srcNode) {
        List childNodes = null;
        if (srcNode instanceof Document) {
            childNodes = new ArrayList();
            childNodes.add(((Document) srcNode).getRootElement());
        } else if (srcNode instanceof Element) {
            childNodes = ((Element) srcNode).elements();
        } else {
            childNodes = Collections.emptyList();
        }
        return childNodes;
    }

    public static Element findParentElement(Element targetNode,
                                            String innerXpath) {
        Element p = targetNode.getParent();
        if (p == null) {
            return null;
        }
        if (p.getPath().equals(innerXpath)) {
            return p;
        } else {
            return findParentElement(p, innerXpath);
        }
    }

    public static Element getRootParent(Element e) {
        Element p;
        Element tmp;
        for (p = e; (tmp = p.getParent()) != null; p = tmp) {
        }
        return p;
    }

    public static Element createMultiParentRecur(Element targetElement,
                                                 String innerXPath) {
        String path = targetElement.getPath();
        String extraPath = innerXPath.substring(path.length() + 1, innerXPath
                .length());
        String paths[] = extraPath.split("/");
        Element currentElement = targetElement;
        for (int i = 0; i < paths.length; i++) {
            Element tmpElement = (Element) currentElement
                    .selectSingleNode(paths[i]);
            if (tmpElement == null) {
                tmpElement = currentElement.addElement(paths[i]);
            }
            currentElement = tmpElement;
        }

        return currentElement;
    }

    public static Element createNodeRecur(String xPath) {
        String paths[] = xPath.split("/");
        Element parentElement = null;
        Element currentElement = null;
        for (int i = 0; i < paths.length; i++) {
            if (StringUtils.isBlank(paths[i])) {
                continue;
            }
            currentElement = DocumentHelper.createElement(paths[i]);
            if (parentElement != null) {
                parentElement.add(currentElement);
            }
            parentElement = currentElement;
        }

        return currentElement;
    }

    public static Element createElement(String innerCode, String text) {
        int lastIndex = innerCode.lastIndexOf("/");
        Element e = null;
        if (lastIndex < 0) {
            e = DocumentHelper.createElement(innerCode);
        } else {
            e = createNodeRecur(innerCode);
        }
        e.setText(text);
        return e;
    }

    /**
     *
     * @param fileName
     * @return Dom4j的Document
     * @throws DocumentException
     */
    public static Document read(String fileName) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(fileName));
        return document;
    }

    /**
     * 格式化输出
     *
     * @param node
     * @return
     * @throws IOException
     */
    public static String formatPrettyString(Node node) throws IOException {
        StringWriter sw = new StringWriter();
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(sw, format);
        writer.write(node);
        writer.close();
        return sw.toString();
    }

    /**
     * 紧凑输出
     *
     * @param node
     * @return
     * @throws IOException
     */
    public static String formatCompactString(Node node) throws IOException {
        StringWriter sw = new StringWriter();
        OutputFormat format = OutputFormat.createCompactFormat();
        XMLWriter writer = new XMLWriter(sw, format);
        writer.write(node);
        writer.close();
        return sw.toString();
    }

    /**
     *
     * @param xml
     * @return
     * @throws Exception
     */
    public static Document parseText(String xml) throws Exception {

        try {
            return DocumentHelper.parseText(xml);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("parse xml text error", e);
        }

    }

    /**
     *
     * @param doc
     * @param path
     * @return
     * @throws Exception
     */
    public static List<Node> getNodes(Document doc, String path)
            throws Exception {
        return (List<Node>) doc.selectNodes(path);
    }

    /**
     *
     * @param doc
     * @param path
     * @return
     * @throws Exception
     */
    public static String getNodesText(Document doc, String path, String split)
            throws Exception {
        StringBuffer sb = new StringBuffer();
        List<Node> nodeList = getNodes(doc, path);

        if (nodeList == null || nodeList.size() == 0) {
            return null;
        }
        int index = 0;
        Iterator it = nodeList.iterator();
        while (it.hasNext()) {
            Node node = (Node) it.next();
            Element element = (Element) node;
            if (index > 0) {
                sb.append(split);
            }
            sb.append(element.getTextTrim());
            index++;
        }

        return sb.toString();

    }

    /**
     *
     * @param doc
     * @param path
     * @return
     * @throws Exception
     */
    public static Node getNode(Document doc, String path) throws Exception {
        return doc.selectSingleNode(path);
    }

    /**
     *
     * @param doc
     * @param path
     * @return
     * @throws Exception
     */
    public static String getNodeText(Document doc, String path)
            throws Exception {

        Node node = getNode(doc, path);

        if (node == null) {
            return null;
        }

        Element element = (Element) node;
        return element.getTextTrim();

    }

    /**
     *
     * @param node
     * @param attributeName
     * @return
     * @throws Exception
     */
    public static String getAttribute(Node node, String attributeName)
            throws Exception {
        Element element = (Element) node;
        return element.attributeValue(attributeName);

    }

}
