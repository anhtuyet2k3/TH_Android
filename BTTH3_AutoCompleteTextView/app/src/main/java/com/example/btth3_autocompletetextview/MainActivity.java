package com.example.btth3_autocompletetextview;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView txt_title;
    AutoCompleteTextView autoCompleteTextView;
    String array[] = {"Hà Nội", "Huế", "Hà Giang", "Hà Nam","Hưng Yên", "Tuyên Quang", "Thái Bình", "Hải Duơng", "Hòa Bình", "Đà Nẵng"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        txt_title = findViewById(R.id.txt_Title);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, array);
        autoCompleteTextView.setAdapter(myadapter);
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txt_title.setText(autoCompleteTextView.getText());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}