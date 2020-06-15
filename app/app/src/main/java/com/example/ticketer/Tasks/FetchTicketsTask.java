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

public class FetchTicketsTask extends AsyncTask<String, Void, Integer> {
    public static final String API_URL = "http://10.0.2.2:8080/TicketerRestfulService/api";

    @SuppressLint("StaticFieldLeak")
    private Context mContext;

    private ListFragment mFragment;

    private List<Map<String, String>> mTicketList;

    public FetchTicketsTask(ListFragment fragment) {
        this.mFragment = fragment;
        this.mContext = mFragment.getContext();
    }

    @Override
    protected Integer doInBackground(String... strings) {
        int responseCode = 0;
        try {
            URL url = new URL(API_URL + "/tickets/");
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
                JSONArray jsonArray = (JSONArray) json.get("tickets");
                mTicketList = new ArrayList<>(jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    String title = (String) jsonObject.get("title");
                    String desc = (String) jsonObject.get("description");
                    String username = (String) jsonObject.get("username");
                    String date = (String) jsonObject.get("creation_date");

                    Map<String, String> ticket = new HashMap<>();

                    ticket.put("Title", title);
                    ticket.put("Desc", desc + " by: " + username + " - " + date);

                    mTicketList.add(ticket);
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
            msg = "Tickets Fetched";

            SimpleAdapter adapter = new SimpleAdapter(mContext, mTicketList,
                    android.R.layout.simple_list_item_2,
                    new String[]{"Title", "Desc"},
                    new int[]{android.R.id.text1, android.R.id.text2});
            mFragment.setListAdapter(adapter);
            adapter.notifyDataSetChanged();

        } else {
            msg = "Failed to fetch tickets";
        }
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
