package com.example.commoneydjdjmemo;

import com.google.gson.Gson;
import java.util.Map;

public class JsonObjectUtility {

    // 傳入 JSON 字串，回傳解析好的 Map 結構
    public static Map<String, UserData> readJsonStringToObject(String jsonString) {
        Gson gson = new Gson();

        try {
            // 直接請 Gson 把字串轉成我們寫好的 DataResponse 物件
            DataResponse response = gson.fromJson(jsonString, DataResponse.class);

            // 回傳裡面的 Map 資料
            if (response != null) {
                return response.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // 如果解析失敗回傳 null
    }
}