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

import com.example.ticketer.MainActivity;
import com.example.ticketer.TicketActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Parameter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UserLogin extends AsyncTask<String, Void, Integer> {
    public static final String API_URL = "http://10.0.2.2:8080/TicketerRestfulService/api";

    private boolean success;

    private String username;

    @SuppressLint("StaticFieldLeak")
    private Context mContext;

    public UserLogin(Context context) {
        this.mContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Integer doInBackground(String... parameters) {
        username = parameters[0];
        int responseCode = 0;
        try {
            URL url = new URL(API_URL + "/users/"+username);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Log.d("This is the sinan", parameters[0]+".."+url);
            InputStream inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                Log.d("line", line);
                sb.append(line);
            }

            JSONObject json = new JSONObject(sb.toString());

            bufferedReader.close();
            inputStream.close();

            responseCode = conn.getResponseCode();

            conn.disconnect();

            if (json != null) {
                Log.d("some message", parameters[0]+":"+parameters[1]);
                try {
                    JSONArray jsonArray = (JSONArray) json.get("users");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        String password = (String) jsonObject.get("password");
                        if (password.equals(parameters[1])) {
                            Log.d("Password is ", "correct");
                            success = true;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return responseCode;
    }

    protected void onPostExecute(Integer responseCode) {
        String msg;
        Log.d("some login", "this.success" + success );
        if ((responseCode >= 200) && (responseCode <= 299) && success) {
            msg = "Login was successful";
            Intent myIntent = new Intent(mContext, MainActivity.class);
            myIntent.putExtra("username", username);
            ActivityCompat.finishAffinity((Activity) mContext);
            mContext.startActivity(myIntent);
        } else {
            msg = "Login failed";
        }
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}