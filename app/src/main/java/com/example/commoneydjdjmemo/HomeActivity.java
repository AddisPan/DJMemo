package com.example.commoneydjdjmemo; // 你的 package

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvMemo; // 1. 宣告變數
    private MemoAdapter adapter; // 新增：把 adapter 宣告在這裡，這樣下面的 onResume 才找得到它
    private List<Memo> memoList = new ArrayList<>(); // 新增：用來裝「真實資料」的空清單

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // 2. 設定 XML

        // 3. 綁定元件 (findViewById)
        rvMemo = findViewById(R.id.rv_memo);

        // 4. 設定 RecyclerView (注意：這裡我們直接把空的 memoList 塞給 Adapter)
        rvMemo.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MemoAdapter(memoList);
        rvMemo.setAdapter(adapter);

        // 5. 綁定剛剛新增的按鈕
        Button btnAddNote = findViewById(R.id.btn_add_note);

        // 6. 設定點擊事件：跳轉到 EditorActivity
        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 從 HomeActivity 跳轉到 EditorActivity
                Intent intent = new Intent(HomeActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        // ========================================================
        // 👇 嘉實 API Gson 測試代碼 (繼續保留在背景幫我們測試) 👇
        // ========================================================
        String mockJson = "{\n" +
                "\"data\": {\n" +
                "\"Glynn\": {\"gender\": \"M\", \"age\": 45, \"likeCat\": false},\n" +
                "\"Cindy\": {\"gender\": \"F\", \"age\": 36, \"likeCat\": true},\n" +
                "\"Morris\": {\"gender\": \"M\", \"age\": 40, \"likeCat\": false},\n" +
                "\"Robert\": {\"gender\": \"M\", \"age\": 41, \"likeCat\": true}\n" +
                "}\n" +
                "}";

        Map<String, UserData> parsedData = JsonObjectUtility.readJsonStringToObject(mockJson);

        if (parsedData != null) {
            for (Map.Entry<String, UserData> entry : parsedData.entrySet()) {
                String name = entry.getKey();
                UserData userData = entry.getValue();
                android.util.Log.d("GsonTest", "解析成功 -> 名字: " + name + ", 資料: " + userData.toString());
            }
        } else {
            android.util.Log.e("GsonTest", "解析失敗，資料為空！");
        }

        // 綁定剛剛新增的跳轉按鈕
        Button btnGoLegacy = findViewById(R.id.btn_go_legacy);
        btnGoLegacy.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, LegacyListActivity.class);
            startActivity(intent);
        });
    } // <--- 這裡是 onCreate 結束的大括號！

    // ========================================================
    // 👇 新增 onResume 方法 (加在 onCreate 的大括號外面！) 👇
    // ========================================================
    @Override
    protected void onResume() {
        super.onResume();
        // 確保你的 memoList 和 adapter 已經在 onCreate 裡面初始化過了
        if (memoList != null && adapter != null) {

            // 1. 把看守員(Adapter)正在盯著的箱子「清空」
            memoList.clear();

            // 2. 去檔案裡把最新的資料讀出來，並「全部倒進」這個箱子裡
            memoList.addAll(FileUtils.readFromXML(this));

            // 3. 拍拍看守員的肩膀，跟他說：「箱子裡的東西換囉，請重新整理畫面！」
            adapter.notifyDataSetChanged();
        }
    }
}