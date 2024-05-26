package com.example.test;

import static java.util.ResourceBundle.getBundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.test.Admin.AddActivity;
import com.example.test.Model.Clothes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class DetailActivity extends AppCompatActivity {
    private TextView addToCartBtn;
    private TextView titleTxt,feeTxt,numberOrderTxt;
    private ImageView plusBtn,MinusBtn,picFood;
    private Clothes object;
    int numberOrder = 1;
    private ManagementCart managementCart;
    DatabaseReference databaseReference ;
    FirebaseDatabase firebaseDatabase ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        databaseReference = firebaseDatabase.getInstance().getReference("Clothes");

        managementCart = new ManagementCart(this);
        initView();
        getBundle();
        ImageButton back = (ImageButton) findViewById(R.id.backDetail);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    private void getBundle()
    {
        object = (Clothes) getIntent().getSerializableExtra("object");
        Glide.with(this)
                .load(object.getPic())
                .into(picFood);

        titleTxt.setText(object.getTitle());
        feeTxt.setText("$ " + object.getFee());
//        descriptionTxt.setText(object.getDescription());
        numberOrderTxt.setText(String.valueOf(numberOrder));

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrder += 1;
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });
        MinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberOrder > 1)
                {
                    numberOrder -=1;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));

            }
        });
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( Integer.parseInt(object.getQuantity()) < numberOrder){
                    Toast.makeText(DetailActivity.this,"So luong khong du",Toast.LENGTH_SHORT).show();
                }
                else {
                    object.setNumberInCart(numberOrder);
                    managementCart.insertClothes(object);


                    DatabaseReference quantityRef = databaseReference.child(object.getId()).child("quantity");
                    quantityRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String currentValue = dataSnapshot.getValue(String.class);
                                int newValue = Integer.parseInt(currentValue) - numberOrder;
                                quantityRef.setValue(String.valueOf(newValue));
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Xử lý khi có lỗi xảy ra
                        }
                    });
                    startActivity(new Intent(DetailActivity.this, CustumerHomeActivity.class));
                }

            }
        });
    }
    private void initView()
    {
        addToCartBtn = findViewById(R.id.Btn_addtocart);
        titleTxt = findViewById(R.id.title);
        feeTxt = findViewById(R.id.priceTxt);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        plusBtn = findViewById(R.id.btn_plus);
        MinusBtn = findViewById(R.id.btn_minus);
        picFood = findViewById(R.id.picCloth);

    }

}