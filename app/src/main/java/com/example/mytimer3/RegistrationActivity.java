package com.example.mytimer3;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class RegistrationActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText passwordCheckEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordCheckEditText = findViewById(R.id.passwordCheckEditText);
        registerButton = findViewById(R.id.registerButton);

        SharedPreferences preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        Gson gson = new GsonBuilder().create();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String passwordCheck = passwordCheckEditText.getText().toString();
                String value = preferences.getString(username, null);

                if (value != null) {
                    Toast.makeText(RegistrationActivity.this, "중복 ID입니다.", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(passwordCheck)) {
                    Toast.makeText(RegistrationActivity.this, "비밀번호 확인이 다릅니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences.Editor editor = preferences.edit();
                    User user = new User(username, password);
                    String jsonUser = gson.toJson(user);
                    editor.putString(username, jsonUser);
                    editor.apply();
                    Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
