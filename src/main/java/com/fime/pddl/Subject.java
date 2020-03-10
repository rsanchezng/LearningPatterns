package com.fime.pddl;

import lombok.Data;

@Data
public class Subject {

    private String subjectName;
    //	private List<Object> dependencies;
    private boolean subjectDone;
    private int credits;
    private int minGrade;

}
