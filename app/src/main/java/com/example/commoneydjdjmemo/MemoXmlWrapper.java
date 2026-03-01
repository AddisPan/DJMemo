package com.example.commoneydjdjmemo;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import java.util.List;

/**
 * 角色: XML 序列化/反序列化的包裝類別 (Wrapper POJO)
 *
 * 責任:
 * - 由於 Simple-XML 庫無法直接將 List 作為 XML 的根節點，此類別提供一個帶有 @Root 註解的容器。
 * - 包含一個 @ElementList 標籤，用於存放 Memo 物件清單。
 *
 * 需求對應:
 * - 支援備忘錄清單的 XML 持久化儲存 (雖然 FileUtils 使用 XmlSerializer 手動處理，此類別作為 Simple-XML 的實作參考)。
 */
@Root(name = "MemoData")
public class MemoXmlWrapper {
    @ElementList(inline = true, required = false)
    public List<Memo> memoList;

    public MemoXmlWrapper() {} // 必須要有空建構子，供 Simple-XML 反序列化使用
    public MemoXmlWrapper(List<Memo> list) { this.memoList = list; }
}
