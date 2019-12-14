package com.assignment.finalassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private int x;
    private int y;
    private int viewW;
    private int viewH;
    private AnimatedView mAnimatedView = null;
    private Button Btnsave;
    private Button viewList;
    FirebaseDatabase db;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        viewW = this.getResources().getDisplayMetrics().widthPixels;
        viewH = this.getResources().getDisplayMetrics().heightPixels;
        setContentView(R.layout.activity_main);

       mAnimatedView = findViewById(R.id.animation);
        Btnsave = findViewById(R.id.save);
        viewList = findViewById(R.id.list);

//        mAnimatedView = new AnimatedView(this);
//        //Set our content to a view, not like the traditional setting to a layout
//        setContentView(mAnimatedView);


        //Firebase
        db = FirebaseDatabase.getInstance();
       databaseReference = FirebaseDatabase.getInstance().getReference("points").child("save_points");


        Btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"X:: "+mAnimatedView.x+"Y:: "+mAnimatedView.y,Toast.LENGTH_LONG).show();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                Date date = new Date();
                date.setTime(timestamp.getTime());
                String formattedDate = new SimpleDateFormat("yyyyMMdd").format(date);
                Map<String, Object> map = new HashMap<>();
                map.put("timestamp", formattedDate );
                map.put("x", mAnimatedView.x+"");
                map.put("y", mAnimatedView.y+"");
                databaseReference.push().setValue(map);
            }
        });


        viewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,ListActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAccelerometer != null) {
            mSensorManager.registerListener(MainActivity.this, mAccelerometer,
                    SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mAnimatedView.onSensorEvent(sensorEvent);
        }
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(MainActivity.this, mAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);

    }
}
