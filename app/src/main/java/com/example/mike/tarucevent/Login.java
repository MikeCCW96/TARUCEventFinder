package com.example.mike.tarucevent;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;



public class Login extends AppCompatActivity {
    EditText txtUsername, txtPassword;
    ImageView pbBackground;
    ProgressBar pbLoading;
    ProgressDialog pd;



    static String topicStr = "MY/TARUC/ERS/000000099/PUB";
    MqttAndroidClient client;
    MqttAndroidClient client2;
    private PahoMqttClient pahoMqttClient;
    private PahoMqttClient pahoMqttClient2;
    String usernameWhole = "";

    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        pd = new ProgressDialog(this,R.style.LoadingDialog);
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_Holo_Spinner);


        String clientId = MqttClient.generateClientId();
        MQTTConstants.CLIENT_ID = clientId;

        pahoMqttClient = new PahoMqttClient();
        pahoMqttClient2 = new PahoMqttClient();
        client = pahoMqttClient.getMqttClient(getApplicationContext(), MQTTConstants.MQTT_BROKER_URL, MQTTConstants.CLIENT_ID);
        client2 = pahoMqttClient.getMqttClient(getApplicationContext(), MQTTConstants.MQTT_BROKER_URL, MQTTConstants.CLIENT_ID);

        String topic = "MY/TARUC/ERS/000000099/PUB";

        try {
            pahoMqttClient.subscribe(client, topic, 1);
        } catch (MqttException e) {
            e.printStackTrace();
        }

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SET_ALARM) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SET_ALARM}, PERMISSION_REQUEST);
        }

        txtUsername = (EditText)findViewById(R.id.usernameText);
        txtPassword = (EditText)findViewById(R.id.passwordText);

    }

    public void LoginStaff(View v){
        Intent intent = new Intent (Login.this, StaffTakeAttendance.class);
        startActivity(intent);

    }

    public void Login(View v){

        if(txtUsername.getText().toString().equals("") || txtPassword.getText().toString().equals("")){
            new AlertDialog.Builder(Login.this)
                    .setTitle("Error")
                    .setMessage("Invalid username or password")
                    .setCancelable(false)
                    .setPositiveButton("OK", null)
                    .show();
        } else if(txtUsername.getText().toString().equals("STAFF01") && txtPassword.getText().toString().equals("staff")) {
            Intent intent = new Intent (Login.this, StaffTakeAttendance.class);
            startActivity(intent);

        }


        else {
            final String username = txtUsername.getText().toString();
            String password = txtPassword.getText().toString();
            usernameWhole = username;

            pd.show();


            String messageBefore = "{\"command\": \"000001\", \"reserve\": \"303030303030303030303030303030303030303030303030\", " +
                    "\"username\": \"" + Action.asciiToHex(username) + "\" ," +
                    "\"password\": \"" + Action.asciiToHex(password) + "\"}";
            String message = Action.asciiToHex(messageBefore);
            String topic = topicStr;

            try {
                client.publish(topic, message.getBytes() ,0,false);
            } catch (MqttException e) {
                e.printStackTrace();
            }

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {

                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

                    String message = Action.hexToAscii(new String(mqttMessage.getPayload()));
                    JSONObject jsonObject = new JSONObject(message);
                    String command = jsonObject.getString("command");
                    if (command.equals("000002")){
                        String username = Action.hexToAscii(jsonObject.getString("username"));
                        String password = Action.hexToAscii(jsonObject.getString("password"));

                        Log.i("lol",usernameWhole);
                        Log.i("lol2", username);

                        if(username.equals(usernameWhole)){
                            pd.cancel();
                            if (password.equals("fail")){
                                new AlertDialog.Builder(Login.this)
                                        .setTitle("Error")
                                        .setMessage("Invalid username or password")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", null)
                                        .show();
                            } else {
                                Toast.makeText(Login.this, "Success", Toast.LENGTH_SHORT).show();


                                String studentId = Action.hexToAscii(jsonObject.getString("studentId"));
                                String result = Action.hexToAscii(jsonObject.getString("result"));
                                Log.i("result", result);
                                ArrayList<String> IdArray = new ArrayList<>();
                                ArrayList<String> titleArray = new ArrayList<>();
                                ArrayList<String> descriptionArray = new ArrayList<>();
                                String eventId = "";
                                String eventTitle = "";
                                String eventDescription = "";
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

                                Intent intent = new Intent(Login.this, MainActivity.class);
                                intent.putExtra("IdArray", IdArray);
                                intent.putExtra("titleArray", titleArray);
                                intent.putExtra("descriptionArray", descriptionArray);
                                intent.putExtra("studentId", studentId);
                                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }



                        }
                    }


                }



                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });
        }





    }
}
