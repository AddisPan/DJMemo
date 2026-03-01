package com.example.commoneydjdjmemo;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 角色: 登入結果 XML 嵌套物件 (POJO)
 *
 * 責任:
 * - 映射 XML 中 JDAuth 標籤內的屬性 (AppID, UserID)。
 * - 展示 Simple-XML 的嵌套解析能力。
 */
@Root(name = "JDAuth", strict = false)
public class JDAuth {
    @Element(name = "AppID", required = false)
    public String appId;

    @Element(name = "UserID", required = false)
    public String userId;
}
