package com.example.gimpay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class User_id_loginAdmin extends AppCompatActivity {



    FirebaseAuth auth;
    ProgressDialog progressDialog;
    Button btn1,btn2,btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_id_login_admin);
      btn1=findViewById(R.id.button6);//Update Payment
      btn2=findViewById(R.id.button7);//Share
      btn3=findViewById(R.id.button8);//Log out
        auth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri= Uri.parse("https://develop.backendless.com/Me_Hu/geo");
                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
                progressDialog.setMessage("Processing...");
                progressDialog.show();


            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Admin_User_Select_Activity.class));
            }
        });
    }
}
