package com.example.demoproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class AdminSignIn extends AppCompatActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private EditText signinEmailEditText,signinPasswordEditText;
    private Button signinButton;
    private TextView signupTextview;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_in);
        this.setTitle("Sign in");
        drawerLayout=(DrawerLayout)findViewById(R.id.AdminSignDrawLayoutId);
        actionBarDrawerToggle=new ActionBarDrawerToggle(AdminSignIn.this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=(NavigationView)findViewById(R.id.adminsignnavigationViewId);
        navigationView.setNavigationItemSelectedListener(this);
        signinEmailEditText=(EditText)findViewById(R.id.AdminsigninEmailId);
        signinPasswordEditText=(EditText)findViewById(R.id.AdminsigninPasswordId);
        signinButton=(Button)findViewById(R.id.AdminsigninButtonId);
        signupTextview=(TextView)findViewById(R.id.AdminsignupTextviewId);
        progressBar=(ProgressBar)findViewById(R.id.AdminSigninprogressbardId);
        mAuth = FirebaseAuth.getInstance();
        signinButton.setOnClickListener(this);
        signupTextview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.AdminsigninButtonId)
        {
            signIn();
        }
        else if(v.getId()==R.id.AdminsignupTextviewId)
        {
            Intent intent=new Intent(AdminSignIn.this,AdminSignUp.class);
            startActivity(intent);

        }
    }

    private void signIn() {
        String email = signinEmailEditText.getText().toString().trim();
        String password = signinPasswordEditText.getText().toString().trim();
        if (email.isEmpty()) {
            signinEmailEditText.setError("Please enter an email account");
            signinEmailEditText.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signinEmailEditText.setError("Please enter a valid email");
            signinEmailEditText.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            signinPasswordEditText.setError("Enter a password");
            signinPasswordEditText.requestFocus();
            return;
        }

        if (password.length() < 6) {
            signinPasswordEditText.setError("Minimum length of password should be 6");
            signinPasswordEditText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    String email=signinEmailEditText.getText().toString().trim();
                    String[] split = email.split("@");
                    String domain = split[1]; //This Will Give You The Domain After '@'
                    if(domain.equals("admission.kuet.ac.bd"))
                    {
                        finish();
                        Intent intent=new Intent(getApplicationContext(),AdminFirstPage.class);
                        intent.putExtra("email",""+email);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else
                    {
                        signinEmailEditText.setError("Please enter email with domain admission.kuet.ac.bd");
                        signinEmailEditText.requestFocus();
                        return;
                    }


                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Log in unsuccessful",Toast.LENGTH_SHORT).show();
                }
            }
        });
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {
        if(item.getItemId()==R.id.UseasapplicantNavigationId)
        {
            Intent intent=new Intent(getApplicationContext(),UserPageActivity.class);
            startActivity(intent);
        }
        return false;
    }
}