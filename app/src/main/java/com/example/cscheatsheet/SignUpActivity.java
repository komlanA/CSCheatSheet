package com.example.cscheatsheet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    public static final String TAG = "SignUpActivity";
    private EditText etUsername;
    private EditText etPassword;
    private EditText etPasswordCheck;
    private Button btnSignUp2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etPasswordCheck = findViewById(R.id.etPasswordCheck);
        btnSignUp2 = findViewById(R.id.btnSignUp2);

        btnSignUp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick sign up button");
                String pw1 = etPassword.getText().toString();
                String pw2 = etPasswordCheck.getText().toString();
                if (etUsername.getText().toString().isEmpty()) {
                    Log.e(TAG, "Empty username");
                    Log.i(TAG, "onClick sign up button");
                    Toast.makeText(SignUpActivity.this, "Cannot leave username empty!", Toast.LENGTH_SHORT).show();
                } else if (etPassword.getText().toString().isEmpty() || etPasswordCheck.getText().toString().isEmpty()) {
                    Log.e(TAG, "Password field(s) empty");
                    Toast.makeText(SignUpActivity.this, "Cannot leave password field(s) empty!", Toast.LENGTH_SHORT).show();
                } else if (!pw1.equals(pw2)) {
                    Log.e(TAG, "Passwords do not match, pw1: " + pw1 + " pw2: " + pw2);
                    Toast.makeText(SignUpActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    etPassword.setText("");
                    etPasswordCheck.setText("");
                } else {
                    // Create the ParseUser
                    ParseUser user = new ParseUser();
                    // Set core properties
                    user.setUsername(etUsername.getText().toString());
                    user.setPassword(etPassword.getText().toString());

                    // Invoke signUpInBackground
                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e != null) {
                                // TODO: better error handling
                                Log.e(TAG, "Issue with sign up", e);
                                Toast.makeText(SignUpActivity.this, "Issue with sign up!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            goLoginActivity();
                            Toast.makeText(SignUpActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    private void goLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
