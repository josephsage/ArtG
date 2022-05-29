package com.example.artg;

import static com.example.artg.Register.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Account extends AppCompatActivity {
    EditText name, email, phone_number, location;
    ProductsModel productsModel;
    Button ac_btn;
    FirebaseAuth fAuth;
    String userId;
    FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        name = findViewById(R.id.txt_account);
        email = findViewById(R.id.txt_account2);
        phone_number = findViewById(R.id.txt_account3);
        location = findViewById(R.id.txt_account4);
        ac_btn = findViewById(R.id.account_btn);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        FirebaseUser firebaseUser = fAuth.getCurrentUser();
        userId = fAuth.getCurrentUser().getUid();
        fstore.collection("Users").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                String USER_NAME = snapshot.getString("Fullname");
                String email1 = snapshot.getString("Email");
                String Phone = snapshot.getString("Phone_number");
                String Location = snapshot.getString("Location");

                name.setText(USER_NAME);
                email.setText(email1);
                phone_number.setText(Phone);
                location.setText(Location);
            }
        });
        ac_btn.setOnClickListener((v) -> {
            final String Phonenumber = phone_number.getText().toString().trim();
            final String Location = location.getText().toString().trim();
            final String Fullname = name.getText().toString().trim();
            //final String Email = email.getText().toString().trim();

            //Checkbox Validation
            /*if(!(artist.isChecked() || customer.isChecked() )) {
                Toast.makeText(Register.this, "Select the account type", Toast.LENGTH_SHORT).show();
                return;
            }*/

            //if (TextUtils.isEmpty(Email)) {
              //  email.setError("Email is Required");
                //return;
           // }
            if (TextUtils.isEmpty(Phonenumber)) {
                phone_number.setError("Password is Required");
                return;
            }

            if (TextUtils.isEmpty(Location)) {
                location.setError("Password is Required");
                return;
            }

            if (TextUtils.isEmpty(Fullname)) {
                name.setError("Password is Required");
                return;
            }

            //Authenticates the user with email and password
            userId = fAuth.getCurrentUser().getUid();
            DocumentReference documentReference = fstore.collection("Users").document(userId);
            Map<String, Object> user = new HashMap<>();
            user.put("Fullname", Fullname);
            //user.put("Email", Email);
            user.put("Phone_number", Phonenumber);
            user.put("Location", Location);
            user.put("Customer", "1");

            //specify if user is Artist or Customer.
                    /*if (artist.isChecked()){
                        user.put("Artist", "1");
                    }
                    if (customer.isChecked()) {
                        user.put("Customer", "1");
                    }*/


            documentReference.update(user);

            documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "onSuccess: user profile is created for " + userId);
                }
            });
        });
    }
}

