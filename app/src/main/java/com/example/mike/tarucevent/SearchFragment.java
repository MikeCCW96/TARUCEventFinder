package com.example.mike.tarucevent;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.util.Arrays;
import java.util.List;

public class SearchFragment extends Fragment {
    String[] categoryWords = {"Meeting", "Annual General Meeting", "Training/Practice", "Class", "Gathering", "Visit", "Trip", "Camp", "Performance", "Nite", "Talk", "Workshop", "Seminar", "Conference", "Exhibition", "Fund Raising", "Competition", "Sports Carnival", "Treasure Hunt", "Others"};
    ArrayList<String> listCategory = new ArrayList<>(Arrays.asList(categoryWords));

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private TextView result;
    private OnFragmentInteractionListener mListener;
    private ListView mListView;
    private String studentId;
    CustomAdapter customAdapter;
    public SearchFragment() {
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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


    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ImageButton btnScan = (ImageButton) view.findViewById(R.id.QRScan);
        ImageButton btnSearch = (ImageButton) view.findViewById(R.id.btnSearch);
        Intent intent2 = getActivity().getIntent();
        studentId = intent2.getStringExtra("studentId");

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }


        mListView = (ListView) view.findViewById(R.id.listView);
        customAdapter = new CustomAdapter();

        mListView.setAdapter(customAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txtCategory = (TextView)view.findViewById(R.id.textViewCategory);
                String category = txtCategory.getText().toString();
                String type = "searchEventCategory";
                SearchCategory searchCategory = new SearchCategory(getActivity());
                searchCategory.execute(type, category, studentId);

            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QRScan.class);
                intent.putExtra("studentId", studentId);
                startActivity(intent);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchTxt = (EditText) getActivity().findViewById(R.id.usernameText);
                String input = searchTxt.getText().toString();
                String type = "searchEvent";

                SearchTask searchTask = new SearchTask(getActivity());
                searchTask.execute(type, input, studentId);
            }
        });
        return view;
    }

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
        void onFragmentInteraction(Uri uri);
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return listCategory.size();
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

            convertView = getActivity().getLayoutInflater().inflate(R.layout.customlayoutcategory, null);
            TextView txtCategory = (TextView)convertView.findViewById(R.id.textViewCategory);

            txtCategory.setText(listCategory.get(position));
            return convertView;
        }
    }


    public class SearchCategory extends AsyncTask<String, Void, String> {

        Context context;
        android.app.AlertDialog alertDialog;

        SearchCategory(Context ctx) {
            context = ctx;
        }
        Connectivity connectivity = new Connectivity();

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String ipAddress = "" + connectivity.getIP();
            String search_url = ipAddress + "/Android/searchEventCategory.php";

            if (type.equals("searchEventCategory")) {
                try {
                    String category = params[1];
                    studentId = params[2];
                    URL url = new URL(search_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("category", "UTF-8") + "=" + URLEncoder.encode(category, "UTF-8");
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
            ArrayList<String> IdArray = new ArrayList<>();
            ArrayList<String> titleArray = new ArrayList<>();
            ArrayList<String> descriptionArray = new ArrayList<>();
            String eventId = "";
            String eventTitle = "";
            String eventDescription = "";
            if (result.equals("fail")) {
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
                        if (jsonObjectId == null) {
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
    }
}
