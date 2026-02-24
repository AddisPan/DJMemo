package com.example.commoneydjdjmemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.List;

public class EditorActivity extends AppCompatActivity {

    // 🎯 1. 宣告元件：新增了 etTag(標籤), btnSaveXml(存XML), btnSaveSp(存SP)
    private EditText etTitle, etContent, etTag;
    private Button btnBack, btnSaveXml, btnSaveSp;

    // 記錄目前是第幾筆資料 (預設 -1 代表是按 + 號新增的)
    private int editPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor); // 綁定介面

        // 讓新增筆記的畫面也是沉浸式！
        WindowUtils.enableImmersiveMode(this);

        // 🎯 2. 綁定 ID (配合新版的 activity_editor.xml)
        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);
        etTag = findViewById(R.id.et_tag);             // 綁定標籤輸入框
        btnBack = findViewById(R.id.btn_back);
        btnSaveXml = findViewById(R.id.btn_save_xml);  // 綁定存 XML 按鈕
        btnSaveSp = findViewById(R.id.btn_save_sp);    // 綁定存 SP 按鈕

        // 3. 接收從 HomeActivity (包含 Adapter) 傳過來的包裹
        Intent intent = getIntent();
        String passedTitle = intent.getStringExtra("title");
        String passedContent = intent.getStringExtra("content");
        String passedTag = intent.getStringExtra("tag"); // 🎯 接收傳過來的標籤

        editPosition = intent.getIntExtra("memo_position", -1);

        // 4. 如果包裹有東西，就把文字填入 EditText
        if (passedTitle != null) etTitle.setText(passedTitle);
        if (passedContent != null) etContent.setText(passedContent);
        if (passedTag != null) etTag.setText(passedTag); // 🎯 填入舊標籤

        // 5. 設定「返回」按鈕
        btnBack.setOnClickListener(v -> finish());

        // ==========================================
        // 🎯 6. 按鈕 A：存入 XML (給首頁 RecyclerView 用)
        // ==========================================
        btnSaveXml.setOnClickListener(v -> {
            Memo memo = createMemoFromInput();
            List<Memo> currentList = FileUtils.readFromXML(EditorActivity.this);

            saveToList(currentList, memo); // 呼叫下方抽出來的共用儲存邏輯
            FileUtils.saveToXML(EditorActivity.this, currentList);

            Toast.makeText(EditorActivity.this, "已存入 XML", Toast.LENGTH_SHORT).show();
            finish();
        });

        // ==========================================
        // 🎯 7. 按鈕 B：存入 SP / JSON (給舊版列表用)
        // ==========================================
        btnSaveSp.setOnClickListener(v -> {
            Memo memo = createMemoFromInput();
            // 呼叫你寫好的 SPUtils！
            List<Memo> currentList = SPUtils.getMemoList(EditorActivity.this);

            saveToList(currentList, memo); // 呼叫下方抽出來的共用儲存邏輯
            SPUtils.saveMemoList(EditorActivity.this, currentList);

            Toast.makeText(EditorActivity.this, "已存入 SP (JSON)", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    /**
     * 🎯 小工具 1：獨立出來的 List 更新邏輯 (判斷是新增還是修改)
     */
    private void saveToList(List<Memo> list, Memo memo) {
        if (editPosition == -1) {
            list.add(0, memo); // 加到最前面
        } else {
            // 防呆機制：確保編輯的位置沒有超出範圍
            if (editPosition < list.size()) {
                list.set(editPosition, memo); // 替換舊資料
            } else {
                list.add(0, memo);
            }
        }
    }

    /**
     * 🎯 小工具 2：把畫面上輸入的文字打包成一個 Memo 物件
     */
    private Memo createMemoFromInput() {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        String tag = etTag.getText().toString(); // 🎯 取得畫面上輸入的標籤

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
        String currentDate = sdf.format(new Date());

        Memo memo = new Memo();
        memo.setTitle(title);
        memo.setContent(content);
        memo.setTag(tag); // 🎯 把標籤存入 Memo 物件
        memo.setTime(currentDate);

        return memo;
    }
}