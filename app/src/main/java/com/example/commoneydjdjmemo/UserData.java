package com.example.commoneydjdjmemo; // 記得確認你的 package 名稱

public class UserData {
    private String gender;
    private int age;
    private boolean likeCat;

    // 為了方便 Log 印出來看，覆寫 toString
    @Override
    public String toString() {
        return "UserData{" +
                "gender='" + gender + '\'' +
                ", age=" + age +
                ", likeCat=" + likeCat +
                '}';
    }
}