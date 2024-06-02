package com.example.phonelist;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    String myphone[] =  {"Iphone 7","SamSung Galaxy S7", "Nokia Lumia 730", "Sony Xperia XZ", "HTC One E9"};
    ArrayAdapter<String> myadapter;
    ListView lv1;
    TextView txt_select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Ánh xạ Id
        txt_select = findViewById(R.id.txt_select);
        lv1 = findViewById(R.id.lv1);
        // adapter - simpelist - arraylist
        myadapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, myphone);
        lv1.setAdapter(myadapter);
        // Bắt sự kiện click khi nhấn vào 1 dòng trong listview
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txt_select.setText("Vị trí "+position+" : "+myphone[position]);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}