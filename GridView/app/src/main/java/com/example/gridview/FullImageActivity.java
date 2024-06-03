package com.example.gridview;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class FullImageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        ImageView imageView = findViewById(R.id.full_image_view);
        int imageId = getIntent().getIntExtra("image_id", -1);
        if (imageId != -1) {
            imageView.setImageResource(imageId);
        }
    }

    public void onBackClicked(View view) {
        finish();
    }
}
