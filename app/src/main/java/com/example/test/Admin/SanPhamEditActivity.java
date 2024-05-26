package com.example.test.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.test.databinding.ActivitySanPhamEditBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class SanPhamEditActivity extends AppCompatActivity {
    private ActivitySanPhamEditBinding binding;
    private ProgressDialog progressDialog;

    private String prodId;



    private ArrayList<String> categoryTitleArrayList,categoryIdArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySanPhamEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prodId = getIntent().getStringExtra("ProductId");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        loadProductInfo();
        loadCategory();


        binding.prodCateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryDialog();
            }
        });
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

        
    }






    private String title="", des="";
    private void validateData(){
        title = binding.productTitleET.getText().toString().trim();
        des = binding.productDesET.getText().toString().trim();

        if(TextUtils.isEmpty(title)){
            Toast.makeText(this, "Enter title", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(des)){
            Toast.makeText(this, "Enter description", Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(selectedCateId)){
            Toast.makeText(this, "Pick Category", Toast.LENGTH_SHORT).show();
        }
        else {
            updateProd();
        }
    }


    private void updateProd(){
        progressDialog.setMessage("Updating product info...");
        progressDialog.show();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("title",""+title);
        hashMap.put("description",""+des);
        hashMap.put("categoryId",""+selectedCateId);
        hashMap.put("category",""+binding.prodCateTV.getText().toString());


        DatabaseReference refUpdate = FirebaseDatabase.getInstance().getReference("Clothes");
        refUpdate.child(prodId)
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(SanPhamEditActivity.this, "Product updated...", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(SanPhamEditActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String selectedCateId="", selectedCateTitle="";

    private void loadProductInfo(){
        DatabaseReference refProd = FirebaseDatabase.getInstance().getReference("Clothes");
        refProd.child(prodId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        selectedCateId = ""+snapshot.child("categoryId").getValue();
                        /*selectedCateTitle = ""+snapshot.child("category").getValue();*/
                        String description = ""+snapshot.child("description").getValue();
                        String title = ""+snapshot.child("title").getValue();
                        String prodImg = ""+snapshot.child("pic").getValue();

                        binding.productTitleET.setText(title);
                        binding.productDesET.setText(description);
                        Glide.with(SanPhamEditActivity.this)
                                .load(prodImg)
                                .into(binding.iconIV);
                        DatabaseReference refCate = FirebaseDatabase.getInstance().getReference("Category");
                        refCate.child(selectedCateId)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String cate = ""+snapshot.child("title").getValue();
                                        binding.prodCateTV.setText(cate);}

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void categoryDialog(){
        String[] categoryArray = new String[categoryTitleArrayList.size()];
        for(int i=0;i<categoryTitleArrayList.size();i++){
            categoryArray[i] = categoryTitleArrayList.get(i);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose category")
                .setItems(categoryArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedCateId = categoryIdArrayList.get(i);
                        selectedCateTitle = categoryTitleArrayList.get(i);

                        binding.prodCateTV.setText(selectedCateTitle);
                    }
                })
                .show();
    }

    private void loadCategory() {
        categoryIdArrayList = new ArrayList<>();
        categoryTitleArrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Category");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryIdArrayList.clear();
                categoryTitleArrayList.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    String id = ""+ds.child("id").getValue();
                    String category = ""+ds.child("title").getValue();
                    categoryIdArrayList.add(id);
                    categoryTitleArrayList.add(category);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}