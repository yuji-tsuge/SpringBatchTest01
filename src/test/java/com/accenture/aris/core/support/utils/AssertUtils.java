package com.accenture.aris.core.support.utils;

import java.beans.PropertyDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.AssertionFailedError;
import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.ReaderConfig;
import net.sf.jxls.reader.XLSReader;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AssertUtils {
    
    public static Map<String, List<?>> getDataPermitNull(String xml, String xls) throws FileNotFoundException, InvalidFormatException, IOException, ParserConfigurationException, SAXException{
    	// Skip errors to get null values
    	ReaderConfig.getInstance().setSkipErrors(true);
        return getData(xml, xls);
    }
    
    public static Map<String, List<?>> getData(String xml, String xls) throws FileNotFoundException, InvalidFormatException, IOException, ParserConfigurationException, SAXException{
        
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Map<String, List<?>> map = getMap(loader.getResourceAsStream(xml));
        XLSReader reader = ReaderBuilder.buildFromXML(loader.getResourceAsStream(xml));
        reader.read(loader.getResourceAsStream(xls), map);
        return map;
    }
    
    private static Map<String, List<?>> getMap(InputStream xml) throws IOException, ParserConfigurationException, SAXException {
        Map<String, List<?>> map = new HashMap<String, List<?>>();
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xml);
        NodeList nodeList = document.getElementsByTagName("loop");
        for(int i = 0; i < nodeList.getLength(); i++) {
            NamedNodeMap nodeMap = nodeList.item(i).getAttributes();
            if(nodeMap != null && nodeMap.getNamedItem("items") != null) {
                map.put(nodeMap.getNamedItem("items").getNodeValue(), new ArrayList<Object>());
            }
        }
        return map; 
    }
    
    public static void assertEquals(List<?> expected, List<?> actual) {
        if(expected.size() != actual.size()) {
            assert(false);
            return;
        }
        for(int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }
    
    public static void assertEquals(Object expected, Object actual) {
        
        PropertyDescriptor[] pd = BeanUtilsBean2.getInstance().getPropertyUtils().getPropertyDescriptors(expected);
        for(int i = 0; i < pd.length; i++) {
            if(!"class".equals(pd[i].getName())) {
                try {
                    Object base = BeanUtilsBean2.getInstance().getPropertyUtils().getSimpleProperty(expected, pd[i].getName());
                    Object trgt = BeanUtilsBean2.getInstance().getPropertyUtils().getSimpleProperty(actual, pd[i].getName());
                    Assert.assertEquals(base, trgt);
                } catch(Exception e) {
                    throw new AssertionFailedError();
                }
            }
        }
    }
}