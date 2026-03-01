package com.example.commoneydjdjmemo;

import com.google.gson.Gson;
import java.util.Map;

/**
 * 角色: JSON 解析工具類 (基於 Gson)
 * 
 * 責任:
 * - 將特定的 JSON 字串解析為 Map<String, UserData> 結構。
 * - 簡化 HomeFragment 中的 JSON 範例解析邏輯。
 */
public class JsonObjectUtility {

    /**
     * 將 JSON 字串解析為物件
     * @param jsonString 來源 JSON
     * @return 解析後的資料 Map，失敗則回傳 null
     */
    public static Map<String, UserData> readJsonStringToObject(String jsonString) {
        Gson gson = new Gson();
        try {
            DataResponse response = gson.fromJson(jsonString, DataResponse.class);
            if (response != null) {
                return response.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
