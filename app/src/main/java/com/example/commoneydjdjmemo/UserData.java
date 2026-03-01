package com.example.commoneydjdjmemo;

import androidx.annotation.NonNull;

/**
 * 角色: 用戶資料實體類 (Entity)
 * 
 * 責任:
 * - 用於映射 JSON 字串中的用戶屬性 (性別、年齡、喜好)。
 * - 提供格式化的字串輸出，便於 Log 觀察解析結果。
 */
public class UserData {
    private String gender;
    private int age;
    private boolean likeCat;

    // Getter 與 Setter
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public boolean isLikeCat() { return likeCat; }
    public void setLikeCat(boolean likeCat) { this.likeCat = likeCat; }

    @NonNull
    @Override
    public String toString() {
        return "性別='" + gender + '\'' +
                ", 年齡=" + age +
                ", 喜歡貓=" + (likeCat ? "是" : "否");
    }
}
