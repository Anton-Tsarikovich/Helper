package com.example.antontsarikovich.helper.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Created by Anton Tsarikovich on 27.11.2015.
 */
@Root(name = "schedule")
public class Subject {
    @ElementList(required = false,inline = true,name = "employee")
    public List<Employee> employee;
    @ElementList (inline = true, entry = "weekNumber")
    public List<String> weekNumbers;

    private String auditory;
    private String lessonTime;
    private String lessonType;
    private boolean numSubgroup;
    private String studentGroup;
    private String subject;

    private String zaoch;

    public Subject(){

    }
 /*   public Subject(String auditory,
                   String lessonTime,
                   String lessonType,
                   boolean numSubgroup,
                   String studentGroup,
                   String subject,
                   )*/

    @Element(required = false,name = "auditory")
    public String getAuditory() {
        return auditory;
    }
    @Element(required = false,name = "auditory")
    public void setAuditory(String auditory) {
        this.auditory = auditory;
    }
    @Element(name = "lessonTime")
    public String getLessonTime() {
        return lessonTime;
    }
    @Element(name = "lessonTime")
    public void setLessonTime(String lessonTime) {
        this.lessonTime = lessonTime;
    }
    @Element(name = "lessonType")
    public String getLessonType() {
        return lessonType;
    }
    @Element(name = "lessonType")
    public void setLessonType(String lessonType) {
        this.lessonType = lessonType;
    }
    @Element(name = "numSubgroup")
    public boolean isNumSubgroup() {
        return numSubgroup;
    }
    @Element(name = "numSubgroup")
    public void setNumSubgroup(boolean numSubgroup) {
        this.numSubgroup = numSubgroup;
    }
    @Element(name = "studentGroup")
    public String getStudentGroup() {
        return studentGroup;
    }
    @Element(name = "studentGroup")
    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }
    @Element(name = "subject")
    public String getSubject() {
        return subject;
    }
    @Element(name = "subject")
    public void setSubject(String subject) {
        this.subject = subject;
    }


    @Element(name = "zaoch")
    public String getZaoch() {
        return zaoch;
    }
    @Element(name = "zaoch")
    public void setZaoch(String zaoch) {
        this.zaoch = zaoch;
    }
    public boolean compareWeek(String week) {
        for(Object object: weekNumbers) {
            if(object.equals("0")) {
                return true;
            }
            if(object.equals(week)) {
                return true;
            }
        }
        return false;
    }




}
