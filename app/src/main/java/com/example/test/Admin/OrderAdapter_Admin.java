package com.example.test.Admin;

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

import com.example.test.CustomerProfileActivity;
import com.example.test.CustumerHomeActivity;
import com.example.test.FilterOrder;
import com.example.test.Model.Profille;
import com.example.test.Model.Request;
import com.example.test.Model.User;
import com.example.test.OrderAdapter;
import com.example.test.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class OrderAdapter_Admin extends RecyclerView.Adapter<OrderAdapter_Admin.ViewHolder> implements Filterable {


    Context context;
    ArrayList<Request> Order,filterList;
    FilterOrder_Admin filterOrder;
    FirebaseDatabase firebaseDatabase;

    public OrderAdapter_Admin(Context context, ArrayList<Request> Order) {
        this.context = context;
        this.Order = Order;
        this.filterList = Order;
    }


    @NonNull
    @Override
    public OrderAdapter_Admin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order_admin,parent,false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter_Admin.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        holder.Orderid.setText(Order.get(position).getId());
        holder.Ordername.setText(Order.get(position).getName());
        holder.Orderaddress.setText(Order.get(position).getAddress());
        holder.Ordertotal.setText(Order.get(position).getTotal());


        holder.shipped.setVisibility(View.GONE);
        holder.toShipBtn.setVisibility(View.GONE);
        holder.received.setVisibility(View.GONE);


        if (Order.get(position).getStatus().equals("0")){
            holder.shipped.setVisibility(View.GONE);
            holder.toShipBtn.setVisibility(View.VISIBLE);
            holder.received.setVisibility(View.GONE);

        }
        if (Order.get(position).getStatus().equals("1")){
            holder.shipped.setVisibility(View.VISIBLE);
            holder.toShipBtn.setVisibility(View.GONE);
            holder.received.setVisibility(View.GONE);

        }
        if (Order.get(position).getStatus().equals("2")){
            holder.shipped.setVisibility(View.GONE);
            holder.toShipBtn.setVisibility(View.GONE);
            holder.received.setVisibility(View.VISIBLE);
        }


        holder.toShipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference data = FirebaseDatabase.getInstance().getReference("Request").child(Order.get(position).getId());
                data.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Thay đổi trạng thái thành "1" (đang vận chuyển)
                        String status = "1";
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


    }
    @Override
    public Filter getFilter() {
        if(filterOrder == null){
            filterOrder = new FilterOrder_Admin(filterList,  this);
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
        TextView Orderid,Ordername,Orderaddress,Ordertotal,Orderstatus ,shipped , received;
        Button toShipBtn ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Orderid = itemView.findViewById(R.id.OderID);
            Ordername = itemView.findViewById(R.id.UserN_admin);
            Orderaddress = itemView.findViewById(R.id.Address_admin);
            Ordertotal = itemView.findViewById(R.id.Totall_admin);
            toShipBtn = itemView.findViewById(R.id.btntoShip);
            shipped= itemView.findViewById(R.id.Shipped) ;
            received = itemView.findViewById(R.id.Received);
            //Orderstatus = itemView.findViewById(R.id.Status);
        }
    }
}
