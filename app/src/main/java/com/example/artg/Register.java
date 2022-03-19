package com.example.artg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    public static final String TAG = "TAG";
    Button register_btn;
    EditText email, password, name;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fstore;
    String userID;
    CheckBox artist, customer;
    boolean valid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        RelativeLayout relativeLayout = findViewById(R.id.main_layout2);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2600);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        register_btn = findViewById(R.id.register_btn);
        email = findViewById(R.id.REG_email);
        password = findViewById(R.id.REG_password);
        name = findViewById(R.id.REG_name);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        artist = findViewById(R.id.Artist);
        customer = findViewById(R.id.Customer);


        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Home.class));
            finish();
        }

        //Check box logic
        artist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    customer.setChecked(false);
                }
            }
        });
        customer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    artist.setChecked(false);
                }
            }
        });

        register_btn.setOnClickListener((v) -> {
            final String mail = email.getText().toString().trim();
            final String pass = password.getText().toString().trim();
            final String fname = name.getText().toString().trim();

            //Checkbox Validation
            if(!(artist.isChecked() || customer.isChecked() )) {
                Toast.makeText(Register.this, "Select the account type", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(mail)) {
                email.setError("Email is Required");
                return;
            }
            if (TextUtils.isEmpty(pass)) {
                password.setError("Password is Required");
                return;

            }
            if (pass.length() < 6) {
                password.setError("Password must be >= 6 Characters");
                return;

            }

            //Authenticates the user with email and password
            fAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener((task) -> {
                if (task.isSuccessful()) {
                    Toast.makeText(Register.this, "User is created.", Toast.LENGTH_SHORT).show();
                    userID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fstore.collection("Users").document(userID);
                    Map<String, Object> user = new HashMap<>();
                    user.put("Fullname", fname);
                    user.put("Email", mail);

                    //specify if user is Artist or Customer
                    if (artist.isChecked()){
                        user.put("Artist", "1");
                    }
                    if (customer.isChecked()) {
                        user.put("Customer", "1");
                    }


                    documentReference.set(user);

                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onSuccess: user profile is created for " + userID);
                        }
                    });
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Toast.makeText(Register.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT);
                }
            });
        });
    }
}