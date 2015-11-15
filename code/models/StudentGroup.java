package com.example.antontsarikovich.helper.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Anton Tsarikovich on 15.11.2015.
 */
@Root(name="studentGroup")
public class StudentGroup {

    @Element(name="id")
    private int id;

    @Element(name="name")
    private String name;

    @Element(name="course")
    private int course;

    @Element(name="facultyId")
    private int facultyId;

    @Element(name="specialityDepartmentEducationFormId")
    private String specialityDepartmentEducationFormId;

    public StudentGroup() {
    }

    public StudentGroup(int id,
                        String name,
                        int course,
                        int facultyId,
                        String specialityDepartmentEducationFormId) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.facultyId = facultyId;
        this.specialityDepartmentEducationFormId = specialityDepartmentEducationFormId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getSpecialityDepartmentEducationFormId() {
        return specialityDepartmentEducationFormId;
    }

    public void setSpecialityDepartmentEducationFormId(String specialityDepartmentEducationFormId) {
        this.specialityDepartmentEducationFormId = specialityDepartmentEducationFormId;
    }
}
