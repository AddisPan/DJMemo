package com.example.commoneydjdjmemo; // 你的 package

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.MemoViewHolder> implements android.widget.Filterable {

    private final List<Memo> memoList;
    private List<Memo> memoListFull; // 🎯 新增：用來裝「全部資料」的備份清單

    public MemoAdapter(List<Memo> memoList) {
        this.memoList = memoList;
        this.memoListFull = new ArrayList<>(memoList); // 複製一份作為備份
    }

    // 🎯 新增一個方法，讓 HomeActivity 資料改變時，可以同時更新「顯示用」跟「備份用」的清單
    public void updateList(List<Memo> newList) {
        this.memoList.clear();
        this.memoList.addAll(newList);
        this.memoListFull = new ArrayList<>(newList); // 備份也要一起更新！
        notifyDataSetChanged();
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

    // ==========================================
    // 🎯 實作 Filterable 介面的過濾邏輯
    // ==========================================
    @Override
    public android.widget.Filter getFilter() {
        return new android.widget.Filter() {
            // 1. 這裡在背景執行過濾比對
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Memo> filteredList = new ArrayList<>();

                // 如果搜尋框是空的，就直接回傳完整的備份清單
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(memoListFull);
                } else {
                    // 把使用者打的字轉成小寫，避免大小寫找不對
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    // 遍歷備份清單，比對 Title 或 Content 是否包含關鍵字
                    for (Memo item : memoListFull) {
                        boolean matchTitle = item.getTitle() != null && item.getTitle().toLowerCase().contains(filterPattern);
                        boolean matchContent = item.getContent() != null && item.getContent().toLowerCase().contains(filterPattern);

                        // 如果標題或內容有中，就加進結果清單
                        if (matchTitle || matchContent) {
                            filteredList.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            // 2. 把過濾完的結果放到畫面上
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                memoList.clear(); // 清空目前的畫面資料
                memoList.addAll((List) results.values); // 塞入過濾後的結果
                notifyDataSetChanged(); // 刷新畫面
            }
        };
    }
}