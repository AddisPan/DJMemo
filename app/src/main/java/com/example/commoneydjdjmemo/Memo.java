package com.example.commoneydjdjmemo; // 記得確認這行要是你的 package

import java.io.Serializable;
import org.simpleframework.xml.Element; // 新增匯入
import org.simpleframework.xml.Root;    // 新增匯入

/*備忘錄的資料模型物件
 *
 * 用途:
 * - 保存一筆備忘錄的所有欄位: id, time, title, tag, content, isCompleted, isSelected
 * - 使用 Simple-XML 庫來序列化/反序列化 (把物件 ↔ XML 互轉)
 *
 * 需求對應:
 * - 實現「資料內容」需求 (時間、標籤、標題、內容、完成狀態)
 * - 被 EditorFragment 和 KeepDataRepository 在新增/編輯/刪除時使用
 *
 * 重要筆記:
 * - Simple-XML 反序列化時需要空建構子
 * - isSelected 只是 UI 狀態，用於複選刪除，不應被存進 XML (可選)
 */
@Root(name = "Memo")
public class Memo implements Serializable {
    // 欄位宣告 - 每個都要加 @Element。required = fals 如果 XML 讀取時沒這個欄位，不要報錯，直接跳過
    // 這樣可以向後相容 (舊版本的 XML 可能沒有某些新欄位)

    @Element(required = false)
    private long id;

    @Element(required = false)
    private String time;

    @Element(required = false)
    private String title;

    @Element(required = false)
    private String tag;

    @Element(required = false)
    private String content;

    @Element(required = false)
    private boolean isCompleted;

    @Element(required = false)
    private boolean isSelected;


    // 空建構子 (Simple-XML 必須要)
    public Memo() {
    }

    // 帶參數的建構子 (新增筆記時用)
    public Memo(String title, String tag, String content, String time) {
        // 用系統時間戳生成唯一 ID
        this.id = System.currentTimeMillis();
        this.title = title;
        this.tag = tag;
        this.content = content;
        this.time = time;

        // 新建的備忘錄預設「未完成」
        this.isCompleted = false;
        // 新建的備忘錄預設「未勾選」
        this.isSelected = false;
    }

    // Getter / Setter 方法
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }

    public boolean isSelected() { return isSelected; }
    public void setSelected(boolean selected) { isSelected = selected; }
}