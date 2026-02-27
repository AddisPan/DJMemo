package com.example.commoneydjdjmemo;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class KeepDataRepository {

    // 1. 單例模式 (Singleton) 必備：隱藏的自己
    private static KeepDataRepository instance;

    // 管家手上的唯一帳本 (集中管理的資料)
    private List<Memo> memoList;

    // ==========================================
    // 📻 2. 建立廣播網 (Observer Pattern)
    // ==========================================
    // 定義一個「接收廣播」的介面
    public interface OnDataChangeListener {
        void onDataChanged(List<Memo> newList);
    }

    // 廣播名單 (誰想知道資料更新，就加進這個清單)
    private final List<OnDataChangeListener> listeners = new ArrayList<>();

    // 註冊廣播 (加入群組)
    public void addListener(OnDataChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    // 取消註冊 (退出群組，避免記憶體外洩)
    public void removeListener(OnDataChangeListener listener) {
        listeners.remove(listener);
    }

    // 大聲廣播：「資料更新啦！」
    private void notifyListeners() {
        for (OnDataChangeListener listener : listeners) {
            listener.onDataChanged(memoList);
        }
    }

    // ==========================================
    // 🔒 3. 單例模式的鎖與鑰匙
    // ==========================================
    private KeepDataRepository() {
        memoList = new ArrayList<>();
    }

    public static KeepDataRepository getInstance() {
        if (instance == null) {
            instance = new KeepDataRepository();
        }
        return instance;
    }

    // ==========================================
    // 📂 4. 管家專屬工作區 (取代原本在 Fragment 裡的邏輯)
    // ==========================================

    // (A) 初始化讀取資料
    public void loadData(Context context) {
        memoList = FileUtils.readFromXML(context);
        notifyListeners(); // 讀完資料後，廣播給首頁更新畫面！
    }

    // (B) 取得目前資料 (給 Adapter 或其他地方需要時用)
    public List<Memo> getMemoList() {
        return memoList;
    }

    // (C) 存檔 (自動判斷是新增還是修改)
    public void saveMemo(Context context, Memo memo, int position) {
        if (position == -1) {
            memoList.add(0, memo); // 新增放最前面
        } else {
            if (position < memoList.size()) {
                memoList.set(position, memo); // 修改舊資料
            } else {
                memoList.add(0, memo);
            }
        }

        // 存入 XML 實體檔案
        FileUtils.saveToXML(context, memoList);

        // 🌟 存檔完成後，立刻廣播通知大家！
        notifyListeners();
    }
}