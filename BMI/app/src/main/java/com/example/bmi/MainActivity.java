package com.example.bmi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    EditText editName, editHeight, editWeight, editBMI, editResult;
    Button btnBMI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.edtName);
        editHeight = findViewById(R.id.edtHeight);
        editWeight = findViewById(R.id.edtWeight);
        editBMI = findViewById(R.id.edtBMI);
        editResult = findViewById(R.id.edtResult);
        btnBMI = findViewById(R.id.btnBMI);

        btnBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double H = Double.parseDouble(editHeight.getText()+"");
                double W = Double.parseDouble(editWeight.getText()+"");
                double BMI = (double) W/ Math.pow(H,2);
                String chuandoan = "";
                if(BMI < 18){
                    chuandoan = "Bạn gầy";
                } else if (BMI <=24.9) {
                    chuandoan = "Bạn bình thường";
                }else if (BMI <=29.9) {
                    chuandoan = "Bạn béo phì độ I";
                }else if (BMI <=34.9) {
                    chuandoan = "Bạn béo phì độ II";
                }
                else{
                    chuandoan = "Bạn béo phì độ III";
                }
                DecimalFormat dcf = new DecimalFormat("#.0");
                editBMI.setText(dcf.format(BMI));
                editResult.setText(chuandoan);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}