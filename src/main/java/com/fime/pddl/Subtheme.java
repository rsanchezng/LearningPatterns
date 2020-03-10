package com.fime.pddl;

import lombok.Data;

@Data
public class Subtheme {

    private String subthemeName;
    private Theme parentTheme;
    //	private List<Object> dependencies;
    private boolean subthemeDone;
    private int maxGradeSubtheme;
    private int studentScore;

}
