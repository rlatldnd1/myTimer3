package com.example.mytimer3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TimerActivity extends AppCompatActivity {
    private TextView timerTextView;
    private TextView moneyTextView;
    private Button startButton;
    private Button pauseButton;
    private Button resetButton;
    private Button profileButton;

    private boolean isTimerRunning;
    private long bufferTime;
    private long startTime;
    private long elapsedTime;
    private Handler handler;

    private String username;
    private String password;
    private long cumulativeTime;

    private static int[] studyTime = {0, 5600, 12500, 21900, 58400, 999999};
    private static int[] futureSalary = {0, 2831, 3471, 5434, 23070, 999999};
    private static String[] futureJob = {"백수", "9급 공무원", "7급 공무원", "5급 공무원", "의사"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        cumulativeTime = intent.getLongExtra("cumulativeTime", 0);

        timerTextView = findViewById(R.id.timerTextView);
        moneyTextView = findViewById(R.id.moneyTextView);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        resetButton = findViewById(R.id.resetButton);
        profileButton = findViewById(R.id.profileButton);

        bufferTime = 0;
        handler = new Handler();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveProfile();
            }
        });

    }

    private void startTimer() {
        if (!isTimerRunning) {
            isTimerRunning = true;
            startTime = System.currentTimeMillis();

            handler.postDelayed(timerRunnable, 0);
            resetButton.setEnabled(false);
        }
    }

    private void pauseTimer() {
        if (isTimerRunning) {
            isTimerRunning = false;
            bufferTime += System.currentTimeMillis() - startTime;

            handler.removeCallbacks(timerRunnable);
            updateMoneyTextView(bufferTime);
            resetButton.setEnabled(true);
        }
    }

    private void resetTimer() {
        isTimerRunning = false;
        cumulativeTime += bufferTime;
        elapsedTime = 0;
        bufferTime = 0;

        handler.removeCallbacks(timerRunnable);
        updateTimerTextView(0);
        updateMoneyTextView(0);
    }

    private void moveProfile() {
        Intent intent = new Intent(TimerActivity.this, ProfileActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        intent.putExtra("cumulativeTime", cumulativeTime);

        startActivity(intent);
    }

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - startTime;


            updateTimerTextView(elapsedTime + bufferTime);

            handler.postDelayed(this, 1000); // Update every second
        }
    };

    private void updateTimerTextView(long timeInMillis) {
        int seconds = (int) (timeInMillis / 1000);
        int minutes = seconds / 60;
        seconds %= 60;

        String timeFormatted = String.format("%02d:%02d", minutes, seconds);
        timerTextView.setText(timeFormatted);
    }
    private void updateMoneyTextView(long time) {

        SharedPreferences preference = getSharedPreferences("UserInfo", MODE_PRIVATE);
        Gson gson = new GsonBuilder().create();
        SharedPreferences.Editor editor = preference.edit();

        String jsonUser = preference.getString(username, null);
        User user = gson.fromJson(jsonUser, User.class);

        user.addToCumulativeTime(time);

        String moneyFormatted = String.valueOf(getFutuerSalary(user.getCumulativeTime())) + "원";
        moneyTextView.setText(moneyFormatted);

        jsonUser = gson.toJson(user);
        editor.putString(username, jsonUser);
        editor.apply();

    }
    private long getFutuerSalary(long userStudyTime) {
        for (int i=0; i<6; i++) {
            if (userStudyTime < studyTime[i]) {
                return userStudyTime * futureSalary[i] / studyTime[i];
            }
        }
        return userStudyTime;
    }

    //private static int[] studyTime = {0, 5600, 12500, 21900, 58400, 999999};
    //private static int[] futureSalary = {0, 2831, 3471, 5434, 23070, 999999};
}
