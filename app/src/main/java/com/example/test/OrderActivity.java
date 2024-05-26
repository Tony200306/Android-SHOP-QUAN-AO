package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.test.Admin.OrderAdapter_Admin;
import com.example.test.Model.Profille;
import com.example.test.Model.Request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;

    private RecyclerView recyclerViewList;
    FirebaseDatabase database;
    DatabaseReference request;
    ImageButton back_btn;
    OrderAdapter adapter2;
    TextView Orderid,Ordername,Orderaddress,Ordertotal,Orderstatus;
    ArrayList<Request> OrderList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        database = FirebaseDatabase.getInstance() ;
        request = database.getReference("Request");

        initView();
        loadOrders(Profille.currentUser.getName());
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void initView() {

        Ordername = findViewById(R.id.UserN);
        Orderaddress = findViewById(R.id.Address);
        Ordertotal = findViewById(R.id.Totall);
        recyclerViewList = findViewById(R.id.OrderRV);
        back_btn = findViewById(R.id.backBTN);

    }
    private void loadOrders(String phone) {
        OrderList = new ArrayList<>();

        request.orderByChild("name").equalTo(phone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                OrderList.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Request model = ds.getValue(Request.class);
                    OrderList.add(model);
                }
                adapter2 = new OrderAdapter(OrderActivity.this, OrderList) {
                };
                recyclerViewList.setAdapter(adapter2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}