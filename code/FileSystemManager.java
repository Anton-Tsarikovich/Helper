package com.example.antontsarikovich.helper;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Created by Anton Tsarikovich on 12.11.2015.
 */
public class FileSystemManager  {
    private Activity activity;
    public FileSystemManager(Activity activity) {
        this.activity = activity;
    }

    public  String loadSettings() {
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_APPEND);
        return sharedPreferences.getString("saved text","");

    }
    public  void saveSettings(String text) {
        SharedPreferences sharedPreferences =  activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("saved text", text);
        editor.commit();
    }
    public void saveToSD(String text, String name) {
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.d("SDLOG", "SD nedostupna " + Environment.getExternalStorageState());
            return;
        }
        File sdPath = Environment.getExternalStorageDirectory();
        sdPath = new File(sdPath.getAbsolutePath() + "/" + "apk");
        File sdFile = new File(sdPath,name);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(sdFile));
            bufferedWriter.write(text);
            bufferedWriter.close();
            Log.d("SDLOG", "Success");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
