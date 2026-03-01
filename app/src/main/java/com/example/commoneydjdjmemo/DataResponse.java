package com.example.commoneydjdjmemo;

import java.util.Map;

/**
 * 角色: 用於映射 JSON 解析結果的物件 (POJO)
 * 
 * 責任:
 * - 代表 JSON 最外層的物件，包含一個名為 "data" 的 Map。
 * - 用於 Gson 解析，將 JSON 字串轉換為 UserData 物件的映射。
 */
public class DataResponse {
    // 這裡的變數名稱 "data" 必須跟 JSON 裡面的 Key 一模一樣
    private Map<String, UserData> data;

    public Map<String, UserData> getData() {
        return data;
    }

    public void setData(Map<String, UserData> data) {
        this.data = data;
    }
}
