package com.example.commoneydjdjmemo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 1. 綁定客廳的版面 (等一下第三步會去改它)
        setContentView(R.layout.activity_home);

        // 2. 開啟沉浸式模式
        WindowUtils.enableImmersiveMode(this);

        // 3. 把我們做好的 HomeFragment 塞進畫框裡！
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }
    }
}