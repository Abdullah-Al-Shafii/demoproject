package com.example.demoproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class TotalNumberActivity extends AppCompatActivity {
    EditText hscphysicsmarksEditText,hscchemistrymarksEditText,hscmathematicsEditText,hscenglishmarksEditText;
    FloatingActionButton marksbutton;
    DatabaseReference databaseReference;
    String email;
    DatabaseError databaseError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_number);
        this.setTitle("HSC information");
        hscphysicsmarksEditText=(EditText)findViewById(R.id.HSCPhysicsEditTextId);
        hscchemistrymarksEditText=(EditText)findViewById(R.id.HSCChemistryEditTextId);
        hscmathematicsEditText=(EditText)findViewById(R.id.HSCMathematicsEditTextId);
        hscenglishmarksEditText=(EditText)findViewById(R.id.HSCEnglishEditTextId);
        marksbutton=(FloatingActionButton) findViewById(R.id.hscmarksbuttonid);
        databaseReference= FirebaseDatabase.getInstance().getReference("Students");
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
                        hscphysicsmarksEditText.setText(""+dataSnapshot.child("hscphysicsMarks").getValue(String.class));
                        hscchemistrymarksEditText.setText(""+dataSnapshot.child("hscchemistryMarks").getValue(String.class));
                        hscmathematicsEditText.setText(""+dataSnapshot.child("hscmathematicsMarks").getValue(String.class));
                        hscenglishmarksEditText.setText(""+dataSnapshot.child("hscEnglishMarks").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        marksbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndGo();
            }
        });
    }

    private void saveAndGo() {
        String hscphysicsMarks=hscphysicsmarksEditText.getText().toString().trim();
        String hscchemistryMarks=hscchemistrymarksEditText.getText().toString().trim();
        String hscmathematicsMarks=hscmathematicsEditText.getText().toString().trim();
        String hscEnglishMarks=hscenglishmarksEditText.getText().toString().trim();
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                email = bundle.getString("email");
            }
            if (!TextUtils.isEmpty(hscphysicsMarks) && !TextUtils.isEmpty(hscchemistryMarks) && !TextUtils.isEmpty(hscmathematicsMarks) && !TextUtils.isEmpty(hscEnglishMarks)) {
                double hscphysics = 0.0;
                hscphysics = Double.parseDouble(hscphysicsMarks);
                double hscchemistry = 0.0;
                hscchemistry = Double.parseDouble(hscphysicsMarks);
                double hscmathematics = 0.0;
                hscmathematics = Double.parseDouble(hscphysicsMarks);
                double hscEnglish = 0.0;
                hscEnglish = Double.parseDouble(hscphysicsMarks);
                double hscTotalMarks= hscphysics + hscchemistry + hscmathematics + hscEnglish;
                double negativehscTotalMarks=hscTotalMarks*(-1);
                if (hscphysics < 160 || hscchemistry < 160 || hscmathematics < 160 || hscEnglish < 160) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TotalNumberActivity.this);
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
                            finish();
                        }
                    });
                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Toast.makeText(TotalNumberActivity.this, "check your marks again", Toast.LENGTH_SHORT).show();
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
                                    double d=Double.parseDouble(""+dataSnapshot.child("hscGPA").getValue(String.class));
                                    d=d*(-1);
                                    dataSnapshot.getRef().child("hscphysicsMarks").setValue(hscphysicsMarks);
                                    dataSnapshot.getRef().child("hscchemistryMarks").setValue(hscchemistryMarks);
                                    dataSnapshot.getRef().child("hscmathematicsMarks").setValue(hscmathematicsMarks);
                                    dataSnapshot.getRef().child("hscEnglishMarks").setValue(hscEnglishMarks);
                                    dataSnapshot.getRef().child("hscTotalMarks").setValue(hscTotalMarks);
                                    dataSnapshot.getRef().child("negativehscTotalMarks").setValue(negativehscTotalMarks+d);
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent intent = new Intent(getApplicationContext(), SSCInformationActivity.class);
                    intent.putExtra("email", "" + email);
                    startActivity(intent);
                }

            }
            else {
                Toast.makeText(getApplicationContext(), "Enter data properly", Toast.LENGTH_SHORT).show();
            }
        }

    }