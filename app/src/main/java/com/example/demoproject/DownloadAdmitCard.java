package com.example.demoproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class DownloadAdmitCard extends AppCompatActivity{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView admitCardImageImageView,admitCardSignatureImageView;
    TextView admitCardApplicantIDEditText,admitCardNameEditText,admitCardhscRegistrationEditText,admitCardGroupEditText,admitCardExamCentreEditText;
    LinearLayout showanddownloadAdmitCardLayout;
    DatabaseReference databaseReference;
    String email;
    DatabaseError databaseError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_admit_card);
        this.setTitle("Admit Card");
        drawerLayout=(DrawerLayout)findViewById(R.id.DownloadAdmitCardDrawLayoutId);
        admitCardImageImageView=(ImageView)findViewById(R.id.AdmitCardImageImageViewId);
        admitCardSignatureImageView=(ImageView)findViewById(R.id.AdmitCardSignatureImageViewId);
        showanddownloadAdmitCardLayout = findViewById(R.id.ShowandDownloadAdmitCardLayoutId);
        admitCardApplicantIDEditText=(TextView) findViewById(R.id.AdmitCardApplicantIDEditTextId);
        admitCardNameEditText=(TextView) findViewById(R.id.AdmitCardNameEditTextId);
        admitCardhscRegistrationEditText=(TextView) findViewById(R.id.AdmitCardhscRegistrationEditTextId);
        admitCardGroupEditText=(TextView)findViewById(R.id.AdmitCardGroupEditTextId);
        admitCardExamCentreEditText=(TextView)findViewById(R.id.AdmitCardExamCentreEditTextId);
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
                        String imageUri = dataSnapshot.child("imageUri").getValue(String.class);
                        if (!TextUtils.isEmpty(imageUri)) {
                            Picasso.get().load(imageUri).placeholder(R.mipmap.ic_launcher).fit().into(admitCardImageImageView);
                        }
                        String signatureUri = dataSnapshot.child("signatureUri").getValue(String.class);
                        if (!TextUtils.isEmpty(signatureUri)) {
                            Picasso.get().load(signatureUri).placeholder(R.mipmap.ic_launcher).fit().into(admitCardSignatureImageView);
                        }
                        admitCardApplicantIDEditText.setText(""+dataSnapshot.child("id").getValue(String.class));
                        admitCardNameEditText.setText(""+dataSnapshot.child("name").getValue(String.class));
                        admitCardhscRegistrationEditText.setText(""+dataSnapshot.child("hscRegistrationNumber").getValue(String.class));
                        admitCardGroupEditText.setText(""+dataSnapshot.child("group").getValue(String.class));
                        admitCardExamCentreEditText.setText(""+dataSnapshot.child("examCentre").getValue(String.class));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public  void  admitCardPdf()
    {
        Bitmap downloadAdmitCardImage = Bitmap.createBitmap(showanddownloadAdmitCardLayout.getWidth(), showanddownloadAdmitCardLayout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(downloadAdmitCardImage);
        showanddownloadAdmitCardLayout.draw(canvas);
        PdfDocument pdfDocument=new PdfDocument();
        PdfDocument.PageInfo mypageinfo=new PdfDocument.PageInfo.Builder(showanddownloadAdmitCardLayout.getWidth(),showanddownloadAdmitCardLayout.getHeight(),1).create();
        PdfDocument.Page page=pdfDocument.startPage(mypageinfo);
        page.getCanvas().drawBitmap(downloadAdmitCardImage,0,0,null);
        pdfDocument.finishPage(page);
        String directory=Environment.getExternalStorageDirectory().getPath();
        String pdffile=directory+"/AdmitCard.pdf";
        File mypdfFile=new File(pdffile);
        try {
            pdfDocument.writeTo(new FileOutputStream(mypdfFile));
            Toast.makeText(getApplicationContext(),"download successful",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"download unsuccessful",Toast.LENGTH_SHORT).show();
        }
        pdfDocument.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.downloadmenulayout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.downloadiconid)
        {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
            int permission=ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permission!=PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getApplicationContext(),"denied",Toast.LENGTH_LONG).show();

                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
            admitCardPdf();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}