package com.example.commoneydjdjmemo; // 你的 package

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView; // 1. 匯入 TextView
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.MemoViewHolder> {

    private List<Memo> memoList;

    public MemoAdapter(List<Memo> memoList) {
        this.memoList = memoList;
    }

    @NonNull
    @Override
    public MemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 2. 載入 Layout (傳統寫法)
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_memo, parent, false);
        return new MemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoViewHolder holder, int position) {
        // 1. 取得現在這一行的資料
        Memo memo = memoList.get(position);

        // 2. 將資料設定到畫面上 (TextView)
        holder.tvTitle.setText(memo.getTitle());
        holder.tvContent.setText(memo.getContent());
        holder.tvDate.setText(memo.getTime());

        // 3. 🎯 今天的新任務：設定這一整行 (itemView) 的點擊事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 【任務 A】使用 Log 印出標題 (這只有在 Android Studio 下方的 Logcat 看得到)
                // 記得如果 Log 變成紅字，要按 Alt+Enter 匯入 android.util.Log;
                android.util.Log.d("MemoClick", "使用者點擊了筆記：" + memo.getTitle());

                // 【任務 B】把資料打包，跳轉到 EditorActivity (作為編輯模式)
                Intent intent = new Intent(v.getContext(), EditorActivity.class);

                // 把這筆筆記的標題跟內容當作「包裹」塞進去
                intent.putExtra("EDIT_TITLE", memo.getTitle());
                intent.putExtra("EDIT_CONTENT", memo.getContent());

                // 出發！
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return memoList.size();
    }

    // 4. 修改 ViewHolder：這裡要宣告 TextView 並執行 findViewById
    public static class MemoViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvContent, tvDate; // 宣告變數

        public MemoViewHolder(@NonNull View itemView) {
            super(itemView);

            // 綁定 ID (請確認 item_memo.xml 裡的 ID 是這些)
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvDate = itemView.findViewById(R.id.tv_date); // 或是 tv_time
        }
    }
}