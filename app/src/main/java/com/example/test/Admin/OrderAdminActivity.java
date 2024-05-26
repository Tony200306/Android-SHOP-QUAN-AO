package com.example.test.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.test.Model.Profille;
import com.example.test.Model.Request;
import com.example.test.OrderAdapter;
import com.example.test.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderAdminActivity extends AppCompatActivity {


    private RecyclerView recyclerViewList;
    FirebaseDatabase database;
    DatabaseReference request;

    OrderAdapter_Admin adapter2;
    TextView Ordername,Orderaddress,Ordertotal;
    ArrayList<Request> OrderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_admin);
        database = FirebaseDatabase.getInstance() ;
        request = database.getReference("Request");

        initView();

        loadOrders(Profille.currentUser.getPhone());

        ImageButton back = (ImageButton) findViewById(R.id.backOrderAdmin) ;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        EditText searchET = findViewById(R.id.searchET);
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    adapter2.getFilter().filter(charSequence);
                }catch (Exception e){

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void initView() {

        Ordername = findViewById(R.id.UserN_admin);
        Orderaddress = findViewById(R.id.Address_admin);
        Ordertotal = findViewById(R.id.Totall_admin);
        recyclerViewList = findViewById(R.id.OrderRV);

    }
    private void loadOrders(String phone) {
        OrderList = new ArrayList<>();

        request.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                OrderList.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Request model = ds.getValue(Request.class);
                    OrderList.add(model);
                }
                adapter2 = new OrderAdapter_Admin(OrderAdminActivity.this, OrderList);

                recyclerViewList.setAdapter(adapter2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}