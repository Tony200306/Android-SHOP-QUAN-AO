package com.example.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.test.Model.Clothes;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    private ArrayList<Clothes> clothes;
    private ManagementCart managementCart;
    private ChangeNumberItemsListener changeNumberItemsListener;






    public CartListAdapter(ArrayList<Clothes> clothes, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.clothes = clothes;
        this.managementCart = new ManagementCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public CartListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_cart,parent,false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(clothes.get(position).getTitle());
        holder.feeEachItem.setText(String.valueOf(clothes.get(position).getFee()));
        holder.totalEachItem.setText(String.valueOf(Math.round((clothes.get(position).getNumberInCart()* clothes.get(position).getFee()) * 100) / 100 ));
        holder.num.setText(String.valueOf(clothes.get(position).getNumberInCart()));


        Glide.with(holder.itemView.getContext())
                .load(clothes.get(position).getPic())
                .into(holder.pic);
        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.plusNumberFood(clothes, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
//                        changeNumberItemsListener.changed();
                    }
                });
            }
        });
        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.minusNumberFood(clothes, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
//                        changeNumberItemsListener.changed();
                    }
                });
            }
        });



    }

    @Override
    public int getItemCount() {
        return clothes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,feeEachItem;
        ImageView pic,plusItem,minusItem;
        TextView totalEachItem,num;
        Button ShipButton ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            feeEachItem = itemView.findViewById(R.id.btnCheckOut);
            pic = itemView.findViewById(R.id.picCart);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            num = itemView.findViewById(R.id.numberItem);
            plusItem = itemView.findViewById(R.id.plusCartBtn);
            minusItem = itemView.findViewById(R.id.minusCartBtn);
            ShipButton = itemView.findViewById(R.id.btnReceived);
        }
    }
}
