package com.example.commoneydjdjmemo; // 記得確認 package 名稱

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.List;

// 1. 宣告這是一個 abstract (抽象) 類別，它繼承 Fragment，並且實作管家的廣播介面
public abstract class BaseFragment extends Fragment implements KeepDataRepository.OnDataChangeListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 2. 當 Fragment 被建立時，自動跟管家「註冊」聽廣播
        KeepDataRepository.getInstance().addListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 3. 當 Fragment 被銷毀時，自動跟管家「取消註冊」，避免記憶體外洩 (Memory Leak)
        KeepDataRepository.getInstance().removeListener(this);
    }

    // 4. 強制規定所有繼承的子類別，都必須實作這個方法來處理資料更新！
    @Override
    public abstract void onDataChanged(List<Memo> newList);
}