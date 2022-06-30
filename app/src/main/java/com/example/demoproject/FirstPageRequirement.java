package com.example.demoproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class FirstPageRequirement extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Button applyNowButton;
    DatabaseReference databaseReference;
    String email;
    DatabaseError databaseError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page_requirement);
        this.setTitle("Home Page");
        databaseReference= FirebaseDatabase.getInstance().getReference("Students");
        drawerLayout=(DrawerLayout)findViewById(R.id.FirstPageDrawLayoutId);
        actionBarDrawerToggle=new ActionBarDrawerToggle(FirstPageRequirement.this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=(NavigationView)findViewById(R.id.navigationViewId);
        navigationView.setNavigationItemSelectedListener(this);
        applyNowButton=(Button)findViewById(R.id.ApplyNowButtonId);
        applyNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndGo();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.usersignoutmenulayout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        else if(item.getItemId()==R.id.Usersignoutid)
        {
            FirebaseAuth mAuth;
            mAuth = FirebaseAuth.getInstance();
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent=new Intent(getApplicationContext(),SignInActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {
        if(item.getItemId()==R.id.ApplyNavigationId)
        {
            Bundle bundle=getIntent().getExtras();
            if(bundle!=null)
            {
                email= bundle.getString("email");
            }
            Intent intent=new Intent(getApplicationContext(),profileActivity.class);
            intent.putExtra("email",""+email);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.ProfileNavigationId)
        {
            Bundle bundle=getIntent().getExtras();
            if(bundle!=null)
            {
                email= bundle.getString("email");
            }
            Intent intent=new Intent(getApplicationContext(),profileActivity.class);
            intent.putExtra("email",""+email);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.PayFeesNavigationId)
        {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                email = bundle.getString("email");
            }
            String notpaid = "not paid yet";
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.child("email").getValue().equals(email)) {
                            String imageUri=dataSnapshot.child("imageUri").getValue().toString();

                          if (dataSnapshot.child("paymentStatus").getValue().equals(notpaid)) {
                                Intent intent = new Intent(getApplicationContext(), PaymentMethodActivity.class);
                                intent.putExtra("email", "" + email);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"You have already paid your fees", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }


            });

        }
        else if(item.getItemId()==R.id.DownloadAdmitCardNavigationId)
        {
            Bundle bundle=getIntent().getExtras();
            if(bundle!=null)
            {
                email= bundle.getString("email");
            }
            String notpaid = "not paid yet";
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.child("email").getValue().equals(email)) {
                            if (dataSnapshot.child("paymentStatus").getValue().equals(notpaid)) {
                                Toast.makeText(getApplicationContext(),"First complete applying and paying fees", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Intent intent=new Intent(getApplicationContext(),DownloadAdmitCard.class);
                                intent.putExtra("email",""+email);
                                startActivity(intent);
                            }
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }


            });
        }
        else if(item.getItemId()==R.id.ResultNavigationId) {
            String publish = "publish";
            String notpublish = "not publish";
            DatabaseReference mdatabaseReference;
            DatabaseError mdatabaseError;
            mdatabaseReference = FirebaseDatabase.getInstance().getReference("Admin");
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                email = bundle.getString("email");
            }
            mdatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.child("isResultPublish").getValue().equals(publish)) {
                            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                            intent.putExtra("email", "" + email);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Result is not yet published", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
        else if(item.getItemId()==R.id.LogOutNavigationId)
        {
            FirebaseAuth mAuth;
            mAuth = FirebaseAuth.getInstance();
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent=new Intent(getApplicationContext(),SignInActivity.class);
            startActivity(intent);
        }
        return false;
    }

    private void saveAndGo() {
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            email= bundle.getString("email");
        }
        Intent intent=new Intent(getApplicationContext(),profileActivity.class);
        intent.putExtra("email",""+email);
        startActivity(intent);
    }


}