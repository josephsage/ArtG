package com.example.artg;

import static com.example.artg.Register.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class Report extends AppCompatActivity {
    public RecyclerView recyclerView;
    FirebaseFirestore db;
    private FirebaseAuth fAuth;
    private String UserId;
    public FirestoreRecyclerAdapter adapter;

    @SuppressLint("WrongConstant")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        recyclerView = findViewById(R.id.recyclerview_report);

        recyclerView.setItemAnimator(null);
        db = FirebaseFirestore.getInstance();
        Query query = db.collection("Uploads").document("MRWwQYNF7tNRuxXIMMtIZSW15EC3").collection("Uploads");

        FirestoreRecyclerOptions<ProductsModel> options = new FirestoreRecyclerOptions.Builder<ProductsModel>()
                .setQuery(query, ProductsModel.class)
                .build();

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

               if (direction == ItemTouchHelper.LEFT) {
                    Toast.makeText(Report.this, "Deleting", Toast.LENGTH_SHORT).show();
                    ProductsViewHolder productsViewHolder = (ProductsViewHolder) viewHolder;
                    productsViewHolder.deleteitem();
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(Report.this, android.R.color.holo_red_dark))
                        .addActionIcon(R.drawable.ic_baseline_delete)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            }
        };

        adapter = new FirestoreRecyclerAdapter<ProductsModel, Report.ProductsViewHolder>(options) {


            @NonNull
            @Override
            public Report.ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_layout, parent, false);
                return new Report.ProductsViewHolder(v);
            }



            @Override
            public void onBindViewHolder(@NonNull Report.ProductsViewHolder holder, int position, @NonNull ProductsModel model) {
                holder.productreport.setText(model.getTitle());
                //String url = model.getArtImage();
                model.getArtImage();
                Glide.with(Report.this).load(model.getArtImage()).error(R.drawable.loco).into(holder.imagereport);

            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


    }

    private class ProductsViewHolder extends RecyclerView.ViewHolder {
        private TextView productreport;
        private ImageView imagereport;
        public ImageView mDeleteproduct;
        ProductsModel model;


        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            productreport = itemView.findViewById(R.id.product_report);
            imagereport = itemView.findViewById(R.id.image_report);
            mDeleteproduct = itemView.findViewById(R.id.product_delete);
        }

        public void deleteitem() {
            getAdapterPosition();
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