package com.example.antontsarikovich.helper;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Anton Tsarikovich on 12.11.2015.
 */
public  class FileSystemManager extends Activity{

    public static void savePassword(final String password, Activity activity){
        SharedPreferences sharedPreferences = activity.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("password", password);
        editor.commit();
    }
    public static String loadPassword(Activity activity) {
        SharedPreferences sharedPreferences = activity.getPreferences(MODE_PRIVATE);
        return sharedPreferences.getString("password","");
    }
}
