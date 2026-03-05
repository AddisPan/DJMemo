package com.example.commoneydjdjmemo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 角色: 應用程式入口 Activity (登入容器)
 * 
 * 責任:
 * - 載入 LoginFragment 進行用戶驗證。
 * - 作為登入流程的宿主容器。
 * 
 * 需求對應:
 * - 符合需求 5：登入頁為一個 Activity，裡面放 Fragment。
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This is branch line!
        // 初始化時載入登入頁面
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.login_fragment_container, new LoginFragment())
                    .commit();
        }
    }
}
