package com.example.commoneydjdjmemo;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色: SharedPreferences (JSON) 持久化工具類
 * 
 * 責任:
 * - 將 Memo 清單物件轉換為 JSON 字串並存入 SharedPreferences
 * - 從 SharedPreferences 讀取 JSON 並還原為 List<Memo>
 * 
 * 需求對應:
 * - 符合需求 4.1：使用 SharedPreference 儲存 Json (ListView 讀取源)
 */
public class SPUtils {
    private static final String SP_NAME = "MemoAppSP";
    private static final String KEY_MEMO_LIST = "memo_list_json";

    // 儲存：物件 -> JSON -> SP
    public static void saveMemoList(Context context, List<Memo> memoList) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        String jsonString = new Gson().toJson(memoList);
        sp.edit().putString(KEY_MEMO_LIST, jsonString).apply();
    }

    // 讀取：SP -> JSON -> 物件
    public static List<Memo> getMemoList(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        String jsonString = sp.getString(KEY_MEMO_LIST, null);
        if (jsonString == null) return new ArrayList<>();
        
        Type type = new TypeToken<List<Memo>>(){}.getType();
        try {
            List<Memo> list = new Gson().fromJson(jsonString, type);
            return list != null ? list : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
