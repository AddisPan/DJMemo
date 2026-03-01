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

/**
 * 角色: 用於建立和編輯備忘錄的 Fragment
 *
 * 責任:
 * - 提供 UI 讓使用者輸入備忘錄的標題、標籤、內容
 * - 支援兩種儲存方式:
 *   1) btnSaveXml: 透過 KeepDataRepository 存進 XML → 會廣播更新
 *   2) btnSaveSp: 直接用 SPUtils 存進 SharedPreference (JSON) → 不會廣播
 * - 透過 Bundle 判斷是「新增模式」(editPosition = -1) 還是「編輯模式」(editPosition >= 0)
 *
 * 需求對應:
 * - 實現「新增/編輯頁面為 Fragment，用 add/remove 切換」
 * - 提供「兩個儲存按鈕，一個存 XML，一個存 JSON」
 *
 * 生命週期重點:
 * - 透過 Repository 存檔時，Repository 會自動廣播，HomeFragment 會自動更新
 * - 透過 SPUtils 存檔時，不會自動廣播，需要手動處理 (或不處理)
 */
public class EditorFragment extends Fragment {
    // 變數宣告 etTitle, etContent, etTag: EditText 輸入框 - 存儲使用者輸入的「標題」、「內容」、「標籤」
    private EditText etTitle, etContent, etTag;

    // editPosition: 編輯位置 (新增/編輯的判別)
    // editPosition == -1: 新增模式 (新建一筆備忘錄)
    // editPosition >= 0: 編輯模式 (修改第 N 筆備忘錄)
    private int editPosition = -1;

    //空建構子 (Fragment 必須)。Android 系統重建 Fragment 時會用反射呼叫空建構子。如果沒有，系統無法重建，會 crash
    public EditorFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Step 1: 畫布充氣 + 啟用沉浸式模式
        View view = inflater.inflate(R.layout.fragment_editor, container, false);
        WindowUtils.enableImmersiveMode(requireActivity());

        // Step 2: 綁定 UI 元件
        etTitle = view.findViewById(R.id.et_title);
        etContent = view.findViewById(R.id.et_content);
        etTag = view.findViewById(R.id.et_tag);
        Button btnBack = view.findViewById(R.id.btn_back);
        Button btnSaveXml = view.findViewById(R.id.btn_save_xml);
        Button btnSaveSp = view.findViewById(R.id.btn_save_sp);

        // Step 3: 檢查是否有傳入 Bundle (判斷新增或編輯模式)
        Bundle bundle = getArguments();
        if (bundle != null) {
            // 編輯模式：把舊資料填入各 EditText
            String passedTitle = bundle.getString("title");
            String passedContent = bundle.getString("content");
            String passedTag = bundle.getString("tag");
            editPosition = bundle.getInt("memo_position", -1);

            if (passedTitle != null) etTitle.setText(passedTitle);
            if (passedContent != null) etContent.setText(passedContent);
            if (passedTag != null) etTag.setText(passedTag);
        }

        // Step 4: 綁定返回按鈕。popBackStack 會回到上一個 Fragment (HomeFragment)
        btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        // Step 5: 綁定「保存到 XML」按鈕
        //路徑: EditorFragment → Repository → FileUtils → XML 檔案。特點：會廣播，所以 HomeFragment 的 UI 會自動更新
        btnSaveXml.setOnClickListener(v -> {
            Memo memo = createMemoFromInput();
            KeepDataRepository.getInstance().saveMemo(requireContext(), memo, editPosition);
            android.widget.Toast.makeText(requireContext(), "已存入 XML", android.widget.Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        // Step 6: 綁定「保存到 JSON」按鈕
        //路徑: EditorFragment → SPUtils → SharedPreference (JSON)。特點：不會廣播，HomeFragment 的 UI 不會自動更新
        btnSaveSp.setOnClickListener(v -> {
            Memo memo = createMemoFromInput();
            List<Memo> currentList = SPUtils.getMemoList(requireContext());

            saveToList(currentList, memo);
            SPUtils.saveMemoList(requireContext(), currentList);

            android.widget.Toast.makeText(requireContext(), "已存入 SP (JSON)", android.widget.Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

    // Helper 方法 1: saveToList
    private void saveToList(List<Memo> list, Memo memo) {
        if (editPosition == -1) {
            // 新增模式：加到最前面
            list.add(0, memo);
        } else {
            // 編輯模式：更新指定位置
            if (editPosition < list.size()) {
                list.set(editPosition, memo);
            } else {
                // 防呆：位置超出範圍時，加到最前面
                list.add(0, memo);
            }
        }
    }

    // Helper 方法 2: createMemoFromInput
    private Memo createMemoFromInput() {
        // ① 取得輸入框的內容
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        String tag = etTag.getText().toString();

        // ② 格式化目前時間 (格式: yyyy/MM/dd HH:mm，例如 2026/02/28 14:30)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
        String currentDate = sdf.format(new Date());

        // ③ 建立新的 Memo 物件
        Memo memo = new Memo();

        // ④ 填入各欄位
        memo.setTitle(title);
        memo.setContent(content);
        memo.setTag(tag);
        memo.setTime(currentDate);

        // ⑤ 回傳這個新建的 Memo 物件
        return memo;
    }
}