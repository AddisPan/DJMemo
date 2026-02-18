package com.example.commoneydjdjmemo; // 你的 package

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.MemoViewHolder> {

    private final List<Memo> memoList;

    public MemoAdapter(List<Memo> memoList) {
        this.memoList = memoList;
    }

    @NonNull
    @Override
    public MemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 載入 Layout
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

        // 3. 🎯 唯一且完美的點擊事件 (兩個 onClick 已經合體了！)
        holder.itemView.setOnClickListener(v -> {

            // 👉 跟被點擊的 View (v) 借用 Context，解決紅字問題
            android.content.Context context = v.getContext();

            // 保留你的任務 A：印出 Log
            android.util.Log.d("MemoClick", "使用者點擊了筆記：" + memo.getTitle());

            // 👉 使用 getBindingAdapterPosition() 取得當下最正確的位置，解決黃字警告
            int currentPosition = holder.getBindingAdapterPosition();

            // 打包資料跳轉到 EditorActivity
            Intent intent = new Intent(context, EditorActivity.class);

            // 統一使用小寫標籤，對應我們剛剛改好的 EditorActivity
            intent.putExtra("title", memo.getTitle());
            intent.putExtra("content", memo.getContent());
            intent.putExtra("tag", memo.getTag());
            intent.putExtra("time", memo.getTime());
            intent.putExtra("memo_position", currentPosition); // 傳送位置情報！

            // 出發！
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return memoList.size();
    }

    // 修改 ViewHolder：這裡要宣告 TextView 並執行 findViewById
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