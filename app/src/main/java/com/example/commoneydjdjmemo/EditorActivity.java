package com.example.commoneydjdjmemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.List;

public class EditorActivity extends AppCompatActivity {

    // 宣告元件
    private EditText etTitle, etContent;
    private Button btnBack, btnSave;

    // 記錄目前是第幾筆資料 (預設 -1 代表是按 + 號新增的)
    private int editPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor); // 綁定介面

        // 🎯 讓新增筆記的畫面也是沉浸式！
        WindowUtils.enableImmersiveMode(this);

        // 1. 綁定 ID
        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);
        btnBack = findViewById(R.id.btn_back);
        btnSave = findViewById(R.id.btn_save);

        // 2. 接收從 HomeActivity (包含 Adapter) 傳過來的包裹
        Intent intent = getIntent();

        // 統一使用小寫的 key 來接收資料 (對齊 MemoAdapter 裡的設定)
        String passedTitle = intent.getStringExtra("title");
        String passedContent = intent.getStringExtra("content");

        // 接收 position，如果沒收到 (代表是按+號進來的) 就預設為 -1
        editPosition = intent.getIntExtra("memo_position", -1);

        // 3. 如果包裹有東西 (代表是點舊筆記進來的)，就把文字填入 EditText
        if (passedTitle != null) {
            etTitle.setText(passedTitle);
        }
        if (passedContent != null) {
            etContent.setText(passedContent);
        }

        // ==========================================
        // 4. 設定「返回」按鈕的點擊事件
        // ==========================================
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 直接關閉此 Activity
            }
        });

        // ==========================================
        // 5. 設定「儲存筆記」按鈕的點擊事件 (支援新增與修改)
        // ==========================================
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取得輸入內容並建立 Memo 物件
                Memo memo = createMemoFromInput();

                // 讀取現有的清單
                List<Memo> currentList = FileUtils.readFromXML(EditorActivity.this);

                // 判斷要「新增」還是「替換」
                if (editPosition == -1) {
                    currentList.add(0, memo); // 加到最前面
                } else {
                    currentList.set(editPosition, memo); // 替換舊資料
                }

                // 存回 XML 並關閉頁面
                FileUtils.saveToXML(EditorActivity.this, currentList);
                android.widget.Toast.makeText(EditorActivity.this, "筆記已儲存", android.widget.Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    /**
     * 這是一個小工具方法：把畫面上輸入的文字打包成一個 Memo 物件
     * 這樣我們就不用在儲存按鈕裡面重複寫一樣的程式碼了！
     */
    private Memo createMemoFromInput() {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();

        // 🎯 重構重點：動態取得當下時間
        // 格式範例：2026/02/19 10:30
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
        String currentDate = sdf.format(new Date());

        Memo memo = new Memo();
        memo.setTitle(title);
        memo.setContent(content);
        memo.setTime(currentDate); // 使用動態時間！

        return memo;
    }
}