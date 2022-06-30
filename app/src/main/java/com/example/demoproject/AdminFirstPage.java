package com.example.demoproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class AdminFirstPage extends AppCompatActivity implements View.OnClickListener {
    CardView adminCheckStudentListButton,adminUpdateIndividualResultButton,adminPublishResultButton,adminemailEligibleCandidateButton,publishEligibleListButton;
    DatabaseReference databaseReference,studentdatabaseReference;
    DatabaseError databaseError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_first_page);
        adminCheckStudentListButton=findViewById(R.id.AdminCheckStudentListButtonId);
        adminUpdateIndividualResultButton=findViewById(R.id.AdminUpdateIndividualResultId);
        adminPublishResultButton=findViewById(R.id.AdminResultPublishId);
        adminemailEligibleCandidateButton=findViewById(R.id.AdminEmailEligibleCandidatesId);
        publishEligibleListButton=findViewById(R.id.PublishEligibleListButtonId);
        databaseReference= FirebaseDatabase.getInstance().getReference("Admin");
        studentdatabaseReference= FirebaseDatabase.getInstance().getReference("Students");
        adminCheckStudentListButton.setOnClickListener(this);
        adminUpdateIndividualResultButton.setOnClickListener(this);
        adminPublishResultButton.setOnClickListener(this);
        adminemailEligibleCandidateButton.setOnClickListener(this);
        publishEligibleListButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.AdminCheckStudentListButtonId)
        {
            Intent intent = new Intent(getApplicationContext(),ShowAllStudentDataActivity.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.AdminUpdateIndividualResultId)
        {
            Intent intent = new Intent(getApplicationContext(),UpdateMeritPosition.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.AdminResultPublishId)
        {
            go();
        }
        else if(v.getId()==R.id.AdminEmailEligibleCandidatesId)
        {
            emailEligibleCandidates();
        }
        else if(v.getId()==R.id.PublishEligibleListButtonId)
        {
            Intent intent = new Intent(getApplicationContext(),CreateEligibleList.class);
            startActivity(intent);
        }
    }

    private void emailEligibleCandidates() {

        studentdatabaseReference.orderByChild("negativehscTotalMarks").limitToFirst(6).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String students="";
                String subject= getResources().getString(R.string.emailsubject);
                String message=getResources().getString(R.string.emailmessage);
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    if(!TextUtils.isEmpty(students)){
                    students=students+","+dataSnapshot.child("email").getValue().toString().trim();}
                    else
                    {
                        students=students+dataSnapshot.child("email").getValue().toString().trim();
                    }
                }

                String[] to=students.split(",");
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL,to);
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void go() {
        String publish="publish";
        String notpublish="not publish";
        String email;
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            email= bundle.getString("email");
        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("isResultPublish").getValue().equals(notpublish)) {
                        dataSnapshot.getRef().child("isResultPublish").setValue(publish);
                        Toast.makeText(getApplicationContext(),"Result is published",Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = new Intent(getApplicationContext(),CreatingMeritList.class);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.adminsignoutmenulayout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.Adminsignoutid)
        {
            FirebaseAuth mAuth;
            mAuth = FirebaseAuth.getInstance();
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent=new Intent(getApplicationContext(),AdminSignIn.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}