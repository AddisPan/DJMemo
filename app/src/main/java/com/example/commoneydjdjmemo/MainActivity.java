package com.example.commoneydjdjmemo; // 記得保留你最上面的 package 宣告喔！

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. 設定畫面 (現在這個 XML 裡面只剩下一個空畫框 FragmentContainerView)
        setContentView(R.layout.activity_main);

        // 2. 判斷如果是第一次開啟，就把 LoginFragment 塞進畫框裡！
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.login_fragment_container, new LoginFragment())
                    .commit();
        }
    }
}