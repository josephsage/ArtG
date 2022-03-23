package com.example.artg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.grpc.Context;

public class prevelant extends AppCompatActivity {

    private RecyclerView recyclerView;
    FirebaseFirestore db;
    private FirebaseAuth fAuth;
    private String UserId;
    private FirestoreRecyclerAdapter adapter;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevelant);

        recyclerView = findViewById(R.id.recyclerview);
        db = FirebaseFirestore.getInstance();
        Query query = db.collection("Users").document("MRWwQYNF7tNRuxXIMMtIZSW15EC3").collection("Uploads");

        FirestoreRecyclerOptions<ProductsModel> options = new FirestoreRecyclerOptions.Builder<ProductsModel>()
                .setQuery(query, ProductsModel.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<ProductsModel, ProductsViewHolder>(options) {
            @NonNull
            @Override
            public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout,parent, false);
                return new ProductsViewHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull ProductsModel model) {
                holder.productname.setText(model.getTitle());
                String url = model.getArtImage();
                model.getArtImage();
               Glide.with(prevelant.this).load(model.getArtImage()).error(R.drawable.loco).into(holder.productimage);
                //Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/artg-7267f.appspot.com/o/Product%20Images%2Fprimary%3ADownload%2F152523.jpg03%2017%2C202211%3A33%3A12jpg?alt=media&token=2ea3ec4e-d6b0-42d2-9fa4-d64e1fb2e694").error(R.drawable.loco).into(holder.productimage);

               /* StorageReference imgref = storage.getReference()
                .child("primary:Download")
                       ;
               imgref.getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(prevelant.this).load(model.getArtImage()).error(R.drawable.acjground_3).into(holder.productimage);
                            }
                        });*/


            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

    }


    private class ProductsViewHolder extends RecyclerView.ViewHolder {
        private TextView productname;
        private ImageView productimage;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            productname = itemView.findViewById(R.id.product_name);
            productimage = itemView.findViewById(R.id.product_image);
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