package com.example.commoneydjdjmemo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 設定這個 Activity 使用 activity_home.xml 這個畫面
        setContentView(R.layout.activity_home);
    }
}