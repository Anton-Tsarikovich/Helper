package com.example.antontsarikovich.helper.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Anton Tsarikovich on 26.11.2015.
 */
@Root(name = "scheduleModel")
public class DayTimetable {

    @ElementList(inline = true, name = "schedule")
    public List<Subject> subjects;

    private String weekDay;
    @Element(name = "weekDay")
    public String getWeekDay() {
        return weekDay;
    }
    @Element(name = "weekDay")
    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }
}
