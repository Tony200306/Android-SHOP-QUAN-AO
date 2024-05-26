package com.example.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Admin.Admin_All_SanPhamActivity;
import com.example.test.Model.Clothes;
import com.example.test.Model.Request;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public abstract class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> implements Filterable {


    Context context;
     ArrayList<Request> Order,filterList;
    ArrayList<Clothes> clothes;

    FilterOrder filterOrder;

    public OrderAdapter(Context context, ArrayList<Request> Order) {
        this.context = context;
        this.Order = Order;
        this.filterList = Order;
    }


    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order_user,parent,false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        holder.Orderid.setText(Order.get(position).getId());
        holder.Ordername.setText(Order.get(position).getName());
        holder.Orderaddress.setText(Order.get(position).getAddress());
        holder.Ordertotal.setText(Order.get(position).getTotal());

        holder.Preparing.setVisibility(View.GONE);
        holder.receivedBtn.setVisibility(View.GONE);
        holder.received.setVisibility(View.GONE);
        holder.delevering.setVisibility(View.GONE);

        if (Order.get(position).getStatus().equals("0")){
            holder.receivedBtn.setVisibility(View.GONE);
            holder.received.setVisibility(View.GONE);
            holder.Preparing.setVisibility(View.VISIBLE);
            holder.delevering.setVisibility(View.GONE);
        }
        if (Order.get(position).getStatus().equals("1")){
            holder.Preparing.setVisibility(View.GONE);
            holder.received.setVisibility(View.GONE);
            holder.delevering.setVisibility(View.VISIBLE);
            holder.receivedBtn.setVisibility(View.VISIBLE);
        }
        if (Order.get(position).getStatus().equals("2")){
            holder.Preparing.setVisibility(View.GONE);
            holder.receivedBtn.setVisibility(View.GONE);
            holder.received.setVisibility(View.VISIBLE);
            holder.delevering.setVisibility(View.GONE);
        }
        holder.receivedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference data = FirebaseDatabase.getInstance().getReference("Request").child(Order.get(position).getId());
                data.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Thay đổi trạng thái thành "1" (đang vận chuyển)
                        String status = "2";
                        // Tạo HashMap để lưu trữ dữ liệu cập nhật
                        HashMap<String, Object> updateData = new HashMap<>();
                        updateData.put("status", status);

                        // Cập nhật dữ liệu trên Firebase
                        data.updateChildren(updateData).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Thông báo cập nhật thành công
                                Toast.makeText(context, "Status updated successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Xử lý trường hợp cập nhật không thành công
                                Toast.makeText(context, "Failed to update status", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu
                        Toast.makeText(context, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), Activity_SanPham_Order.class);
                intent.putExtra("request",  Order.get(holder.getAdapterPosition()));
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }
    @Override
    public Filter getFilter() {
        if(filterOrder == null){
            filterOrder = new FilterOrder(filterList, this);
        }
        return filterOrder;
    }
    private String convertCodeToStatus(String status)
    {
        if(status.equals("0"))
            return "Placed";
        else if(status.equals("1"))
            return "On my way";
        else
            return "Shipped";
    }


    @Override
    public int getItemCount() {
        return Order.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Orderid,Ordername,Orderaddress,Ordertotal,Orderstatus , Preparing , received , delevering;
        Button receivedBtn ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Orderid = itemView.findViewById(R.id.OderID);
            Ordername = itemView.findViewById(R.id.UserN);
            Orderaddress = itemView.findViewById(R.id.Address);
            Ordertotal = itemView.findViewById(R.id.Totall);
            Preparing = itemView.findViewById(R.id.Preparing);
            receivedBtn = itemView.findViewById(R.id.btnReceived);
            received = itemView.findViewById(R.id.Received);
            delevering = itemView.findViewById(R.id.Shipped);
            //Orderstatus = itemView.findViewById(R.id.Status);
        }
    }
}
