//package com.example.test;
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Filter;
//import android.widget.Filterable;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.test.databinding.RowCategoryBinding;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.ArrayList;
//
//public class AdapterDanhMuc extends RecyclerView.Adapter<AdapterDanhMuc.HolderProduct> implements Filterable {
//
//    Context context;
//    public ArrayList<CategoryDomain> categoriesArrayList, filterList;
//    FilterDanhMuc filterDanhMuc;
//    /*private RowCategoryBinding binding;*/
//
//    public AdapterDanhMuc(Context context, ArrayList<CategoryDomain> categoriesArrayList) {
//        this.context = context;
//        this.categoriesArrayList = categoriesArrayList;
//        this.filterList = categoriesArrayList;
//    }
//
//    @NonNull
//    @Override
//    public HolderProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category,parent,false);
//        return new AdapterDanhMuc.HolderProduct(inflate);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull HolderProduct holder, int position) {
//        CategoryDomain model = categoriesArrayList.get(position);
//        String id = model.getId();
//        String categoryTitle = model.getTitle();
//        String uid = model.getUid();
//        String timestamp = model.getTimestamp();
//
//
//        holder.Cate.setText(categoriesArrayList.get(position).getTitle());
//
//        Glide.with(context)
//                .load(categoriesArrayList.get(position).getPic())
//                .into(holder.categoryiv);
//
//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Delete").setMessage("Do you want to delete?")
//                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Toast.makeText(context, "Deleting...", Toast.LENGTH_SHORT).show();
//                                deleteCategroy(model, holder);
//                            }
//                        })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//                            }
//                        }).show();
//
//            }
//        });
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, SanPhamActivity.class);
//                intent.putExtra("categoryId",id);
//                intent.putExtra("categoryTitle", categoryTitle);
//                context.startActivity(intent);
//
//            }
//        });
//    }
//    private void deleteCategroy(CategoryDomain model, HolderProduct holder) {
//        String id = model.getId();
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Category");
//        ref.child(id).removeValue()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(context, "Successfull", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return categoriesArrayList.size();
//    }
//
//    @Override
//    public Filter getFilter() {
//        if(filterDanhMuc == null){
//            filterDanhMuc = new FilterDanhMuc(filterList, this);
//        }
//        return filterDanhMuc;
//    }
//
//    class HolderProduct extends RecyclerView.ViewHolder{
//        ImageView categoryiv;
//        TextView Cate;
//        ImageButton delete;
//        public HolderProduct(@NonNull View itemView) {
//            super(itemView);
//            categoryiv = itemView.findViewById(R.id.categoryIV);
//            Cate = itemView.findViewById(R.id.danhmucTV);
//            delete = itemView.findViewById(R.id.deleteBTN);
//        }
//    }
//
//
//}
