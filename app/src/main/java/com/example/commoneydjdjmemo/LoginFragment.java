package com.example.commoneydjdjmemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

/**
 * 角色: 用戶登入介面 Fragment
 * 
 * 責任:
 * - 提供帳號密碼輸入欄位。
 * - 驗證登入資訊 (目前為範例邏輯：帳號 admin / 密碼 1234)。
 * - 登入成功後跳轉至 HomeActivity (主頁面)。
 * 
 * 需求對應:
 * - 符合需求 5：登入成功後跳轉到工作清單頁。
 */
public class LoginFragment extends Fragment {

    public LoginFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        EditText etAccount = view.findViewById(R.id.et_account);
        EditText etPassword = view.findViewById(R.id.et_password);
        Button btnLogin = view.findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> {
            String account = etAccount.getText().toString();
            String password = etPassword.getText().toString();

            // 簡易驗證邏輯
            if ("addis".equals(account) && "1234".equals(password)) {
                Toast.makeText(requireContext(), "登入成功！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
                requireActivity().finish(); // 銷毀登入頁，防止返回
            } else {
                Toast.makeText(requireContext(), "帳號或密碼錯誤", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
