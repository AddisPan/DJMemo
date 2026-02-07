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
        super.onCreate(savedInstanceState);

        // 2. 初始化 Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // 3. 設定畫面 (原本是 R.layout.activity_main，現在改成 binding.getRoot())
        setContentView(binding.getRoot());

        // --- 測試區 ---

        // 4. 設定按鈕點擊事件 (不用 findViewById，直接用 binding.元件ID)
        // 你的 ID 是 btn_login，這裡就會自動變成 btnLogin (駝峰式命名)
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取得輸入框文字
                String username = binding.etUsername.getText().toString();

                // 顯示 Toast 訊息
                Toast.makeText(MainActivity.this, "點擊登入！帳號：" + username, Toast.LENGTH_SHORT).show();
            }
        });
    }
}