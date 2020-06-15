package com.example.ticketer.Tasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.fragment.app.ListFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FetchLocationsTask extends AsyncTask<String, Void, Integer> {
    public static final String API_URL = "http://10.0.2.2:8080/Locationapp/api";

    @SuppressLint("StaticFieldLeak")
    private Context mContext;

    private ListFragment mFragment;

    private List<Map<String, String>> mLocationList;

    public FetchLocationsTask(ListFragment fragment) {
        this.mFragment = fragment;
        this.mContext = mFragment.getContext();
    }

    @Override
    protected Integer doInBackground(String... strings) {
        int responseCode = 0;
        try {
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
                Log.d("line", line);
                sb.append(line);
            }
            JSONObject json = new JSONObject(sb.toString());

            bufferedReader.close();
            inputStream.close();

            responseCode = conn.getResponseCode();

            conn.disconnect();

            if (json != null) {
                JSONArray jsonArray = (JSONArray) json.get("userlocation");
                mLocationList = new ArrayList<>(jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    String longitude = (String) jsonObject.get("longitude");
                    String altitude = (String) jsonObject.get("altitude");
                    String username = (String) jsonObject.get("username");
                    String date = (String) jsonObject.get("creation_date");

                    Map<String, String> location = new HashMap<>();

                    location.put("longitude", "Altitude: " + altitude + ", longitude: " + longitude);
                    location.put("altitude", username + " - " + date);

                    mLocationList.add(location);
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return responseCode;
    }

    protected void onPostExecute(Integer responseCode) {
        String msg;
        if ((responseCode >= 200) && (responseCode <= 299)) {
            msg = "Locations Fetched";

            SimpleAdapter adapter = new SimpleAdapter(mContext, mLocationList,
                    android.R.layout.simple_list_item_2,
                    new String[]{"longitude", "altitude"},
                    new int[]{android.R.id.text1, android.R.id.text2});
            mFragment.setListAdapter(adapter);
            adapter.notifyDataSetChanged();

        } else {
            msg = "Failed to fetch locations";
        }
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
