package com.example.commoneydjdjmemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class EditorActivity extends AppCompatActivity {

    // 宣告元件
    private EditText etTitle, etTag, etContent;
    private Button btnSaveSp, btnSaveFile;

    // 記錄目前是第幾筆資料 (預設 -1 代表是按 + 號新增的)
    private int editPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor); // 綁定介面

        // 1. 綁定 ID
        etTitle = findViewById(R.id.et_title);
        etTag = findViewById(R.id.et_tag);
        etContent = findViewById(R.id.et_content);
        btnSaveSp = findViewById(R.id.btn_save_sp);
        btnSaveFile = findViewById(R.id.btn_save_file);

        // 2. 接收從 HomeActivity (包含 Adapter) 傳過來的包裹
        Intent intent = getIntent();

        // 統一使用小寫的 key 來接收資料 (對齊 MemoAdapter 裡的設定)
        String passedTitle = intent.getStringExtra("title");
        String passedTag = intent.getStringExtra("tag");
        String passedContent = intent.getStringExtra("content");

        // 接收 position，如果沒收到 (代表是按+號進來的) 就預設為 -1
        editPosition = intent.getIntExtra("memo_position", -1);

        // 3. 如果包裹有東西 (代表是點舊筆記進來的)，就把文字填入 EditText
        if (passedTitle != null) {
            etTitle.setText(passedTitle);
        }
        if (passedTag != null) {
            etTag.setText(passedTag);
        }
        if (passedContent != null) {
            etContent.setText(passedContent);
        }

        // ==========================================
        // 4. 設定「儲存 (SP)」按鈕的點擊事件 (支援新增與修改)
        // ==========================================
        btnSaveSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取得輸入內容並建立 Memo 物件
                Memo memo = createMemoFromInput();

                // 讀取現有的 SP 清單
                List<Memo> currentList = SPUtils.getMemoList(EditorActivity.this);

                // 判斷要「新增」還是「替換」
                if (editPosition == -1) {
                    currentList.add(0, memo); // 加到最前面
                } else {
                    currentList.set(editPosition, memo); // 替換舊資料
                }

                // 存回 SP 並關閉頁面
                SPUtils.saveMemoList(EditorActivity.this, currentList);
                finish();
            }
        });

        // ==========================================
        // 5. 設定「儲存 (File)」按鈕的點擊事件 (支援新增與修改)
        // ==========================================
        btnSaveFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取得輸入內容並建立 Memo 物件
                Memo memo = createMemoFromInput();

                // 讀取現有的 File 清單
                List<Memo> currentList = FileUtils.readFromXML(EditorActivity.this);

                // 判斷要「新增」還是「替換」
                if (editPosition == -1) {
                    currentList.add(0, memo); // 加到最前面
                } else {
                    currentList.set(editPosition, memo); // 替換舊資料
                }

                // 存回 XML 並關閉頁面
                FileUtils.saveToXML(EditorActivity.this, currentList);
                finish();
            }
        });
    }

    /**
     * 這是一個小工具方法：把畫面上輸入的文字打包成一個 Memo 物件
     * 這樣我們就不用在 SP 和 File 的按鈕裡面重複寫兩次一樣的程式碼了！
     */
    private Memo createMemoFromInput() {
        String title = etTitle.getText().toString();
        String tag = etTag.getText().toString();
        String content = etContent.getText().toString();

        Memo memo = new Memo();
        memo.setTitle(title);
        memo.setTag(tag);
        memo.setContent(content);
        memo.setTime("2026/02/18"); // 今天的日期
        return memo;
    }
}