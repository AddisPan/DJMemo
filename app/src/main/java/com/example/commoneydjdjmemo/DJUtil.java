package com.example.commoneydjdjmemo;
import org.simpleframework.xml.core.Persister;

public class DJUtil {
    // 將 XML 字串轉換為 Java 物件
    public static <T> T buildStringObj(String xmlString, Class<T> clazz) {
        Persister persister = new Persister();
        try {
            return persister.read(clazz, xmlString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}