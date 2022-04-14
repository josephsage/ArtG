package com.example.artg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ViewdetailActivity extends AppCompatActivity {

    ProductsModel productsModel = null;
    TextView description, artist, phone;
    ImageView imgdesc;
    Button btncart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdetail);

        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof ProductsModel) {
            productsModel = (ProductsModel) object;
        }

        description = findViewById(R.id.viewdesc);
        imgdesc = findViewById(R.id.detailimage);
        artist = findViewById(R.id.viewArist);
        phone = findViewById(R.id.viewPhone);

        if (productsModel !=null){
            description.setText(productsModel.getDescription());
            artist.setText(productsModel.getArtist());
            phone.setText(productsModel.getPhone());
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