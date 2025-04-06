package com.example.btl_cookapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterMonAn extends BaseAdapter implements Filterable{

    Context context;
    int IDLayout;
    ArrayList<MonAn> mylist;
    //list trung gian
    ArrayList<MonAn>mylistAll;
    Button btn;
    MonAnDAO DB;
    public MyAdapterMonAn(Context context , int IDLayout, @NonNull ArrayList<MonAn>mylist) {
        this.context=context;
        this.IDLayout=IDLayout;
        this.mylist=mylist;
        this.mylistAll=mylist;
        DB = new MonAnDAO(context);
    }

    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public Object getItem(int position) {
        return mylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(IDLayout, parent, false);
        }

        btn = convertView.findViewById(R.id.btn);
        Button btnEdit = convertView.findViewById(R.id.btn_edit);

        if (context instanceof BaiDangActivity) {
            btnEdit.setVisibility(View.VISIBLE);
            btn.setText("Xóa");

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MonAn myMonAn = mylist.get(position);
                    Intent intent = new Intent(context, EditMonAnActivity.class);
                    // Pass ID as well
                    intent.putExtra("id", myMonAn.getID());
                    intent.putExtra("tenmonan", myMonAn.getTenMonAn());
                    intent.putExtra("congthuc", myMonAn.getCongThuc());
                    intent.putExtra("user", myMonAn.getUser());
                    intent.putExtra("image", myMonAn.getImage());
                    context.startActivity(intent);
                }
            });
            //Xóa món ăn
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MonAn myMonAn = mylist.get(position);
                    DB.deleteData(myMonAn);
                    Toast.makeText(context, "Xóa món ăn thành công", Toast.LENGTH_SHORT).show();
                    Intent intentdangxuat = new Intent(context, TrangChuActivity.class);
                    context.startActivity(intentdangxuat);
                }
            });
        } else {
            btnEdit.setVisibility(View.GONE);
            btn.setVisibility(View.GONE);
        }

        MonAn myMonAn=mylist.get(position);
        ImageView img_monan=convertView.findViewById(R.id.anhmonan);
        Bitmap hinh= BitmapFactory.decodeByteArray(myMonAn.image,0,myMonAn.image.length);
        img_monan.setImageBitmap(hinh);
        TextView txt_tenmonan=convertView.findViewById(R.id.txttenmon);
        txt_tenmonan.setText(myMonAn.tenMonAn);
        TextView txt_user=convertView.findViewById(R.id.txtuser);
        txt_user.setText(myMonAn.user);
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                //key search trên listview
                String strSearch = constraint.toString();
                if(strSearch.isEmpty())
                {
                    mylist = mylistAll;
                }
                else
                {
                    ArrayList<MonAn> list = new ArrayList<>();
                    for (MonAn monan : mylistAll)
                    {
                        if(monan.getTenMonAn().toLowerCase().contains(strSearch.toLowerCase()))
                        {
                            list.add(monan);
                        }
                    }
                    mylist = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mylist;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mylist=(ArrayList<MonAn>)results.values;
                notifyDataSetChanged();
            }
        };
    }

}

