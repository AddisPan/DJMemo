package com.example.commoneydjdjmemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

// 繼承老牌的 BaseAdapter
public class MemoBaseAdapter extends BaseAdapter {

    private Context context;
    private List<Memo> memoList;

    public MemoBaseAdapter(Context context, List<Memo> memoList) {
        this.context = context;
        this.memoList = memoList;
    }

    @Override
    public int getCount() {
        // 告訴 ListView 總共有幾筆資料
        return memoList != null ? memoList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        // 取得特定位置的資料
        return memoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 這是 ListView 的核心：把資料塞進 XML 畫面裡

        // 1. 如果畫面是空的，就去載入我們剛剛畫的 item_memo_legacy.xml
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_memo_legacy, parent, false);
        }

        // 2. 綁定裡面的 TextView
        TextView tvTitle = convertView.findViewById(R.id.tv_legacy_title);
        TextView tvTime = convertView.findViewById(R.id.tv_legacy_time);

        // 3. 拿出這筆資料
        Memo memo = memoList.get(position);

        // 4. 把字塞進去
        tvTitle.setText(memo.getTitle());
        tvTime.setText(memo.getTime());

        return convertView;
    }
}