package com.example.commoneydjdjmemo; // 你的 package

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView; // 匯入 RecyclerView

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvMemo; // 1. 宣告變數

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // 2. 設定 XML

        // 3. 綁定元件 (findViewById)
        rvMemo = findViewById(R.id.rv_memo);

        // 4. 準備假資料
        List<Memo> testData = new ArrayList<>();
        testData.add(new Memo("公司架構練習", "現在改用 findViewById 了", "2026/02/12"));
        testData.add(new Memo("Adapter", "ViewHolder 也要改寫", "2026/02/12"));
        testData.add(new Memo("成功了", "看到這行代表程式沒壞", "2026/02/12"));

        // 5. 設定 Adapter
        MemoAdapter adapter = new MemoAdapter(testData);

        // 6. 設定 RecyclerView
        rvMemo.setLayoutManager(new LinearLayoutManager(this));
        rvMemo.setAdapter(adapter);
    }
}