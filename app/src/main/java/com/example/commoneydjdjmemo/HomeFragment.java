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

/* 角色: 使用 RecyclerView 顯示備忘錄清單的 Fragment
 *
 * 責任:
 * - 初始化 RecyclerView 和它的 Adapter
 * - 向 KeepDataRepository 註冊監聽，接收資料更新廣播
 * - 提供 UI 互動: 新增筆記、複選刪除、搜尋、切換到 ListView
 *
 * 需求對應:
 * - 實現「工作清單頁為一個 Activity，裡面放 Fragment，分別使用 ListView 及 RecyclerView」
 *   透過 RecyclerView 版本展現
 * - 支援複選刪除 (isSelected) 和搜尋功能 (SearchView + Filter)
 *
 * 生命週期重點:
 * - Fragment 向 KeepDataRepository 註冊監聽
 * - 一定要在 onDestroyView 時呼叫 removeListener()，避免記憶體洩漏
 */
public class HomeFragment extends BaseFragment {
    // rvMemo: RecyclerView 控制項，用來顯示備忘錄清單
    // adapter: Adapter，負責把 Memo 物件轉成 UI 顯示
    // memoList: 備忘錄清單 (實際上很少直接用，通常透過 Repository 和 Adapter)

    private RecyclerView rvMemo;
    private MemoAdapter adapter;
    private List<Memo> memoList = new ArrayList<>();

    //空建構子 (Fragment 必須)。Fragment 在被系統銷毀重建時，會用反射呼叫空建構子。如果沒有空建構子，重建時會 crash

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Step 1: 畫布充氣 (Inflate Layout)。把 fragment_home.xml 這個佈局文件轉成 View 物件。這是 Fragment 建立 UI 的第一步
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Step 2: 綁定 UI 控制項 (findViewById)。注意：要前加 view.，因為這些控制項在 view 裡面
        rvMemo = view.findViewById(R.id.rv_memo);
        Button btnAddNote = view.findViewById(R.id.btn_add_note);
        Button btnGoLegacy = view.findViewById(R.id.btn_go_legacy);
        Button btnDeleteSelected = view.findViewById(R.id.btn_delete_selected);
        androidx.appcompat.widget.SearchView searchView = view.findViewById(R.id.search_view);

        // Step 3: 設定 SearchView 樣式 (美化搜尋框)
        android.widget.EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(android.graphics.Color.BLACK);
        searchEditText.setHintTextColor(android.graphics.Color.parseColor("#757575"));

        // Step 4: 設定 RecyclerView (列表的佈局管理)
        rvMemo.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Step 5: 向 Repository 註冊監聽 (Observer 模式)
        KeepDataRepository.getInstance().addListener(this);

        // Step 6: 建立 Adapter 並綁定到 RecyclerView
        adapter = new MemoAdapter(new ArrayList<>());
        rvMemo.setAdapter(adapter);

        // Step 7: 向 Repository 要求載入資料
        KeepDataRepository.getInstance().loadData(requireContext());

        // Step 8: 綁定各按鈕的點擊事件。新增筆記按鈕。點擊後跳轉到 EditorFragment (新增模式，editPosition = -1)
        btnAddNote.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new EditorFragment())  // ✅ 用 add() 疊加，不是 replace() 替換
                    .addToBackStack(null)
                    .commit();
        });

        // Demo: Gson 解析測試 (保留或移除皆可)
        // 這段代碼展示如何用 Gson 解析 JSON
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

        //切換到 ListView 按鈕。點擊後跳轉到 LegacyListFragment
        btnGoLegacy.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new LegacyListFragment())  // ✅ 用 add() 疊加
                    .addToBackStack(null)
                    .commit();
        });

        //刪除已選按鈕。邏輯：倒序遍歷清單，找出所有 isSelected=true 的 Memo，刪除它們。為什麼倒序？避免刪除時的 index 偏移問題
        btnDeleteSelected.setOnClickListener(v -> {
            boolean hasDeleted = false;

            // 倒序迴圈：從最後一個往前查
            // 原因：刪除時 list 會縮短，如果正序會跳過項目
            List<Memo> list = KeepDataRepository.getInstance().getMemoList();
            for (int i = list.size() - 1; i >= 0; i--) {
                Memo m = list.get(i);
                if (m.isSelected()) {
                    list.remove(i);
                    hasDeleted = true;
                }
            }

            if (hasDeleted) {
                // 如果真的有刪除 1. 存進 XML 檔案 2. 更新 Adapter (刷新 UI)
                FileUtils.saveToXML(requireContext(), list);
                adapter.updateList(list);
                android.widget.Toast.makeText(requireContext(), "刪除成功！", android.widget.Toast.LENGTH_SHORT).show();
            } else {
                android.widget.Toast.makeText(requireContext(), "請先勾選要刪除的筆記", android.widget.Toast.LENGTH_SHORT).show();
            }
        });

        //搜尋功能：當搜尋文字改變時，呼叫 adapter.getFilter().filter(newText) 來過濾清單。Adapter 會根據搜尋條件更新顯示的項目
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        // 回傳 view (必須做)
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 每次 Fragment 恢復時，重新整理一次清單
        // 避免使用者編輯完回來後，還看著舊資料
        if (adapter != null && KeepDataRepository.getInstance().getMemoList() != null) {
            adapter.updateList(KeepDataRepository.getInstance().getMemoList());
        }
    }


    @Override
    public void onDataChanged(List<Memo> newList) {
        // 當收到管家廣播時，直接把新資料交給 Adapter 去刷新畫面
        if (adapter != null) {
            adapter.updateList(newList);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 退出廣播群組，避免記憶體洩漏
        // 這樣 Repository 就不會再呼叫這個 Fragment 的 onDataChanged
        KeepDataRepository.getInstance().removeListener(this);
    }
}