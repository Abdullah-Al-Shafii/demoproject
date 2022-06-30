package com.example.demoproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

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

public class CreatingMeritList extends AppCompatActivity {
    LinearLayout meritlistdownloadlayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    RecyclerView recyclerView;
    meritlistadapter adapter;
    List<MeritListStudents> muploadlist;
    DatabaseReference databaseReference;
    DatabaseError databaseError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_merit_list);
        this.setTitle("Merit list");
        meritlistdownloadlayout= findViewById(R.id.MeritListTableLayoutId);
        recyclerView=findViewById(R.id.meritlistrecylerViewId);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recylerdividerlayout));
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        muploadlist=new ArrayList<>();
        adapter= new meritlistadapter(getApplicationContext(),muploadlist);
        databaseReference= FirebaseDatabase.getInstance().getReference("Students");
    }

    @Override
    protected void onStart() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            String notpublish="Yet not Publish";
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    if (dataSnapshot.child("meritPosition").getValue().equals(notpublish)) {
                    }
                    else
                    {
                        MeritListStudents meritListStudents= new MeritListStudents(""+dataSnapshot.child("id").getValue(String.class), ""+dataSnapshot.child("meritPosition").getValue(String.class));
                        muploadlist.add(meritListStudents);
                    }
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
        Bitmap downloadmeritImage = Bitmap.createBitmap(meritlistdownloadlayout.getWidth(), meritlistdownloadlayout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(downloadmeritImage);
        meritlistdownloadlayout.draw(canvas);
        PdfDocument pdfDocument=new PdfDocument();
        PdfDocument.PageInfo mypageinfo=new PdfDocument.PageInfo.Builder(meritlistdownloadlayout.getWidth(),meritlistdownloadlayout.getHeight(),10).create();
        PdfDocument.Page page=pdfDocument.startPage(mypageinfo);
        page.getCanvas().drawBitmap(downloadmeritImage,0,0,null);
        pdfDocument.finishPage(page);
        String directory= Environment.getExternalStorageDirectory().getPath();
        String pdffile=directory+"/MeritList.pdf";
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
            ActivityCompat.requestPermissions(CreatingMeritList.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
            int permission=ActivityCompat.checkSelfPermission(CreatingMeritList.this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permission!=PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getApplicationContext(),"denied",Toast.LENGTH_LONG).show();

                ActivityCompat.requestPermissions(CreatingMeritList.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
            eligiblelistpdf();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}