package com.example.artg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

public class prevelant extends AppCompatActivity {

    private RecyclerView recyclerView;
    FirebaseFirestore db;
    ImageView profileAccount;
    private FirebaseAuth fAuth;
    private String UserId;
    private FirestoreRecyclerAdapter adapter;
    BottomNavigationView bottomNavigationView;
    private static final String TAG = "firestoreSearchActivity";


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevelant);

        recyclerView = findViewById(R.id.recyclerview);
        profileAccount = findViewById(R.id.profileaccount);
        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.home);

        profileAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Account.class));
            }
        });

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),profile.class));
                        overridePendingTransition(0,0);
                        return;
                    case R.id.home:
                        return;
                    /* case R.id.Cart:
                        startActivity(new Intent(getApplicationContext(), Checkout.class));
                        overridePendingTransition(0,0);
                        return;*/
                }
                return;

            }
        });
        recyclerView.setItemAnimator(null);
        db = FirebaseFirestore.getInstance();
        Query query = db.collection("Uploads").document("MRWwQYNF7tNRuxXIMMtIZSW15EC3" ).collection("Uploads");

        FirestoreRecyclerOptions<ProductsModel> options = new FirestoreRecyclerOptions.Builder<ProductsModel>()
                .setQuery(query, ProductsModel.class)
                .build();

        EditText searchbox = findViewById(R.id.searchbox);
        searchbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG, "searchbox has changed to: " + editable.toString());
                Query query;
                if(editable.toString().isEmpty()){
                    query = db.collection("Uploads")
                            .document("MRWwQYNF7tNRuxXIMMtIZSW15EC3" )
                            .collection("Uploads");

                }else {
                    query = db.collection("Uploads")
                            .document("MRWwQYNF7tNRuxXIMMtIZSW15EC3")
                            .collection("Uploads")
                            .whereEqualTo("Title", editable.toString());
                    
                }
                FirestoreRecyclerOptions<ProductsModel> options = new FirestoreRecyclerOptions.Builder<ProductsModel>()
                        .setQuery(query, ProductsModel.class)
                        .build();
                adapter.updateOptions(options);
            }
        });
        adapter = new FirestoreRecyclerAdapter<ProductsModel, ProductsViewHolder>(options) {
            @NonNull
            @Override
            public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout,parent, false);
                return new ProductsViewHolder(v);
            }

            @Override
            public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull ProductsModel model) {
                holder.productname.setText(model.getTitle());
                String url = model.getArtImage();
                model.getArtImage();
               Glide.with(prevelant.this).load(model.getArtImage()).error(R.drawable.loco).into(holder.productimage);
                ProductsModel finalModel = model;
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent intent = new Intent(prevelant.this, ViewdetailActivity.class);
                       intent.putExtra("detail", finalModel);
                       intent.putExtra("check", finalModel);
                       prevelant.this.startActivity(intent);
                   }
               });
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
                model = model;



            }
        };

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));//(this, LinearLayout.VERTICAL, false));
        recyclerView.setAdapter(adapter);

    }


    private class ProductsViewHolder extends RecyclerView.ViewHolder {
        private TextView productname;
        private ImageView productimage;
        ProductsModel model;


        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            productname = itemView.findViewById(R.id.product_name);
            productimage = itemView.findViewById(R.id.product_image);
           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(),ViewdetailActivity.class);
                    intent.putExtra("model",model);
                    itemView.getContext().startActivity(intent);
                    intent.putExtra("desc", model.getDescription());
                }
            });*/
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