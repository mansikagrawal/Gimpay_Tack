package com.example.gimpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.Constraints;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.geo.GeoCategory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.TimeUnit;

public class Services_Updates extends AppCompatActivity {
    ProgressDialog pd;

private  static  final  String TAG="Services_Updates";
Button share,logout;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String email = user.getEmail();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services__updates);
        pd=new ProgressDialog(this);
        share=findViewById(R.id.button4);//getting id for button share location
      logout=findViewById(R.id.button5);// getting id for button logout
// Getting current user

// Display message for notifications
                  Toast.makeText(Services_Updates.this, "Allowed", Toast.LENGTH_SHORT).show();
                  // Defining a constraint that battery should not be low for notifications
                Constraints constraints=new Constraints.Builder().setRequiresBatteryNotLow(true).build();
//Generating a periodic work request with a delay of 150 min. and build the constraints after that request
        //request is enqueued.

                PeriodicWorkRequest periodicWorkRequest=new PeriodicWorkRequest.Builder(MyperiodicWork.class,
                        1, TimeUnit.HOURS).setConstraints(constraints).build();
                WorkManager.getInstance().enqueue(periodicWorkRequest);







                // Implementing Backendless


        Backendless.Geo.addCategory("Employee", new AsyncCallback<GeoCategory>() {
            @Override
            public void handleResponse(GeoCategory response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(Services_Updates
                                .this, ""+fault.getMessage()

                        , Toast.LENGTH_SHORT).show();
            }
        });


        // Button for sharing location
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Services_Updates.this,new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},0);

                }
                else
                {
                    Intent intent=new Intent(Services_Updates.this,MapsActivity.class);
                    intent.putExtra("type",email);
                    startActivity(intent);
                   //startActivity(new Intent(getApplicationContext(),MapsActivity.class));
                }

            }
        });


        //Button for log out
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("Processing...");
                pd.show();
                startActivity(new Intent(getApplicationContext(),Admin_User_Select_Activity.class));
       pd.dismiss();


            }
        });





    }
}
