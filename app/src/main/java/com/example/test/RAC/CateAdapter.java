//package com.example.test;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.core.content.ContextCompat;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//
//import java.util.ArrayList;
//
//public class CateAdapter  extends RecyclerView.Adapter<CateAdapter.ViewHolder> {
//    ArrayList<CategoryDomain> categoryDomains;
//    Context context;
//    public  CateAdapter(ArrayList<CategoryDomain> categoryDomains,Context context)
//    {
//        this.categoryDomains = categoryDomains;
//        this.context = context;
//
//    }
//
//    @Override
//    public CateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
//        return new ViewHolder(inflate);
//
//    }
//
//
//
//    @Override
//    public void onBindViewHolder(@NonNull CateAdapter.ViewHolder holder, int position) {
//        CategoryDomain model = categoryDomains.get(position);
//        String id = model.getId();
//        String categoryTitle = model.getTitle();
//        holder.categoryName.setText(categoryDomains.get(position).getTitle());
//        Glide.with(context)
//                .load(categoryDomains.get(position).getPic())
//                .into(holder.categoryPic);
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, ShowProductsActivity.class);
//                intent.putExtra("categoryId",id);
//                intent.putExtra("categoryTitle", categoryTitle);
//                context.startActivity(intent);
//            }
//        });
//
//
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return categoryDomains.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView categoryName ;
//        ImageView categoryPic;
//
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            categoryName = itemView.findViewById(R.id.categoryName);
//            categoryPic = itemView.findViewById(R.id.categoryPic);
//        }
//    }
//}
//
