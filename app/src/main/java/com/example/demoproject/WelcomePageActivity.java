package com.example.demoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class WelcomePageActivity extends AppCompatActivity {
    ProgressBar progressBar;
    private  int progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        progressBar=(ProgressBar)findViewById(R.id.firstpageprogressbarid);
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
                startApp();
            }
        });
        thread.start();
    }

    private void startApp() {
        Intent intent=new Intent(getApplicationContext(),UserPageActivity.class);
        startActivity(intent);
        finish();
    }

    private void doWork() {
        for(progress=20;progress<=100;progress+=20)
        {
            try {
                Thread.sleep(1000);
                progressBar.setProgress(progress);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}