package com.example.commoneydjdjmemo; // 你的 package

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
        Memo memo = memoList.get(position);

        // 3. 設定資料 (直接操作 ViewHolder 裡的變數)
        holder.tvTitle.setText(memo.getTitle());
        holder.tvContent.setText(memo.getContent());

        // 注意：如果你昨天定義的是 getDate() 還是 getTime()，這裡要對應
        holder.tvDate.setText(memo.getTime());
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