package com.example.mike.tarucevent;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;
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

public class MainActivity extends AppCompatActivity {

    public void Setting(){
        Intent intent = new Intent(this, Testing.class);
        startActivity(intent);
    }

    private TextView mTextMessage;
    private MqttAndroidClient client;
    private String TAG = "MainActivity";
    private PahoMqttClient pahoMqttClient;
    private BroadcastReceiver receiver;
    private String studentId;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.content,new HomeFragment()).commit();
                    return true;

                case R.id.navigation_search:
                    transaction.replace(R.id.content,new SearchFragment()).commit();
                    return true;

                case R.id.navigation_settings:
                    transaction.replace(R.id.content,new SettingsFragment()).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Event Finder");
        setContentView(R.layout.activity_main);

        String type = "subscriptionRetrieve";
        Intent intent2 = getIntent();
        studentId = intent2.getStringExtra("studentId");

        MqttMessageService.studentId = studentId;

        Intent intent = new Intent(MainActivity.this, MqttMessageService.class);
        startService(intent);

        SubscriptionRetrieveMainTask subscriptionRetrieveMainTask = new SubscriptionRetrieveMainTask(this);
        subscriptionRetrieveMainTask.execute(type, studentId);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content,new HomeFragment()).commit();



        IntentFilter filter = new IntentFilter("mqttMessage");



        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = intent.getStringExtra("mqttData");
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        };

        registerReceiver(receiver, filter);



    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        Intent intent = new Intent (MainActivity.this, Login.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        super.onDestroy();

    }

    public class SubscriptionRetrieveMainTask extends AsyncTask<String, Void, String> {

        Context context;
        android.app.AlertDialog alertDialog;
        SubscriptionRetrieveMainTask(Context ctx){
            context = ctx;
        }
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

        }

        @Override
        protected void onPostExecute(final String result) {



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

            /*
            pahoMqttClient = new PahoMqttClient();

            client = pahoMqttClient.getMqttClient(getApplicationContext(), MQTTConstants.MQTT_BROKER_URL, MQTTConstants.CLIENT_ID);



            for (String temp : activityArray){
                String topic = "SmartcampusTest/TARUCEvent/Subscription/#";

                if (!topic.isEmpty()) {
                    try {
                        pahoMqttClient.subscribe(client, topic, 1);


                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("lul", temp);
            }*/



        }

    }

}

