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
    TextView tenmonan,congthuc,user;
    ImageView anh;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_mon_an);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        tenmonan=findViewById(R.id.cttenmonan);
        congthuc=findViewById(R.id.ctcongthuc);
        user=findViewById(R.id.user);
        anh=findViewById(R.id.ctanh);
        button = findViewById(R.id.btnthemthucdon);
        //Nhận Intent
        Intent intentct=getIntent();
        //Đưa bundle ra khỏi intent
        Bundle mybundle=intentct.getBundleExtra("package");
        //Lấy dữ liệu khỏi bundle
        String ten=mybundle.getString("tenmonan");
        String ct=mybundle.getString("congthuc");
        String nguoitao=mybundle.getString("user");
        byte[] hinh=mybundle.getByteArray("anh");
        tenmonan.setText(ten);
        congthuc.setText(ct);
        user.setText(nguoitao);
        Bitmap bitmap= BitmapFactory.decodeByteArray(hinh,0,hinh.length);
        anh.setImageBitmap(bitmap);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long calID = getCalendarId(ChiTietMonAnActivity.this);
                if (calID == -1) {
                    Toast.makeText(ChiTietMonAnActivity.this, "Không tìm thấy lịch", Toast.LENGTH_SHORT).show();
                    return;
                }
                ContentValues values = new ContentValues();
                values.put(CalendarContract.Events.DTSTART, System.currentTimeMillis() + 3600000);
                values.put(CalendarContract.Events.DTEND, System.currentTimeMillis() + 7200000);
                values.put(CalendarContract.Events.TITLE, "Cuộc họp quan trọng");
                values.put(CalendarContract.Events.DESCRIPTION, "Ghi chú: Đừng quên mang tài liệu");
                values.put(CalendarContract.Events.CALENDAR_ID, calID);
                values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
            }
        });
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