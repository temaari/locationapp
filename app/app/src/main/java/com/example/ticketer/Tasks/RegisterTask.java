package com.example.ticketer.Tasks;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.example.ticketer.LoginActivity;
import com.example.ticketer.MainActivity;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class RegisterTask extends AsyncTask<String, Void, Integer> {
    public static final String API_URL = "http://10.0.2.2:8080/TicketerRestfulService/api";

    @SuppressLint("StaticFieldLeak")
    private Context mContext;

    private String username;

    public RegisterTask(Context context) {
        this.mContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Integer doInBackground(String... parameters) {
        username = parameters[3];
        int responseCode = 0;
        try {
            String postData = URLEncoder.encode("firstName", "UTF-8") + "=" + URLEncoder.encode(parameters[0], "UTF-8") + "&";
            postData += URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode(parameters[1], "UTF-8") + "&";
            postData += URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(parameters[2], "UTF-8") + "&";
            postData += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&";
            postData += URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(parameters[4], "UTF-8");

            Log.d("hi", postData);
            URL url = new URL(API_URL + "/users/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // Send the request to the server
            OutputStream outputStream = conn.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            bufferedWriter.write(postData);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            responseCode = conn.getResponseCode();

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseCode;
    }

    protected void onPostExecute(Integer responseCode) {
        String msg;
        if ((responseCode >= 200) && (responseCode <= 299)) {
            msg = "Account creation was successful";
            Intent myIntent = new Intent(mContext, LoginActivity.class);
            myIntent.putExtra("username", username);
            ActivityCompat.finishAffinity((Activity) mContext);
            mContext.startActivity(myIntent);
        } else {
            msg = "Account creation failed";
        }
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}