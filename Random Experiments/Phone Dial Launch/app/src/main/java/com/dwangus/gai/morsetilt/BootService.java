package com.dwangus.gai.morsetilt;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

/**
 The Android system force-stops a service only when memory is low and it must recover system resources for the activity that has user focus.
 If the service is bound to an activity that has user focus, it's less likely to be killed; if the service is declared to run in the foreground,
 it's rarely killed. If the service is started and is long-running, the system lowers its position in the list of background tasks over time,
 and the service becomes highly susceptible to killing—if your service is started, you must design it to gracefully handle restarts by the system.
 If the system kills your service, it restarts it as soon as resources become available, but this also depends on the value that you return from onStartCommand().
 For more information about when the system might destroy a service, see the Processes and Threading document.
 */
public class BootService extends Service {

    /** indicates how to behave if the service is killed */
    int mStartMode;

    //https://developer.android.com/reference/android/content/BroadcastReceiver.html
    //http://stackoverflow.com/questions/9092134/broadcast-receiver-within-a-service
    private final BroadcastReceiver outgoingCall = new BroadcastReceiver() {
        //http://stackoverflow.com/questions/40495868/how-to-perform-action-on-a-specific-number-dial-in-android
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
                String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                Log.e("Number = ", number);

                if (number.equals("123")) {
                    //http://stackoverflow.com/questions/18462759/launch-application-by-dialing-a-number-then-disconnect-call-android
                    Intent appIntent = new Intent(context, SettingsActivity.class);
                    appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(appIntent);
                }
            } else {
                Log.e("ACTION", intent.getAction());
                Log.e("BROADCAST", "Couldn't start broadcastreceiver");
            }
        }
    };

    /*
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(this, "Service started by user.", Toast.LENGTH_LONG).show();
    }
    */

    /** Called when the service is being created. */
    @Override
    public void onCreate() {
        Log.e("START_SERVICE", "Starting BootService");
        //http://stackoverflow.com/questions/9092134/broadcast-receiver-within-a-service
        try {
            String act = "com.example.foo.bar.CUSTOMACTION";


            IntentFilter filter = new IntentFilter();
            //The Intent namespace is global. Make sure that Intent action names and other strings are written
            // in a namespace you own, or else you may inadvertently conflict with other applications.
            filter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);


            filter.addAction(act);
            filter.addCategory("android.intent.category.DEFAULT");

            registerReceiver(outgoingCall, filter);
        } catch(Exception e) {
            Log.e("ERROR", e.toString());
        }
        Log.e("CREATE", "Created BootService");
    }

    /** The service is starting, due to a call to startService() */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return mStartMode;
        //https://developer.android.com/reference/android/app/Service.html#onStartCommand%28android.content.Intent,%20int,%20int%29
        Log.e("ONSTART", "Starting command");
        sendBroadcast(new Intent("com.example.foo.bar.CUSTOMACTION"));
        return START_STICKY;
    }

    /** A client is binding to the service with bindService() */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /** Called when The service is no longer used and is being destroyed */
    @Override
    public void onDestroy() {
        Log.e("ONDESTROY", "Destroying service");
        //unregisterReceiver(outgoingCall);//Do I need this?
    }
}


/*
From the original "OutgoingCallReceiver.java":
package com.dwangus.gai.morsetilt;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

public class OutgoingCallReceiver extends BroadcastReceiver {
    //http://stackoverflow.com/questions/40495868/how-to-perform-action-on-a-specific-number-dial-in-android
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.e("Number = ", number);

            if (number.equals("123")) {
                //http://stackoverflow.com/questions/18462759/launch-application-by-dialing-a-number-then-disconnect-call-android
                Intent appIntent = new Intent(context, SettingsActivity.class);
                appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(appIntent);
            }

        }
    }
}


 */