package com.example.bai7_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btnRequest;
    EditText edita, editb, editKQ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnRequest = findViewById(R.id.btnRequest);
        edita = findViewById(R.id.edta);
        editb = findViewById(R.id.edtb);
        editKQ = findViewById(R.id.edtKQ);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(MainActivity.this, resuilt_activity.class);
                Bundle mybundle = new Bundle();
                int a = Integer.parseInt(edita.getText().toString());
                int b = Integer.parseInt(editb.getText().toString());
                myintent.putExtra("soa",a);
                myintent.putExtra("sob",b);
                // Khởi động Intent và chờ kết quả
                startActivityForResult(myintent,99);
                // Khởi động Intent và chờ kết quả trả về

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // gọi hàm lấy kết quả trả về
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 99 && resultCode == 33){
            int resuilt = data.getIntExtra("resuilt",0);
            editKQ.setText("Tổng 2 số là: "+resuilt);
        }
        if(requestCode == 99 && resultCode == 44){
            int resuilt = data.getIntExtra("resuilt",0);
            editKQ.setText("Hiệu 2 số là: "+resuilt);
        }
    }
}