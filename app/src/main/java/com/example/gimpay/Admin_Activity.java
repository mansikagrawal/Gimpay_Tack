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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Admin_Activity extends AppCompatActivity {

    Button btn3;
    ProgressDialog pd;
FirebaseAuth adauth;
//Edit Texts
    EditText e1,e2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_);
        btn3=findViewById(R.id.admin_btn);
        adauth=FirebaseAuth.getInstance();
        pd=new ProgressDialog(this);
        e1=findViewById(R.id.email_admin);
        e2=findViewById(R.id.pass_admin);

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String admail=e1.getText().toString().trim();
                String adpass=e2.getText().toString().trim();
                if(TextUtils.isEmpty(admail))
                {
                    e1.setError("Required Field ");
                    return;
                }
                if(TextUtils.isEmpty(adpass))
                {
                    e2.setError("Required Field ");
                    return;
                }


                //progress dialog
                pd.setMessage("Processing...");
                pd.show();
                adauth.signInWithEmailAndPassword(admail,adpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Admin_Activity.this, "Successful..", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),User_id_loginAdmin.class));
                            pd.dismiss();
                        }
                        else
                        {
                            Toast.makeText(Admin_Activity.this, "Error", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }
                });
                //startActivity(new Intent(getApplicationContext(),User_id_loginAdmin.class));
            }
        });

    }
}
