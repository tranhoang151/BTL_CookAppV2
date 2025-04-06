package com.example.btl_cookapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button btnlogin, btnback;
    UserDAO DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Login");


        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        btnlogin=findViewById(R.id.btndangnhap);
        btnback=findViewById(R.id.btnback);
        DB= new UserDAO(LoginActivity.this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=username.getText().toString();
                String pass=password.getText().toString();
                if(user.isEmpty() || pass.isEmpty())
                    Toast.makeText(LoginActivity.this,"Vui lòng nhập tất cả các trường", Toast.LENGTH_SHORT).show();
                else {
                    boolean checkuserpassword= DB.checkUsernamePassword(user,pass);
                    if(checkuserpassword){
                        Toast.makeText(LoginActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),TrangChuActivity.class);
                        intent.putExtra("username",user);
                        startActivity(intent);
                    }else {
                        Toast.makeText(LoginActivity.this,"Thông tin không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}