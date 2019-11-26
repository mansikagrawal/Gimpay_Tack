package com.example.gimpay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class User_Signup_Activity extends AppCompatActivity {
TextView txt2;

EditText e1,e2,e3;
Button btn1;
ProgressDialog progressDialog;
FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__signup_);
        txt2=findViewById(R.id.signin_txt);
        e1=findViewById(R.id.email_signup);
        e2=findViewById(R.id.pass_signup);
        progressDialog=new ProgressDialog(this);
        e3=findViewById(R.id.confirmpass_signup);
        btn1=findViewById(R.id.signup_btn);
        mauth=FirebaseAuth.getInstance();
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),User_Activity.class));
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=e1.getText().toString().trim();
                String pass=e2.getText().toString().trim();
                String confirm=e3.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    e1.setError("Required Field");
                return;
                }
                if(TextUtils.isEmpty(pass))
                {
                    e2.setError("Required Field");return;

                }
                if(pass.equalsIgnoreCase(confirm)!=true)
                {
                    e3.setError("Retype password");
                }
                //progress dialog
                progressDialog.setMessage("Processing...");
                progressDialog.show();
                mauth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(User_Signup_Activity.this, "Successful..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        progressDialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(User_Signup_Activity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    }
                });
            }
        });
    }
}
