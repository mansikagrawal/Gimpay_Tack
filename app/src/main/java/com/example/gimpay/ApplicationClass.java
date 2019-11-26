package com.example.gimpay;

import android.app.Application;

import com.backendless.Backendless;

public class ApplicationClass extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Backendless.setUrl("https://api.backendless.com");
        Backendless.initApp(getApplicationContext(),"45AE2E4D-90E2-4771-FFAD-700B746E4C00","ADAF09A1-1DC6-BD62-FF21-973E73052A00");
    }



}
