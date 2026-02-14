package com.example.commoneydjdjmemo; // 請確認這是你的 package 名稱

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

        // 2. 設定「儲存 (SP)」按鈕的點擊事件
        btnSaveSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 未來這裡要寫 SharedPreferences 的儲存邏輯
                Toast.makeText(EditorActivity.this, "點擊了儲存 (SP)", Toast.LENGTH_SHORT).show();

                // 儲存完畢後，關閉這一頁，就會自動回到 HomeActivity 列表頁
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