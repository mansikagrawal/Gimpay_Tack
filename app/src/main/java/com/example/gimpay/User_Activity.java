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

public class User_Activity extends AppCompatActivity {

    TextView txt;
    Button btn4;
EditText e1,e2;
Button btn;
FirebaseAuth mauth;
ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_);
        e1=findViewById(R.id.email_login_user);
        e2=findViewById(R.id.pass_login_user);
        btn=findViewById(R.id.login_btn);
        mauth=FirebaseAuth.getInstance();
        pd=new ProgressDialog(this);
        txt=findViewById(R.id.signup_txt);
        btn4=findViewById(R.id.login_btn);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),User_Signup_Activity.class));
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(getApplicationContext(),));
                String lemail=e1.getText().toString().trim();
                String lpass=e2.getText().toString().trim();
                if(TextUtils.isEmpty(lemail))
                {e1.setError("Required Field");
                   // Toast.makeText(User_Activity.this, "Required Field", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(lpass))
                {
                    e2.setError("Required Field");
return;                }
                mauth.signInWithEmailAndPassword(lemail,lpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                            Toast.makeText(User_Activity.this, "Successful..", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            pd.dismiss();
                        }
                        else
                        {
                            Toast.makeText(User_Activity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }
                });
            }
        });
    }
}
