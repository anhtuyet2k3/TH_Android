package com.example.btth4_qlsv;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editMaLop, editTenLop, editSiSo;
    Button btnInsert, btnUpdate, btnDelete, btnQuery;
    // khai báo list view
    ListView lv;
    // ArrayList là một danh sách động lưu trữ các phần tử
    ArrayList<String> mylist;
    //ArrayAdapter là một adapter dùng để kết nối dữ liệu từ ArrayList với ListView
    ArrayAdapter<String> myadapter;
    SQLiteDatabase mydata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editMaLop = findViewById(R.id.editMaLop);
        editTenLop =findViewById(R.id.editTenLop);
        editSiSo =findViewById(R.id.editSiSo);
        btnInsert = findViewById(R.id.btnInsert);
        btnUpdate =findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnQuery = findViewById(R.id.btnQuery);

        // tạo list view
        lv = findViewById(R.id.lv);
        mylist = new ArrayList<>();
        // khai báo dapter kết nối với arraylist
        myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,mylist);
        // kết noi listview với arraylist thông qua myadapter
        lv.setAdapter(myadapter);

        // tạo hoặc mở database
        mydata = openOrCreateDatabase("qlsinhvien.db", MODE_PRIVATE,null);
        // tạo table chứa cơ sở dữ liệu
        try {
            String sql = "CREATE TABLE tbllop(malop TEXT primary key, tenlop TEXT, siso INTEGER)";
            mydata.execSQL(sql);
        }catch (Exception e){
            Log.e("Error","Bảng đã tồn tại");
        }

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String malop = editMaLop.getText().toString();
                String tenlop = editTenLop.getText().toString();
                try {
                    Integer siso = Integer.parseInt(editSiSo.getText().toString());
                    //một lớp trong Android được sử dụng để lưu trữ một tập hợp các cặp giá trị khóa (key-value) mà muốn thêm hoặc cập nhật vào cơ sở dữ liệu
                    ContentValues myvalue = new ContentValues();
                    myvalue.put("malop",malop);
                    myvalue.put("tenlop",tenlop);
                    myvalue.put("siso",siso);
                    String msg ="";
                    if(mydata.insert("tbllop",null,myvalue)==-1){
                        msg = "Thêm lớp thất bại";
                        showData();
                    }
                    else{
                        msg = "Thêm lớp thành công";
                        showData();
                    }
                    Toast.makeText(MainActivity.this,msg,Toast.LENGTH_LONG).show();
                }catch (NumberFormatException e){
                    Toast.makeText(MainActivity.this, "Sĩ số lớp phải là một số nguyên", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String malop = editMaLop.getText().toString();
                try {
                    Integer siso = Integer.parseInt(editSiSo.getText().toString());
                    ContentValues myvalue = new ContentValues();
                    myvalue.put("siso",siso);
                    int n = mydata.update("tbllop",myvalue,"malop LIKE ?",new String[]{malop});
                    String msg="";
                    if(n==0){
                        msg += "Cập nhật sĩ số thất bại";
                        showData();
                    }else{
                        msg += "Cập nhật sĩ số thành công";
                        showData();
                    }
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                }catch (NumberFormatException e){
                    Toast.makeText(MainActivity.this, "Sĩ số lớp phải là một số nguyên", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String malop = editMaLop.getText().toString();
                int n = mydata.delete("tbllop","malop LIKE ?",new String[]{malop});
                String msg = "";
                if(n==0){
                    msg = msg + "Xóa lớp thất bại";
                }
                else{
                    msg = msg + "Xóa lớp thành công";
                    showData();
                }
                Toast.makeText(MainActivity.this,msg,Toast.LENGTH_LONG).show();
            }
        });

        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Thêm TextWatcher cho editMaLop để lắng nghe sự thay đổi văn bản
        editMaLop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void loadData(String queryMaLop) {
        mylist.clear();
        //xác định điều kiện WHERE cho truy vấn SQL.
        String selection = "malop LIKE ?";
        // mảng các giá trị dùng để thay thế cho placeholder ?
        String[] selectionArgs = {queryMaLop+"%"};
        Cursor c = mydata.query("tbllop",null,selection,selectionArgs,null,null,null);
        // do mới chỉ trả về con trỏ ở lệnh trên nên thêm 1 lệnh để đưa con trỏ về dòng đầu của list
        c.moveToNext();
        String data="";
        if(c.moveToFirst()){
            do {
                data= c.getString(0)+" - "+c.getString(1)+" - "+c.getString(2);
                mylist.add(data);
            }while (c.moveToNext());
        }
        c.close();
        myadapter.notifyDataSetChanged();
    }

    public void showData(){
        mylist.clear();
        Cursor c = mydata.query("tbllop",null,null,null,null,null,null);
        // do mới chỉ trả về con trỏ ở lệnh trên nên thêm 1 lệnh để đưa con trỏ về dòng đầu của list
        c.moveToNext();
        String data="";
        while(c.isAfterLast()==false){
            data= c.getString(0)+" - "+c.getString(1)+" - "+c.getString(2);
            c.moveToNext();
            mylist.add(data);
        }
        c.close();
        myadapter.notifyDataSetChanged();
    }

    public void Clear(){
        editTenLop.getText().clear();
        editMaLop.getText().clear();
        editSiSo.getText().clear();
    }
}