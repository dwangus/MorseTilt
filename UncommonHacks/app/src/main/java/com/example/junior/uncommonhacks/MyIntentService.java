package com.example.junior.uncommonhacks;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Log;

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

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService implements SensorEventListener {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private SensorManager mSensorManager;
    private final float[] mAccelerometerReading = new float[3];
    private final float[] mMagnetometerReading = new float[3];

    private final float[] mRotationMatrix = new float[9];
    private final float[] mOrientationAngles = new float[3];

    String orientation;
    Handler orientationHandler = new Handler();

    public MyIntentService() {
        super("MyIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);


        orientationHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //final int currentOrientation = getResources().getConfiguration().orientation;


                debugGyro();
                StringRequest data = new StringRequest(Request.Method.POST, "https://tiltapp.herokuapp.com/updatedata",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("data", response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String,String> params = new HashMap<>();
                        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
                        String date = df.format(Calendar.getInstance().getTime());



                        //String orientation = getResources().getConfiguration().orientation == 1 ? "Portrait" : "Landscape";

                        params.put("orientation",orientation);
                        params.put("dateandtime", date);
                        params.put("phoneNumber", "812-562-6455");
                       Log.d("testing", orientation);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(MyIntentService.this);
                requestQueue.add(data);
                try {
                    Log.i("data: ", data.toString());
                }

                catch (Exception e){

                }

                orientationHandler.postDelayed(this, 5000);
            }
        }, 5000);

    }




    void debugGyro() {
        updateOrientationAngles();
        Log.i("mOrientationAngle1", ": " +  mOrientationAngles[0]);
        Log.d("mOrienationAngle2", ": " +  mOrientationAngles[1]);
        Log.d("mOrientationAngle3", ": " +  mOrientationAngles[2]);


    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, mAccelerometerReading,
                    0, mAccelerometerReading.length);
        }
        else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, mMagnetometerReading,
                    0, mMagnetometerReading.length);
        }

    }

    public void updateOrientationAngles() {
        // Update rotation matrix, which is needed to update orientation angles.
        mSensorManager.getRotationMatrix(mRotationMatrix, null,
                mAccelerometerReading, mMagnetometerReading);

        // "mRotationMatrix" now has up-to-date information.

        mSensorManager.getOrientation(mRotationMatrix, mOrientationAngles);

        mOrientationAngles[0]= mOrientationAngles[0] * 57;
        mOrientationAngles[1] = mOrientationAngles[1] * 57;

        if(Math.abs(mOrientationAngles[1]) > 60){
            orientation = "Portrait";
        }
        else if(Math.abs(mOrientationAngles[1]) < 60){
            orientation = "Landscape";
        }

        //Log.d("Orieantation", orientation);




        // "mOrientationAngles" now has up-to-date information.
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
