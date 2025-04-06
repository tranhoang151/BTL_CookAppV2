package com.example.btl_cookapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    EditText username, password, repassword;
    Button dangki, dangnhap;
    UserDAO DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Signup");
        username= findViewById(R.id.username);
        password=findViewById(R.id.password);
        repassword=findViewById(R.id.repassword);
        dangki=findViewById(R.id.btndangki);
        dangnhap=findViewById(R.id.btndangnhap);
        DB=new UserDAO(SignupActivity.this);
        dangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=username.getText().toString();
                String pass=password.getText().toString();
                String repass= repassword.getText().toString();
                if(user.isEmpty() || pass.isEmpty() || repass.isEmpty())
                    Toast.makeText(SignupActivity.this,"Vui lòng nhập tất cả các trường",Toast.LENGTH_SHORT).show();
                else
                if(pass.equals(repass)){
                    Boolean checkuser=DB.checkUsername(user);
                    if(!checkuser){
                        Boolean insert=DB.insertData(user,pass);
                        if(insert){
                            Toast.makeText(SignupActivity.this,"Đăng kí thành công", Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent(getApplicationContext(),TrangChuActivity.class);
                            intent.putExtra("username",user);
                            startActivity(intent);
                        }else {

                            Toast.makeText(SignupActivity.this, "Đăng kí không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(SignupActivity.this,"Người dùng đã tồn tại! Hãy đăng nhập", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SignupActivity.this,"Mật khẩu không khớp",Toast.LENGTH_SHORT).show();
                }
            }
        });
        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}