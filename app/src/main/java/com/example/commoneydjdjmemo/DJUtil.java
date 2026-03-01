package com.example.commoneydjdjmemo;
import org.simpleframework.xml.core.Persister;

/**
 * 角色: XML 解析工具類
 * 
 * 責任:
 * - 封裝 Simple-XML 的 Persister，提供簡單的靜態方法將 XML 字串轉換為 Java 物件。
 * - 減少其他地方重複編寫解析器程式碼。
 * 
 * 業界標準:
 * - 使用泛型 <T> 確保解析結果的型別安全。
 */
public class DJUtil {
    /**
     * 將 XML 字串解析為指定類別的物件
     * @param xmlString 來源 XML 字串
     * @param clazz 目標類別 (例如 Memo.class)
     * @return 解析後的物件，失敗則回傳 null
     */
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
