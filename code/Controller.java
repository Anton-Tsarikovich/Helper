package com.example.antontsarikovich.helper;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;



public class Controller extends Activity {
    private FileSystemManager fileSystemManager;
    private String numberOfGroup;
    private TextView textNumberGroup;
    private EditText getNumber;
    private Button downloadButton;

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
                        fileSystemManager.saveSettings(getNumber.getText().toString());
                        setContentView(R.layout.timetable_layout);
                        break;
                }
            }
        };

        if(numberOfGroup.isEmpty()) {
        }
        else {
            Log.d(TAG, numberOfGroup);
            textNumberGroup= (TextView) findViewById(R.id.someTextView);
            String temp = numberOfGroup;
            textNumberGroup.setText(temp);
            setContentView(R.layout.timetable_layout);

        }

        downloadButton.setOnClickListener(clickListener);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_controller, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void initialization() {
        fileSystemManager = new FileSystemManager(Controller.this);
        numberOfGroup = fileSystemManager.loadSettings();

        getNumber = (EditText) findViewById(R.id.getNumberGroup);
        downloadButton = (Button) findViewById(R.id.downloadButton);
    }

}
