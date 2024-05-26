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
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.test.Model.FoodDomain;
//
//import java.util.ArrayList;
//
//public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder>
//{
//    ArrayList<FoodDomain> popularfood;
//    Context context;
//
//    public PopularAdapter(ArrayList<FoodDomain> popularfood, Context context) {
//        this.popularfood = popularfood;
//        this.context = context;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular,parent,false);
//        return new ViewHolder(inflate);
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull PopularAdapter.ViewHolder holder,
//                                  int i) {
//        holder.title.setText(popularfood.get(i).getTitle());
//        holder.fee.setText(String.valueOf(popularfood.get(i).getFee()));
//
//        Glide.with(context)
//                .load(popularfood.get(i).getPic())
//                .into(holder.pic);
//
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(holder.itemView.getContext(),DetailActivity.class);
//                intent.putExtra("object",popularfood.get(holder.getAdapterPosition()));
//                holder.itemView.getContext().startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return popularfood.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView title,fee ;
//        ImageView pic;
//        TextView addBtn;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            title =itemView.findViewById(R.id.title);
//            fee =itemView.findViewById(R.id.fee);
//            pic = itemView.findViewById(R.id.pic);
//
//            addBtn = itemView.findViewById(R.id.buy_btn);
//        }
//    }
//}
