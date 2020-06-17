package com.example.ticketer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String username;
    private FragmentRefreshListener fragmentRefreshListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = getIntent().getStringExtra("username");
        TextView loggedinUsers = (TextView) this.findViewById(R.id.textView);
        loggedinUsers.append(" ");
        loggedinUsers.append(username);

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
            LocationFragment locationFragment = new LocationFragment();
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.setReorderingAllowed(false);
//                transaction.detach(locationFragment).attach(locationFragment).commit();
                if(getFragmentRefreshListener()!= null){
                    getFragmentRefreshListener().onRefresh();
                }
            break;
        }
    }

    public FragmentRefreshListener getFragmentRefreshListener() {
        return fragmentRefreshListener;
    }

    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener;
    }

    public interface FragmentRefreshListener{
        void onRefresh();
    }
}