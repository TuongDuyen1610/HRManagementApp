package com.example.hrmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button loginBtn, registerBtn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Khởi tạo FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Ánh xạ các View từ layout
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);

        // Xử lý sự kiện đăng nhập
        loginBtn.setOnClickListener(view -> loginUser());

        // Chuyển đến màn hình đăng ký
        registerBtn.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        // Kiểm tra xem user đã đăng nhập chưa
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish(); // Đóng màn hình đăng nhập nếu đã đăng nhập
        }
    }

    private void loginUser() {
        String userEmail = email.getText().toString().trim();
        String userPass = password.getText().toString().trim();

        if (userEmail.isEmpty() || userPass.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish(); // Đóng màn hình đăng nhập sau khi đăng nhập thành công
                    } else {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
