package com.example.antontsarikovich.helper;

import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

/**
 * Created by Anton Tsarikovich on 15.11.2015.
 */
public class NetworkDownloader {

    private final static String TAG = "LOGS";

    public NetworkDownloader(){}

    public byte[] getFile(String ... urls) {
        URL url;
        try {
            url = new URL(urls[0]);
            HttpURLConnection conection = (HttpURLConnection)url.openConnection();
            conection.setRequestMethod("GET");

            conection.setInstanceFollowRedirects(false);
            conection.connect();

            InputStream input = conection.getInputStream();
            byte resultArray[] = new byte[0];
            int count;
            byte data[] = new byte[128];
            while ((count = input.read(data)) != -1) {
                Log.d(TAG, "read " + count);
                Log.d(TAG, "String " + new String(data));
                resultArray = addToArray(data, resultArray, count);
            }
            input.close();
            conection.disconnect();
            Log.d(TAG, "read done");
            return resultArray;

        } catch (Exception ex) {
            Log.e(TAG, "error " + ex.getMessage());
        }
        return null;
    }
    protected byte[] addToArray(byte data[], byte resultArray[], int count) {
        byte tempArray[] = new byte[resultArray.length + count];

        System.arraycopy(resultArray, 0, tempArray, 0, resultArray.length);
        System.arraycopy(data, 0, tempArray, resultArray.length, count);


        return tempArray;
    }
}
