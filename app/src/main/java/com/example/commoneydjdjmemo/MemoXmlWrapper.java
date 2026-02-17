package com.example.commoneydjdjmemo;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import java.util.List;

@Root(name = "MemoData")
public class MemoXmlWrapper {
    @ElementList(inline = true, required = false)
    public List<Memo> memoList;

    public MemoXmlWrapper() {} // 必須要有空建構子
    public MemoXmlWrapper(List<Memo> list) { this.memoList = list; }
}