package com.example.antontsarikovich.helper.models;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Anton Tsarikovich on 26.11.2015.
 */
@Root(name = "scheduleXmlModels")
public class Timetable {
    @ElementList(inline = true, name = "scheduleModel")
    public List<DayTimetable> dayTimetables;
}
