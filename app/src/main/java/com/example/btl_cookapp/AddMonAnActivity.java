package com.example.btl_cookapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddMonAnActivity extends AppCompatActivity {

    final int RESQUEST_TAKE_PHOTO = 123;
    final int RESQUEST_CHOOSE_PHOTO = 321;
    Button chonhinh, chuphinh, luu, huy, back;
    ImageView anh;
    EditText tenmon, congthuc;
    RadioGroup radioGroup;
    MonAnDAO DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mon_an);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Thêm Công Thức Món Ăn");
        anhXa();
        addEvents();

    }
    private void anhXa() {
        chonhinh = findViewById(R.id.chonhinh);
        chuphinh = findViewById(R.id.chuphinh);
        tenmon = findViewById(R.id.ten);
        anh = findViewById(R.id.imageView);
        congthuc = findViewById(R.id.congthuc);
        luu = findViewById(R.id.btnluu);
        huy = findViewById(R.id.btnhuy);
        back=findViewById(R.id.btnbackpf);
        radioGroup = findViewById(R.id.radioGroup);
        DB = new MonAnDAO(AddMonAnActivity.this);
    }

    private void addEvents() {
        chonhinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
        chuphinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertmonan() ;

            }
        });
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenthuy = new Intent(AddMonAnActivity.this, ProfileActivity.class);
                Intent  intent=getIntent();
                //Lấy dữ liệu
                String name=intent.getStringExtra("username");
                intenthuy.putExtra("username",name);
                startActivity(intenthuy);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=getIntent();
                //Lấy dữ liệu
                String name=intent.getStringExtra("username");
                Intent intentback = new Intent(AddMonAnActivity.this, TrangChuActivity.class);
                intentback.putExtra("username",name);
                startActivity(intentback);
            }
        });
    }

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
            return;
        } else {
            startActivityForResult(intent, RESQUEST_TAKE_PHOTO);
        }


    }

    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, RESQUEST_CHOOSE_PHOTO);

    }
    private int getSelectedIndex() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            View child = radioGroup.getChildAt(i);
            if (child instanceof RadioButton) {
                if (child.getId() == selectedId) {
                    return i;
                }
            }
        }
        return -1;
    }
    private void insertmonan() {
        Intent  intent=getIntent();
        //Lấy dữ liệu
        String user=intent.getStringExtra("username");
        String ten = tenmon.getText().toString();
        if (ten.isEmpty()) {
            Toast.makeText(AddMonAnActivity.this, "Tên không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        String cthuc = congthuc.getText().toString();
        if (cthuc.isEmpty()) {
            Toast.makeText(AddMonAnActivity.this, "Công thức không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        byte[] hinh = getByteArrayFromImageView(anh);
        if (hinh == null) {
            Toast.makeText(AddMonAnActivity.this, "Hình ảnh không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        int selected = getSelectedIndex();
        TypeMonAn typeMonAn =  TypeMonAn.getTypeByValue(selected);
        if (DB.insertData(new MonAn(ten, cthuc, hinh, user, typeMonAn))) {
            Toast.makeText(AddMonAnActivity.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
            Intent intentdangxuat=new Intent(AddMonAnActivity.this,TrangChuActivity.class);
            startActivity(intentdangxuat);
        }

    }

    //Chuyển đổi từ Bitmap sang byte[]
    private byte[] getByteArrayFromImageView(ImageView imgv) {
        try {
            BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
            Bitmap bmp = drawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            return byteArray;
        }
        catch (Exception e) {
            return null;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RESQUEST_TAKE_PHOTO) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                anh.setImageBitmap(bitmap);
            } else if (requestCode == RESQUEST_CHOOSE_PHOTO) {
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    anh.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}