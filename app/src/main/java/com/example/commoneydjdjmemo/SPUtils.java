package com.example.commoneydjdjmemo; // 確認你的 package 名稱

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SPUtils {
    // SP 的檔案名稱與儲存 Key
    private static final String SP_NAME = "MemoAppSP";
    private static final String KEY_MEMO_LIST = "memo_list_json";

    // 儲存：將 List<Memo> 轉成 JSON 字串並存入 SP
    public static void saveMemoList(Context context, List<Memo> memoList) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonString = gson.toJson(memoList); // 把清單變成 JSON 字串
        sp.edit().putString(KEY_MEMO_LIST, jsonString).apply(); // 存進去！
    }

    // 讀取：從 SP 拿出 JSON 字串，並轉回 List<Memo>
    public static List<Memo> getMemoList(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        String jsonString = sp.getString(KEY_MEMO_LIST, null);

        // 如果是第一次打開 App，沒有資料，就回傳一個空的清單
        if (jsonString == null) {
            return new ArrayList<>();
        }

        // 把 JSON 字串變回 Java 的 List
        Gson gson = new Gson();
        Type type = new TypeToken<List<Memo>>(){}.getType();
        return gson.fromJson(jsonString, type);
    }
}