package com.example.mike.tarucevent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment{
    ArrayList<String> listTitle = new ArrayList<>();
    ArrayList<String> listDescription = new ArrayList<>();

    ArrayList<String> IdArray = new ArrayList<>();
    ArrayList<String> titleArray = new ArrayList<>();
    ArrayList<String> descriptionArray = new ArrayList<>();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private ListView mListView;
    String studentId = "";
    CustomAdapter customAdapter;
    private OnFragmentInteractionListener mListener;

    Button btnSubscribed, btnAll, btnJoined;

    public HomeFragment() {

    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button btnHome = (Button)view.findViewById(R.id.button);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Toast.makeText(getActivity(), "Home Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        mListView = (ListView) view.findViewById(R.id.listView);
        customAdapter = new CustomAdapter();

        Intent intent2 = getActivity().getIntent();
        studentId = intent2.getStringExtra("studentId");

        Intent intent = getActivity().getIntent();
        IdArray = (ArrayList<String>) intent.getSerializableExtra("IdArray");
        titleArray = (ArrayList<String>) intent.getSerializableExtra("titleArray");
        descriptionArray = (ArrayList<String>) intent.getSerializableExtra("descriptionArray");

        mListView.setAdapter(customAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TextView getTitleText = (TextView)view.findViewById(R.id.textViewTitle);
                TextView getIdText = (TextView)view.findViewById(R.id.textViewId);
                //String string = adapter.getItem(position);
                String string = getIdText.getText().toString();


                String type = "SelectedEvent";

                SelectedEventTask selectedEventTask = new SelectedEventTask(getContext());
                selectedEventTask.execute(type, string, studentId);
            }
        });

        btnSubscribed = (Button)view.findViewById(R.id.btnSubscribed);
        btnAll = (Button)view.findViewById(R.id.btnAll);
        btnJoined = (Button)view.findViewById(R.id.btnJoined);

        btnAll.setBackgroundResource(R.drawable.button);
        btnAll.setTextColor(Color.WHITE);
        btnSubscribed.setBackgroundResource(R.color.transparent);
        btnSubscribed.setTextColor(Color.BLACK);
        btnJoined.setBackgroundResource(R.color.transparent);
        btnJoined.setTextColor(Color.BLACK);



        btnSubscribed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubscribed.setBackgroundResource(R.drawable.button);
                btnSubscribed.setTextColor(Color.WHITE);
                btnAll.setBackgroundResource(R.color.transparent);
                btnAll.setTextColor(Color.BLACK);
                btnJoined.setBackgroundResource(R.color.transparent);
                btnJoined.setTextColor(Color.BLACK);
                String type = "RetrieveSubscribed";
                RetrieveSubscribedEventTask retrieveSubscribedEventTask = new RetrieveSubscribedEventTask(getContext());
                retrieveSubscribedEventTask.execute(type, studentId);
            }
        });

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAll.setBackgroundResource(R.drawable.button);
                btnAll.setTextColor(Color.WHITE);
                btnSubscribed.setBackgroundResource(R.color.transparent);
                btnSubscribed.setTextColor(Color.BLACK);
                btnJoined.setBackgroundResource(R.color.transparent);
                btnJoined.setTextColor(Color.BLACK);
                String type = "Retrieve";
                RetrieveEventTaskInside retrieveEventTaskInside = new RetrieveEventTaskInside(getContext());
                retrieveEventTaskInside.execute(type, studentId);
            }
        });

        btnJoined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAll.setBackgroundResource(R.color.transparent);
                btnAll.setTextColor(Color.BLACK);
                btnSubscribed.setBackgroundResource(R.color.transparent);
                btnSubscribed.setTextColor(Color.BLACK);
                btnJoined.setBackgroundResource(R.drawable.button);
                btnJoined.setTextColor(Color.WHITE);
                String type = "Retrieve";
                RetrieveJoinedEventTask retrieveJoinedEventTask = new RetrieveJoinedEventTask(getContext());
                retrieveJoinedEventTask.execute(type, studentId);
            }
        });



        String type = "Retrieve";
        RetrieveEventTaskInside retrieveEventTaskInside = new RetrieveEventTaskInside(getContext());
        retrieveEventTaskInside.execute(type, studentId);

        return view;
    }


    class CustomAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return IdArray.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getActivity().getLayoutInflater().inflate(R.layout.customlayout, null);
            TextView txtTitle = (TextView)convertView.findViewById(R.id.textViewStartDate);
            TextView txtDescription = (TextView)convertView.findViewById(R.id.textViewStartTime);
            TextView txtId = (TextView)convertView.findViewById(R.id.textViewId);

            txtTitle.setText(titleArray.get(position));
            txtDescription.setText(descriptionArray.get(position));
            txtId.setText(IdArray.get(position));

            return convertView;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class RetrieveSubscribedEventTask extends AsyncTask<String, Void, String> {
        Context context;
        android.app.AlertDialog alertDialog;
        RetrieveSubscribedEventTask(Context ctx) {
            context = ctx;
        }
        Connectivity connectivity = new Connectivity();

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String ipAddress = "" + connectivity.getIP();
            String retrieve_url = ipAddress + "/Android/retrieveEventSubscribed.php";

            if (type.equals("RetrieveSubscribed")) {
                try {
                    studentId = params[1];
                    URL url = new URL(retrieve_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
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
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
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
        protected void onPostExecute(String result) {
            ArrayList<String> IdArrayAsync = new ArrayList<>();
            ArrayList<String> titleArrayAsync = new ArrayList<>();
            ArrayList<String> descriptionArrayAsync = new ArrayList<>();
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
                    IdArrayAsync.add(jsonValueId);
                    titleArrayAsync.add(jsonValueTitle);
                    descriptionArrayAsync.add(jsonValueDescription);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            IdArray.clear();
            IdArray = (ArrayList<String>) IdArrayAsync;
            titleArray.clear();
            titleArray = (ArrayList<String>) titleArrayAsync;
            descriptionArray.clear();
            descriptionArray = (ArrayList<String>) descriptionArrayAsync;
            customAdapter.notifyDataSetChanged();
            mListView.setAdapter(customAdapter);

        }
    }

    public class RetrieveEventTaskInside extends AsyncTask<String, Void, String> {
        Context context;
        android.app.AlertDialog alertDialog;

        RetrieveEventTaskInside(Context ctx) {
            context = ctx;
        }
        Connectivity connectivity = new Connectivity();

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String ipAddress = "" + connectivity.getIP();
            String retrieve_url = ipAddress + "/Android/retrieveEvent.php";

            if (type.equals("Retrieve")) {
                try {
                    studentId = params[1];
                    URL url = new URL(retrieve_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("", "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
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
        protected void onPostExecute(String result) {
            ArrayList<String> IdArrayAsync = new ArrayList<>();
            ArrayList<String> titleArrayAsync = new ArrayList<>();
            ArrayList<String> descriptionArrayAsync = new ArrayList<>();
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
                    IdArrayAsync.add(jsonValueId);
                    titleArrayAsync.add(jsonValueTitle);
                    descriptionArrayAsync.add(jsonValueDescription);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            IdArray.clear();
            IdArray = (ArrayList<String>) IdArrayAsync;
            titleArray.clear();
            titleArray = (ArrayList<String>) titleArrayAsync;
            descriptionArray.clear();
            descriptionArray = (ArrayList<String>) descriptionArrayAsync;
            customAdapter.notifyDataSetChanged();
            mListView.setAdapter(customAdapter);

        }
    }


    public class RetrieveJoinedEventTask extends AsyncTask<String, Void, String> {
        Context context;
        android.app.AlertDialog alertDialog;

        RetrieveJoinedEventTask(Context ctx) {
            context = ctx;
        }
        Connectivity connectivity = new Connectivity();

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String ipAddress = "" + connectivity.getIP();
            String retrieve_url = ipAddress + "/Android/retrieveJoinedEvent.php";

            if (type.equals("Retrieve")) {
                try {
                    studentId = params[1];
                    URL url = new URL(retrieve_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
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
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
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
        protected void onPostExecute(String result) {

            if (result.equals("fail")){
                IdArray.clear();
                titleArray.clear();
                descriptionArray.clear();
                customAdapter.notifyDataSetChanged();
                mListView.setAdapter(customAdapter);
            } else {
                ArrayList<String> IdArrayAsync = new ArrayList<>();
                ArrayList<String> titleArrayAsync = new ArrayList<>();
                ArrayList<String> descriptionArrayAsync = new ArrayList<>();
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
                        IdArrayAsync.add(jsonValueId);
                        titleArrayAsync.add(jsonValueTitle);
                        descriptionArrayAsync.add(jsonValueDescription);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                IdArray.clear();
                IdArray = (ArrayList<String>) IdArrayAsync;
                titleArray.clear();
                titleArray = (ArrayList<String>) titleArrayAsync;
                descriptionArray.clear();
                descriptionArray = (ArrayList<String>) descriptionArrayAsync;
                customAdapter.notifyDataSetChanged();
                mListView.setAdapter(customAdapter);
            }



        }
    }
}
