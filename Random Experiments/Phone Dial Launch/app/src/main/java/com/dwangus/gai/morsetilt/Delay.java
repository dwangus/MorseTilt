package com.dwangus.gai.morsetilt;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by David Wang on 1/15/2017.
 */
public class Delay extends IntentService {

    public Delay() {
        super("Name for service");
    }

    @Override
    protected void onHandleIntent (Intent intent) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sendBroadcast(new Intent("xyz"));
    }
}
