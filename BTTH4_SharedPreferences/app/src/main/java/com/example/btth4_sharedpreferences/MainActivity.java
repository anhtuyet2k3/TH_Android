package com.example.btth4_sharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText editA, editB, editKQ;
    TextView tvHistory;
    Button btnClear, btnTotal;
    String history = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editA = findViewById(R.id.editA);
        editB = findViewById(R.id.editB);
        editKQ = findViewById(R.id.editKQ);
        btnClear = findViewById(R.id.btnClear);
        btnTotal = findViewById(R.id.btnTotal);
        tvHistory = findViewById(R.id.tvHistory);

        // tạo hoặc lấy đối tượng SharedPreferences với tên là mysave, để chế độ đọc là private
        SharedPreferences myprefs = getSharedPreferences("mysave", MODE_PRIVATE);
        // lấy String từ đối tượng myprefs với key: ls nếu không có thì mặc định để rỗng
        history = myprefs.getString("ls","");
        tvHistory.setText(history);

        btnTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int a = Integer.parseInt(editA.getText().toString());
                    int b = Integer.parseInt(editB.getText().toString());
                    int kq = a + b;
                    editKQ.setText(kq+"");
                    // định dạng form hiển thị của history
                    history = history + a + " + " + b + " = " + kq + "\n";
                    tvHistory.setText(history);
                }catch (NumberFormatException e){
                    Toast.makeText(MainActivity.this, "Số nhập chưa chính xác", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               history="";
               tvHistory.setText(history);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences myprefs = getSharedPreferences("mysave", MODE_PRIVATE);
        // tạo 1 đối tượng chỉnh sửa trong SharePreferences
        SharedPreferences.Editor myedit = myprefs.edit();
        // put phần chỉnh sửa của history với key:ls vào SharedPreferences
        myedit.putString("ls",history);
        myedit.commit();
    }
}