package com.example.antontsarikovich.helper.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Anton Tsarikovich on 28.11.2015.
 */
@Root(name = "employee")
public class Employee {
    String academicDepartment;
    String firstName;
    String id;
    String lastName;
    String middleName;
    public Employee(){}
    @Element(name = "firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Element(name = "firstName")
    public String getFirstName() {
        return firstName;
    }
    @Element(name = "id")
    public void setId(String id) {
        this.id = id;
    }
    @Element(name = "id")
    public String getId() {
        return id;
    }
    @Element(name = "lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Element(name = "lastName")
    public String getLastName() {
        return lastName;
    }
    @Element(name = "middleName")
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    @Element(name = "middleName")
    public String getMiddleName() {
        return middleName;
    }
}
