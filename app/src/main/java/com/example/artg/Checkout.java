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

import org.json.JSONException;

import java.io.IOException;

public class Checkout extends AppCompatActivity {
    ProductsModel productsModel;
    TextView Artist, title, Price;
    ImageView imagechk;
    Button buy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        title = findViewById(R.id.Titlebuy);
        Artist = findViewById(R.id.pricebuy);
        imagechk = findViewById(R.id.buyimage);
        buy = findViewById(R.id.mtnpayment);
        Price = findViewById(R.id.priceobuy);
        Intent intent = getIntent();

        String phone = getIntent().getStringExtra("check");
        String artist = getIntent().getStringExtra("artist");
        String price = getIntent().getStringExtra("price");
        title.setText(phone);
        Artist.setText(artist);
        Price.setText(price);
        Glide.with(getApplicationContext()).load(intent.getStringExtra("image")).into(imagechk);


        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // final gettoken gt = new gettoken();
                //final requesttopay rp = new requesttopay();
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



           // Glide.with(getApplicationContext()).load(productsModel.getArtImage()).error(R.drawable.loco).into(imagechk);
        }
    }