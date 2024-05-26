package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.test.Model.User;
import com.example.test.Model.Profille;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CustomerProfileActivity extends AppCompatActivity {

    EditText firstname, lastname , mobile ,Email , Password;

    Button Update;
    LinearLayout password, LogOut;
    DatabaseReference databaseReference, data;
    FirebaseDatabase firebaseDatabase;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageButton btn_back;
    User user  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        firstname = (EditText) findViewById(R.id.fnamee);
        lastname = (EditText) findViewById(R.id.lnamee);

        Email = (EditText) findViewById(R.id.editemail);
        mobile =(EditText)  findViewById(R.id.editMobile);
        Update = (Button) findViewById(R.id.update);
        Password = (EditText) findViewById(R.id.editPassword);
        LogOut = (LinearLayout) findViewById(R.id.logout_layout);
        btn_back = (ImageButton) findViewById(R.id.btn_backProfile) ;

        firstname.setText(Profille.currentUser.getUsername());
        lastname.setText(Profille.currentUser.getName());
        Email.setText(Profille.currentUser.getEmail());
        mobile.setText(Profille.currentUser.getPhone());
        Password.setText(Profille.currentUser.getPassword());

        updateinformation();
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerProfileActivity.this);
                builder.setMessage("Are you sure you want to Logout ?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(CustomerProfileActivity.this, ChooseActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    private void updateinformation() {
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = FirebaseDatabase.getInstance().getReference("users").child(Profille.currentUser.getUsername());
                data.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User customer = dataSnapshot.getValue(User.class);

                        String mobilenoo = mobile.getText().toString().trim();
                        String Uname = firstname.getText().toString().trim();
                        String Name = lastname.getText().toString().trim();
                        String newemail = Email.getText().toString().trim();
                        String newpass = Password.getText().toString().trim();

                        HashMap<String, String> user = new HashMap<>();
                        user.put("email", String.valueOf(newemail));
                        user.put("username", Uname);
                        user.put("name", Name);
                        user.put("phone", String.valueOf(mobilenoo));
                        user.put("password", String.valueOf(newpass));

                        Profille.currentUser.setEmail(newemail);
                        Profille.currentUser.setName(Name);
                        Profille.currentUser.setPassword(newpass);
                        Profille.currentUser.setPhone(String.valueOf(mobilenoo));
                        Profille.currentUser.setUsername(String.valueOf(Uname));
                        firebaseDatabase.getInstance().getReference("users").child(Profille.currentUser.getUsername()).setValue(user);
                        Toast.makeText(CustomerProfileActivity.this,"Updated Successfully!",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CustomerProfileActivity.this,CustumerHomeActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });






    }
}
