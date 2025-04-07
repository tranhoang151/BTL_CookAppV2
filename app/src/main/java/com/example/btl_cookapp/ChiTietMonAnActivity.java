package com.example.btl_cookapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.TimeZone;

public class ChiTietMonAnActivity extends AppCompatActivity {
    TextView tenmonan, congthuc, user;
    ImageView anh;
    Button buttonThemThucDon, buttonBookmark;
    private BookmarkDAO bookmarkDAO;
    private String currentUsername;
    private int monAnId;
    private boolean isBookmarked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_mon_an);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        tenmonan = findViewById(R.id.cttenmonan);
        congthuc = findViewById(R.id.ctcongthuc);
        user = findViewById(R.id.user);
        anh = findViewById(R.id.ctanh);
        buttonBookmark = findViewById(R.id.btnBookmark);

        bookmarkDAO = new BookmarkDAO(this);

        // Nhận Intent
        Intent intentct = getIntent();

        // Get current user from intent if available
        currentUsername = intentct.getStringExtra("username");

        // Đưa bundle ra khỏi intent
        Bundle mybundle = intentct.getBundleExtra("package");

        // Lấy dữ liệu khỏi bundle
        String ten = mybundle.getString("tenmonan");
        String ct = mybundle.getString("congthuc");
        String nguoitao = mybundle.getString("user");
        byte[] hinh = mybundle.getByteArray("anh");
        monAnId = mybundle.getInt("id", -1);

        tenmonan.setText(ten);
        congthuc.setText(ct);
        user.setText(nguoitao);
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh, 0, hinh.length);
        anh.setImageBitmap(bitmap);

        // Check if recipe is already bookmarked
        if (currentUsername != null && monAnId != -1) {
            isBookmarked = bookmarkDAO.isBookmarked(currentUsername, monAnId);
            updateBookmarkButtonText();
        } else {
            buttonBookmark.setVisibility(View.GONE);
        }

        buttonBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUsername != null && monAnId != -1) {
                    if (isBookmarked) {
                        // Remove bookmark
                        boolean removed = bookmarkDAO.removeBookmark(currentUsername, monAnId);
                        if (removed) {
                            isBookmarked = false;
                            Toast.makeText(ChiTietMonAnActivity.this, "Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Add bookmark
                        boolean added = bookmarkDAO.addBookmark(currentUsername, monAnId);
                        if (added) {
                            isBookmarked = true;
                            Toast.makeText(ChiTietMonAnActivity.this, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                        }
                    }
                    updateBookmarkButtonText();
                } else {
                    Toast.makeText(ChiTietMonAnActivity.this, "Vui lòng đăng nhập để sử dụng tính năng này", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateBookmarkButtonText() {
        if (isBookmarked) {
            buttonBookmark.setText("Xóa khỏi danh sách yêu thích");
        } else {
            buttonBookmark.setText("Thêm vào danh sách yêu thích");
        }
    }

    private long getCalendarId(Context context) {
        Cursor cursor = context.getContentResolver().query(
                CalendarContract.Calendars.CONTENT_URI,
                new String[]{CalendarContract.Calendars._ID},
                null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                long id = cursor.getLong(0);
                cursor.close();
                return id;
            }
            cursor.close();
        }
        return -1;
    }
}