package com.example.artg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Account extends AppCompatActivity {
    TextView name, number;
    ProductsModel productsModel;
    FirebaseAuth fAuth;
    String userId;
    FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        name = findViewById(R.id.txt_account);
        number = findViewById(R.id.txt_account2);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        FirebaseUser firebaseUser = fAuth.getCurrentUser();
        userId = fAuth.getCurrentUser().getUid();
        fstore.collection("Users").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                String USER_NAME = snapshot.getString("Fullname");
                String email = snapshot.getString("Email");

                name.setText(USER_NAME);
                number.setText(email);
            }
        });

    }
}