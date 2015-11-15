package com.example.antontsarikovich.helper.models;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton Tsarikovich on 15.11.2015.
 */
@Root
public class StudentGroups {

    @ElementList(name="studentGroupXmlModels")
    List<StudentGroup> groupList = new ArrayList<>();
}
