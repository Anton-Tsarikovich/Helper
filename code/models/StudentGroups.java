package com.example.antontsarikovich.helper.models;

import android.util.Log;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;


/**
 * Created by Anton Tsarikovich on 15.11.2015.
 */
@Root(name = "studentGroupXmlModels")
public class StudentGroups {
    private final static String TAG = "LOGS";
    private final static String MyGroup = "350502";

    @ElementList(inline = true, name = "studentGroup")
    public List<StudentGroup> groupList;
    public String getElements() {
        Log.d(TAG, groupList.get(0).getName());
        for (int i = 0; i < groupList.size(); i++) {
            if (groupList.get(i).getName().equals(MyGroup)) {
                Log.d(TAG, "ok");
                Log.d(TAG, Integer.toString(groupList.get(i).getId()));
                return Integer.toString(groupList.get(i).getId());
            }
        }
        return null;
    }
}
