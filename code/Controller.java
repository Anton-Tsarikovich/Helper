package com.example.antontsarikovich.helper;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.os.AsyncTask;

import com.example.antontsarikovich.helper.models.StudentGroups;
import java.util.concurrent.ExecutionException;



public class Controller extends Activity {
    private FileSystemManager fileSystemManager;
    private String numberOfGroup;
    private TextView textNumberGroup;
    private EditText getNumber;
    private Button downloadButton;
    StudentGroups groups;
    XMLParser xmlParser;
    private NetworkDownloader networkDownloader;

    private static final String TAG = "LOGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);
        initialization();
        numberOfGroup = fileSystemManager.loadSettings();
        if(numberOfGroup.isEmpty()) {
            setContentView(R.layout.activity_first_start);
        }
        initialization();

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.downloadButton:
                        Log.d(TAG, "pressed");
                        download(getResources().getString(R.string.all_group_url), "AllTimetable", null);
                        byte tempArray[] = new byte[0];
                        try {
                            tempArray = new GoDownload().execute(getResources().getString(R.string.this_group_url) + groups.getElements()).get();
                        } catch (InterruptedException | ExecutionException e) {
                            Log.e(TAG,e.getMessage());
                        }
                        fileSystemManager.saveToSD(new String(tempArray), "Timetable");

                }
            }
        };
        downloadButton.setOnClickListener(clickListener);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_controller, menu);
        return true;
    }
    private void download(String url, String nameTimetable, String group) {
        String finalUrl = url;
        if (group != null) {
            finalUrl = finalUrl + group;
        }
        byte array[] = new byte[0];
        try {
            array = new GoDownload().execute(finalUrl).get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG,e.getMessage());
        }
        String str  = new String(array);
        final String substring = str.substring(55);
        fileSystemManager.saveToSD(substring, nameTimetable);
        groups = xmlParser.parseAllXML(substring);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }
    private void initialization() {
        fileSystemManager = new FileSystemManager(Controller.this);
        xmlParser = new XMLParser();
       // numberOfGroup = fileSystemManager.loadSettings();
        getNumber = (EditText) findViewById(R.id.getNumberGroup);
        downloadButton = (Button) findViewById(R.id.downloadButton);
        networkDownloader = new NetworkDownloader();
    }

    private class GoDownload extends AsyncTask<String, Void, byte[]> {

        public GoDownload() {
        }
        protected byte[] doInBackground(String... urls) {
            return networkDownloader.getFile(urls);
        }
    }

}
