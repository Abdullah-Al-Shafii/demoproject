package com.example.demoproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class ImageSignatureActivity extends AppCompatActivity implements View.OnClickListener {
    Button chooseImageButton, uploadImageButton;
    ImageView uploadImageView;
    ProgressBar imageprogressBar;
    Uri uploadImageUri;
    Button chooseSignatureButton, uploadSignatureButton;
    ImageView uploadSignatureView;
    ProgressBar signatureprogressBar;
    Uri uploadSignatureUri;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    StorageTask uploadTask;
    String email;
    private static final int imageRequest = 1;
    private static final int signatureRequest = 2;
    DatabaseError databaseError;
    StorageException storageException;
    Button imagesignaturebutton;
    String imageUri;
    String signatureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_signature);
        databaseReference = FirebaseDatabase.getInstance().getReference("Students");
        storageReference = FirebaseStorage.getInstance().getReference("Students");
        chooseImageButton = (Button) findViewById(R.id.chooseImageButtonId);
        uploadImageButton = (Button) findViewById(R.id.UploadmagebuttonId);
        uploadImageView = (ImageView) findViewById(R.id.UploadImageviewId);
        imageprogressBar = (ProgressBar) findViewById(R.id.imageprogressBarId);
        chooseSignatureButton = (Button) findViewById(R.id.chooseSignatureButtonId);
        uploadSignatureButton = (Button) findViewById(R.id.UploadSignaturebuttonId);
        uploadSignatureView = (ImageView) findViewById(R.id.UploadSignatureviewId);
        signatureprogressBar = (ProgressBar) findViewById(R.id.signatureprogressBarId);
        imagesignaturebutton = (Button) findViewById(R.id.imagesignaturebuttonid);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            email = bundle.getString("email");
        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("email").getValue().equals(email)) {
                        imageUri = dataSnapshot.child("imageUri").getValue(String.class);
                        if (!TextUtils.isEmpty(imageUri)) {
                            Picasso.get().load(imageUri).placeholder(R.mipmap.ic_launcher).fit().into(uploadImageView);
                        }
                        signatureUri = dataSnapshot.child("signatureUri").getValue(String.class);
                        if (!TextUtils.isEmpty(signatureUri)) {
                            Picasso.get().load(signatureUri).placeholder(R.mipmap.ic_launcher).fit().into(uploadSignatureView);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), storageException.getMessage(), Toast.LENGTH_LONG).show();
            }


        });

        chooseImageButton.setOnClickListener(this);
        uploadImageButton.setOnClickListener(this);
        chooseSignatureButton.setOnClickListener(this);
        uploadSignatureButton.setOnClickListener(this);
        imagesignaturebutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.chooseImageButtonId) {
            openFileChooserImage();
        } else if (v.getId() == R.id.UploadmagebuttonId) {
            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(getApplicationContext(), "Uploading the image", Toast.LENGTH_LONG).show();
            } else {
                saveImage();
            }
        } else if (v.getId() == R.id.chooseSignatureButtonId) {
            openFileChooserSignature();
        } else if (v.getId() == R.id.UploadSignaturebuttonId) {
            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(getApplicationContext(), "Uploading the image", Toast.LENGTH_LONG).show();
            } else {
                saveSignature();
            }
        } else if (v.getId() == R.id.imagesignaturebuttonid) {
            go();
        }

    }

    private void openFileChooserImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, imageRequest);
    }

    private void openFileChooserSignature() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, signatureRequest);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == imageRequest && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uploadImageUri = data.getData();
            Picasso.get().load(uploadImageUri).into(uploadImageView);
            Toast.makeText(getApplicationContext(),"image upload successful",Toast.LENGTH_SHORT).show();
        }
        if (requestCode == signatureRequest && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uploadSignatureUri = data.getData();
            Picasso.get().load(uploadSignatureUri).into(uploadSignatureView);
            Toast.makeText(getApplicationContext(),"signature upload successful",Toast.LENGTH_SHORT).show();
        }
    }

    public String getFileExtension(Uri imageUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }


    private void saveImage() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            email = bundle.getString("email");
        }
        StorageReference ref = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uploadImageUri));
        ref.putFile(uploadImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful()) ;
                Uri downloadUrl = uriTask.getResult();
                imageUri = downloadUrl.toString();
                if (!TextUtils.isEmpty(imageUri)) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                if (dataSnapshot.child("email").getValue().equals(email)) {
                                    dataSnapshot.getRef().child("imageUri").setValue(imageUri);
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Choose image first", Toast.LENGTH_LONG).show();
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getApplicationContext(), storageException.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    private void saveSignature() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            email = bundle.getString("email");
        }
        StorageReference reff = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uploadSignatureUri));
        reff.putFile(uploadSignatureUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful()) ;
                Uri downloadUrl = uriTask.getResult();
                signatureUri = downloadUrl.toString();
                if (!TextUtils.isEmpty(signatureUri)) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                if (dataSnapshot.child("email").getValue().equals(email)) {
                                    dataSnapshot.getRef().child("signatureUri").setValue(signatureUri);
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Choose image first", Toast.LENGTH_LONG).show();
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getApplicationContext(), storageException.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    private void go() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            email = bundle.getString("email");
        }
        String notpaid = "not paid yet";
        if(!TextUtils.isEmpty(imageUri) && !TextUtils.isEmpty(signatureUri)) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.child("email").getValue().equals(email)) {
                                Intent intent = new Intent(getApplicationContext(), PaymentMethodActivity.class);
                                intent.putExtra("email", "" + email);
                                startActivity(intent);
                           }

                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), storageException.getMessage(), Toast.LENGTH_LONG).show();
                }


            });

        }

      else
      {
            Toast.makeText(getApplicationContext(),"Upload image and signature properly",Toast.LENGTH_SHORT).show();
      }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.usersignoutmenulayout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.Usersignoutid)
        {
            FirebaseAuth mAuth;
            mAuth = FirebaseAuth.getInstance();
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent=new Intent(getApplicationContext(),SignInActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}