package com.example.mike.tarucevent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Mike on 10/18/2017.
 */

public class SearchTask extends AsyncTask<String, Void, String> {

    Context context;
    android.app.AlertDialog alertDialog;
    SearchTask(Context ctx){
        context = ctx;
    }
    ProgressDialog pd;
    String studentId = "";
    Connectivity connectivity = new Connectivity();

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String ipAddress = "" + connectivity.getIP();
        String search_url = ipAddress + "/Android/searchEvent.php";

        if(type.equals("searchEvent")){
            try {
                String input = params[1];
                studentId = params[2];
                URL url = new URL(search_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("input", "UTF-8")+"="+URLEncoder.encode(input, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result="";
                String line="";
                while ((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute(){
        pd = new ProgressDialog(context,R.style.LoadingDialog);
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_Holo_Spinner);
        pd.show();
    }

    @Override
    protected void onPostExecute(final String result) {
        Integer splashInterval = 1500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pd.cancel();
                ArrayList<String> IdArray = new ArrayList<>();
                ArrayList<String> titleArray = new ArrayList<>();
                ArrayList<String> descriptionArray = new ArrayList<>();
                String eventId = "";
                String eventTitle = "";
                String eventDescription = "";
                if (result.equals("fail")){
                    //do shit pls
                } else {
                    try {
                        JSONObject response = new JSONObject(result);
                        eventId = response.getString("EventId");
                        eventTitle = response.getString("EventTitle");
                        eventDescription = response.getString("EventDescription");

                        JSONArray jsonArrayId = new JSONArray(eventId);
                        JSONArray jsonArrayTitle = new JSONArray(eventTitle);
                        JSONArray jsonArrayDescription = new JSONArray(eventDescription);

                        for (int i = 0; i < jsonArrayId.length(); i++) {
                            JSONObject jsonObjectId = jsonArrayId.optJSONObject(i);
                            JSONObject jsonObjectTitle = jsonArrayTitle.optJSONObject(i);
                            JSONObject jsonObjectDescription = jsonArrayDescription.optJSONObject(i);
                            if(jsonObjectId == null) {
                                continue;
                            }
                            String jsonValueId = jsonObjectId.optString("EventId");
                            String jsonValueTitle = jsonObjectTitle.optString("EventTitle");
                            String jsonValueDescription = jsonObjectDescription.optString("EventDescription");
                            IdArray.add(jsonValueId);
                            titleArray.add(jsonValueTitle);
                            descriptionArray.add(jsonValueDescription);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Intent intent = new Intent(context, SearchEvent.class);
                intent.putExtra("IdArray", IdArray);
                intent.putExtra("titleArray", titleArray);
                intent.putExtra("descriptionArray", descriptionArray);
                intent.putExtra("studentId", studentId);


                context.startActivity(intent);

            }
        }, splashInterval);




    }
}
