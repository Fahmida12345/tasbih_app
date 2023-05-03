package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class Compass extends AppCompatActivity implements SensorEventListener {
    ImageView qiblaC;
    SensorManager manager;

    float degree = 0;
    float crrDeg = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        qiblaC = findViewById(R.id.qiblaC);
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);

    }

    @Override
    protected void onStart() {
    super.onStart();
    manager.registerListener(this, manager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
        protected void onPause() {
            super.onPause();
            manager.unregisterListener(this);
        }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
            degree = Math.round(sensorEvent.values[0]);
            RotateAnimation animation = new RotateAnimation(crrDeg, -degree,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(210);
            animation.setFillAfter(true);

            qiblaC.startAnimation(animation);
            crrDeg = -degree;
        }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}