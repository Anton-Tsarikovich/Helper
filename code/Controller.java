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

import org.simpleframework.xml.core.Persister;

import java.io.Reader;
import java.io.StringReader;
import java.util.concurrent.ExecutionException;



public class Controller extends Activity {
    private FileSystemManager fileSystemManager;
    private String numberOfGroup;
    private TextView textNumberGroup;
    private EditText getNumber;
    private Button downloadButton;
    private NetworkDownloader networkDownloader;

    private static final String TAG = "LOGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);

        Log.d(TAG, "View created");
        initialization();
        Log.d(TAG, "initialization");
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.downloadButton:
                        byte array[] = new byte[0];
                        try {
                            array = new GoDownload().execute(getResources().getString(R.string.all_group_url)).get();
                        } catch (InterruptedException | ExecutionException e) {
                            Log.e(TAG,e.getMessage());
                       }
                        setContentView(R.layout.timetable_layout);
                        textNumberGroup = (TextView) findViewById(R.id.someTextView);
                        if(array != null)
                            Log.e(TAG, new String(array));
                        else
                            Log.e(TAG, "null");

                        String str  = new String(array);
                        String str2 = str.substring(55);
                        fileSystemManager.saveToSD(str2);

                        Log.d(TAG, "str 1 " + str);
                        Log.d(TAG," str 2 " +str2);

                        Reader reader = new StringReader(str2);
                        Persister serializer = new Persister();
                        try {
                            StudentGroups groups = serializer.read(StudentGroups.class, reader, false);

                        } catch (Exception e) {
                            Log.e(TAG,e.getMessage());
                        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }
    private void initialization() {
        fileSystemManager = new FileSystemManager(Controller.this);
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
