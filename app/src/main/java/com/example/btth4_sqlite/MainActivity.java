package com.example.btth4_sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String DB_PATH_SUFFIX ="/databases";
    SQLiteDatabase database = null;
    String DATABASE_NAME = "qlsach.db";
    // khai báo listview
    ListView lv;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Gọi hàm copy CSDL từ assets vào thư mục Database
        processCopy();
        // Mở CSDL lên để dùng
        database = openOrCreateDatabase("mydatabase", MODE_PRIVATE, null);
        // Tạo list view
        lv = findViewById(R.id.lv);
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);
        lv.setAdapter(myadapter);
        // truy vấn CSDL và cập nhật hiển thị lên ListView
        Cursor c = database.query("tbl_sach", null, null, null, null, null, null);
        c.moveToFirst();
        String data = "";
        while (c.isAfterLast()==false){
            data = c.getString(0)+"-"+c.getString(1)+" - "+c.getString(2);
            mylist.add(data);
            c.moveToNext();
        }
        c.close();
        myadapter.notifyDataSetChanged();
    }
    private void processCopy() {
        //private app
        File dbfile = getDatabasePath(DATABASE_NAME);
        if(!dbfile.exists()){
            try {
               CopyDataBaseFormatAsset(); 
            }catch (Exception e){
                Toast.makeText(this, e.toString(),Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    private void CopyDataBaseFormatAsset() {
        // private app
        try {
            InputStream myInput;
            myInput = getAssets().open(DATABASE_NAME);
            // Path to the just created empty db
            String outFileName = getDatabasePath();
            // If the path doesn't exist first, create it
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if(!f.exists()){
                f.mkdir();
            }
            // Open the empty db as the output stream
            OutputStream myoutput = new FileOutputStream(outFileName);
            // transfer bytes from the inputfile đến the outputlife
            // truyền bytes dữ liệu từ input đến output
            int size = myInput.available();
            byte[] buffer = new byte[size];
            myInput.read(buffer);
            myoutput.write(buffer);
            // close the streams
            myoutput.flush();
            myoutput.close();
            myInput.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}