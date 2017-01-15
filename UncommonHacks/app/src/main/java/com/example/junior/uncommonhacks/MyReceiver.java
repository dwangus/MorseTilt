package com.example.junior.uncommonhacks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

//        Intent appIntent = new Intent(context, MainActivity.class);
//        appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(appIntent);
//        setResultData(null);
//        // an Intent broadcast.
        System.out.println("h");
        Log.d("calling.uncommon", "calling");
        Log.i("calling.uncommon", "calling");
        Toast.makeText(context,"WORKS!",Toast.LENGTH_LONG).show();
    }


}
