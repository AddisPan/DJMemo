package com.example.commoneydjdjmemo;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

/* 
 * 角色: 中央資料管理器 (使用 Singleton 單例模式)
 *
 * 責任:
 * - 管理全應用的唯一備忘錄資料清單 (memoList)
 * - 提供資料的載入、儲存與刪除方法
 * - 實現 Observer 觀察者模式：當資料改變時，自動通知所有註冊的 Fragment (如 HomeFragment)
 *
 * 核心模式:
 * 1. Singleton (單例): 確保全 App 只有一個「管家」，資料不會亂掉。
 * 2. Observer (觀察者): 像廣播電台，一處更新，到處收到通知。
 *
 * 需求對應:
 * - 實現「資料管理」與「UI 自動更新」
 */
public class KeepDataRepository {
    // 1. 單例模式：唯一的實體
    private static KeepDataRepository instance;

    // 2. 帳本：存放所有的備忘錄
    private List<Memo> memoList;

    // 3. 廣播介面：定義「當資料變動時要做什麼」
    public interface OnDataChangeListener {
        void onDataChanged(List<Memo> newList);
    }

    // 4. 廣播名單：記錄誰在聽廣播
    private final List<OnDataChangeListener> listeners = new ArrayList<>();

    // 註冊廣播：Fragment 開始聽
    public void addListener(OnDataChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    // 取消註冊：Fragment 離開時停止聽 (防止記憶體外洩)
    public void removeListener(OnDataChangeListener listener) {
        listeners.remove(listener);
    }

    // 發送廣播：遍歷名單，通知大家資料變了
    private void notifyListeners() {
        for (OnDataChangeListener listener : listeners) {
            listener.onDataChanged(new ArrayList<>(memoList)); // 傳遞副本更安全
        }
    }

    // 私有建構子：防止外部直接 new
    private KeepDataRepository() {
        memoList = new ArrayList<>();
    }

    // 取得唯一實例
    public static KeepDataRepository getInstance() {
        if (instance == null) {
            instance = new KeepDataRepository();
        }
        return instance;
    }

    // 從 XML 檔案載入資料
    public void loadData(Context context) {
        memoList = FileUtils.readFromXML(context);
        notifyListeners();
    }

    // 取得目前的清單
    public List<Memo> getMemoList() {
        return memoList;
    }

    /**
     * 儲存或更新備忘錄
     * @param position 如果是 -1 表示新增，否則為更新指定位置
     */
    public void saveMemo(Context context, Memo memo, int position) {
        if (position == -1) {
            memoList.add(0, memo); // 新增的排在最上面
        } else if (position >= 0 && position < memoList.size()) {
            memoList.set(position, memo); // 更新現有內容
        }

        // 持久化到硬碟並通知 UI
        FileUtils.saveToXML(context, memoList);
        notifyListeners();
    }

    /**
     * 複選刪除：移除所有被勾選的項目
     */
    public void deleteSelectedMemos(Context context) {
        // 使用「倒序迴圈」刪除，避免 Index 偏移錯誤
        for (int i = memoList.size() - 1; i >= 0; i--) {
            if (memoList.get(i).isSelected()) {
                memoList.remove(i);
            }
        }
        // 存檔並廣播
        FileUtils.saveToXML(context, memoList);
        notifyListeners();
    }
}
