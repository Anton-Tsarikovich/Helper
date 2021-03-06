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

    @ElementList(inline = true, name = "studentGroup")
    public List<StudentGroup> groupList;
    public String getElement(String numberOfGroup) {
        for (int i = 0; i < groupList.size(); i++) {
            if (groupList.get(i).getName().equals(numberOfGroup)) {
                Log.d(TAG, Integer.toString(groupList.get(i).getId()));
                return Integer.toString(groupList.get(i).getId());
            }
        }
        return null;
    }
}
