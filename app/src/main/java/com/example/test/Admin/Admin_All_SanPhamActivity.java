package com.example.test.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.test.Model.Clothes;
import com.example.test.databinding.ActivityAdminSanPhamAllBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Admin_All_SanPhamActivity extends AppCompatActivity {
    private ArrayList<Clothes> prodList;
    private ActivityAdminSanPhamAllBinding binding;
    private AdapterSanPham_admin adapterSanPhamAdmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminSanPhamAllBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadAllProducts();

        binding.backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.addProdBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_All_SanPhamActivity.this, AddActivity.class));
            }
        });
        binding.searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    adapterSanPhamAdmin.getFilter().filter(charSequence);
                }catch (Exception e){

                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void loadAllProducts() {
        prodList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Clothes");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                prodList.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Clothes model = ds.getValue(Clothes.class);
                    prodList.add(model);
                }
                adapterSanPhamAdmin = new AdapterSanPham_admin(Admin_All_SanPhamActivity.this, prodList);
                binding.ProdRV.setAdapter(adapterSanPhamAdmin);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}