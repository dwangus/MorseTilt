package com.dwangus.gai.morsetilt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

//http://stackoverflow.com/questions/10428510/how-to-start-launch-application-at-boot-time-android
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("START", "Starting BootReceiver");
        /*
        Intent myIntent = new Intent(context, SettingsActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
        */
        //http://stackoverflow.com/questions/2974276/run-my-application-in-background-when-i-start-device-power-on-in-android
        Intent myIntent = new Intent(context, BootService.class);
        context.startService(myIntent);
        /*
        Note: A service too is not guaranteed to run from device boot to device shut down,
            as in extreme cases the Android system may kill the service also to gain additional memory.
         */
    }
}
