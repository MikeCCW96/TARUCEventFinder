package com.example.mike.tarucevent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchEvent extends AppCompatActivity {
    ArrayList<String> IdArray = new ArrayList<>();
    ArrayList<String> titleArray = new ArrayList<>();
    ArrayList<String> descriptionArray = new ArrayList<>();

    private ListView mListView;
    private String studentId;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Search Result");


        Intent intent = getIntent();
        IdArray = (ArrayList<String>) intent.getSerializableExtra("IdArray");
        titleArray = (ArrayList<String>) intent.getSerializableExtra("titleArray");
        descriptionArray = (ArrayList<String>) intent.getSerializableExtra("descriptionArray");
        studentId = intent.getStringExtra("studentId");

        mListView = (ListView)findViewById(R.id.listView);
        CustomAdapter customAdapter = new CustomAdapter();

        mListView.setAdapter(customAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView getIdText = (TextView)view.findViewById(R.id.textViewId);

                String string = getIdText.getText().toString();

                String type = "SelectedEvent";

                SelectedEventTask selectedEventTask = new SelectedEventTask(getApplicationContext());
                selectedEventTask.execute(type, string, studentId);
            }
        });

    }

    class CustomAdapter extends BaseAdapter {
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

            convertView = getLayoutInflater().inflate(R.layout.customlayout, null);
            TextView txtTitle = (TextView)convertView.findViewById(R.id.textViewStartDate);
            TextView txtDescription = (TextView)convertView.findViewById(R.id.textViewStartTime);
            TextView txtId = (TextView)convertView.findViewById(R.id.textViewId);

            txtTitle.setText(titleArray.get(position));
            txtDescription.setText(descriptionArray.get(position));
            txtId.setText(IdArray.get(position));

            return convertView;
        }
    }


}
