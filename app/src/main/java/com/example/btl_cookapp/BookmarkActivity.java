package com.example.btl_cookapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BookmarkActivity extends AppCompatActivity {
    ArrayList<MonAn> bookmarkedList;
    MyAdapterMonAn myAdapterMonAn;
    BookmarkDAO bookmarkDAO;
    ListView listView;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // Get username from intent
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        if (username == null || username.isEmpty()) {
            Toast.makeText(this, "Không có thông tin người dùng", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        listView = findViewById(R.id.listviewbookmarks);
        bookmarkedList = new ArrayList<>();
        bookmarkDAO = new BookmarkDAO(BookmarkActivity.this);
        myAdapterMonAn = new MyAdapterMonAn(BookmarkActivity.this, R.layout.layout_item, bookmarkedList);
        listView.setAdapter(myAdapterMonAn);

        loadBookmarks();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentct = new Intent(BookmarkActivity.this, ChiTietMonAnActivity.class);
                // Lấy dữ liệu
                String cttenmon = bookmarkedList.get(position).getTenMonAn();
                String ctcongthuc = bookmarkedList.get(position).getCongThuc();
                String ctuser = bookmarkedList.get(position).getUser();
                byte[] ctanh = bookmarkedList.get(position).getImage();
                int monanId = bookmarkedList.get(position).getID();

                // Trước khi đưa vào intent chúng ta đóng gói dữ liệu vào bundle
                Bundle mybundle = new Bundle();
                // Đưa dữ liệu vào bundle
                mybundle.putString("tenmonan", cttenmon);
                mybundle.putString("congthuc", ctcongthuc);
                mybundle.putString("user", ctuser);
                mybundle.putByteArray("anh", ctanh);
                mybundle.putInt("id", monanId);

                // Đưa bundle vào intent
                intentct.putExtra("package", mybundle);
                intentct.putExtra("username", username);

                // Khởi động intent
                startActivity(intentct);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload bookmarks when returning to this activity
        loadBookmarks();
    }

    private void loadBookmarks() {
        bookmarkedList.clear();
        bookmarkedList.addAll(bookmarkDAO.getBookmarkedMonAn(username));
        myAdapterMonAn.notifyDataSetChanged();

        if (bookmarkedList.isEmpty()) {
            Toast.makeText(this, "Bạn chưa có công thức yêu thích nào", Toast.LENGTH_SHORT).show();
        }
    }
}