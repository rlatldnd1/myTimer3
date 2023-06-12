package com.example.mytimer3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView usernameTextView;
    private TextView emailTextView;
    private TextView ageTextView;
    private Button editButton;


    private String username;
    private String password;
    private String cumulativeTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        cumulativeTime = intent.getStringExtra("cumulativeTime");


        usernameTextView = findViewById(R.id.usernameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        ageTextView = findViewById(R.id.ageTextView);
        editButton = findViewById(R.id.editButton);

        // Set the initial user profile information
        String username = "JohnDoe";
        String email = "johndoe@example.com";
        int age = 25;

        usernameTextView.setText(username);
        emailTextView.setText(email);
        ageTextView.setText(String.valueOf(age));

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the Edit Profile button click event
                Toast.makeText(ProfileActivity.this, "Edit Profile Clicked", Toast.LENGTH_SHORT).show();
                // Add your code for handling the edit button click event
            }
        });
    }
}
