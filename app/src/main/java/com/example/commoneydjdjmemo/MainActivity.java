package com.example.commoneydjdjmemo; // 你的 package

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. 設定 Layout (傳統寫法)
        setContentView(R.layout.activity_main);

        // 2. 載入 LoginFragment
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, new LoginFragment())
                    .commit();
        }
    }
}