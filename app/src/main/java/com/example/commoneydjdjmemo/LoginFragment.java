package com.example.commoneydjdjmemo; // 確認你的 package 名稱

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    public LoginFragment() {
        // 必須要有空建構子
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 1. 把剛剛搬好的登入畫布充氣出來
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // 2. 綁定元件 (記得前面要加 view.)
        EditText etAccount = view.findViewById(R.id.et_account);
        EditText etPassword = view.findViewById(R.id.et_password);
        Button btnLogin = view.findViewById(R.id.btn_login);

        // 3. 設定登入按鈕的點擊事件 (加入你說的 admin 1234 判斷)
        btnLogin.setOnClickListener(v -> {
            String account = etAccount.getText().toString();
            String password = etPassword.getText().toString();

            if ("admin".equals(account) && "1234".equals(password)) {
                // 帳密正確，跳轉到 HomeActivity (客廳)
                Intent intent = new Intent(requireActivity(), HomeActivity.class);
                startActivity(intent);
                // 關閉外層的 MainActivity，避免按返回鍵又回到登入頁
                requireActivity().finish();
            } else {
                Toast.makeText(requireContext(), "帳號或密碼錯誤", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}