package com.example.pr3;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("UnspecifiedImmutableFlag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        EditText edtHours = findViewById(R.id.edtHours);
        EditText edtMinutes = findViewById(R.id.edtMinutes);
        EditText edtSeconds = findViewById(R.id.edtSeconds);
        View btnStartAlarm = findViewById(R.id.btnStartAlarm);

        btnStartAlarm.setOnClickListener(view -> {
            int hours = Integer.parseInt(edtHours.getText().toString());
            int minutes = Integer.parseInt(edtMinutes.getText().toString());
            int seconds = Integer.parseInt(edtSeconds.getText().toString());

            if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59 || seconds < 0 || seconds > 59) {
                Toast.makeText(MainActivity.this, "Некорректное время", Toast.LENGTH_SHORT).show();
                return;
            }

            long triggerTime = System.currentTimeMillis() +
                    (hours * 3600 + minutes * 60 + seconds) * 1000L;

            Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent,
                    PendingIntent.FLAG_IMMUTABLE);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);

            Toast.makeText(MainActivity.this, "Будильник установлен!", Toast.LENGTH_SHORT).show();
        });
    }
}