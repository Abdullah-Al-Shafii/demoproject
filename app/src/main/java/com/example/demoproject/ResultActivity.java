package com.example.demoproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class ResultActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    TextView resultApplicantIDEditText,resultNameEditText,resulthscRegistrationEditText,resultGroupEditText,resultQuotaEditText,resultMeritPositionEditText;
    Button downloadResultButton;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    LinearLayout showanddownloadResultLayout;
    Bitmap downloadResultImage;
    DatabaseReference databaseReference;
    String email;
    DatabaseError databaseError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        this.setTitle("Result");
        showanddownloadResultLayout = findViewById(R.id.ShowandDownloadResultLayoutId);
        resultApplicantIDEditText=(TextView)findViewById(R.id.ResultApplicantIDEditTextId);
        resultNameEditText=(TextView)findViewById(R.id.ResultNameEditTextId);
        resulthscRegistrationEditText=(TextView)findViewById(R.id.ResulthscRegistrationEditTextId);
        resultGroupEditText=(TextView)findViewById(R.id.ResultGroupEditTextId);
        resultQuotaEditText=(TextView)findViewById(R.id.ResultQuotaEditTextId);
        resultMeritPositionEditText=(TextView)findViewById(R.id.ResultMeritPositionEditTextId);
        downloadResultButton=(Button) findViewById(R.id.DownloadResultButtonId);
        databaseReference= FirebaseDatabase.getInstance().getReference("Students");
        drawerLayout=(DrawerLayout)findViewById(R.id.ResultDrawLayoutId);
        actionBarDrawerToggle=new ActionBarDrawerToggle(ResultActivity.this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=(NavigationView)findViewById(R.id.navigationViewId);
        navigationView.setNavigationItemSelectedListener(this);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            email= bundle.getString("email");
        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("email").getValue().equals(email)) {
                        resultApplicantIDEditText.setText(""+dataSnapshot.child("id").getValue(String.class));
                        resultNameEditText.setText(""+dataSnapshot.child("name").getValue(String.class));
                        resulthscRegistrationEditText.setText(""+dataSnapshot.child("hscRegistrationNumber").getValue(String.class));
                        resultGroupEditText.setText(""+dataSnapshot.child("group").getValue(String.class));
                        resultQuotaEditText.setText(""+dataSnapshot.child("quota").getValue(String.class));
                        resultMeritPositionEditText.setText(""+dataSnapshot.child("meritPosition").getValue(String.class));

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        downloadResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadResultImage = Bitmap.createBitmap(showanddownloadResultLayout.getWidth(), showanddownloadResultLayout.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(downloadResultImage);
                showanddownloadResultLayout.draw(canvas);
                if(isStoragePermissionGranted()){
                    SaveImage(downloadResultImage);
                }

            }
        });
    }

    private void SaveImage(Bitmap finalBitmap) {
        /*"/storage/emulated/0/Download"*/
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File("/"+root+"/Download");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_LONG).show();
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        /*progressBar.setVisibility(View.GONE);*/
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(ResultActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else {
            return true;
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            case 2: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
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
                            if (dataSnapshot.child("paymentStatus").getValue().equals(notpaid)) {
                                Intent intent = new Intent(getApplicationContext(), PaymentMethodActivity.class);
                                intent.putExtra("email", "" + email);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"You have already paid your fees", Toast.LENGTH_LONG).show();
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
        else if(item.getItemId()==R.id.DownloadAdmitCardNavigationId)
        {
            Bundle bundle=getIntent().getExtras();
            if(bundle!=null)
            {
                email= bundle.getString("email");
            }
            Intent intent=new Intent(getApplicationContext(),DownloadAdmitCard.class);
            intent.putExtra("email",""+email);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.ResultNavigationId)
        {
            Bundle bundle=getIntent().getExtras();
            if(bundle!=null)
            {
                email= bundle.getString("email");
            }
            Intent intent=new Intent(getApplicationContext(),ResultActivity.class);
            intent.putExtra("email",""+email);
            startActivity(intent);
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


}