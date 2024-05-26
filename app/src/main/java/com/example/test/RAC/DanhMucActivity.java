//package com.example.test;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.View;
//
//import com.example.test.databinding.ActivityDanhMucBinding;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
//public class DanhMucActivity extends AppCompatActivity {
//
//
//    private FirebaseAuth firebaseAuth;
//    ActivityDanhMucBinding binding;
//
//    private ArrayList<CategoryDomain> categoriesArrayList;
//    private AdapterDanhMuc adapterDanhMuc;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityDanhMucBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        loadCategories();
//
//        binding.backAdminCate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(DanhMucActivity.this,AdminHomeActivity.class));
//            }
//        });
//        binding.searchET.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                try{
//                    adapterDanhMuc.getFilter().filter(charSequence);
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
//        binding.addCategoryBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(DanhMucActivity.this,AddCateActivity.class));
//            }
//        });
//    }
//
//    private void loadCategories() {
//        categoriesArrayList = new ArrayList<>();
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Category");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                categoriesArrayList.clear();
//                for(DataSnapshot ds : snapshot.getChildren()){
//                    CategoryDomain model = ds.getValue(CategoryDomain.class);
//                    categoriesArrayList.add(model);
//                }
//                /*RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DanhMucActivity.this,RecyclerView.VERTICAL,false);
//                binding.categoriesRV.setLayoutManager(layoutManager);*/
//                adapterDanhMuc = new AdapterDanhMuc(DanhMucActivity.this, categoriesArrayList);
//                binding.categoriesRV.setAdapter(adapterDanhMuc);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//}