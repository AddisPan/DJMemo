package com.example.commoneydjdjmemo; // 你的 package

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. 設定畫面 (現在這個 XML 裡面已經有輸入框跟按鈕了)
        setContentView(R.layout.activity_main);

        // 2. 綁定按鈕 (直接使用 findViewById)
        Button btnLogin = findViewById(R.id.btn_login);

        // 3. 設定點擊事件
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳轉到 HomeActivity (客廳)
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);

                // 關閉 MainActivity (警衛室)，這樣按返回鍵才不會又退回登入頁
                finish();
            }
        });
    }
}