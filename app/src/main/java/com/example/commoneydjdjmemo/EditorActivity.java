package com.example.commoneydjdjmemo; // 請確認這是你的 package 名稱

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditorActivity extends AppCompatActivity {

    // 宣告元件
    private EditText etTitle, etTag, etContent;
    private Button btnSaveSp, btnSaveFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor); // 綁定剛畫好的介面

        // 1. 綁定 ID
        etTitle = findViewById(R.id.et_title);
        etTag = findViewById(R.id.et_tag);
        etContent = findViewById(R.id.et_content);
        btnSaveSp = findViewById(R.id.btn_save_sp);
        btnSaveFile = findViewById(R.id.btn_save_file);

        // 🎯 接收從 HomeActivity (透過 Adapter) 傳過來的包裹
        Intent intent = getIntent();
        String passedTitle = intent.getStringExtra("EDIT_TITLE");
        String passedContent = intent.getStringExtra("EDIT_CONTENT");

        // 如果包裹不是空的 (代表是點擊列表進來的，不是按右下角 + 號進來的)
        // 就把文字直接填入 EditText 中
        if (passedTitle != null) {
            etTitle.setText(passedTitle);
        }
        if (passedContent != null) {
            etContent.setText(passedContent);
        }

        // 2. 設定「儲存 (SP)」按鈕的點擊事件
        btnSaveSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. 取得使用者輸入的標題與內容
                String title = etTitle.getText().toString();
                String content = etContent.getText().toString();

                // (你可以先寫死今天的日期)
                String date = "2026/02/16";

                // 2. 把輸入的內容包裝成一個 Memo 物件
                Memo newMemo = new Memo(title, content, date);

                // 3. 從 SP 拿出「舊的筆記清單」
                java.util.List<Memo> currentList = SPUtils.getMemoList(EditorActivity.this);

                // 4. 把「新的筆記」加進去清單裡
                currentList.add(newMemo);

                // 5. 把更新後的清單，重新存回 SP 裡面！
                SPUtils.saveMemoList(EditorActivity.this, currentList);

                // 6. 存檔完畢，關閉這個頁面回到首頁
                finish();
            }
        });

        // 3. 設定「儲存 (File)」按鈕的點擊事件
        btnSaveFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 未來這裡要寫 File 檔案的儲存邏輯
                Toast.makeText(EditorActivity.this, "點擊了儲存 (File)", Toast.LENGTH_SHORT).show();

                // 一樣關閉頁面返回
                finish();
            }
        });
    }
}