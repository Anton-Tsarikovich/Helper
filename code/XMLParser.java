package com.example.antontsarikovich.helper;

import android.util.Log;

import com.example.antontsarikovich.helper.Group.Group;
import com.example.antontsarikovich.helper.models.StudentGroups;
import com.example.antontsarikovich.helper.models.Timetable;

import org.simpleframework.xml.core.Persister;

import java.io.Reader;
import java.io.StringReader;

/**
 * Created by Anton Tsarikovich on 25.11.2015.
 */
public class XMLParser {
    private Reader reader;
    private Persister serializer;
    private StudentGroups groups;
    private Timetable timetable;
    private Group group;
    public XMLParser(){
        serializer = new Persister();
    }

    public StudentGroups parseAllXML(String XLMString) {
        reader = new StringReader(XLMString);
        try {
            groups = serializer.read(StudentGroups.class, reader, false);
        } catch (Exception e) {
            Log.e("LOGS", e.getMessage());
        }
        return groups;
    }
    public Timetable parseTimetable(String XLMString) {
        reader = new StringReader(XLMString);
        try {
            timetable = serializer.read(Timetable.class, reader, false);
        } catch (Exception e) {
            Log.e("LOGS", e.getMessage());
        }
        return timetable;
    }
    public Group parseNames(String XLMString) {
        reader = new StringReader(XLMString);
        try {
            group = serializer.read(Group.class, reader, false);
        } catch (Exception e) {
            Log.e("LOGS", e.getMessage());
        }
        return group;
    }

}
