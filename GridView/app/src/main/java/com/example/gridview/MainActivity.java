package com.example.gridview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Integer[] img={R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f };
    TextView textView;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.selection);
        gridView = findViewById(R.id.gridView);

        // Gán data vào ImageAdapter
        ImageAdapter adapter = new ImageAdapter(this, img);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Chuyển sang Activity khác để hiển thị hình ảnh lớn
                Intent intent = new Intent(MainActivity.this, FullImageActivity.class);
                intent.putExtra("image_id", img[position]);
                startActivity(intent);
            }
        });
//        // Gán data vào ArrayAdapter
//        ArrayAdapter<String> mydapter = new ArrayAdapter<String>(this, , arr);
//        //gán Datasource vào GridView
//        gridView.setAdapter(mydapter);
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                textView.setText(arr[position]);    // hiển thị phần tử được chọn
//            }
//        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}