package com.example.antontsarikovich.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import android.os.AsyncTask;

import com.example.antontsarikovich.helper.Group.Group;
import com.example.antontsarikovich.helper.models.StudentGroups;
import com.example.antontsarikovich.helper.models.Timetable;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;



public class Controller extends Activity {

    private final Map<String, Integer> colorType = new HashMap<String, Integer>() {
        {   put("ЛК", 0);
            put("ЛР", 1);
            put("ПЗ", 2);
        }
    };
    @SuppressLint("UseSparseArrays")
    private Map<Integer, Integer> monthDays = new HashMap<Integer, Integer>() {
        {
            put(1, 31);
            put(3, 31);
            put(4, 30);
            put(5, 31);
            put(6, 30);
            put(7, 31);
            put(8, 31);
            put(9, 30);
            put(10, 31);
            put(11, 30);
            put(12, 31);
        }
    };
    private Calendar calendar = Calendar.getInstance();
    private Group group;
    private ListView listView;
    private String[] names;
    private FileSystemManager fileSystemManager;
    private String numberOfGroup;
    private EditText getNumber;
    private Timetable timetable;
    private int colors[] = new int[3];
    private StudentGroups groups;
    private XMLParser xmlParser;
    private NetworkDownloader networkDownloader;
    private ProgressDialog progressDialog;

    private static final String TAG = "LOGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);
        initialization();
        if (numberOfGroup.isEmpty()) {
            setContentView(R.layout.activity_first_start);
            getNumber = (EditText) findViewById(R.id.getNumberGroup);
            Button downloadButton = (Button) findViewById(R.id.downloadButton);
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.downloadButton:
                            if (getNumber.getText().toString().isEmpty() && getNumber.getText().length() != 6) {
                                break;
                            }

                            showProgress();
                            numberOfGroup = getNumber.getText().toString();
                            fileSystemManager.saveSettings(getNumber.getText().toString());
                            download(getResources().getString(R.string.all_group_url), "AllTimetable", null);
                            download(getResources().getString(R.string.this_group_url) + groups.getElement(getNumber.getText().toString()), getNumber.getText().toString(), getNumber.getText().toString());
                            iHaveTimetable();
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

        byte array[] = new byte[0];
        try {
            array = new GoDownload().execute(url).get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG,e.getMessage());
        }
        String str  = new String(array);
        final String substring = str.substring(55);
        fileSystemManager.saveToSD(substring, nameTimetable);
        if(group == null) {
            groups = xmlParser.parseAllXML(substring);
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
        networkDownloader = new NetworkDownloader();

    }

    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog = ProgressDialog.show(this, "",
                "Loading. Please wait...");
        progressDialog.setCancelable(false);
    }

    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private class GoDownload extends AsyncTask<String, Void, byte[]> {

        public GoDownload() {
        }
        protected byte[] doInBackground(String... urls) {
            return networkDownloader.getFile(urls);
        }
    }

    private void iHaveTimetable(){
        String groupTimetable = fileSystemManager.readFromSD(numberOfGroup);
        timetable = xmlParser.parseTimetable(groupTimetable);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int week = getWeek(dayOfMonth, month, year);
        String temp = fileSystemManager.readFromSDSpecialFor(numberOfGroup + ".txt");
        String tempTemp = temp.substring(1);
        group = xmlParser.parseNames(tempTemp);
        String tempStr = fileSystemManager.readFromSD("miss" + numberOfGroup);
        String miss = "";
        int counter = 0;
        for(int i = 0; i < tempStr.length();i++){
            if(tempStr.charAt(i) == ' ') {
                group.studentsList.get(counter).setLoadMiss(Integer.parseInt(miss));
                counter++;
                miss = "";
                continue;
            }
        int tempInt = Integer.parseInt(String.valueOf(tempStr.charAt(i)));
        miss += Integer.toString(tempInt);

        }

        prepareTable(day, week);

    }
    private int getWeek(int day, int month, int year) {
        if(year%4 == 0) {
            monthDays.put(2, 29);
        }
        else {
            monthDays.put(2, 28);
        }
        month++;
        int monthCount = 9;
        int thisYear = year;
        int week = 0;
        if(month < 9) {
            thisYear -= 1;
        }
        int countDays = 0;
        for (int yearCount = thisYear; yearCount <= year; yearCount++ ) {
            for (; monthCount <= month; monthCount++) {
                for (int dayCount = 0; dayCount <= getDays(monthCount); dayCount++, countDays++) {
                    if (countDays % 7 == 0) {
                        week++;
                        if (week == 5) {
                            week = 1;
                        }
                    }
                    if(dayCount == day && monthCount == month && yearCount == year) {
                        return week;
                    }


                }
                if(monthCount == 13) {
                    break;
                }
            }
            monthCount = 0;
        }
        return -1;
    }

    private int getDays(int month) {
        for(Map.Entry<Integer, Integer> entry : monthDays.entrySet()) {
            if(month == entry.getKey()) {
                return entry.getValue();
            }
        }
        return -1;
    }

    private void prepareTable(final int day, final int week) {
        colors[0] = Color.GREEN;
        colors[1] = Color.RED;
        colors[2] = Color.YELLOW;
        hideProgress();
        setContentView(R.layout.timetable_layout);
        Button showMissButton = (Button) findViewById(R.id.showMissButton);
        View.OnClickListener showClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTableMiss(day,week);
            }
        };
        showMissButton.setOnClickListener(showClickListener);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linLayout);
        int thisDay = calendar.get(Calendar.DAY_OF_WEEK);
        LayoutInflater layoutInflater = getLayoutInflater();
        if(thisDay != 1) {
            for (int i = 0; i < timetable.dayTimetables.get(day - 2).subjects.size(); i++) {
                if (timetable.dayTimetables.get(day - 2).subjects.get(i).compareWeek(Integer.toString(week))) {
                    View item = layoutInflater.inflate(R.layout.item_subject, linearLayout, false);
                    TextView subject = (TextView) item.findViewById(R.id.subject);
                    subject.setText(timetable.dayTimetables.get(day - 2).subjects.get(i).getSubject());

                    TextView time = (TextView) item.findViewById(R.id.time);
                    time.setText(timetable.dayTimetables.get(day - 2).subjects.get(i).getLessonTime());
                    if (!timetable.dayTimetables.get(day - 2).subjects.get(i).getSubject().equals("ФизК")) {
                        TextView auditory = (TextView) item.findViewById(R.id.auditorytory);
                        auditory.setText(timetable.dayTimetables.get(day - 2).subjects.get(i).getAuditory());

                        TextView name = (TextView) item.findViewById(R.id.name);
                        name.setText(timetable.dayTimetables.get(day - 2).subjects.get(i).employee.get(0).getLastName() +
                                " " + timetable.dayTimetables.get(day - 2).subjects.get(i).employee.get(0).getFirstName() +
                                " " + timetable.dayTimetables.get(day - 2).subjects.get(i).employee.get(0).getMiddleName());
                    }
                    final int finalDay = day - 2;
                    final int finalInt = i;
                    Button table = (Button) item.findViewById(R.id.goTable);
                    View.OnClickListener clickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showList(day, week, timetable.dayTimetables.get(day - 2).subjects.get(finalInt).getSubject());
                            Log.d(TAG, timetable.dayTimetables.get(finalDay).subjects.get(finalInt).getSubject());
                        }
                    };
                    table.setOnClickListener(clickListener);

                    item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                    for (Map.Entry<String, Integer> entry : colorType.entrySet()) {
                        if (entry.getKey().equals(timetable.dayTimetables.get(day - 2).subjects.get(i).getLessonType())) {
                            item.setBackgroundColor(colors[entry.getValue()]);
                        }
                    }
                    linearLayout.addView(item);
                }
            }
        } else {
            View item = layoutInflater.inflate(R.layout.item_subject, linearLayout, false);
            TextView subject = (TextView) item.findViewById(R.id.subject);
            subject.setText("Выходной");

            item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            linearLayout.addView(item);
        }



    }

    private void showList(final int day,final int week,final String sub) {
        names = new String [group.studentsList.size()];
        for(int i = 0; i < group.studentsList.size(); i++) {
            names[i] = group.studentsList.get(i).getFirstName() + " " + group.studentsList.get(i).getLastName();
        }
        setContentView(R.layout.miss_layout);
        listView = (ListView) findViewById(R.id.lvMain);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice,
                names);
        TextView groupList = (TextView) findViewById(R.id.SubText);
        groupList.setText(sub);

        listView.setAdapter(adapter);
        final Button saveChangesButton = (Button) findViewById(R.id.saveButton);
        Button backButton = (Button) findViewById(R.id.backButton);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.saveButton: {
                        Log.d(TAG, "checked");
                        SparseBooleanArray sparseBooleanArray = listView.getCheckedItemPositions();
                        for (int i = 0; i < sparseBooleanArray.size(); i++) {
                            int key = sparseBooleanArray.keyAt(i);
                            if (sparseBooleanArray.get(key)) {
                                Log.d(TAG, names[key]);
                                saveMiss(names[key]);
                            }
                        }
                        saveChanges();
                        prepareTable(day, week);
                        break;
                    }
                    case R.id.backButton: {
                        prepareTable(day, week);
                    }
                }
            }
        };
        backButton.setOnClickListener(clickListener);
        saveChangesButton.setOnClickListener(clickListener);
    }

    private void saveMiss(String name) {
        for (int i = 0;i < group.studentsList.size(); i++ ) {
            String str = group.studentsList.get(i).getFirstName() + " " + group.studentsList.get(i).getLastName();
            if(name.equals(str)) {
                group.studentsList.get(i).setMiss();
            }
        }
    }
    private void saveChanges() {
        String str = "";
        for(int i = 0;i < group.studentsList.size(); i++) {
            str += group.studentsList.get(i).getMiss() + " ";
        }
        fileSystemManager.saveToSD(str, "miss" + numberOfGroup);
    }

    private void setTableMiss(final int day, final int week) {

        setContentView(R.layout.miss_show_layout);
        LinearLayout linLayout = (LinearLayout) findViewById(R.id.missLinLayout);

        LayoutInflater ltInflater = getLayoutInflater();

        for (int i = 0; i < group.studentsList.size(); i++) {
            View item = ltInflater.inflate(R.layout.item_miss_layout, linLayout, false);
            TextView firstName = (TextView) item.findViewById(R.id.firstName);
            firstName.setText(group.studentsList.get(i).getFirstName());
            TextView lastName = (TextView) item.findViewById(R.id.lastName);
            lastName.setText(group.studentsList.get(i).getLastName());
            TextView missCount = (TextView) item.findViewById(R.id.miss);
            missCount.setText(Integer.toString(group.studentsList.get(i).getMiss()));
            item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            Button back = (Button) findViewById(R.id.backMiss);
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prepareTable(day,week);

                }
            };
            back.setOnClickListener(clickListener);
            linLayout.addView(item);
        }
    }

}

