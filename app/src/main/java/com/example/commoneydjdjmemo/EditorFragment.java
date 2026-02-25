package com.example.commoneydjdjmemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EditorFragment extends Fragment {

    // 1. 宣告元件 (原本的按鈕變成區域變數，所以不用宣告在上面)
    private EditText etTitle, etContent, etTag;

    // 記錄目前是第幾筆資料 (預設 -1 代表是按 + 號新增的)
    private int editPosition = -1;

    public EditorFragment() {
        // 空的建構子
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 2. 畫布充氣
        View view = inflater.inflate(R.layout.fragment_editor, container, false);

        // 沉浸式模式 (套用心法：this 換成 requireActivity())
        WindowUtils.enableImmersiveMode(requireActivity());

        // 3. 綁定 ID (套用心法：加上 view.)
        etTitle = view.findViewById(R.id.et_title);
        etContent = view.findViewById(R.id.et_content);
        etTag = view.findViewById(R.id.et_tag);
        Button btnBack = view.findViewById(R.id.btn_back);
        Button btnSaveXml = view.findViewById(R.id.btn_save_xml);
        Button btnSaveSp = view.findViewById(R.id.btn_save_sp);

        // 🎯 4. 接收從 Adapter 傳過來的包裹 (用 Bundle 取代 Intent)
        Bundle bundle = getArguments();
        if (bundle != null) {
            String passedTitle = bundle.getString("title");
            String passedContent = bundle.getString("content");
            String passedTag = bundle.getString("tag");
            editPosition = bundle.getInt("memo_position", -1);

            if (passedTitle != null) etTitle.setText(passedTitle);
            if (passedContent != null) etContent.setText(passedContent);
            if (passedTag != null) etTag.setText(passedTag);
        }

        // 🎯 5. 設定「返回」按鈕 (用 popBackStack 退回上一頁，取代 finish)
        btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        // 6. 按鈕 A：存入 XML
        btnSaveXml.setOnClickListener(v -> {
            Memo memo = createMemoFromInput();
            List<Memo> currentList = FileUtils.readFromXML(requireContext());

            saveToList(currentList, memo);
            FileUtils.saveToXML(requireContext(), currentList);

            Toast.makeText(requireContext(), "已存入 XML", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack(); // 存檔後退回首頁
        });

        // 7. 按鈕 B：存入 SP / JSON
        btnSaveSp.setOnClickListener(v -> {
            Memo memo = createMemoFromInput();
            List<Memo> currentList = SPUtils.getMemoList(requireContext());

            saveToList(currentList, memo);
            SPUtils.saveMemoList(requireContext(), currentList);

            Toast.makeText(requireContext(), "已存入 SP (JSON)", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack(); // 存檔後退回首頁
        });

        return view;
    }

    // --- 小工具區 (幾乎不用動) ---
    private void saveToList(List<Memo> list, Memo memo) {
        if (editPosition == -1) {
            list.add(0, memo);
        } else {
            if (editPosition < list.size()) {
                list.set(editPosition, memo);
            } else {
                list.add(0, memo);
            }
        }
    }

    private Memo createMemoFromInput() {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        String tag = etTag.getText().toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
        String currentDate = sdf.format(new Date());

        Memo memo = new Memo();
        memo.setTitle(title);
        memo.setContent(content);
        memo.setTag(tag);
        memo.setTime(currentDate);

        return memo;
    }
}