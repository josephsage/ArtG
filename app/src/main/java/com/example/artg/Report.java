package com.example.artg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Report extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteRef = db.collection("Uploads").document("MRWwQYNF7tNRuxXIMMtIZSW15EC3").collection("Uploads").document();
    private TextView textView;
    private static final String title = "Title";
    private String artist = "Artist";
    private String phone = "Phone";
    ImageView imagereport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        textView = findViewById(R.id.one);

    }

    @Override
    protected void onStart() {
        super.onStart();
        noteRef.addSnapshotListener(this,new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error !=null) {

                }
                if (value.exists()) {
                    String Title = value.getString(title);
                    String Artist = value.getString(artist);
                    String Phone = value.getString(phone);
                    textView.setText("Title: " + title + " \n" + "Phone: " + phone);
                }
            }
        });
    }

    public void loadNote(View view){
        noteRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                       if (documentSnapshot.exists()){
                           String Title = documentSnapshot.getString(title);
                           String Artist = documentSnapshot.getString(artist);
                           String Phone = documentSnapshot.getString(phone);
                           textView.setText("Title: "+ title +" \n" + "Phone: " + phone );


                       }else {
                           Toast.makeText(Report.this, "not available", Toast.LENGTH_SHORT).show();
                       }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
}