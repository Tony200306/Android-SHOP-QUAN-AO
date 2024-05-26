package com.example.test.RAC;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.test.AdapterSanPham_Customer;
import com.example.test.Model.Clothes;
import com.example.test.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Customer_All_SANPHAMnew extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private ArrayList<Clothes> prodList;

    private AdapterSanPham_Customer adapterSanPham,adapter2;
    private String categoryId, categoryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sanpham_all);
        Intent intent = getIntent();
        categoryId = intent.getStringExtra("categoryId");
        categoryTitle = intent.getStringExtra("categoryTitle");
        if(categoryId != null && categoryTitle != null)
        {
            loadProdList();
        }
        else{
            ShowAllProducts();
        }


        ImageButton back = (ImageButton) findViewById(R.id.backProd) ;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



    }
    private void ShowAllProducts() {
        prodList = new ArrayList<>();
        RecyclerView show = (RecyclerView) findViewById(R.id.ProdRV1);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Clothes");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                prodList.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Clothes model = ds.getValue(Clothes.class);
                    prodList.add(model);
                }
                adapterSanPham = new AdapterSanPham_Customer(Customer_All_SANPHAMnew.this,prodList);
                show.setAdapter(adapterSanPham);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void loadProdList() {
        prodList = new ArrayList<>();
        RecyclerView show = (RecyclerView) findViewById(R.id.ProdRV1);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Clothes");
        ref.orderByChild("categoryId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        prodList.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            Clothes model = ds.getValue(Clothes.class);
                            prodList.add(model);
                        }
                        adapter2 = new AdapterSanPham_Customer(Customer_All_SANPHAMnew.this, prodList);
                        show.setAdapter(adapter2);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}