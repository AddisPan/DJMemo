package com.example.commoneydjdjmemo;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/* 
 * 角色: RecyclerView Adapter
 * 符合需求: 
 * - 兩種 ViewType 顯示 (完成/未完成)
 * - 支援勾選記錄 (isSelected)
 * - 長按切換完成狀態
 */
public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.MemoViewHolder> implements android.widget.Filterable {
    private final List<Memo> memoList;
    private List<Memo> memoListFull;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_COMPLETED = 1;

    public MemoAdapter(List<Memo> memoList) {
        this.memoList = memoList;
        this.memoListFull = new ArrayList<>(memoList);
    }

    @Override
    public int getItemViewType(int position) {
        return memoList.get(position).isCompleted() ? TYPE_COMPLETED : TYPE_NORMAL;
    }

    public void updateList(List<Memo> newList) {
        this.memoList.clear();
        this.memoList.addAll(newList);
        this.memoListFull = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 根據狀態選擇佈局
        int layoutId = (viewType == TYPE_COMPLETED) ? R.layout.item_memo_done : R.layout.item_memo;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new MemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoViewHolder holder, int position) {
        Memo memo = memoList.get(position);

        // 基本資料綁定
        holder.tvTitle.setText(memo.getTitle());
        if (holder.tvTag != null) holder.tvTag.setText(memo.getTag());
        holder.tvContent.setText(memo.getContent());
        holder.tvDate.setText(memo.getTime());

        // 處理「已完成」的視覺效果 (加刪除線)
        if (memo.isCompleted()) {
            holder.tvTitle.setPaintFlags(holder.tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvTitle.setTextColor(0xFFAAAAAA); // 變灰色
        } else {
            holder.tvTitle.setPaintFlags(holder.tvTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.tvTitle.setTextColor(0xFF333333); // 恢復黑色
        }

        // 複選框：記錄勾選狀態 (需求 2)
        holder.cbDelete.setOnCheckedChangeListener(null);
        holder.cbDelete.setChecked(memo.isSelected());
        holder.cbDelete.setOnCheckedChangeListener((buttonView, isChecked) -> {
            memo.setSelected(isChecked);
        });

        // 點擊 Item 進入編輯 (需求 3)
        holder.itemView.setOnClickListener(v -> openEditor(holder.itemView.getContext(), memo, position));

        // 長按切換完成狀態 (需求 1.1)
        holder.itemView.setOnLongClickListener(v -> {
            memo.setCompleted(!memo.isCompleted());
            // 存入 XML 並通知廣播
            KeepDataRepository.getInstance().saveMemo(holder.itemView.getContext(), memo, position);
            Toast.makeText(holder.itemView.getContext(), memo.isCompleted() ? "任務已完成" : "任務恢復中", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void openEditor(android.content.Context context, Memo memo, int position) {
        if (context instanceof AppCompatActivity) {
            Bundle bundle = new Bundle();
            bundle.putString("title", memo.getTitle());
            bundle.putString("content", memo.getContent());
            bundle.putString("tag", memo.getTag());
            bundle.putInt("memo_position", position);
            
            EditorFragment editor = new EditorFragment();
            editor.setArguments(bundle);
            
            ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, editor)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public int getItemCount() { return memoList.size(); }

    public static class MemoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvTag, tvContent, tvDate;
        CheckBox cbDelete;

        public MemoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTag = itemView.findViewById(R.id.tv_tag); // 注意：item_memo_done 可能沒有這個
            tvContent = itemView.findViewById(R.id.tv_content);
            tvDate = itemView.findViewById(R.id.tv_date);
            cbDelete = itemView.findViewById(R.id.cb_delete);
        }
    }

    @Override
    public android.widget.Filter getFilter() {
        return new android.widget.Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Memo> filtered = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filtered.addAll(memoListFull);
                } else {
                    String searchText = constraint.toString().toLowerCase();
                    for (Memo memo : memoListFull) {
                        if ((memo.getTitle() != null && memo.getTitle().toLowerCase().contains(searchText)) ||
                            (memo.getContent() != null && memo.getContent().toLowerCase().contains(searchText))) {
                            filtered.add(memo);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filtered;
                return results;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                memoList.clear();
                if (results.values != null) memoList.addAll((List<Memo>) results.values);
                notifyDataSetChanged();
            }
        };
    }
}
