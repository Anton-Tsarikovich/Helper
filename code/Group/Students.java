package com.example.antontsarikovich.helper.Group;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Anton Tsarikovich on 01.12.2015.
 */
@Root(name = "student")
public class Students {
    private String LastName;
    private String FirstName;
    private int miss;
    @Element(name="LastName")
    public String getLastName() {
        return LastName;
    }
    @Element(name="LastName")
    public void setLastName(String lastName) {
        LastName = lastName;
    }
    @Element(name="firstName")
    public String getFirstName() {
        return FirstName;
    }
    @Element(name="firstName")
    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public int getMiss() {
        return miss;
    }

    public void setMiss() {
        this.miss += 1;
    }
    public void setLoadMiss(int miss) {
        this.miss = miss;
    }
}
