package com.example.commoneydjdjmemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import java.util.List;

/**
 * 角色: 舊版工作清單 Fragment (ListView 實作)
 * 
 * 責任:
 * - 使用 ListView 配合 MemoBaseAdapter 顯示備忘錄。
 * - 讀取 SharedPreferences (JSON) 資料源。
 * 
 * 需求對應:
 * - 符合需求 1：工作清單頁分別使用 ListView 及 RecyclerView。
 * - 符合需求 4.1：ListView 讀取 JSON 資料。
 */
public class LegacyListFragment extends Fragment {

    public LegacyListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 加載佈局並強制設定背景色，防止 Fragment 疊加穿透
        View view = inflater.inflate(R.layout.fragment_legacy_list, container, false);
        view.setBackgroundColor(android.graphics.Color.WHITE);
        view.setClickable(true);

        ListView listView = view.findViewById(R.id.list_view_legacy);

        // 讀取 JSON (SP) 資料源
        List<Memo> spDataList = SPUtils.getMemoList(requireContext());

        // 設定 Adapter
        MemoBaseAdapter adapter = new MemoBaseAdapter(requireContext(), spDataList);
        listView.setAdapter(adapter);

        return view;
    }
}
