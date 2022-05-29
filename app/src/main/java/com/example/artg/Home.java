package com.example.artg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {
    ImageView imagelogout,imageupload, imagereport, imagepurchase, imageregistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imagelogout = (ImageView) findViewById(R.id.imageLogout);
        imageupload = (ImageView) findViewById(R.id.imageUpload);
        imagereport = (ImageView) findViewById(R.id.report);
       imagepurchase = (ImageView) findViewById(R.id.purchase2);
       imageregistered = (ImageView) findViewById(R.id.registered);
        imagelogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        imageupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Upload.class));
            }
        });
        imagereport = (ImageView) findViewById(R.id.report);

        imagereport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Report.class));

            }
        });
        imagepurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Purchases.class));
            }
        });
        imageregistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Registered.class));
            }
        });
    }
}