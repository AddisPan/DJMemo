package com.example.commoneydjdjmemo;

import java.util.Map;

public class DataResponse {
    // 這裡的變數名稱 "data" 必須跟 JSON 裡面的 Key 一模一樣
    // Map 的 Key 是 String (放名字), Value 是 UserData (放詳細資料)
    private Map<String, UserData> data;

    // 取得資料的方法
    public Map<String, UserData> getData() {
        return data;
    }
}