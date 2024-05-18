package com.example.btth01_cb2;

import android.app.Activity;
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

public class MainActivity extends Activity {
    Button btncf, btnfc, btnC;
    EditText edtdoF, edtdoC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtdoF = findViewById(R.id.edt1);
        edtdoC = findViewById(R.id.edt2);
        btnfc = findViewById(R.id.btn1);
        btncf = findViewById(R.id.btn2);
        btnC = findViewById(R.id.btn3);

        btncf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat dcf = new DecimalFormat("#.00");
                String doC = edtdoC.getText()+"";
                int c = Integer.parseInt(doC);
                edtdoF.setText(""+dcf.format((c*1.8+32)));
            }
        });

        btnfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat dfc = new DecimalFormat("#.00");
                String doF = edtdoF.getText()+"";
                int f = Integer.parseInt(doF);
                edtdoC.setText(""+dfc.format(((f-32)/1.8)));
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtdoC.getText().clear();
                edtdoF.getText().clear();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}