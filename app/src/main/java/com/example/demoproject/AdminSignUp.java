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

public class AdminSignUp extends AppCompatActivity implements View.OnClickListener{
    private EditText signupEmailEditText, signupPasswordEditText;
    private Button signupButton;
    private TextView signinTextview;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_up);
        this.setTitle("Sign up");
        mAuth = FirebaseAuth.getInstance();
        signupEmailEditText = (EditText) findViewById(R.id.AdminsignupEmailId);
        signupPasswordEditText = (EditText) findViewById(R.id.AdminsignupPasswordId);
        signupButton = (Button) findViewById(R.id.AdminsignupButtonId);
        signinTextview = (TextView) findViewById(R.id.AdminsigninTextviewId);
        progressBar = (ProgressBar) findViewById(R.id.AdminSignUpprogressbardId);
        databaseReference= FirebaseDatabase.getInstance().getReference("Admin");
        signupButton.setOnClickListener(this);
        signinTextview.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.AdminsignupButtonId) {
            getSignup();
        } else if (v.getId() == R.id.AdminsigninTextviewId) {
            Intent intent = new Intent(AdminSignUp.this,AdminFirstPage.class);
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
                    String[] split = email.split("@");
                    String domain = split[1];
                    if(domain.equals("admission.kuet.ac.bd"))
                    {
                        String isResultPublish="not publish";
                        email=signupEmailEditText.getText().toString().trim();
                        String key=databaseReference.push().getKey();
                        Admin admin=new Admin(isResultPublish,email);
                        databaseReference.child(key).setValue(admin);
                        finish();
                        Intent intent=new Intent(getApplicationContext(),AdminFirstPage.class);
                        intent.putExtra("email",""+email);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else
                    {
                        signupEmailEditText.setError("Please enter email with domain admission.kuet.ac.bd");
                        signupEmailEditText.requestFocus();
                        return;
                    }


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