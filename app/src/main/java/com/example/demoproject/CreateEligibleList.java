package com.example.demoproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateEligibleList extends AppCompatActivity {
    LinearLayout eligiblelistdownloadlayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    RecyclerView recyclerView;
    eligiblelistadapter adapter;
    List<EligibleListStudents> uploadlist;
    DatabaseReference databaseReference;
    DatabaseError databaseError;
    Intent myFileIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_eligible_list);
        this.setTitle("Eligible list");
        eligiblelistdownloadlayout= findViewById(R.id.EligibleListTableLayoutId);
        recyclerView=findViewById(R.id.eligiblelistrecylerViewId);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recylerdividerlayout));
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        uploadlist=new ArrayList<>();
        adapter=new eligiblelistadapter(getApplicationContext(),uploadlist);
        databaseReference= FirebaseDatabase.getInstance().getReference("Students");
    }

    @Override
    protected void onStart() {
        databaseReference.orderByChild("negativehscTotalMarks").limitToFirst(6).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                long l=1;
                uploadlist.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    String str = Long.toString(l);
                    l++;
                    EligibleListStudents eligibleListStudents=new EligibleListStudents(str,dataSnapshot.child("id").getValue(String.class),dataSnapshot.child("name").getValue(String.class));
                    uploadlist.add(eligibleListStudents);
                }
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        super.onStart();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public  void  eligiblelistpdf()
    {
        Bitmap downloadeligibleImage = Bitmap.createBitmap(eligiblelistdownloadlayout.getWidth(), eligiblelistdownloadlayout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(downloadeligibleImage);
        eligiblelistdownloadlayout.draw(canvas);
        PdfDocument pdfDocument=new PdfDocument();
        PdfDocument.PageInfo mypageinfo=new PdfDocument.PageInfo.Builder(eligiblelistdownloadlayout.getWidth(),eligiblelistdownloadlayout.getHeight(),10).create();
        PdfDocument.Page page=pdfDocument.startPage(mypageinfo);
        page.getCanvas().drawBitmap(downloadeligibleImage,0,0,null);
        pdfDocument.finishPage(page);
        String directory= Environment.getExternalStorageDirectory().getPath();
        /*myFileIntent=new Intent(Intent.ACTION_GET_CONTENT);
        myFileIntent.setType("*");
        startActivityForResult(myFileIntent,10);*/
        String pdffile=directory+"/eligibleList.pdf";
        File mypdfFile=new File(pdffile);
        try {
            pdfDocument.writeTo(new FileOutputStream(mypdfFile));
            Toast.makeText(getApplicationContext(),"download successful",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
           Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        pdfDocument.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        switch (requestCode)
        {
            case 10:
                if(resultCode==RESULT_OK)
                {
                    String path=data.getData().getPath();
                    Toast.makeText(getApplicationContext(),path,Toast.LENGTH_LONG).show();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
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
             ActivityCompat.requestPermissions(CreateEligibleList.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
             int permission=ActivityCompat.checkSelfPermission(CreateEligibleList.this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
             if(permission!=PackageManager.PERMISSION_GRANTED)
             {
                 Toast.makeText(getApplicationContext(),"denied",Toast.LENGTH_LONG).show();

                 ActivityCompat.requestPermissions(CreateEligibleList.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
             }
             eligiblelistpdf();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}