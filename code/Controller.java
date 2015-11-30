package com.example.antontsarikovich.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.os.AsyncTask;

import com.example.antontsarikovich.helper.models.StudentGroups;
import com.example.antontsarikovich.helper.models.Timetable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;



public class Controller extends Activity {

    private Map<String, Integer> colorType = new HashMap<String, Integer>() {
        {   put("ЛК", 0);
            put("ЛР", 1);
            put("ПЗ", 2);
        }
    };

    private FileSystemManager fileSystemManager;
    private String numberOfGroup;
    private EditText getNumber;
    private Button downloadButton;
    private Timetable timetable;
    private int colors[] = new int[3];
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();
    StudentGroups groups;
    XMLParser xmlParser;
    private NetworkDownloader networkDownloader;

    private static final String TAG = "LOGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);
        initialization();
        if (numberOfGroup.isEmpty()) {
            setContentView(R.layout.activity_first_start);
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.downloadButton:
                            if (getNumber.getText().toString().isEmpty() && getNumber.getText().length() != 6) {
                                break;
                            }
                            Log.d(TAG, "lol");
                            showDialog(1);
                            fileSystemManager.saveSettings(getNumber.getText().toString());
                            download(getResources().getString(R.string.all_group_url), "AllTimetable", null);
                            download(getResources().getString(R.string.this_group_url) + groups.getElement(getNumber.getText().toString()), getNumber.getText().toString(), getNumber.getText().toString());
                            setContentView(R.layout.timetable_layout);
                            Log.d(TAG, "Success");
                    }

                }
            };
            downloadButton.setOnClickListener(clickListener);
        } else {
            iHaveTimetable();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_controller, menu);
        return true;
    }
    private void download(String url, String nameTimetable, String group) {
        String finalUrl = url;

        byte array[] = new byte[0];
        try {
            array = new GoDownload().execute(finalUrl).get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG,e.getMessage());
        }
        String str  = new String(array);
        final String substring = str.substring(55);
        fileSystemManager.saveToSD(substring, nameTimetable);
        if(group == null) {
            groups = xmlParser.parseAllXML(substring);
        }
        else {
            timetable = xmlParser.parseTimetable(substring);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }
    private void initialization() {
        fileSystemManager = new FileSystemManager(Controller.this);
        xmlParser = new XMLParser();
        numberOfGroup = fileSystemManager.loadSettings();
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

    protected Dialog onCreateDialog(int id) {
        if(id == 1) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Загрузка");
            adb.setMessage("Подождите, пожалуйста");
            return adb.create();
        }
        return super.onCreateDialog(id);
    }

    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        Log.d(TAG, "prepareDialog");
        if(id == 1) {
            ((AlertDialog)dialog).setMessage("Подождите, пожалуйста");
        }
    }

    private void iHaveTimetable(){
        String groupTimetable = fileSystemManager.readFromSD(numberOfGroup);
        timetable = xmlParser.parseTimetable(groupTimetable);

        prepareTable();

    }

    private void prepareTable() {
        colors[0] = Color.GREEN;
        colors[1] = Color.RED;
        colors[2] = Color.YELLOW;
        String data = dateFormat.format(new Date(System.currentTimeMillis()));
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        setContentView(R.layout.timetable_layout);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linLayout);
        LayoutInflater layoutInflater = getLayoutInflater();

        for(int i = 0;i < timetable.dayTimetables.get(day - 2).subjects.size(); i++) {
            View item = layoutInflater.inflate(R.layout.item_subject,linearLayout,false);
            TextView subject = (TextView) item.findViewById(R.id.subject);
            subject.setText(timetable.dayTimetables.get(day - 2).subjects.get(i).getSubject());

            TextView time = (TextView) item.findViewById(R.id.time);
            time.setText(timetable.dayTimetables.get(day - 2).subjects.get(i).getLessonTime());

            TextView auditory = (TextView) item.findViewById(R.id.auditorytory);
            auditory.setText(timetable.dayTimetables.get(day - 2).subjects.get(i).getAuditory());

            TextView name = (TextView) item.findViewById(R.id.name);
            name.setText(timetable.dayTimetables.get(day - 2).subjects.get(i).employee.get(0).getLastName() +
                            " " + timetable.dayTimetables.get(day - 2).subjects.get(i).employee.get(0).getFirstName() +
                            " " + timetable.dayTimetables.get(day - 2).subjects.get(i).employee.get(0).getMiddleName());
            item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            int keyColor;
            for(Map.Entry<String, Integer> entry : colorType.entrySet()) {
                String str1 = entry.getKey();
                String str2 = timetable.dayTimetables.get(day - 2).subjects.get(i).getLessonType();
                if(entry.getKey().equals(timetable.dayTimetables.get(day - 2).subjects.get(i).getLessonType())) {
                    item.setBackgroundColor(colors[entry.getValue()]);
                }
            }

            linearLayout.addView(item);
        }


    }

}

