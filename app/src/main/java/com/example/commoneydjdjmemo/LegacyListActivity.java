package com.example.commoneydjdjmemo;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class LegacyListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legacy_list);

        // 1. 綁定 ListView 元件
        ListView listView = findViewById(R.id.list_view_legacy);

        // 2. ★ 讀取 SP (JSON) 裡的資料 ★
        List<Memo> spDataList = SPUtils.getMemoList(this);

        // 3. 建立並設定 Adapter
        MemoBaseAdapter adapter = new MemoBaseAdapter(this, spDataList);
        listView.setAdapter(adapter);
    }
}