package com.fime.pddl;

import java.util.List;

import lombok.Data;

@Data
public class Activity {

    private String activityIdentifier;
    private String activityName;
    private Subtheme parentSubtheme;
    //	si dependencies es nula o vacia califica para: (not-has-reqs LA1050) y si esta llena puede ser: (has-reqs A4 Binary)
    private List<Object> dependencies;
    private boolean activityDone;
    private int value; //Maestro
    private int studentGrade;
    private int duration;
    private List<Resource> resources;

}
