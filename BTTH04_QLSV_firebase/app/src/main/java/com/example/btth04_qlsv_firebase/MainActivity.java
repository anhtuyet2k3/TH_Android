package com.example.btth04_qlsv_firebase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edtName, edtEmail;
    private Button btnAdd, btnUp, btnDel;
    private ListView lsvStudent ;
    private List<Student> studentList;
    private FirebaseDatabaseHelper databaseHelper;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        btnAdd = findViewById(R.id.btnAdd);
        btnUp = findViewById(R.id.btnUpdate);
        btnDel = findViewById(R.id.btnDel);
        lsvStudent = findViewById(R.id.lsvStudents);
        studentList = new ArrayList<>();
        databaseHelper = new FirebaseDatabaseHelper();

        databaseReference = FirebaseDatabase.getInstance("https://btth04-qlsv-firebase-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Log.d("Data from database",database+"");
        Log.d("Data from databaseReference",databaseReference+"");
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStudent();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStudent();
            }
        });
        loadStudents();

    }

    private void loadStudents() {
        databaseHelper.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                studentList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Student student = postSnapshot.getValue(Student.class);
                    studentList.add(student);
                }
                // Update ListView adapter here
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void deleteStudent() {
        String id = getSeleltedStudent();
        databaseHelper.deleteStudent(id);
    }

    private void updateStudent() {
        String id = getSeleltedStudent();
        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        Student student = new Student(id,name, email);
        databaseHelper.updateStudent(id,student);
    }

    private void addStudent() {
        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        String id = databaseHelper.getReference().push().getKey();
        Student student = new Student(id,name, email);
        databaseHelper.addStudent(student);
    }

    public String getSeleltedStudent() {
        int selectedPosition = lsvStudent.getCheckedItemPosition(); // or recyclerView.getSelectedPosition()
        if (selectedPosition != ListView.INVALID_POSITION) {
            Student selectedStudent = (Student) lsvStudent.getItemAtPosition(selectedPosition);
            return selectedStudent.getId();
        }
        return null;
    }
}