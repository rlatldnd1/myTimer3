package com.example.mytimer3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ProfileActivity extends AppCompatActivity {

    private EditText usernameText;
    private EditText passwordText;
    private EditText cumulativeTimeText;
    private Button editButton;


    private String username;
    private String password;
    private long cumulativeTime;
    private boolean isEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        cumulativeTime = intent.getLongExtra("cumulativeTime", 0);


        usernameText = findViewById(R.id.usernameEditText);
        passwordText = findViewById(R.id.passwordEditText);
        cumulativeTimeText = findViewById(R.id.cumulativeTimeEditText);
        editButton = findViewById(R.id.editButton);


        usernameText.setText(username);
        passwordText.setText(password);
        cumulativeTimeText.setText(Long.toString(cumulativeTime));
        isEnabled = false;

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEnabled) {
                    User newUser = new User(usernameText.getText().toString(), passwordText.getText().toString(), Long.parseLong(cumulativeTimeText.getText().toString()));
                    editProfile(newUser);

                    passwordText.setEnabled(false);
                    cumulativeTimeText.setEnabled(false);
                    Toast.makeText(ProfileActivity.this, "수정됨", Toast.LENGTH_SHORT).show();
                    isEnabled = false;
                }

                else {
                    passwordText.setEnabled(true);
                    cumulativeTimeText.setEnabled(true);
                    Toast.makeText(ProfileActivity.this, "변경하세요", Toast.LENGTH_SHORT).show();
                    isEnabled = true;
                }
            }
        });

    }
    private void editProfile(User user) {

        SharedPreferences preference = getSharedPreferences("UserInfo", MODE_PRIVATE);
        Gson gson = new GsonBuilder().create();
        SharedPreferences.Editor editor = preference.edit();

        String jsonUser = gson.toJson(user);
        editor.putString(username, jsonUser);
        editor.apply();

    }
}
