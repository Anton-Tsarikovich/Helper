package com.example.antontsarikovich.helper.Group;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Anton Tsarikovich on 01.12.2015.
 */
@Root(name = "StudentsOfGroup")
public class Group {
    @ElementList(inline = true, name = "student")
    public List<Students> studentsList;

}
