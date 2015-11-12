package com.example.antontsarikovich.helper;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
}
