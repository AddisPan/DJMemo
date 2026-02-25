package com.example.commoneydjdjmemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import java.util.List;

public class LegacyListFragment extends Fragment {

    public LegacyListFragment() {
        // Fragment 必須保留一個空的建構子
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 1. 把畫布充氣出來 (取代原本的 setContentView)
        View view = inflater.inflate(R.layout.fragment_legacy_list, container, false);

        // 2. 綁定 ListView 元件 (🌟心法：加上 view.)
        ListView listView = view.findViewById(R.id.list_view_legacy);

        // 3. 讀取 SP (JSON) 裡的資料 (🌟心法：把 this 改成 requireContext())
        List<Memo> spDataList = SPUtils.getMemoList(requireContext());

        // 4. 建立並設定 Adapter (🌟心法：把 this 改成 requireContext())
        MemoBaseAdapter adapter = new MemoBaseAdapter(requireContext(), spDataList);
        listView.setAdapter(adapter);

        // 5. 記得回傳畫布
        return view;
    }
}