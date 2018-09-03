package com.example.mike.tarucevent;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

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

/**
 * Created by Mike on 10/21/2017.
 */

public class SubscriptionTask extends AsyncTask<String, Void, String> {

    Context context;
    android.app.AlertDialog alertDialog;
    SubscriptionTask(Context ctx){
        context = ctx;
    }

    String studentId = "";
    Connectivity connectivity = new Connectivity();

    String strMeeting = "";
    String strAnnual = "";
    String strTraining = "";
    String strClass = "";
    String strGathering = "";
    String strVisit = "";
    String strTrip = "";
    String strCamp = "";
    String strPerformance = "";
    String strNite = "";
    String strTalk = "";
    String strWorkshop = "";
    String strSeminar = "";
    String strConference = "";
    String strExhibition = "";
    String strFundRaising = "";
    String strCompetition = "";
    String strSportsCarnival = "";
    String strTreasureHunt = "";
    String strOthers = "";

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String ipAddress = "" + connectivity.getIP();
        String retrieveSelected_url = ipAddress + "/Android/subscribe.php";

        if(type.equals("subscribe")){
            try {
                studentId = params[1];
                strMeeting = params[2];
                strAnnual = params[3];
                strTraining = params[4];
                strClass = params[5];
                strGathering = params[6];
                strVisit = params[7];
                strTrip = params[8];
                strCamp = params[9];
                strPerformance = params[10];
                strNite = params[11];
                strTalk = params[12];
                strWorkshop = params[13];
                strSeminar = params[14];
                strConference = params[15];
                strExhibition = params[16];
                strFundRaising = params[17];
                strCompetition = params[18];
                strSportsCarnival = params[19];
                strTreasureHunt = params[20];
                strOthers = params[21];

                URL url = new URL(retrieveSelected_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("studentId", "UTF-8")+"="+URLEncoder.encode(studentId, "UTF-8")+"&"+
                                    URLEncoder.encode("strMeeting", "UTF-8")+"="+URLEncoder.encode(strMeeting, "UTF-8")+"&"+
                                    URLEncoder.encode("strAnnual", "UTF-8")+"="+URLEncoder.encode(strAnnual, "UTF-8")+"&"+
                                    URLEncoder.encode("strTraining", "UTF-8")+"="+URLEncoder.encode(strTraining, "UTF-8")+"&"+
                                    URLEncoder.encode("strClass", "UTF-8")+"="+URLEncoder.encode(strClass, "UTF-8")+"&"+
                                    URLEncoder.encode("strGathering", "UTF-8")+"="+URLEncoder.encode(strGathering, "UTF-8")+"&"+
                                    URLEncoder.encode("strVisit", "UTF-8")+"="+URLEncoder.encode(strVisit, "UTF-8")+"&"+
                                    URLEncoder.encode("strTrip", "UTF-8")+"="+URLEncoder.encode(strTrip, "UTF-8")+"&"+
                                    URLEncoder.encode("strCamp", "UTF-8")+"="+URLEncoder.encode(strCamp, "UTF-8")+"&"+
                                    URLEncoder.encode("strPerformance", "UTF-8")+"="+URLEncoder.encode(strPerformance, "UTF-8")+"&"+
                                    URLEncoder.encode("strNite", "UTF-8")+"="+URLEncoder.encode(strNite, "UTF-8")+"&"+
                                    URLEncoder.encode("strTalk", "UTF-8")+"="+URLEncoder.encode(strTalk, "UTF-8")+"&"+
                                    URLEncoder.encode("strWorkshop", "UTF-8")+"="+URLEncoder.encode(strWorkshop, "UTF-8")+"&"+
                                    URLEncoder.encode("strSeminar", "UTF-8")+"="+URLEncoder.encode(strSeminar, "UTF-8")+"&"+
                                    URLEncoder.encode("strConference", "UTF-8")+"="+URLEncoder.encode(strConference, "UTF-8")+"&"+
                                    URLEncoder.encode("strExhibition", "UTF-8")+"="+URLEncoder.encode(strExhibition, "UTF-8")+"&"+
                                    URLEncoder.encode("strFundRaising", "UTF-8")+"="+URLEncoder.encode(strFundRaising, "UTF-8")+"&"+
                                    URLEncoder.encode("strCompetition", "UTF-8")+"="+URLEncoder.encode(strCompetition, "UTF-8")+"&"+
                                    URLEncoder.encode("strSportsCarnival", "UTF-8")+"="+URLEncoder.encode(strSportsCarnival, "UTF-8")+"&"+
                                    URLEncoder.encode("strTreasureHunt", "UTF-8")+"="+URLEncoder.encode(strTreasureHunt, "UTF-8")+"&"+
                                    URLEncoder.encode("strOthers", "UTF-8")+"="+URLEncoder.encode(strOthers, "UTF-8");
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
    protected void onPostExecute(String s) {
        MqttMessageService.Meeting = Integer.parseInt(strMeeting);
        MqttMessageService.Annual = Integer.parseInt(strAnnual);
        MqttMessageService.Training = Integer.parseInt(strTraining);
        MqttMessageService.Class = Integer.parseInt(strClass);
        MqttMessageService.Gathering = Integer.parseInt(strGathering);
        MqttMessageService.Visit = Integer.parseInt(strVisit);
        MqttMessageService.Trip = Integer.parseInt(strTrip);
        MqttMessageService.Camp = Integer.parseInt(strCamp);
        MqttMessageService.Performance = Integer.parseInt(strPerformance);
        MqttMessageService.Nite = Integer.parseInt(strNite);
        MqttMessageService.Talk = Integer.parseInt(strTalk);
        MqttMessageService.Workshop = Integer.parseInt(strWorkshop);
        MqttMessageService.Seminar = Integer.parseInt(strSeminar);
        MqttMessageService.Conference = Integer.parseInt(strConference);
        MqttMessageService.Exhibition = Integer.parseInt(strExhibition);
        MqttMessageService.FundRaising = Integer.parseInt(strFundRaising);
        MqttMessageService.Competition = Integer.parseInt(strCompetition);
        MqttMessageService.SportsCarnival = Integer.parseInt(strSportsCarnival);
        MqttMessageService.TreasureHunt = Integer.parseInt(strTreasureHunt);
        MqttMessageService.Others = Integer.parseInt(strOthers);
        MqttMessageService.Meeting = Integer.parseInt(strMeeting);
        MqttMessageService.Meeting = Integer.parseInt(strMeeting);
        MqttMessageService.Meeting = Integer.parseInt(strMeeting);
        MqttMessageService.Meeting = Integer.parseInt(strMeeting);

    }
}
