package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.Model.User;
import com.example.test.Model.Profille;
import com.example.test.User.SignUpActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText email,password;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference table_user = database.getReference("users");
        btn = ( Button) findViewById(R.id.btnLogin);
        Button back = (Button) findViewById(R.id.btnBack) ;
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
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(TextUtils.isEmpty(email.getText().toString()) ){
                            /*email.requestFocus();*/
                            email.setError("Pleas enter your user name");
                        }
                        else if( TextUtils.isEmpty(password.getText().toString())){
                            /*password.requestFocus();*/
                            password.setError("Pleas enter your password ");
                        }

                        else{
                            if(snapshot.child(email.getText().toString()).exists()) {
                                User user = snapshot.child(email.getText().toString()).getValue(User.class);
                                if (user.getPassword().equals(password.getText().toString())) {
                                    Profille.currentUser = user;
                                    Toast.makeText(LoginActivity.this, "login success", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, CustumerHomeActivity.class));
                                    email.setText("");
                                    password.setText("");
                                } else {
                                    Toast.makeText(LoginActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "user not exist", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        TextView signup = findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigationToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_SHORT).show();
            }
        }
    }
    void navigationToSecondActivity(){
        Intent intent = new Intent(LoginActivity.this, CustumerHomeActivity.class);
        startActivity(intent);
    }
}