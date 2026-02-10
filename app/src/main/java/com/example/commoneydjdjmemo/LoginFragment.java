package com.example.commoneydjdjmemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

// 1. 匯入databinding自動生成的類別 (名稱規則：xml檔名轉駝峰 + Binding)
import com.example.commoneydj1djmemo.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    // 2. 宣告 Binding 變數
    private FragmentLoginBinding binding;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 3. 初始化 Binding (這行取代了 inflater.inflate)
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        // 回傳根視圖 (Root View)
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 4. 直接使用 binding 存取元件 (不需要 findViewById 了！)
        binding.btnLogin.setOnClickListener(v -> {

            // 取得輸入文字
            String account = binding.etAccount.getText().toString();
            String password = binding.etPassword.getText().toString();

            // 檢查邏輯
            if (account.equals("admin") && password.equals("1234")) {
                Toast.makeText(getActivity(), "登入成功 (ViewBinding)", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "帳號或密碼錯誤", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 5. Fragment 的銷毀生命週期 (記憶體優化)
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // 釋放 binding 避免記憶體洩漏
    }
}