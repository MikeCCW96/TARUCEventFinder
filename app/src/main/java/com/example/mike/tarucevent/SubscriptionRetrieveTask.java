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
 * Created by Mike on 10/21/2017.
 */

public class SubscriptionRetrieveTask extends AsyncTask<String, Void, String> {

    Context context;
    android.app.AlertDialog alertDialog;
    SubscriptionRetrieveTask(Context ctx){
        context = ctx;
    }
    ProgressDialog pd;
    String studentId = "";
    Connectivity connectivity = new Connectivity();

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String ipAddress = "" + connectivity.getIP();
        String retrieveSelected_url = ipAddress + "/Android/subscriptionRetrieve.php";

        if(type.equals("subscriptionRetrieve")){
            try {
                studentId = params[1];
                URL url = new URL(retrieveSelected_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("studentId", "UTF-8")+"="+URLEncoder.encode(studentId, "UTF-8");
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
        Integer splashInterval = 1000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pd.cancel();
                ArrayList<String> activityArray = new ArrayList<>();
                String subscriptionActivity = "";
                try {
                    JSONObject response = new JSONObject(result);
                    subscriptionActivity = response.getString("ActivityType");
                    JSONArray jsonActivityArray = new JSONArray(subscriptionActivity);
                    for (int i = 0; i < jsonActivityArray.length(); i++) {
                        JSONObject jsonObjectActivity = jsonActivityArray.optJSONObject(i);
                        if(jsonObjectActivity == null) {
                            continue;
                        }
                        String jsonValueActivity = jsonObjectActivity.optString("ActivityType");
                        activityArray.add(jsonValueActivity);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(context, Subscription.class);
                intent.putExtra("activityArray", activityArray);
                intent.putExtra("studentId", studentId);
                context.startActivity(intent);
            }
        }, splashInterval);




    }
}
