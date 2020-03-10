package com.fime.pddl;

import lombok.Data;

@Data
public class Theme {

    private String themeName;
    private Subject parentSubject;
    //	private List<Object> dependencies;
    private boolean themeDone;

 
}