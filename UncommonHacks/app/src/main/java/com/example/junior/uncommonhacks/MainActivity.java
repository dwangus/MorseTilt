package com.example.junior.uncommonhacks;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent msgIntent = new Intent(this, MyIntentService.class);
        startService(msgIntent);

//        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
//        MyReceiver mreceiver = new MyReceiver();
//
//        registerReceiver(mreceiver, intentFilter);

//        orientationHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                //final int currentOrientation = getResources().getConfiguration().orientation;
//
//
//
//                StringRequest data = new StringRequest(Request.Method.POST, "https://tiltapp.herokuapp.com/updatedata",
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                Log.i("data", response);
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }){
//                    @Override
//                    protected Map<String, String> getParams(){
//                        Map<String,String> params = new HashMap<>();
//                        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
//                        String date = df.format(Calendar.getInstance().getTime());
//
//                        String orientation = getResources().getConfiguration().orientation == 1 ? "Portrait" : "Landscape";
//                        params.put("orientation",orientation);
//                        params.put("dateandtime", date);
//                        params.put("phoneNumber", "812-562-6455");
//                        Log.d("testing", orientation);
//                        return params;
//                    }
//                };
//                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
//                requestQueue.add(data);
//                try {
//                    Log.i("data: ", data.toString());
//                }
//
//                catch (Exception e){
//
//                }
//
//                orientationHandler.postDelayed(this, 5000);
//            }
//        }, 5000);


    }


}
