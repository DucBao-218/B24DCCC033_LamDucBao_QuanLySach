package com.example.baithuchanhso2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baithuchanhso2.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(v -> handleLogin());
    }

    private void handleLogin() {
        String username = binding.etUsername.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            binding.tvErrorMessage.setText(R.string.error_fields_empty);
            binding.tvErrorMessage.setVisibility(View.VISIBLE);
            return;
        }

        if ("admin".equals(username) && "123456".equals(password)) {
            binding.tvErrorMessage.setVisibility(View.GONE);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            binding.tvErrorMessage.setText(R.string.error_login_invalid);
            binding.tvErrorMessage.setVisibility(View.VISIBLE);
        }
    }
}
