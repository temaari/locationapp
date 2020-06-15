package com.example.ticketer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button updateButton = (Button) this.findViewById(R.id.updateLocation_button);
        Button getLocationButton = (Button) this.findViewById(R.id.getLocation_button);

        updateButton.setOnClickListener(this);
        getLocationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.updateLocation_button:
                Toast.makeText(this, "Update Location", Toast.LENGTH_SHORT).show();
            break;
            case R.id.getLocation_button:
                Toast.makeText(this, "Get list of users location", Toast.LENGTH_SHORT).show();
            break;
        }
    }
}