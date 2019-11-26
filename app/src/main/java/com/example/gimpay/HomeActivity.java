package com.example.gimpay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;

public class HomeActivity extends AppCompatActivity {
//  private   FirebaseAuth mauth;
 //ImageButton imageButton;
 Button btn1,btn2,btn3;
 Bitmap bmp;
 ProgressDialog pd;
ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        pd=new ProgressDialog(this);
       // imageButton=(ImageButton) findViewById(R.id.imageView);
        imageView=findViewById(R.id.imageView2);// for camera image click
        btn1=findViewById(R.id.button);// for click image
        btn2=findViewById(R.id.button2);//for access location services
        btn3=findViewById(R.id.button3);//share on whatsapp
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            imageView.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               startActivityForResult(i,0);




            }
        });
       // mauth=FirebaseAuth.getInstance();

       btn3.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {


              Intent share = new Intent(Intent.ACTION_SEND);
               share.setType("image/*");
               share.putExtra(Intent.EXTRA_STREAM, Uri.parse(String.valueOf(imageView)));
               share.setPackage("com.whatsapp");//package name of the app
               startActivity(Intent.createChooser(share, "Share Image"));

           }
       });
       btn2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               pd.setMessage("Processing...");
               pd.show();
               startActivity(new Intent(getApplicationContext(),Services_Updates.class));
               pd.dismiss();
           }
       });




    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                imageView.setEnabled(true);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if((requestCode==0)&&(resultCode==RESULT_OK)&&(data!=null))
        {
            Bundle b=data.getExtras();
            bmp=(Bitmap)b.get("data");
            imageView.setImageBitmap(bmp);
            imageView.buildDrawingCache();
            Bitmap bm=imageView.getDrawingCache();


        }
        else
        {
            Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show();
        }



        super.onActivityResult(requestCode, resultCode, data);
    }




}
