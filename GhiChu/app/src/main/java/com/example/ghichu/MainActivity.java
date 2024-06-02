package com.example.ghichu;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    EditText editHour, editMinute,editWork;
    Button btnAdd;
    TextView date;
    ListView lv1;
    ArrayList<String> arrayWork;
    ArrayAdapter<String>arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editHour = findViewById(R.id.editHour);
        editMinute = findViewById(R.id.editMinute);
        editWork = findViewById(R.id.editWork);
        btnAdd = findViewById(R.id.btnAdd);
        lv1 = findViewById(R.id.lv1);
        date = findViewById(R.id.datenow);

        // dữ liệu tự động
        // khai báo mảng arraylist kiểu String rỗng
        arrayWork = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayWork);
        lv1.setAdapter(arrayAdapter);

        //loadData
        loadData();
        arrayAdapter.notifyDataSetChanged();

        // set date
        Date currentDate = Calendar.getInstance().getTime();
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");
        date.setText("Hôm nay: "+formatter.format(currentDate));

        // Kiểm tra nhập trường dữ liệu
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editHour.getText().toString().isEmpty() || editMinute.getText().toString().isEmpty() || editWork.getText().toString().isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Info missing");
                    builder.setMessage("Please enter information of the work");
                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
                else{
                    String str = editWork.getText().toString()+" - "+editHour.getText().toString() + ":" + editMinute.getText().toString();
                    arrayWork.add(str);
                    arrayAdapter.notifyDataSetChanged();
                    editHour.setText("");
                    editMinute.setText("");
                    editWork.setText("");

                    saveData();
                }
            }
        });

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
               builder.setTitle("Delete entry");
               builder.setMessage("Are you sure you want to delete this entry?");
               builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       arrayWork.remove(position);
                       // cập nhật Adapter
                       arrayAdapter.notifyDataSetChanged();
                       saveData();
                   }
               });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
               builder.show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.date), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // save data
    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("my_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<>(arrayWork);
        editor.putStringSet("my_set",set);
        editor.apply();
    }
    // load data
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("my_pref", MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet("my_set", new HashSet<>());
        arrayWork.clear();
        arrayWork.addAll(set);
        arrayAdapter.notifyDataSetChanged();
    }
}