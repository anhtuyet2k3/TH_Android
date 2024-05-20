package com.example.btth01_dialog_checkbox_radio;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText editName, editCM, editTTBSung;
    CheckBox cbDocBao, cbDocSach, cbDocCoding;
    RadioButton rdTrungCap, rdCaoDang, rdDaiHoc;
    RadioGroup group;
    Button btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.edtName);
        editCM = findViewById(R.id.edtCM);
        editTTBSung = findViewById(R.id.edtTTBSung);
        cbDocBao = findViewById(R.id.cbDocBao);
        cbDocSach = findViewById(R.id.cbDocSach);
        cbDocCoding = findViewById(R.id.cbDocCoding);
        rdTrungCap = findViewById(R.id.rdTrungCap);
        rdCaoDang = findViewById(R.id.rdCaoDang);
        rdDaiHoc = findViewById(R.id.rdDaiHoc);
        btnSend = findViewById(R.id.btnGui);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
            // TODO Auto-generated method stub
                doShowInformation();
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public  void doShowInformation() {
        // kiểm tra nhập tên hợp lệ
        String ten = editName.getText().toString();
        ten = ten.trim();
        if(ten.length()<3){
            editName.requestFocus();
            editName.selectAll();
            Toast.makeText(this,"Tên phải >= 3 ký tự", Toast.LENGTH_LONG).show();
            return;
        }
        // kiểm tra CMND hợp lệ
        String cmnd = editCM.getText().toString();
        cmnd = cmnd.trim();
        if(cmnd.length()!=9){
            editCM.requestFocus();
            editCM.selectAll();
            Toast.makeText(this, "CMND phải đúng 9 ký tự", Toast.LENGTH_LONG).show();
            return;
        }
        // Kiểm tra bằng cấp
        String bang ="";
        group = findViewById(R.id.group);
        int id = group.getCheckedRadioButtonId(); // trả về id
        if(id==-1){
            Toast.makeText(this,"Phải chọn bằng cấp",Toast.LENGTH_LONG).show();
            return;
        }

        RadioButton rad = findViewById(id);
        bang = rad.getText() +"";

        // Kiểm tra sở thích
        String sothich = "";
        //kiểm tra so thích phải chọn tối thiểu 1

            if(cbDocBao.isChecked()){
                sothich +=cbDocBao.getText()+"\n";
            }
            if(cbDocSach.isChecked()) {
                sothich += cbDocSach.getText() + "\n";
            }
            if(cbDocCoding.isChecked()) {
                sothich += cbDocCoding.getText() + "\n";
            }

        String bosung=editTTBSung.getText()+"";
        // Tạo Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông tin cá nhân");
        builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // Tạo nội dung
        String msg = ten +"\n" + cmnd +"\n" + bang +"\n"+ sothich +"\n----------------\n"+"Thông tin bổ sung:\n"+bosung+"\n"+"--------------";
        builder.setMessage(msg);    // thiết lập nội dung
        builder.create().show();    // Hiển thị dialog
    }
    public void  onBackPressed1(){
        AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
        b.setTitle("Question");
        b.setMessage("Are you sure you want to exit");
        b.setIcon(R.drawable.ic_launcher_background);
        b.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                finish();
            }
        });
        b.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        b.create().show();
    }
}