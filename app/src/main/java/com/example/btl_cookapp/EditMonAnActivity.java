package com.example.btl_cookapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditMonAnActivity extends AppCompatActivity {
    private ImageView imageView;
    private EditText edtTenMon, edtCongThuc;
    private RadioGroup radioGroup;
    private Button btnChonAnh, btnCapNhat, btnHuy;
    private MonAnDAO monAnDAO;
    private byte[] imageBytes;
    private MonAn monAn;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mon_an);

        initViews();
        monAnDAO = new MonAnDAO(this);

        // Get data from intent
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        String tenMon = intent.getStringExtra("tenmonan");
        String congThuc = intent.getStringExtra("congthuc");
        String user = intent.getStringExtra("user");
        byte[] image = intent.getByteArrayExtra("image");

        // Get the existing MonAn from database using the ID
        monAn = monAnDAO.getMonAnById(id);

        // If not found, create a new MonAn object
        if (monAn == null) {
            TypeMonAn type = TypeMonAn.MonAnPhu; // Default value
            monAn = new MonAn(id, tenMon, congThuc, image, user, type);
        }

        // Now set data to views
        setDataToViews();

        btnChonAnh.setOnClickListener(v -> chooseImage());
        btnCapNhat.setOnClickListener(v -> updateMonAn());
        btnHuy.setOnClickListener(v -> finish());
    }

    private void initViews() {
        imageView = findViewById(R.id.imageView);
        edtTenMon = findViewById(R.id.ten);
        edtCongThuc = findViewById(R.id.congthuc);
        radioGroup = findViewById(R.id.radioGroup);
        btnChonAnh = findViewById(R.id.chonhinh);
        btnCapNhat = findViewById(R.id.btnCapNhat);
        btnHuy = findViewById(R.id.btnHuy);
    }

    private void setDataToViews() {
        if (monAn != null) {
            edtTenMon.setText(monAn.getTenMonAn());
            edtCongThuc.setText(monAn.getCongThuc());
            imageBytes = monAn.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imageView.setImageBitmap(bitmap);

            // Set radio button based on meal type
            TypeMonAn type = monAn.getType();
            if (type != null) {
                if (type == TypeMonAn.MonAnSang) {
                    radioGroup.check(R.id.radioMonAnSang);
                } else if (type == TypeMonAn.MonAnTrua) {
                    radioGroup.check(R.id.radioMonAnTrua);
                } else if (type == TypeMonAn.MonAnToi) {
                    radioGroup.check(R.id.radioMonAnToi);
                } else if (type == TypeMonAn.MonAnPhu) {
                    radioGroup.check(R.id.radioMonAnPhu);
                }
            }
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                Uri uri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);

                // Convert bitmap to byte array
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                imageBytes = stream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateMonAn() {
        String tenMon = edtTenMon.getText().toString().trim();
        String congThuc = edtCongThuc.getText().toString().trim();

        if (tenMon.isEmpty() || congThuc.isEmpty() || imageBytes == null) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get selected meal type
        TypeMonAn type = null;
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.radioMonAnSang) {
            type = TypeMonAn.MonAnSang;
        } else if (selectedId == R.id.radioMonAnTrua) {
            type = TypeMonAn.MonAnTrua;
        } else if (selectedId == R.id.radioMonAnToi) {
            type = TypeMonAn.MonAnToi;
        } else if (selectedId == R.id.radioMonAnPhu) {
            type = TypeMonAn.MonAnPhu;
        }

        // Update MonAn object
        monAn.setTenMonAn(tenMon);
        monAn.setCongThuc(congThuc);
        monAn.setImage(imageBytes);
        monAn.setType(type);

        // Update in database
        boolean success = monAnDAO.updateData(monAn);
        if (success) {
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

            // Create intent to return to the BaiDangActivity with refresh flag
            Intent intent = new Intent(this, BaiDangActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear the activity stack
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}