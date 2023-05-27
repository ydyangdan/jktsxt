package com.example.jktx.common;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class XmlUtils {
    public static Map<String, String> parseXml(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream inputStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            Document document = builder.parse(inputStream);

            // 获取根节点
            Element root = document.getDocumentElement();

            // 遍历子节点，将节点名和节点值存储在Map中
            Map<String, String> resultMap = new HashMap<>();
            NodeList nodeList = root.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String nodeName = element.getNodeName();
                String nodeValue = element.getTextContent();
                resultMap.put(nodeName, nodeValue);
            }

            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

