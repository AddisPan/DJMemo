package com.example.commoneydjdjmemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * 角色: 舊版 ListView 的轉接器 (BaseAdapter)
 * 
 * 責任:
 * - 實作 ListView 的 getView() 方法，負責項目的 UI 繪製。
 * - 配合 LegacyListFragment 展示傳統的資料呈現方式。
 * 
 * 業界標準:
 * - 實作基本的 View 複用邏輯，透過 convertView 減少 Inflation。
 */
public class MemoBaseAdapter extends BaseAdapter {

    private final Context context;
    private final List<Memo> memoList;

    public MemoBaseAdapter(Context context, List<Memo> memoList) {
        this.context = context;
        this.memoList = memoList;
    }

    @Override
    public int getCount() {
        return memoList.size();
    }

    @Override
    public Object getItem(int position) {
        return memoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return memoList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. 複用 View 邏輯：如果 convertView 已經存在，就不要重新 Inflate
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_memo_legacy, parent, false);
        }

        // 2. 綁定 UI 控制項
        TextView tvTitle = convertView.findViewById(R.id.tv_legacy_title);
        TextView tvTime = convertView.findViewById(R.id.tv_legacy_time);

        // 3. 填入資料
        Memo memo = memoList.get(position);
        if (memo != null) {
            tvTitle.setText(memo.getTitle());
            tvTime.setText(memo.getTime());
        }

        return convertView;
    }
}
