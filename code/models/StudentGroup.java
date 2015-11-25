package com.example.antontsarikovich.helper.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.net.PortUnreachableException;

/**
 * Created by Anton Tsarikovich on 15.11.2015.
 */
@Root(name =  "studentGroup")
public class StudentGroup {
    public StudentGroup(){}
    public StudentGroup(final String name,
                        final int course,
                        final int facultyId,
                        final String specialityDepartmentEducationFormId) {
        this.name = name;
        this.course = course;
        this.facultyId = facultyId;
        this.specialityDepartmentEducationFormId = specialityDepartmentEducationFormId;
    }
    private int id;
    private String name;
    private int course;
    private int facultyId;
    private String specialityDepartmentEducationFormId;

    @Element(name="id")
    public int getId() {
        return id;
    }


    @Element(name="id")
    public void setId(int id) {
        this.id = id;
    }
    @Element(name="name")
    public String getName() {
        return name;
    }

    @Element(name="name")
    public void setName(String name) {
        this.name = name;
    }
    @Element(required = false,name="course")
    public int getCourse() {
        return course;
    }


    @Element(required = false,name="course")
    public void setCourse(int course) {
        this.course = course;
    }
    @Element(name="facultyId")
    public int getFacultyId() {
        return facultyId;
    }
    @Element(name="facultyId")
    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    @Element(name="specialityDepartmentEducationFormId")
    public String getSpecialityDepartmentEducationFormId() {
        return specialityDepartmentEducationFormId;
    }
    @Element(name="specialityDepartmentEducationFormId")
    public void setSpecialityDepartmentEducationFormId(String specialityDepartmentEducationFormId) {
        this.specialityDepartmentEducationFormId = specialityDepartmentEducationFormId;
    }
}
