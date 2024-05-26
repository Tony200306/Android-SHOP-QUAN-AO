package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.test.Model.Clothes;

import java.util.ArrayList;
public class AdapterSanPham_Customer extends RecyclerView.Adapter<AdapterSanPham_Customer.HolderProduct>  implements Filterable {
    Context context;
    public ArrayList<Clothes> productsArrayList,filterlist;
    private FilterProd filter;

    public AdapterSanPham_Customer(Context context, ArrayList<Clothes> productsArrayList) {
        this.context = context;
        this.productsArrayList = productsArrayList;
        this.filterlist = productsArrayList;
    }

    @NonNull
    @Override
    public AdapterSanPham_Customer.HolderProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_allprod,parent,false);
        return new AdapterSanPham_Customer.HolderProduct(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSanPham_Customer.HolderProduct holder, int position) {
        Clothes model = productsArrayList.get(position);
        String title = model.getTitle();
        String description = model.getDescription();
        String fee = String.valueOf(model.getFee());
        holder.prodTitle.setText(title);
        holder.prodDes.setText(description);
        holder.prodPrice.setText(fee);
        Glide.with(context)
                .load(productsArrayList.get(position).getPic())
                .into(holder.imgview);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(),DetailActivity.class);
                intent.putExtra("object",productsArrayList.get(holder.getAdapterPosition()));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  productsArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
                filter = new FilterProd(filterlist, this );
        }
        return filter;
    }

    public class HolderProduct extends RecyclerView.ViewHolder {
        ImageView imgview;

        TextView prodTitle, prodDes, prodCate, prodPrice;
        public HolderProduct(@NonNull View itemView) {
            super(itemView);
            imgview = itemView.findViewById(R.id.prodView1);
            prodTitle = itemView.findViewById(R.id.TitleTV1);
            prodDes = itemView.findViewById(R.id.DescriptionTV1);
            prodPrice = itemView.findViewById(R.id.PriceTV1);
        }
    }
}
