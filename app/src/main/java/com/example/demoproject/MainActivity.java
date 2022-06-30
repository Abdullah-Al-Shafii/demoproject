package com.example.demoproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView)findViewById(R.id.listViewId);
        String[] Information=getResources().getStringArray(R.array.Information);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(MainActivity.this,R.layout.samplelayout,R.id.textViewId,Information);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position==0)
                {
                    Intent intent=new Intent(getApplicationContext(),ReqiuirementsforAdmissionExam.class);
                    startActivity(intent);
                }
                else if(position==4)
                {
                    Intent intent=new Intent(getApplicationContext(),Date.class);
                    startActivity(intent);
                }
                else if(position==2)
                {
                    Intent intent=new Intent(getApplicationContext(),ExamSystemActivity.class);
                    startActivity(intent);
                }
                else if(position==5)
                {
                    Intent intent=new Intent(getApplicationContext(),Departments.class);
                    startActivity(intent);
                }
                else if(position==6)
                {
                    Intent intent=new Intent(getApplicationContext(),Halls.class);
                    startActivity(intent);
                }
                else if(position==7)
                {
                    Intent intent=new Intent(getApplicationContext(),History.class);
                    startActivity(intent);
                }
                else if(position==9)
                {
                    Intent intent=new Intent(getApplicationContext(),WebViewActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menulayout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.signinid)
        {
            Intent intent=new Intent(getApplicationContext(),SignInActivity.class);
            startActivity(intent);
            return true;
        }
        else if(item.getItemId()==R.id.signupid)
        {
            Intent intent=new Intent(getApplicationContext(),SignUpActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}