package com.example.mike.tarucevent;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
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

import br.com.goncalves.pugnotification.notification.PugNotification;

public class MqttMessageService extends Service {

    private static final String TAG = "MqttMessageService";
    private PahoMqttClient pahoMqttClient;
    private MqttAndroidClient mqttAndroidClient;
    public static String studentId = "";
    public static Integer Meeting = 0;
    public static Integer Annual = 0;
    public static Integer Training = 0;
    public static Integer Class = 0;
    public static Integer Gathering = 0;
    public static Integer Visit = 0;
    public static Integer Trip = 0;
    public static Integer Camp = 0;
    public static Integer Performance = 0;
    public static Integer Nite = 0;
    public static Integer Talk = 0;
    public static Integer Workshop = 0;
    public static Integer Seminar = 0;
    public static Integer Conference = 0;
    public static Integer Exhibition = 0;
    public static Integer FundRaising = 0;
    public static Integer Competition = 0;
    public static Integer SportsCarnival = 0;
    public static Integer TreasureHunt = 0;
    public static Integer Others = 0;


    public MqttMessageService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        SubscriptionRetrieveMainTask subscriptionRetrieveMainTask = new SubscriptionRetrieveMainTask(this);
        subscriptionRetrieveMainTask.execute("subscriptionRetrieve", studentId);

        pahoMqttClient = new PahoMqttClient();
        mqttAndroidClient = pahoMqttClient.getMqttClient(getApplicationContext(), MQTTConstants.MQTT_BROKER_URL, MQTTConstants.CLIENT_ID);

        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                Log.i("String", s);
                Log.i("Message", new String(mqttMessage.getPayload()));

                String message = Action.hexToAscii(new String(mqttMessage.getPayload()));
                JSONObject jsonObject = new JSONObject(message);
                String command = jsonObject.getString("command");
                Log.i("command", command);

                if (command.equals("000003")) {
                    String category = Action.hexToAscii(jsonObject.getString("category"));
                    String eventName = Action.hexToAscii(jsonObject.getString("eventname"));

                    if (Meeting == 1) {
                        if (category.equals("Meeting")) {
                            setMessageNotification("New Meeting Event!", eventName);
                        }
                    }

                    if (Annual == 1) {
                        if (category.equals("Annual")) {
                            setMessageNotification("New Annual Event!", eventName);
                        }
                    }

                    if (Training == 1) {
                        if (category.equals("Training/Practice")) {
                            setMessageNotification("New Training/Practice Event!", eventName);
                        }
                    }

                    if (Class == 1) {
                        if (category.equals("Class")) {
                            setMessageNotification("New Class Event!", eventName);
                        }
                    }

                    if (Gathering == 1) {
                        if (category.equals("Gathering")) {
                            setMessageNotification("New Gathering Event!", eventName);
                        }
                    }
                    if (Visit == 1) {
                        if (category.equals("Visit")) {
                            setMessageNotification("New Visit Event!", eventName);
                        }
                    }
                    if (Trip == 1) {
                        if (category.equals("Trip")) {
                            setMessageNotification("New Trip Event!", eventName);
                        }
                    }
                    if (Camp == 1) {
                        if (category.equals("Camp")) {
                            setMessageNotification("New Camp Event!", eventName);
                        }
                    }
                    if (Performance == 1) {
                        if (category.equals("Performance")) {
                            setMessageNotification("New Performance Event!", eventName);
                        }
                    }
                    if (Nite == 1) {
                        if (category.equals("Nite")) {
                            setMessageNotification("New Nite Event!", eventName);
                        }
                    }
                    if (Talk == 1) {
                        if (category.equals("Talk")) {
                            setMessageNotification("New Talk Event!", eventName);
                        }
                    }
                    if (Workshop == 1) {
                        if (category.equals("Workshop")) {
                            setMessageNotification("New Workshop Event!", eventName);
                        }
                    }
                    if (Seminar == 1) {
                        if (category.equals("Seminar")) {
                            setMessageNotification("New Seminar Event!", eventName);
                        }
                    }
                    if (Conference == 1) {
                        if (category.equals("Conference")) {
                            setMessageNotification("New Conference Event!", eventName);
                        }
                    }
                    if (Exhibition == 1) {
                        if (category.equals("Exhibition")) {
                            setMessageNotification("New Exhibition Event!", eventName);
                        }
                    }
                    if (FundRaising == 1) {
                        if (category.equals("FundRaising")) {
                            setMessageNotification("New Fund Raising Event!", eventName);
                        }
                    }
                    if (Competition == 1) {
                        if (category.equals("Competition")) {
                            setMessageNotification("New Competition Event!", eventName);
                        }
                    }
                    if (SportsCarnival == 1) {
                        if (category.equals("SportsCarnival")) {
                            setMessageNotification("New Sports Carnival Event!", eventName);
                        }
                    }
                    if (TreasureHunt == 1) {
                        if (category.equals("TreasureHunt")) {
                            setMessageNotification("New Treasure Hunt Event!", eventName);
                        }
                    }
                    if (Others == 1) {
                        if (category.equals("Others")) {
                            setMessageNotification("New Others Event!", eventName);
                        }
                    }
                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }




    private void setMessageNotification(@NonNull String topic, @NonNull String msg) {

        //.setContentIntent(pendingIntent).build();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notification_icon_ev)
                        .setContentTitle(topic)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setContentText(msg);

        Intent resultIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(Login.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(100, mBuilder.build());

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

            for (String temp : activityArray){
                Log.i("wut", temp);
                switch (temp) {
                    case "Meeting":
                        Meeting = 1;
                        break;
                    case "Annual General Meeting":
                        Annual = 1;
                        break;
                    case "Training/Practice":
                        Training = 1;
                        break;
                    case "Class":
                        Class = 1;
                        break;
                    case "Gathering":
                        Gathering = 1;
                        break;
                    case "Visit":
                        Visit = 1;
                        break;
                    case "Trip":
                        Trip = 1;
                        break;
                    case "Camp":
                        Camp = 1;
                        break;
                    case "Performance":
                        Performance = 1;
                        break;
                    case "Nite":
                        Nite = 1;
                        break;
                    case "Talk":
                        Talk = 1;
                        break;
                    case "Workshop":
                        Workshop = 1;
                        break;
                    case "Seminar":
                        Seminar = 1;
                        break;
                    case "Conference":
                        Conference = 1;
                        break;
                    case "Exhibition":
                        Exhibition = 1;
                        break;
                    case "Fund Raising":
                        FundRaising = 1;
                        break;
                    case "Competition":
                        Competition = 1;
                        break;
                    case "Sports Carnival":
                        SportsCarnival = 1;
                        break;
                    case "Treasure Hunt":
                        TreasureHunt = 1;
                        break;
                    case "Others":
                        Others = 1;
                        break;

                }
            }



        }

    }


}
