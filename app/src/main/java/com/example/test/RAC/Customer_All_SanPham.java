//package com.example.test;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.View;
//
//import com.example.test.Model.FoodDomain;
//import com.example.test.databinding.ActivityCustomerAllSanPhamBinding;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
//public class Customer_All_SanPham extends AppCompatActivity {
//    private ArrayList<FoodDomain> prodList;
//    private ActivityCustomerAllSanPhamBinding binding;
//    private AdapterSanPham adapterSanPham;
// //   private String categoryId, categoryTitle;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityCustomerAllSanPhamBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        Intent intent = getIntent();
////        categoryId = intent.getStringExtra("categoryId");
////        categoryTitle = intent.getStringExtra("categoryTitle");
//
//        binding.backProdAdmin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//
//
//        loadProdList();
//        /*setContentView(R.layout.activity_san_pham);*/
//
//        binding.searchET.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                try{
//                    adapterSanPham.getFilter().filter(charSequence);
//                }catch (Exception e){
//
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//    }
//
//    private void loadProdList() {
//        prodList = new ArrayList<>();
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Clothes");
//        ref.orderByChild("title")
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        prodList.clear();
//                        for(DataSnapshot ds : snapshot.getChildren()){
//                            FoodDomain model = ds.getValue(FoodDomain.class);
//                            prodList.add(model);
//                        }
//                        adapterSanPham = new AdapterSanPham(Customer_All_SanPham.this, prodList);
//                        binding.ProdRV.setAdapter(adapterSanPham);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//    }
//}