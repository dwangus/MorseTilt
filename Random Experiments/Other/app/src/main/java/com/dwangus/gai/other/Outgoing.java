package com.dwangus.gai.other;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

public class Outgoing extends BroadcastReceiver {
    //http://stackoverflow.com/questions/40495868/how-to-perform-action-on-a-specific-number-dial-in-android
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.e("Number = ", number);

            if (number.equals("123")) {
                //http://stackoverflow.com/questions/18462759/launch-application-by-dialing-a-number-then-disconnect-call-android
                Intent appIntent = new Intent(context, MainActivity.class);
                appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(appIntent);
            }

        } else {
            Log.e("HELLO_2", intent.getAction());
            Log.e("HELLO_BROADCAST_2", "Nope_2");
            final Intent in = new Intent();
            in.setAction("abc");
            in.setComponent(new ComponentName("com.dwangus.gai.morsetilt","com.dwangus.gai.morsetilt.Outgoing"));
            sendBroadcast(in);
        }
    }
}