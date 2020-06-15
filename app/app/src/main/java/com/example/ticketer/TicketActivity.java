package com.example.ticketer;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ticketer.Tasks.NewTicketTask;


public class TicketActivity extends AppCompatActivity {
    private EditText mTitleView;
    private EditText mDescriptionView;

    String username;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ticket);

        username = getIntent().getStringExtra("username");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("New Ticket");

        mTitleView = findViewById(R.id.title);
        mDescriptionView = findViewById(R.id.desciption);

        Button mSubmitBtn = findViewById(R.id.submitButton);

        mSubmitBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSubmit();
            }
        });
    }

    private void attemptSubmit() {
        mTitleView.setError(null);
        mDescriptionView.setError(null);
        // mUsernameView.setError(null);

        String title = mTitleView.getText().toString();
        String desciption = mDescriptionView.getText().toString();
        // String username = mUsernameView.getText().toString().toLowerCase();

        boolean cancel = false;
        View focusView = null;

        // validate info
        if (TextUtils.isEmpty(title)) {
            mTitleView.setError(getString(R.string.error_field_required));
            focusView = mTitleView;
            cancel = true;
        }
        if (TextUtils.isEmpty(desciption)) {
            mDescriptionView.setError(getString(R.string.error_field_required));
            focusView = mDescriptionView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            NewTicketTask newTicketTask = new NewTicketTask(this);
            newTicketTask.execute(title, desciption, username);
        }
    }

}
