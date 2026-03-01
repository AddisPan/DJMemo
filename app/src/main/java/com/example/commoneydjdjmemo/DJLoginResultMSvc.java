package com.example.commoneydjdjmemo;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 角色: 登入 API 回傳結果的 XML 映射物件 (POJO)
 * 
 * 責任:
 * - 定義登入結果 XML 的結構，包含 Result 根節點、MID 屬性以及嵌套的 JDAuth 物件。
 * - 用於 Simple-XML 庫的反序列化過程。
 * 
 * 需求對應:
 * - 展現 XML 解析能力，符合專案對不同資料格式處理的要求。
 */
@Root(name = "Result", strict = false)
public class DJLoginResultMSvc {
    @Attribute(name = "MID", required = false)
    public String mid;

    @Element(name = "JDAuth", required = false)
    public JDAuth jdAuth;
}
