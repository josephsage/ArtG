package com.example.artg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Purchases extends AppCompatActivity {

    private RecyclerView recyclerView;
    FirebaseFirestore db;
    private FirebaseAuth fAuth;
    private String UserId;
    private FirestoreRecyclerAdapter adapter;
    BottomNavigationView bottomNavigationView;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchases);

        recyclerView = findViewById(R.id.recyclerview_purchase);

        recyclerView.setItemAnimator(null);
        db = FirebaseFirestore.getInstance();
        Query query = db.collection("Users");

        FirestoreRecyclerOptions<ProductsModel> options = new FirestoreRecyclerOptions.Builder<ProductsModel>()
                .setQuery(query, ProductsModel.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<ProductsModel, ProductsViewHolder>(options) {
            @NonNull
            @Override
            public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_layout,parent, false);
                return new ProductsViewHolder(v);
            }

            @Override
            public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull ProductsModel model) {
                holder.purchase.setText(model.getFullname());
                //String url = model.getArtImage();
                //model.getArtImage();
                //Glide.with(Purchases.this).load(model.getArtImage()).error(R.drawable.loco).into(holder.productimage);

            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        recyclerView.setAdapter(adapter);

    }


    private class ProductsViewHolder extends RecyclerView.ViewHolder {
        private TextView purchase;
        ProductsModel model;


        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            purchase = itemView.findViewById(R.id.purchase_artist);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}