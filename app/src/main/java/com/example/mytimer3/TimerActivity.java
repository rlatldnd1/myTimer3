package com.example.mytimer3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        long money = cumulativeTime + time;
        String moneyFormatted = String.valueOf(money) + "원";
        moneyTextView.setText(moneyFormatted);


    }
}
