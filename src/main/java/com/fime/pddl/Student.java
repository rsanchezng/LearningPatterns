package com.fime.pddl;

import lombok.Data;

@Data
public class Student {

    private String studentName;
    //	available-credits student1
    private int availableCredits;
    //	Esto hace referencia a total-credits-subject-gain student1
    private int acumulatedCredits;

}
