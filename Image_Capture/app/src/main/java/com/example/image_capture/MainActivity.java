package com.example.image_capture;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.ContentValues;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity {

    ImageView img;
    ImageButton btnCamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.imgGirl);
        btnCamera = findViewById(R.id.btnCamera);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(ACTION_IMAGE_CAPTURE);
                // Xác nhận từ user
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(MainActivity.this,new
                            String[]{android.Manifest.permission.CAMERA}, 1);
                    return;
                }
                startActivityForResult(myintent,55);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable
    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 55 && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(photo);
            saveImage(photo);
        }
    }

    private void saveImage(Bitmap bitmap) {
        //Khai báo một đối tượng OutputStream để ghi dữ liệu
        OutputStream fos;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Tạo một đối tượng ContentValues để lưu trữ các giá trị metadata cho ảnh
                ContentValues values = new ContentValues();
                // Đường dẫn tương đối nơi ảnh sẽ được lưu
                values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyApp");
                // Đặt trạng thái của ảnh là đang xử lý (đang được lưu)
                values.put(MediaStore.Images.Media.IS_PENDING, true);
                // Loại của ảnh
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                // Tên hiển thị của ảnh
                values.put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_" + System.currentTimeMillis() + ".jpg");
                // Chèn một bản ghi mới vào MediaStore và lấy URI của ảnh mới tạo
                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                // Mở một OutputStream tới URI của ảnh để ghi dữ liệu
                fos = getContentResolver().openOutputStream(uri);
                // Sau khi ghi xong, cập nhật lại trạng thái của ảnh là không còn đang xử lý
                values.put(MediaStore.Images.Media.IS_PENDING, false);
                getContentResolver().update(uri, values, null, null);
            } else {
                // Với các phiên bản Android cũ hơn (trước Android Q)
                // Lấy đường dẫn tới thư mục Pictures trên bộ nhớ ngoài
                String imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                // Tạo một File mới với tên file ảnh và đường dẫn xác định
                File image = new File(imagesDir, "IMG_" + System.currentTimeMillis() + ".jpg");
                // Mở một FileOutputStream tới file ảnh để ghi dữ liệu
                fos = new FileOutputStream(image);
            }
            // Nén Bitmap và ghi dữ liệu ảnh dưới dạng JPEG với chất lượng 100 vào OutputStream
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            // Đóng OutputStream
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}