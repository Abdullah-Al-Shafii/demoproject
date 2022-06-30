package com.example.demoproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reginald.editspinner.EditSpinner;

import org.jetbrains.annotations.NotNull;

public class SSCInformationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    EditText sscRollnumberEditText,sscRegistrationNumberEditText;
    EditText sscGPAEditText;
    FloatingActionButton nextButton1;
    DatabaseReference databaseReference;
    String email;
    DatabaseError databaseError;
    EditSpinner sscboardspinner,sscpassingyearspinner;
    String[] sscboards,sscpassingyear;
    private  boolean isfirstselected=true;
    private boolean firstselected=true;
    String sscboard,sscpassyear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sscinformation);
        this.setTitle("SSC information");
       drawerLayout=(DrawerLayout)findViewById(R.id.SSCInformationDrawLayoutId);
        actionBarDrawerToggle=new ActionBarDrawerToggle(SSCInformationActivity.this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=(NavigationView)findViewById(R.id.navigationViewId);
        navigationView.setNavigationItemSelectedListener(this);
        sscRollnumberEditText=(EditText)findViewById(R.id.SSCRollNumberEditTextId);
        sscGPAEditText=(EditText)findViewById(R.id.SSCGPAEditTextId);
        sscRegistrationNumberEditText=(EditText)findViewById(R.id.SSCRegistrationNumberEditTextId);
        nextButton1=(FloatingActionButton)findViewById(R.id.nextbutton1id);
        databaseReference= FirebaseDatabase.getInstance().getReference("Students");
        sscboardspinner=(EditSpinner)findViewById(R.id.sscboardspinnerid);
        sscpassingyearspinner=(EditSpinner)findViewById(R.id.sscpassingyearspinnerid);
        sscboards=getResources().getStringArray(R.array.Boards);
        sscpassingyear=getResources().getStringArray(R.array.SSCpassingyears);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.spinnersamplelayout,R.id.spinnertextviewid,sscboards);
        sscboardspinner.setAdapter(adapter);

        sscboardspinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    if(isfirstselected==true)
                    {
                        isfirstselected=false;
                    }
                    else{
                        sscboard="";
                        Toast.makeText(getApplicationContext(),"Select a board properly",Toast.LENGTH_LONG).show();}

                }
                else{
                    sscboard=sscboards[position];
                    isfirstselected=false;
                }
            }
        });

        ArrayAdapter<String> adapter1=new ArrayAdapter<>(this,R.layout.spinnersamplelayout,R.id.spinnertextviewid,sscpassingyear);
        sscpassingyearspinner.setAdapter(adapter1);


        sscpassingyearspinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    if(firstselected==true)
                    {
                        firstselected=false;
                    }
                    else{
                        sscpassyear="";
                        Toast.makeText(getApplicationContext(),"Select a passing year properly",Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    sscpassyear = sscpassingyear[position];
                    firstselected=false;
                }
            }
        });


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
                        sscRollnumberEditText.setText(""+dataSnapshot.child("sscRollNumber").getValue(String.class));
                        sscRegistrationNumberEditText.setText(""+dataSnapshot.child("sscRegistrationNumber").getValue(String.class));
                        sscGPAEditText.setText(""+dataSnapshot.child("sscGPA").getValue(String.class));
                        sscboardspinner.setText(""+dataSnapshot.child("sscBoardName").getValue(String.class));
                        sscpassingyearspinner.setText(""+dataSnapshot.child("sscPassingYear").getValue(String.class));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        nextButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndGo();
            }
        });

    }


    private void saveAndGo() {
        String sscRoll=sscRollnumberEditText.getText().toString().trim();
        String sscRegistration=sscRegistrationNumberEditText.getText().toString().trim();
        String sscGPA=sscGPAEditText.getText().toString().trim();
        sscboard=sscboardspinner.getText().toString().trim();
        sscpassyear=sscpassingyearspinner.getText().toString().trim();
        double sscgpa=Double.parseDouble(sscGPA);

            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                email = bundle.getString("email");
            }
            if (!TextUtils.isEmpty(sscRoll) && !TextUtils.isEmpty(sscRegistration) && !TextUtils.isEmpty(sscboard) && !TextUtils.isEmpty(sscpassyear) && !TextUtils.isEmpty(sscGPA)) {

                if (sscgpa < 4.00) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SSCInformationActivity.this);
                    alertDialogBuilder.setTitle("Alert dialog");
                    alertDialogBuilder.setMessage("Sorry!you are not eligible.Do you want to see requirement?");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Bundle bundle = getIntent().getExtras();
                            if (bundle != null) {
                                email = bundle.getString("email");
                            }
                            Intent intent = new Intent(getApplicationContext(), FirstPageRequirement.class);
                            intent.putExtra("email", "" + email);
                            startActivity(intent);
                        }
                    });
                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Toast.makeText(SSCInformationActivity.this, "check your gpa again", Toast.LENGTH_SHORT).show();
                        }
                    });

                    alertDialogBuilder.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                if (dataSnapshot.child("email").getValue().equals(email)) {
                                    dataSnapshot.getRef().child("sscRollNumber").setValue(sscRoll);
                                    dataSnapshot.getRef().child("sscRegistrationNumber").setValue(sscRegistration);
                                    dataSnapshot.getRef().child("sscBoardName").setValue(sscboard);
                                    dataSnapshot.getRef().child("sscPassingYear").setValue(sscpassyear);
                                    dataSnapshot.getRef().child("sscGPA").setValue(sscGPA);
                                }

                            }
                            Intent intent = new Intent(getApplicationContext(), BasicInformationActivity.class);
                            intent.putExtra("email", "" + email);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }else {
                Toast.makeText(getApplicationContext(), "Enter data properly", Toast.LENGTH_SHORT).show();
            }
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
            Intent intent=new Intent(getApplicationContext(),FirstPageRequirement.class);
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

}