package com.example.locationapp.Tasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class UpdateLocationTask extends AsyncTask<String, Void, Integer> {
    public static final String API_URL = "http://10.0.2.2:8080/Locationapp/api";

    @SuppressLint("StaticFieldLeak")
    private Context mContext;

    public UpdateLocationTask(Context context) {
        this.mContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Integer doInBackground(String... parameters) {
        int responseCode = 0;
        try {
            String updateData = URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(parameters[0], "UTF-8") + "&";
            updateData += URLEncoder.encode("altitude", "UTF-8") + "=" + URLEncoder.encode(parameters[1], "UTF-8") + "&";
            updateData += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(parameters[2], "UTF-8");

            Log.d("hi", updateData);

            URL url = new URL(API_URL + "/userlocation/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            // Read the server response and return it as JSON
            InputStream inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                Log.d("lineend", line);
                sb.append(line);

            }
            JSONObject json = new JSONObject(sb.toString());
            JSONArray someString = json.getJSONArray("userlocation");
            JSONObject o = null;
            for (int i = 0; i < someString.length(); i++) {
                o = someString.getJSONObject(i);
                String suser = o.getString("username");

                if (suser.equals(parameters[2])) {
                    o.put("longitude", parameters[0]);
                    o.put("altitude", parameters[1]);
                    json.put("userlocation", someString);
                    Log.d("suserline", o.toString());
                    break;
                }
            Log.d("end", "this is the end"+o.toString());
            }

            bufferedReader.close();
            inputStream.close();

            // Send the request to the server
            responseCode = conn.getResponseCode();
            Log.d("hi", "This is the response code"+responseCode);


            conn.disconnect();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
            osw.write(o.toString());
            osw.flush();
            osw.close();

            responseCode = con.getResponseCode();
            Log.d("seond", "code"+responseCode);

            con.disconnect();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return responseCode;
    }

    protected void onPostExecute(Integer responseCode) {
        String msg;
        if ((responseCode >= 200) && (responseCode <= 299)) {
            msg = "Location was updated successful.";
        } else {
            msg = "Location failed to update.";
        }
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}