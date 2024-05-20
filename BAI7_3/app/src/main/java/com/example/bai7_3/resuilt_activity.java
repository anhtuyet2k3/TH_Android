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

public class resuilt_activity extends AppCompatActivity {
    Button btnSum, btnSubtraction;
    EditText edita, editb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resuilt);

        btnSum = findViewById(R.id.btnSum);
        btnSubtraction = findViewById(R.id.btnSubtraction);
        edita = findViewById(R.id.edta);
        editb = findViewById(R.id.edtb);

        // lấy intent
        Intent myintent = getIntent();
        // lấy biến trong intent
        int a = myintent.getIntExtra("soa",0);
        int b = myintent.getIntExtra("sob",0);
        edita.setText(a+"");
        editb.setText(b+"");

        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myintent.putExtra("resuilt",(a+b));
                setResult(33, myintent);
                finish();
            }
        });

        btnSubtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myintent.putExtra("resuilt",(a-b));
                setResult(44,myintent);
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