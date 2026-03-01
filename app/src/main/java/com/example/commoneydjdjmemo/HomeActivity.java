package com.example.commoneydjdjmemo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 角色: 主畫面 Activity (工作清單容器)
 * 
 * 責任:
 * - 作為 HomeFragment (清單頁) 與 EditorFragment (編輯頁) 的宿主容器。
 * - 負責 Fragment 的切換管理。
 * 
 * 需求對應:
 * - 符合需求 1：工作清單頁為一個 Activity，裡面放 Fragment。
 */
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 初始化沉浸式狀態列
        WindowUtils.enableImmersiveMode(this);

        // 第一次啟動時加載首頁 Fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }
    }
}
