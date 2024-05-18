package com.example.bai7_2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class result_activity extends AppCompatActivity {
    Button btnBack;
    EditText editKQ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);

        editKQ = findViewById(R.id.kq);
        btnBack = findViewById(R.id.btnBack);

        // Nhận intent
        Intent myintent = getIntent();
        // Lấy bundle khỏi intent
        Bundle mybundle = myintent.getBundleExtra("mypackage");
        // Lấy dữ liệu khỏi Bundle
        int a = mybundle.getInt("soa");
        int b = mybundle.getInt("sob");
        // Tiến hành giải PT và hiển thị kết quả
        String nghiem ="";
        if(a==0 && b==0){
            nghiem = "PT vô số nghiệm";
        }
        else if(a==0 && b!=0){
            nghiem = "PT vô số nghiệm";
        }
        else{
            nghiem = ""+(-1.0)*b/a;
        }
        editKQ.setText(nghiem);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}