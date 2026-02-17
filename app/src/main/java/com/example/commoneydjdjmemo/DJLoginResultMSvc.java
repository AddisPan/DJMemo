package com.example.commoneydjdjmemo;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Result", strict = false)
public class DJLoginResultMSvc {
    @Attribute(name = "MID", required = false) // MID 是屬性 (<Result MID='108'>)
    public String mid;

    @Element(name = "JDAuth", required = false) // JDAuth 是裡面的節點
    public JDAuth jdAuth;
}