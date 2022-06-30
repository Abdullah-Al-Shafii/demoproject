package com.example.demoproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ShowAllStudentDataActivity extends AppCompatActivity {
    private ListView showdatalistView;
    DatabaseReference databaseReference;
    private List<Students> studentsList;
    private Adapter showdataadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_student_data);
        this.setTitle("Applicants' data");
        studentsList=new ArrayList<>();
        showdataadapter=new Adapter(ShowAllStudentDataActivity.this,studentsList);
        showdatalistView=(ListView)findViewById(R.id.showDatalistViewId);
        databaseReference= FirebaseDatabase.getInstance().getReference("Students");
    }

    @Override
    protected void onStart() {
        databaseReference.orderByChild("negativehscTotalMarks").endBefore(640).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                studentsList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                   Students students=new Students(""+dataSnapshot.child("id").getValue(String.class),""+dataSnapshot.child("name").getValue(String.class),""+dataSnapshot.child("hscRegistrationNumber").getValue(String.class),""+dataSnapshot.child("examCentre").getValue(String.class),""+dataSnapshot.child("hscGPA").getValue(String.class),dataSnapshot.child("hscTotalMarks").getValue(Double.class));
                    studentsList.add(students);
                }
                showdatalistView.setAdapter(showdataadapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        super.onStart();
    }
}