package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.test.Admin.AdapterSanPham_admin;
import com.example.test.Admin.AddActivity;
import com.example.test.Admin.Admin_All_SanPhamActivity;
import com.example.test.Model.Clothes;
import com.example.test.Model.Request;
import com.example.test.databinding.ActivityAdminSanPhamAllBinding;
import com.example.test.databinding.AllSanphamOrderBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class All_SanPham extends AppCompatActivity {
    private Request request ;
    private ArrayList<Clothes> prodList;
    private AllSanphamOrderBinding binding;
    private AdapterSanPham_Customer adapterSanPhamAdmin;
    private ArrayList<Clothes> clothesorder ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AllSanphamOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadAllProducts();
        getBundle();
        loadAllProducts();
        binding.backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void loadAllProducts() {
        prodList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Request")
                .child("1713086065645").child("clothesOder");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                prodList.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Clothes model = ds.getValue(Clothes.class);
                    prodList.add(model);
                }
                adapterSanPhamAdmin = new AdapterSanPham_Customer(All_SanPham.this, clothesorder);
                binding.ProdRV.setAdapter(adapterSanPhamAdmin);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void getBundle(){
        request = (Request) getIntent().getSerializableExtra("request");
        clothesorder = request.getclothesOder();
    }


}
