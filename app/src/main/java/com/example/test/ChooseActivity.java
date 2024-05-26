package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.test.Admin.LoginAdminActivity;

public class ChooseActivity extends AppCompatActivity {
    Button Admin, User;
    Intent intent;
    String type;
    ConstraintLayout bgimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        Admin = (Button) findViewById(R.id.chef);
        User = (Button) findViewById(R.id.customer);

        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ChooseActivity.this, LoginAdminActivity.class);
                startActivity(it);
            }
        });
        User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iten = new Intent(ChooseActivity.this,LoginActivity.class);
                startActivity(iten);
            }
        });

    }
}