package com.example.artg;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Upload extends AppCompatActivity {
    private Spinner spinner;
    Button uploadbtn;
    ImageView imageuploadview;
    EditText artist, title, description_edt, price_edt, phone;
    private  static final int GallerPick = 1;
    private Uri ImageUri;
    private String Arttype,Artist,Title,Description,Price,savecurrentdate,savecurrenttime,userID, Phone;
    private String productRandomKey;
    private StorageReference productImageRef;
    private FirebaseFirestore fstore;
    private FirebaseAuth fAuth;
    String downloadUrl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);


        productImageRef= FirebaseStorage.getInstance().getReference().child("Product Images");

       // spinner = findViewById(R.id.spin1);
        uploadbtn = findViewById(R.id.uploadbutton);
        imageuploadview = findViewById(R.id.imageUpload);
        artist = findViewById(R.id.creator);
        title = findViewById(R.id.name);
        phone = findViewById(R.id.contact);
        description_edt = findViewById(R.id.description);
        price_edt = findViewById(R.id.price);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        //Spinner code
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ArtType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
        //select from gallery
        imageuploadview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                validateproductdata();
            }
        });

    }
        //Art information for the upload
    private void validateproductdata() {
        Description = description_edt.getText().toString();
        Price = price_edt.getText().toString();
        Title = title.getText().toString();
        Artist = artist.getText().toString();
        Phone = phone.getText().toString();

        if (ImageUri == null)
        {
            Toast.makeText(this, "Image is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Artist))
        {
            Toast.makeText(this, "Please Write the Artists Name",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Title))
        {
            Toast.makeText(this, "Please Write the Art Title",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description))
        {
            Toast.makeText(this, "Please Write the Art Description",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Price))
        {
            Toast.makeText(this, "Please Write the Art Price",Toast.LENGTH_SHORT).show();
        }
        else {
            storeArtinformation();


        }
    }

    private void storeArtinformation() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd,yyyy");
        savecurrentdate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        savecurrenttime = currentTime.format(calendar.getTime());

        productRandomKey = savecurrentdate+savecurrenttime;

        final StorageReference filepath = productImageRef.child(ImageUri.getLastPathSegment()+productRandomKey+"jpg");
        UploadTask uploadTask = (UploadTask) filepath.putFile(ImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ImageUri = uri;
                        userID = fAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fstore.collection("Uploads").document("MRWwQYNF7tNRuxXIMMtIZSW15EC3").collection("Uploads").document();
                        Map<String, Object> user = new HashMap<>();
                        user.put("Artist", Artist);
                        user.put("Phone", Phone);
                        user.put("Title",Title );
                        user.put("Description",Description );
                        user.put("Price",Price );
                        user.put("ArtImage",ImageUri.toString());
                        documentReference.set(user, SetOptions.merge());

                        startActivity(new Intent(getApplicationContext(), Report.class));
                    }
                });
            }
        });
    }

    //select image
    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GallerPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GallerPick && resultCode==RESULT_OK && data!=null)
        {
            ImageUri = data.getData();
            imageuploadview.setImageURI(ImageUri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),ImageUri);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

   /* @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String choice = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getApplicationContext(), choice, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
*/
    }

