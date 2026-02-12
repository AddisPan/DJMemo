package com.example.commoneydjdjmemo; // 請確認你的 package 名稱

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LifecycleTestFragment extends Fragment {

    // 定義 TAG，方便等一下在 Logcat 搜尋
    private static final String TAG = "Lifecycle";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "🟢 onAttach (寄生開始)");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "🟢 onCreate (初始化)");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "🔵 onCreateView (準備畫面)");
        // 為了測試方便，我們直接回傳一個空的 View，不需要 layout xml
        return new View(getActivity());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "🔵 onViewCreated (畫面產生完畢 - 可以在這做事了)");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "🟡 onStart (可見)");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "🔴 onResume (可互動 - 活過來了!)");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "🔴 onPause (暫停 - 失去焦點)");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "🟡 onStop (不可見 - 進入後台)");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "🔵 onDestroyView (畫面銷毀 - 記得清空 Binding!)");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "🟢 onDestroy (死亡)");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "🟢 onDetach (脫離宿主)");
    }
}