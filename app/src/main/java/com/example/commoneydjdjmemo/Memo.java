package com.example.commoneydjdjmemo; // 記得確認這行要是你的 package

import java.io.Serializable;

// 1. 實作 Serializable (打勾✅)
public class Memo implements Serializable {

    // 2. 定義所有欄位 (打勾✅)
    private long id;            // 唯一識別碼 (資料庫用)
    private String time;        // 時間 (原本叫 date，改叫 time 比較通用)
    private String tag;         // 標籤 (例如：工作、生活)
    private String title;       // 標題
    private String content;     // 內容
    private boolean isCompleted;// 是否完成 (打勾✅)
    private boolean isSelected; // 是否選取 (刪除用) (打勾✅)

    // 建構子：快速產生一筆新筆記
    public Memo(String title, String content, String time) {
        // 這裡我們先用當下時間當作 id，確保每筆都不一樣
        this.id = System.currentTimeMillis();
        this.title = title;
        this.content = content;
        this.time = time;

        // 預設值
        this.tag = "一般";
        this.isCompleted = false;
        this.isSelected = false;
    }

    // 3. 生成 Getter / Setter (打勾✅)
    // 下面這些是讓外部存取資料用的

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