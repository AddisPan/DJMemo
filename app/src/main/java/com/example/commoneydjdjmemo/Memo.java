package com.example.commoneydjdjmemo; // 記得確認這行要是你的 package

import java.io.Serializable;
import org.simpleframework.xml.Element; // 新增匯入
import org.simpleframework.xml.Root;    // 新增匯入

// 1. 加上 @Root 標籤，告訴 Simple-XML 這是一個 XML 節點
@Root(name = "Memo")
public class Memo implements Serializable {

    // 2. 每個想要存進 XML 的欄位，上面都要加 @Element(required = false)
    // required = false 代表如果讀取時沒這個欄位，也不要報錯閃退
    @Element(required = false)
    private long id;

    @Element(required = false)
    private String time;

    @Element(required = false)
    private String tag;

    @Element(required = false)
    private String title;

    @Element(required = false)
    private String content;

    @Element(required = false)
    private boolean isCompleted;

    @Element(required = false)
    private boolean isSelected;

    // =========================================================
    // 【超級重要新增】Simple-XML 專用的空建構子 (不可省略！)
    // =========================================================
    public Memo() {
    }

    // =========================================================
    // 原本你寫的建構子 (保留，給你在 EditorActivity 新增筆記時用)
    // =========================================================
    public Memo(String title, String content, String time) {
        this.id = System.currentTimeMillis();
        this.title = title;
        this.content = content;
        this.time = time;

        this.tag = "一般";
        this.isCompleted = false;
        this.isSelected = false;
    }

    // =========================================================
    // 3. 原本的 Getter / Setter (完全保留不動)
    // =========================================================
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }

    public boolean isSelected() { return isSelected; }
    public void setSelected(boolean selected) { isSelected = selected; }
}