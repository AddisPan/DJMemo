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
                // 1. 取得輸入框文字 (去除前後空白)
                String username = binding.etUsername.getText().toString().trim();
                String password = binding.etPassword.getText().toString().trim();

                // 2. 檢查是否空白 (防呆機制)
                if (username.isEmpty()) {
                    binding.etUsername.setError("請輸入帳號");
                    return; // 中斷程式，不往下跑
                }
                if (password.isEmpty()) {
                    binding.etPassword.setError("請輸入密碼");
                    return;
                }

                // 3. 驗證帳號密碼 (模擬後端驗證)
                if (username.equals("admin") && password.equals("1234")) {
                    Toast.makeText(MainActivity.this, "登入成功！", Toast.LENGTH_SHORT).show();

                    // --- 新增這三行 ---
                    // 1. 建立意圖：從 這裡(this) 去 HomeActivity
                    android.content.Intent intent = new android.content.Intent(MainActivity.this, HomeActivity.class);

                    // 2. 出發！
                    startActivity(intent);

                    // 3. 關門 (把登入頁關掉，這樣按上一頁就會直接離開 App，不會回到登入頁)
                    finish();
                    // ----------------
                } else {
                    // 登入失敗
                    Toast.makeText(MainActivity.this, "帳號或密碼錯誤", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}