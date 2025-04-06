package com.example.btl_cookapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class TrangChuActivity extends AppCompatActivity {
    ArrayList<MonAn> mylist=new ArrayList<>();
    MyAdapterMonAn myAdapterMonAn;
    MonAnDAO DB;
    ListView listView;
    ImageButton btnprofile;
    ImageButton btnsearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        anhxa();
        readData();
        sukien();
    }
    private  void anhxa(){
        listView=findViewById(R.id.lv);
        btnprofile=findViewById(R.id.btnProfile);
        btnsearch=findViewById(R.id.btnSearch);
        DB = new MonAnDAO(TrangChuActivity.this);
        myAdapterMonAn=new MyAdapterMonAn(TrangChuActivity.this,R.layout.layout_item,mylist);
        listView.setAdapter(myAdapterMonAn);
    }
    private  void sukien(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentct=new Intent(TrangChuActivity.this,ChiTietMonAnActivity.class);
                //Lấy dữ liệu
                String cttenmon=mylist.get(position).getTenMonAn().toString();
                String ctcongthuc=mylist.get(position).getCongThuc().toString();
                String ctuser=mylist.get(position).getUser().toString();
                byte[] ctanh= mylist.get(position).getImage();
                int typeMonAn = mylist.get(position).getType().getValue();
                //Trước khi đưa vào intent chúng ta đóng gói dữ liệu vào bundle
                Bundle mybundle=new Bundle();
                //Đưa dũ liệu vào bundle
                mybundle.putString("tenmonan",cttenmon);
                mybundle.putString("congthuc",ctcongthuc);
                mybundle.putString("user",ctuser);
                mybundle.putByteArray("anh",ctanh);
                mybundle.putInt("type",typeMonAn);
                //Đưa bundle vào intent
                intentct.putExtra("package",mybundle);
                //Khởi động intent
                startActivity(intentct);

            }
        });
        btnprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=getIntent();
                //Lấy dữ liệu
                String username=intent.getStringExtra("username");
                Intent intentprofile=new Intent(TrangChuActivity.this,ProfileActivity.class);
                intentprofile.putExtra("username",username);
                startActivity(intentprofile);
            }
        });
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentsearch=new Intent(TrangChuActivity.this,SearchActivity.class);
                startActivity(intentsearch);
            }
        });
    }
    private  void readData(){
        mylist.clear();
        mylist.addAll(DB.getAllData());
        myAdapterMonAn.notifyDataSetChanged();
    }
}