package com.example.gimpay;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyperiodicWork extends Worker {
    private  static  final  String TAG="MyperiodicWork";
    public MyperiodicWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
      //  Log.e(TAG,"Time to update the currrent location");
        displayNotification("Open Gimpay","Update your current Location");
        return Result.success();
    }
    private  void  displayNotification(String task,String desc)
    {
        NotificationManager manager= (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel("simplifiedcoding","simplifiedcoding",NotificationManager.IMPORTANCE_HIGH);
        manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),"simplifiedcoding").
                setContentTitle(task)
                .setContentText(desc)
                .setSmallIcon(R.mipmap.ic_launcher);
        manager.notify(1,builder.build());//To get Notification


    }
}
