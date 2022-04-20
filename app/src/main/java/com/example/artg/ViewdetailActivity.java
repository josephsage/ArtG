package com.example.artg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;

public class ViewdetailActivity extends AppCompatActivity {

    ProductsModel productsModel = null;
    TextView description, artist, phone, price;
    ImageView imgdesc;
    Button btnbuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdetail);
        btnbuy = findViewById(R.id.buybtn);
       //String detail = phone.getText().toString();
        btnbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PHONE = phone.getText().toString();
                String ARTIST = artist.getText().toString();
                String PRICE = price.getText().toString();
                //String IMAGE = imgdesc.toString();
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                Intent intent = new Intent(ViewdetailActivity.this, Checkout.class);
                intent.putExtra("check", PHONE );
                intent.putExtra("artist", ARTIST);
                intent.putExtra("price", PRICE);
                intent.putExtra("image", productsModel.getArtImage());
                ViewdetailActivity.this.startActivity(intent);
            }
        });

        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof ProductsModel) {
            productsModel = (ProductsModel) object;
        }

        description = findViewById(R.id.viewdesc);
        imgdesc = findViewById(R.id.detailimage);
        artist = findViewById(R.id.viewArist);
        phone = findViewById(R.id.viewPhone);
        price = findViewById(R.id.viewPrice);


        if (productsModel !=null){
            description.setText(productsModel.getDescription());
            artist.setText(productsModel.getArtist());
            phone.setText(productsModel.getPhone());
            price.setText(productsModel.getPrice());
            Glide.with(getApplicationContext()).load(productsModel.getArtImage()).into(imgdesc);



       /* ProductsModel model = (ProductsModel)  getIntent().getSerializableExtra("model");
        description = findViewById(R.id.viewdesc);
        description.setText(model.getDescription());
       /* Object object =  getIntent().getSerializableExtra("model");
        if (object instanceof ProductsModel) {
            productsModel = (ProductsModel) object;
        }

        description = findViewById(R.id.viewdesc);

        if (productsModel !=null){
            description.setText(productsModel.getDescription());
        }*/

       //description.setText(getIntent().getStringExtra("model"));
        //ImageView imageView = findViewById(R.id.detailimage);
        //String desc = getIntent().getStringExtra("desc");
    }
}}