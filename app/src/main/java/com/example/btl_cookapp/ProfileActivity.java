package com.example.btl_cookapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity {
    ImageView anh;
    TextView username;
    Button themmon, dangxuat, baidang, back, bookmarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        Intent intent=getIntent();
        //Lấy dữ liệu
        String name=intent.getStringExtra("username");

        anh=findViewById(R.id.avatar);
        username=findViewById(R.id.txtNameProfile);
        username.setText(name);
        themmon=findViewById(R.id.btnthemmon);
        dangxuat=findViewById(R.id.btndangxuat);
        baidang=findViewById(R.id.btnbaidang);
        back=findViewById(R.id.btnbacktc);
        bookmarks=findViewById(R.id.btnbookmarks);

        themmon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentthem=new Intent(ProfileActivity.this,AddMonAnActivity.class);
                intentthem.putExtra("username",name);
                startActivity(intentthem);
            }
        });
        dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentdangxuat=new Intent(ProfileActivity.this,LoginActivity.class);
                startActivity(intentdangxuat);
            }
        });
        baidang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentbaidang=new Intent(ProfileActivity.this,BaiDangActivity.class);
                intentbaidang.putExtra("username",username.getText().toString());
                startActivity(intentbaidang);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentb=new Intent(ProfileActivity.this,TrangChuActivity.class);
                intentb.putExtra("username",username.getText().toString());
                startActivity(intentb);
            }
        });

        // Add listener for bookmarks button
        bookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBookmarks = new Intent(ProfileActivity.this, BookmarkActivity.class);
                intentBookmarks.putExtra("username", username.getText().toString());
                startActivity(intentBookmarks);
            }
        });
    }
}