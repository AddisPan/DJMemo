package com.example.commoneydjdjmemo;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeFragment extends BaseFragment {

    // 1. 從 HomeActivity 搬過來的變數宣告
    private RecyclerView rvMemo;
    private MemoAdapter adapter;
    private List<Memo> memoList = new ArrayList<>();

    public HomeFragment() {
        // Fragment 必須保留一個空的建構子
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 2. 先把畫布充氣 (Inflate) 出來，存成 view
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 3. 綁定元件 (使用你真實的 ID，並且前面加上 "view.")
        rvMemo = view.findViewById(R.id.rv_memo);
        Button btnAddNote = view.findViewById(R.id.btn_add_note);
        Button btnGoLegacy = view.findViewById(R.id.btn_go_legacy);
        Button btnDeleteSelected = view.findViewById(R.id.btn_delete_selected);
        androidx.appcompat.widget.SearchView searchView = view.findViewById(R.id.search_view);

        // 1. 透過內建的 ID，把 SearchView 裡面的「真正輸入框」挖出來
        android.widget.EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);

        // 2. 把使用者打進去的字體顏色改成「純黑色」 (超明顯)
        searchEditText.setTextColor(android.graphics.Color.BLACK);

        // 3. 把「搜尋標題或內容...」這個提示字的顏色，改成「較深的灰色」 (避免太透明)
        searchEditText.setHintTextColor(android.graphics.Color.parseColor("#757575")); // 你也可以換成你喜歡的色碼

        // 4. 設定 RecyclerView (管家連線版)
        rvMemo.setLayoutManager(new LinearLayoutManager(requireContext()));

        // 🌟 步驟 3-1: 向管家註冊，表示 HomeFragment 想收聽廣播
        KeepDataRepository.getInstance().addListener(this);

        // 🌟 步驟 3-2: 先拿一個空的 List 給 Adapter 初始化
        adapter = new MemoAdapter(new ArrayList<>());
        rvMemo.setAdapter(adapter);

        // 🌟 步驟 3-3: 呼叫管家去讀取資料 (管家讀完會自動觸發最下面的 onDataChanged！)
        KeepDataRepository.getInstance().loadData(requireContext());

        // ==========================================
        // 下一步：我們要開始把點擊事件搬進來這裡！
        // ==========================================


        // 6. 設定點擊事件：跳轉到 EditorActivity
        btnAddNote.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new EditorFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // ========================================================
        // 👇 嘉實 API Gson 測試代碼 (繼續保留在背景幫我們測試) 👇
        // ========================================================
        String mockJson = "{\n" +
                "\"data\": {\n" +
                "\"Glynn\": {\"gender\": \"M\", \"age\": 45, \"likeCat\": false},\n" +
                "\"Cindy\": {\"gender\": \"F\", \"age\": 36, \"likeCat\": true},\n" +
                "\"Morris\": {\"gender\": \"M\", \"age\": 40, \"likeCat\": false},\n" +
                "\"Robert\": {\"gender\": \"M\", \"age\": 41, \"likeCat\": true}\n" +
                "}\n" +
                "}";

        Map<String, UserData> parsedData = JsonObjectUtility.readJsonStringToObject(mockJson);

        if (parsedData != null) {
            for (Map.Entry<String, UserData> entry : parsedData.entrySet()) {
                String name = entry.getKey();
                UserData userData = entry.getValue();
                android.util.Log.d("GsonTest", "解析成功 -> 名字: " + name + ", 資料: " + userData.toString());
            }
        } else {
            android.util.Log.e("GsonTest", "解析失敗，資料為空！");
        }

        // 綁定剛剛新增的跳轉按鈕
        btnGoLegacy.setOnClickListener(v -> {
            // 把畫框裡的 HomeFragment 替換成 LegacyListFragment
            // addToBackStack(null) 讓你可以按手機返回鍵回到首頁！
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new LegacyListFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // 2. 設定點擊事件
        btnDeleteSelected.setOnClickListener(v -> {
            boolean hasDeleted = false; // 用來記錄這次到底有沒有刪除東西

            // 🚨 【超級重點】避坑指南：倒序迴圈！
            // 為什麼要從最後一個 (size - 1) 往前檢查到 0？
            // 因為如果從前面刪除，List 的長度會立刻縮水，後面的項目會往前遞補，
            // 這時迴圈的 Index 就會大亂，甚至導致程式崩潰 (Crash)！
            for (int i = memoList.size() - 1; i >= 0; i--) {
                Memo currentMemo = memoList.get(i);

                if (currentMemo.isSelected()) {
                    memoList.remove(i); // 從清單中殺掉它！
                    hasDeleted = true;  // 標記我們有刪除動作
                }
            }

            // 3. 如果真的有刪除東西，就進行存檔與畫面更新
            if (hasDeleted) {
                // (1) 把最新的 (已經移除了項目的) 清單，重新存進 XML 裡覆蓋舊的
                FileUtils.saveToXML(requireContext(), memoList);

                adapter.updateList(memoList); // 一樣，呼叫神奇同步方法確保備份清單也被更新

                // (3) 跳出小提示
                android.widget.Toast.makeText(requireContext(), "刪除成功！", android.widget.Toast.LENGTH_SHORT).show();
            } else {
                // 如果使用者什麼都沒勾就按刪除
                android.widget.Toast.makeText(requireContext(), "請先勾選要刪除的筆記", android.widget.Toast.LENGTH_SHORT).show();
            }
        });

        // ==========================================
        // 🎯 綁定 SearchView 搜尋功能
        // ==========================================

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // 按下鍵盤的「搜尋」鍵時要做的事 (我們不需要，因為我們邊打字邊搜)
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 當搜尋框的文字改變時，立刻叫 Adapter 去過濾資料！
                if (adapter != null) {
                    adapter.getFilter().filter(newText);
                }
                return true;
            }
        });

        // 5. 記得最後是回傳 view
        return view;
    }

    // ========================================================
    // onResume 方法 (搬過來，並套用心法微調)
    // ========================================================
    @Override
    public void onResume() {
        super.onResume();
        // 既然有管家了，就不用自己去讀 XML 檔案啦！
        // 確保每次從編輯頁退回來時，直接跟管家拿「最新的帳本」來刷新畫面
        if (adapter != null && KeepDataRepository.getInstance().getMemoList() != null) {
            adapter.updateList(KeepDataRepository.getInstance().getMemoList());
        }
    }

    @Override
    public void onDataChanged(List<Memo> newList) {
        // 當收到管家廣播時，直接把新資料交給 Adapter 去刷新畫面！
        if (adapter != null) {
            adapter.updateList(newList);
        }
    }

    // ========================================================
    // 🌟 步驟 4: 畫面銷毀時，取消註冊廣播
    // ========================================================
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 退出廣播群組，減輕管家的負擔
        KeepDataRepository.getInstance().removeListener(this);
    }
}