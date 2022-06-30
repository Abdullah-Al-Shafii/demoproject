 package com.example.demoproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText signupEmailEditText, signupPasswordEditText;
    private Button signupButton;
    private TextView signinTextview;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.setTitle("Sign up");
        mAuth = FirebaseAuth.getInstance();
        signupEmailEditText = (EditText) findViewById(R.id.signupEmailId);
        signupPasswordEditText = (EditText) findViewById(R.id.signupPasswordId);
        signupButton = (Button) findViewById(R.id.signupButtonId);
        signinTextview = (TextView) findViewById(R.id.signinTextviewId);
        progressBar = (ProgressBar) findViewById(R.id.progressbardId);
        databaseReference= FirebaseDatabase.getInstance().getReference("Students");
        signupButton.setOnClickListener(this);
        signinTextview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signupButtonId) {
            getSignup();
        } else if (v.getId() == R.id.signinTextviewId) {
            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(intent);

        }
    }

    private void getSignup() {
        String email = signupEmailEditText.getText().toString().trim();
        String password = signupPasswordEditText.getText().toString().trim();

        if (email.isEmpty()) {
            signupEmailEditText.setError("Please enter an email account");
            signupEmailEditText.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signupEmailEditText.setError("Please enter a valid email");
            signupEmailEditText.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            signupPasswordEditText.setError("Enter a password");
            signupPasswordEditText.requestFocus();
            return;
        }

        if (password.length() < 6) {
            signupPasswordEditText.setError("Minimum length of password should be 6");
            signupPasswordEditText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String email=signupEmailEditText.getText().toString().trim();
                    String hscRollNumber="",hscRegistrationNumber="",hscBoardName="",hscPassingYear="";
                    String sscRollNumber="",sscRegistrationNumber="",sscBoardName="",sscPassingYear="";
                    String name="",fatherName="",motherName="",permanentAdress="",presentAddress="";
                    String gender="",mobileNumber="",quota="";
                    String dateofBirth="";
                    String imageUri="",signatureUri="";
                    String guardianName="",relationwithgurdian="",gurdiancontactnumber="";
                    String group="",id="",hscGPA="",sscGPA="";
                    String key=databaseReference.push().getKey();
                    id=key;
                    String paymentStatus="not paid yet",paymentNumber="",transactionID="";
                    String examCentre="",birthCertificateNumber="",bloodGroup="";
                    String meritPosition="Yet not Publish";
                    String hscphysicsMarks="",hscchemistryMarks="",hscmathematicsMarks="",hscEnglishMarks="";
                    double hscTotalMarks=0.0,negativehscTotalMarks=0.0;
                    String eligibleStatus="not yet publish";
                    double hscMarksGPA=0.0;
                    StudentBasicInfo studentBasicInfo=new StudentBasicInfo(email,hscRollNumber,hscRegistrationNumber,hscBoardName,hscPassingYear,sscRollNumber,sscRegistrationNumber,sscBoardName,sscPassingYear,name,fatherName,motherName,permanentAdress,presentAddress,gender,mobileNumber,quota,dateofBirth,imageUri,signatureUri,guardianName,relationwithgurdian,gurdiancontactnumber,group,id,hscGPA,sscGPA,paymentStatus,paymentNumber,transactionID,examCentre,birthCertificateNumber,bloodGroup,meritPosition,hscphysicsMarks,hscchemistryMarks,hscmathematicsMarks,hscEnglishMarks,hscTotalMarks,negativehscTotalMarks,hscTotalMarks,eligibleStatus);
                    databaseReference.child(key).setValue(studentBasicInfo);
                    Intent intent=new Intent(getApplicationContext(),UserFirstPage.class);
                    intent.putExtra("email",""+email);
                    finish();
                    startActivity(intent);
                } else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();}
                }
            }
        });
        progressBar.setVisibility(View.GONE);
    }

}