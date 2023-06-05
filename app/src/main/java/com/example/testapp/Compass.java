package com.example.testapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Compass extends AppCompatActivity implements SensorEventListener {
    ImageView qiblaC;
    SensorManager manager;
    AlertDialog dialog;

    float degree = 0;
    float crrDeg = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        qiblaC = findViewById(R.id.qiblaC);
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if(manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null){
            Toast.makeText(this, "Rotate your phone to calibrate the sensor ", Toast.LENGTH_LONG).show();
        }else {
            showalert();
        }

    }

    private void showalert() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.alert, null);

        dialog = new AlertDialog.Builder(Compass.this).setView(view).create();
        dialog.show();

        Button alertBtn = view.findViewById(R.id.alartBtn);

        alertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Compass.this, homePage.class);
                startActivity(i);
            }
        });
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