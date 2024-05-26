//
//package com.example.test;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.ProgressDialog;
//import android.content.ContentResolver;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.webkit.MimeTypeMap;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.material.textfield.TextInputLayout;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.OnProgressListener;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//public class AddCateActivity extends AppCompatActivity {
//
//    private ImageButton imageButton;
//    Button post_dish;
//    TextInputLayout  Dishes;
//    String dishes, id, uid, timeStamp;
//    Uri imageUri;
//
//
//    FirebaseStorage storage;
//    StorageReference storageReference;
//    FirebaseDatabase firebaseDatabase;
//    DatabaseReference databaseReference,dataa;
//    FirebaseAuth Fauth;
//    StorageReference ref;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_cate);
//
//        storage = FirebaseStorage.getInstance();
//        storageReference = storage.getReference();
//
//        Dishes = (TextInputLayout)findViewById(R.id.cateName);
//
//        post_dish = (Button) findViewById(R.id.postCate);
//
//        Fauth = FirebaseAuth.getInstance();
//        databaseReference = firebaseDatabase.getInstance().getReference("Category");
//
//        imageButton = (ImageButton) findViewById(R.id.cateimage_upload);
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent gallery = new Intent();
//                gallery.setAction(Intent.ACTION_GET_CONTENT);
//                gallery.setType("image/*");
//                startActivityForResult(gallery,2);
//            }
//        });
//        post_dish.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (imageUri != null){
//                    dishes = Dishes.getEditText().getText().toString().trim();
//                    long timestamp = System.currentTimeMillis();
//                    timeStamp = String.valueOf(System.currentTimeMillis());
//                    id = timeStamp;
//                    uid = timeStamp;
//                    uploadToFirebase(imageUri);
//                }else{
//                    Toast.makeText(AddCateActivity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        ImageButton back = (ImageButton) findViewById(R.id.backAddCate);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//
//
//    }
//
//    private void uploadToFirebase(Uri uri){
//        final ProgressDialog progressDialog = new ProgressDialog(AddCateActivity.this);
//        progressDialog.setTitle("Uploading.....");
//        progressDialog.show();
//        final StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
//        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        CategoryDomain info = new CategoryDomain(id, uid, dishes,uri.toString(), timeStamp);
//                        databaseReference.child(id).setValue(info);
//                        progressDialog.dismiss();
//                        Toast.makeText(AddCateActivity.this,"Cate Posted Successfully!",Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(AddCateActivity.this,DanhMucActivity.class));
//                    }
//                });
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                progressDialog.dismiss();
//                Toast.makeText(AddCateActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
//            }
//        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
//                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
//                progressDialog.setMessage("Uploaded "+(int) progress+"%");
//                progressDialog.setCanceledOnTouchOutside(false);
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode== 2&& resultCode== RESULT_OK && data != null) {
//            imageUri = data.getData();
//            imageButton.setImageURI(imageUri);
//        }
//    }
//
//    private String getFileExtension(Uri mUri){
//
//        ContentResolver cr = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(cr.getType(mUri));
//
//    }
//
//}
