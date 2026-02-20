package com.example.commoneydjdjmemo; // 你的 package

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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

        // ==========================================
        // 🎯 新增：處理 CheckBox (防亂跳機制)
        // ==========================================
        // (1) 先把監聽器拔掉，避免受到之前回收的舊狀態影響
        holder.cbDelete.setOnCheckedChangeListener(null);

        // (2) 依照 Memo 的真實狀態，把 CheckBox 打勾或取消打勾
        holder.cbDelete.setChecked(memo.isSelected());

        // (3) 狀態設定好之後，再把監聽器裝回去！
        holder.cbDelete.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // 當使用者點擊 CheckBox 時，把狀態存回這筆 Memo 裡面
            memo.setSelected(isChecked);
        });
        // ==========================================

        // 3. 唯一且完美的點擊事件 (點擊整行跳轉編輯頁)
        holder.itemView.setOnClickListener(v -> {
            android.content.Context context = v.getContext();
            android.util.Log.d("MemoClick", "使用者點擊了筆記：" + memo.getTitle());
            int currentPosition = holder.getBindingAdapterPosition();

            Intent intent = new Intent(context, EditorActivity.class);
            intent.putExtra("title", memo.getTitle());
            intent.putExtra("content", memo.getContent());
            intent.putExtra("tag", memo.getTag());
            intent.putExtra("time", memo.getTime());
            intent.putExtra("memo_position", currentPosition);

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
        CheckBox cbDelete; // 宣告

        public MemoViewHolder(@NonNull View itemView) {
            super(itemView);

            // 綁定 ID (請確認 item_memo.xml 裡的 ID 是這些)
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvDate = itemView.findViewById(R.id.tv_date); // 或是 tv_time
            cbDelete = itemView.findViewById(R.id.cb_delete); // 綁定 CheckBo
        }
    }
}