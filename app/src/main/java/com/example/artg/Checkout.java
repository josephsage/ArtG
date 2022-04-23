package com.example.artg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.artg.mtn.checkrtp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Checkout extends AppCompatActivity {
    ProductsModel productsModel;
    TextView Artist, title, Price;
    ImageView imagechk;
    Button buy, purchase;
    private FirebaseFirestore fstore;
    private FirebaseAuth fAuth;
    private String userID;
    private ArrayList<ProductsModel> purchases = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);



        title = findViewById(R.id.Titlebuy);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        Artist = findViewById(R.id.pricebuy);
        imagechk = findViewById(R.id.buyimage);
        buy = findViewById(R.id.mtnpayment);
        purchase = findViewById(R.id.PURCHASE);
        Price = findViewById(R.id.priceobuy);
        Intent intent = getIntent();

        String phone = getIntent().getStringExtra("check");
        String artist = getIntent().getStringExtra("artist");
        String price = getIntent().getStringExtra("price");
        title.setText(phone);
        Artist.setText(artist);
        Price.setText(price);
        Glide.with(getApplicationContext()).load(intent.getStringExtra("image")).into(imagechk);

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                purchasearray();
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final checkrtp ckrtp = new checkrtp();

                new AsyncTask() {

                    @Override
                    protected Object doInBackground(Object[] objects) {

                        try {
                            ckrtp.checkpaymnt();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute();
                Toast.makeText(Checkout.this, "Processing Transaction", Toast.LENGTH_SHORT).show();
            }
        });




    }

    private void purchasearray() {
        String phone = getIntent().getStringExtra("check");
        String artist = getIntent().getStringExtra("artist");
        String price = getIntent().getStringExtra("price");
        purchases = new ArrayList<>();
        userID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fstore.collection("Users").document(userID);
        Map<String, Object> user = new  HashMap<>();
        //for(int i = 0; i< purchases.size(); i++) {
            user.put("Purchases", FieldValue.arrayUnion(artist, phone, price));
            //user.put("Purchases", Arrays.asList(phone));
            //user.put("Purchases", Arrays.asList(price));
            //user.put("Email", mail);
            documentReference.update(user);
        Toast.makeText(Checkout.this, "Item purchased.", Toast.LENGTH_LONG).show();

        }
    }
