package com.example.commoneydjdjmemo;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "JDAuth", strict = false) // strict=false 代表 XML 裡有我們沒寫到的欄位時，不要報錯
public class JDAuth {
    @Element(name = "AppID", required = false)
    public String appId;

    @Element(name = "UserID", required = false)
    public String userId;
}