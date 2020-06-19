package com.example.locationapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.locationapp.Tasks.UserLogin;
public class LoginActivity extends AppCompatActivity {

    private EditText mUsernameView;
    private EditText mPasswordView;

    private String username;

    TextView userView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = getIntent().getStringExtra("username");

        mUsernameView = findViewById(R.id.userName);
        String passedUsername = getIntent().getStringExtra("username_key");
        userView = (TextView)findViewById(R.id.userName);
        userView.setText(passedUsername);
        mPasswordView = findViewById(R.id.password);
        Button mLoginButtonn = findViewById(R.id.button_first);

        mLoginButtonn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (username != null) {
            mUsernameView.setText(username);
        }
    }

    private void attemptLogin() {
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        String username = mUsernameView.getText().toString().toLowerCase();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // validate info
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            UserLogin userLogin = new UserLogin(this);
            userLogin.execute(username, password);
        }
    }

}
