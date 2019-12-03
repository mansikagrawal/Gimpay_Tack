package com.example.gimpay;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.AlphabeticIndex;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_Part extends AppCompatActivity {

String cName;
    Button btn1,btn2;//button9 add Button 10 deduct
    int salary=500;
EditText e1,e2;
    FirebaseDatabase database;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__part);
        btn1=findViewById(R.id.button9);//Add
        btn2=findViewById(R.id.button10);//deduct
        e1=findViewById(R.id.editText);
        e2=findViewById(R.id.editText2);

        database=FirebaseDatabase.getInstance();//object of database
        reference=database.getReference("Employee_Details");//create referce
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=e1.getText().toString();
                String pay1=e2.getText().toString();
                int pay=Integer.parseInt(pay1);

                 cName=reference.push().getKey();//make key
                //generated random key
                Record record=new Record();
                record.setMail(email);
                String str=record.getMail();


                if(email.equals(str))
                {

                    String res=e2.getText().toString();
                    int x=Integer.parseInt(res);
                    int amt=add_pay(salary,x);
                    salary=amt;
                    record.setPay(salary);
                    reference.child(cName).setValue(record);



                }
                else
                {
                    Toast.makeText(Admin_Part.this, "User Unknown", Toast.LENGTH_SHORT).show();
                }
               // record.setPrevpay(pay);
               //  int payment=record.getPrevpay();
               //  record.setPay(payment);



                Toast.makeText(Admin_Part.this, "Data saved ", Toast.LENGTH_SHORT).show();

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=e1.getText().toString();
                Record record=new Record();
             record.setMail(email);
             String str=record.getMail();


             if(email.equals(str))
             {

          String pay=e2.getText().toString();
          int pay1=Integer.parseInt(pay);
          int x=deduct_pay(salary,pay1);
          salary=x;
record.setPay(x);
                 reference.child(cName).setValue(record);



             }
             else
             {
                 Toast.makeText(Admin_Part.this, "User Unknown", Toast.LENGTH_SHORT).show();
             }
                Toast.makeText(Admin_Part.this, "Data saved", Toast.LENGTH_SHORT).show();






            }
        });

    }
    static  int add_pay(int initialpay,int passedpay)
    {
        return initialpay+passedpay;
    }

    static  int deduct_pay(int initialpay1,int passedpay1)
    {
        return  initialpay1-passedpay1;
    }


}
