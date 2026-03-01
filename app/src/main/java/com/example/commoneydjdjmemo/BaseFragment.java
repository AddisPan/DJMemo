package com.example.commoneydjdjmemo;

import androidx.fragment.app.Fragment;
import java.util.List;

/**
 * 角色: 所有 Fragment 的基底類別
 * 
 * 責任:
 * - 實作 KeepDataRepository.OnDataChangeListener 廣播介面。
 * - 強制子類別必須實作 onDataChanged，以接收資料變更通知。
 * 
 * 業界標準:
 * - 透過抽象基底類別統一廣播行為，減少重複代碼。
 */
public abstract class BaseFragment extends Fragment implements KeepDataRepository.OnDataChangeListener {

    /**
     * 當 KeepDataRepository 廣播資料更新時，此方法會被觸發。
     * @param newList 最新備忘錄清單
     */
    @Override
    public abstract void onDataChanged(List<Memo> newList);
}
