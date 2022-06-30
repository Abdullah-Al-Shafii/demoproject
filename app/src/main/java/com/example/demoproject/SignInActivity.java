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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener ,NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private EditText signinEmailEditText,signinPasswordEditText;
    private Button signinButton;
    private TextView signupTextview;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        this.setTitle("Sign in");
        drawerLayout=(DrawerLayout)findViewById(R.id.SignDrawLayoutId);
        actionBarDrawerToggle=new ActionBarDrawerToggle(SignInActivity.this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=(NavigationView)findViewById(R.id.signnavigationViewId);
        navigationView.setNavigationItemSelectedListener(this);
        signinEmailEditText=(EditText)findViewById(R.id.signinEmailId);
        signinPasswordEditText=(EditText)findViewById(R.id.signinPasswordId);
        signinButton=(Button)findViewById(R.id.signinButtonId);
        signupTextview=(TextView)findViewById(R.id.signupTextviewId);
        progressBar=(ProgressBar)findViewById(R.id.progressbardId);
        mAuth = FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference("Students");
        signinButton.setOnClickListener(this);
        signupTextview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.signinButtonId)
        {
            signIn();
        }
        else if(v.getId()==R.id.signupTextviewId)
        {
            Intent intent=new Intent(getApplicationContext(),SignUpActivity.class);
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
                    Intent intent=new Intent(getApplicationContext(),UserFirstPage.class);
                    String  email=signinEmailEditText.getText().toString().trim();
                    intent.putExtra("email",""+email);
                    finish();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Error : "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
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
        if(item.getItemId()==R.id.GoHomePageNavigationId)
        {
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.UseasadminNavigationId)
        {

            Intent intent=new Intent(getApplicationContext(),UserPageActivity.class);
            startActivity(intent);
        }
        return false;
    }


}
