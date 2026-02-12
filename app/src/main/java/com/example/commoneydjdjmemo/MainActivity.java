package com.example.commoneydjdjmemo; // 你的 package name

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.commoneydjdjmemo.databinding.ActivityMainBinding; // 自動產生的類別

public class MainActivity extends AppCompatActivity {

    // 1. 宣告 Binding 變數
    // 命名規則：xml檔名 (activity_main) -> ActivityMainBinding
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // --- 核心邏輯：如果是第一次啟動，就載入 LoginFragment ---
//        if (savedInstanceState == null) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .add(R.id.fragment_container, new LoginFragment())
//                    .commit();
//        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new LifecycleTestFragment()) // 換成測試用的 Fragment
                    .commit();
        }
    }
}