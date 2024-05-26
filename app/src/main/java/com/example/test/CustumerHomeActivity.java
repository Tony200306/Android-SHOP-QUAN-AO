package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.Model.Clothes;
import com.example.test.Model.Profille;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustumerHomeActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter,adapter2,adapter3;

    private RecyclerView recyclerView,searchView;

    private ArrayList<Clothes> list,list2;


    ConstraintLayout constraintLayout;
    TextView userName ,etSearch;



    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Clothes");

    private DatabaseReference databaseReferenceCate = FirebaseDatabase.getInstance().getReference("Category");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);


        recycleViewAllProduct();
        bottomNavigation();
        etSearch = findViewById(R.id.etSearch);

        userName = findViewById(R.id.textView6);

        userName.setText(Profille.currentUser.getName());
        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustumerHomeActivity.this,SearchActivity.class));
            }
        });
    }

    private void bottomNavigation()
    {
        LinearLayout CartBtn = findViewById(R.id.cartBtn);
        LinearLayout homeBtn= findViewById(R.id.homeBtn);
        //LinearLayout login= findViewById(R.id.profileBtn);
        LinearLayout Order= findViewById(R.id.OwnOrderBtn);
        LinearLayout Profile = findViewById(R.id.LogOutBtn);
        CartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustumerHomeActivity.this,CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustumerHomeActivity.this, CustumerHomeActivity.class));
            }
        });
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustumerHomeActivity.this, CustomerProfileActivity.class));

            }
        });
        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustumerHomeActivity.this, OrderActivity.class));
            }
        });



    }

    private void recycleViewAllProduct()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecyclerView recyclerViewPopularList = (RecyclerView)findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter2 = new AdapterSanPham_Customer( this , list);
        recyclerViewPopularList.setAdapter(adapter2);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if ( Integer.parseInt((String) dataSnapshot.child("quantity").getValue()) == 0){

                    }
                    else {
                        Clothes model = dataSnapshot.getValue(Clothes.class);
                        list.add(model);
                    }

                }
                adapter2.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}