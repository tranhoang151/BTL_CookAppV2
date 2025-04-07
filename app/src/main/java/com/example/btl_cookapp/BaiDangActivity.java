package com.example.btl_cookapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BaiDangActivity extends AppCompatActivity {
    ArrayList<MonAn> mylist;
    MyAdapterMonAn myAdapterMonAn;
    private MonAnDAO DB;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_dang);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // Get username from intent
        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");

        listView = findViewById(R.id.listviewbaidang);
        mylist = new ArrayList<>();
        DB = new MonAnDAO(BaiDangActivity.this);
        myAdapterMonAn = new MyAdapterMonAn(BaiDangActivity.this, R.layout.layout_item, mylist);
        listView.setAdapter(myAdapterMonAn);
        readData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentct = new Intent(BaiDangActivity.this, ChiTietMonAnActivity.class);
                // Lấy dữ liệu
                String cttenmon = mylist.get(position).getTenMonAn();
                String ctcongthuc = mylist.get(position).getCongThuc();
                String ctuser = mylist.get(position).getUser();
                byte[] ctanh = mylist.get(position).getImage();
                int monanId = mylist.get(position).getID();

                // Đưa dữ liệu vào bundle
                Bundle mybundle = new Bundle();
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
    private  void readData(){
        mylist.clear();
        mylist.addAll(DB.getAllData());
        myAdapterMonAn.notifyDataSetChanged();
    }
}