package com.example.test.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.Model.User;
import com.example.test.Model.Profille;
import com.example.test.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginAdminActivity extends AppCompatActivity {

    private EditText email,password;

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        email = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference table_user = database.getReference("admin");
        btn = ( Button) findViewById(R.id.btnRegister);


        Button back = (Button) findViewById(R.id.backtoLogin_Options) ;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                table_user.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange( DataSnapshot snapshot) {
                        if(TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString()) ){
                            Toast.makeText(LoginAdminActivity.this, "Please enter user name or pass word", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            if (snapshot.child(email.getText().toString()).exists()) {
                                User user = snapshot.child(email.getText().toString()).getValue(User.class);
                                if (user.getPassword().equals(password.getText().toString())) {
                                    Profille.currentUser = user;
                                    Toast.makeText(LoginAdminActivity.this, "login success", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginAdminActivity.this, AdminHomeActivity.class));


                                } else {
                                    Toast.makeText(LoginAdminActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginAdminActivity.this, "user not exist", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}