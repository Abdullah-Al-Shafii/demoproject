package com.example.demoproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class PaymentMethodActivity extends AppCompatActivity implements View.OnClickListener {
    CardView bkashCardView,rocketCardView,nagadCardView,surecashCardView;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        bkashCardView=findViewById(R.id.bkashCardViewid);
        rocketCardView=findViewById(R.id.rocketCardViewid);
        nagadCardView=findViewById(R.id.nagadCardViewid);
        surecashCardView=findViewById(R.id.surecashCardViewid);
        bkashCardView.setOnClickListener(this);
        rocketCardView.setOnClickListener(this);
        nagadCardView.setOnClickListener(this);
        surecashCardView.setOnClickListener(this);
    }

      @Override
       public void onClick(View v) {
        if(v.getId()==R.id.bkashCardViewid)
        {
            Bundle bundle=getIntent().getExtras();
            if(bundle!=null)
            {
                email= bundle.getString("email");
            }
           Intent intent=new Intent(getApplicationContext(),BkashPaymentActivity.class);
            intent.putExtra("email",""+email);
            startActivity(intent);
        }
        else if(v.getId()==R.id.rocketCardViewid)
        {
               Bundle bundle=getIntent().getExtras();
                if(bundle!=null)
                {
                    email= bundle.getString("email");
                }
                Intent intent=new Intent(getApplicationContext(),RocketPaymentActivity.class);
                intent.putExtra("email",""+email);
                startActivity(intent);
        }
      /* else if(v.getId()==R.id.nagadCardViewid)
        {
          Intent intent=new Intent(getApplicationContext(),School.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.surecashCardViewid)
        {
            Intent intent=new Intent(getApplicationContext(),School.class);
            startActivity(intent);
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.usersignoutmenulayout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.Usersignoutid)
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
}