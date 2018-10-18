package com.jsjf.common;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Iterator;

public class DomXMLEncodeUtils {
    public static String readStringXml(String xml,String key) throws DocumentException {
        Document doc = null;
        String result = "-99";
        doc = DocumentHelper.parseText(xml); // 将字符串转为XML
        Element rootElt = doc.getRootElement(); // 获取根节点
        Iterator iterss = rootElt.elementIterator(key); ///获取根节点下的子节点body
        // 遍历body节点
        while (iterss.hasNext()) {
            Element recordEless = (Element) iterss.next();
            result = recordEless.getStringValue(); // 拿到body节点下的子节点result值
        }
        return result;
    }
}
