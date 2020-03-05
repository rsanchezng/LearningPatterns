package com.fime.pddl;

public class PDDLProblem {

	PDDLProperties properties;
	StringBuilder header;

	public PDDLProblem(PDDLProperties properties) {
		header = new StringBuilder();
		this.properties = properties;
	}
	
	public byte[] getPDDLProblem() {
		
		StringBuilder problemPDDL = new StringBuilder();
		
		problemPDDL.append("(define (problem degree-example)\r\n" + 
				" (:domain degree)\r\n" + 
				" (:objects\r\n" + 
				" 	" + properties.getStudentName()  + 
				" )\r\n" + 
				"\r\n" + 
				"(:init\r\n" + 
				"	(free " + properties.getStudentName() + " )\r\n" + 
				"	(= (total-credits-subject-gain " + properties.getStudentName() + " ) 0)\r\n" + 
				"	(= (available-credits " + properties.getStudentName() + " ) 48)\r\n" + 
				"\r\n" + 
				"	(available-subject DataStructuresAlgs Mary)\r\n" + 
				"	(= (credits-subject DataStructuresAlgs) 6)\r\n" + 
				"	(= (mingrade DataStructuresAlgs) 70)\r\n" + 
				"	(not-approved DataStructuresAlgs Mary)\r\n" + 
				"	\r\n" + 
				"	(not-done-LA A1 DataStructuresAlgs Mary)\r\n" + 
				"	(not-done-LA A2 DataStructuresAlgs Mary)\r\n" + 
				"	(not-done-LA A3 DataStructuresAlgs Mary)\r\n" + 
				"	(not-done-LA A4 DataStructuresAlgs Mary)\r\n" + 
				"	(not-done-LA A5 DataStructuresAlgs Mary)\r\n" + 
				"	(not-done-LA A6 DataStructuresAlgs Mary)\r\n" + 
				"	(not-done-LA A7 DataStructuresAlgs Mary)\r\n" + 
				"	(not-done-LA A8 DataStructuresAlgs Mary)\r\n" + 
				"	(not-done-LA A9 DataStructuresAlgs Mary)\r\n" + 
				"	(not-done-LA A10 DataStructuresAlgs Mary)\r\n" + 
				"	(not-done-LA A11 DataStructuresAlgs Mary)\r\n" + 
				"	(not-done-LA A12 DataStructuresAlgs Mary)\r\n" + 
				"\r\n" + 
				"	(= (score Binary Mary) 0)\r\n" + 
				"	(= (score Red-Black Mary) 0)\r\n" + 
				"	(= (score Quicksort Mary) 0)\r\n" + 
				"	(= (score TreeSort Mary) 0)\r\n" + 
				"\r\n" + 
				"	(= (quantity-resource rec0)100000)\r\n" + 
				"	(= (quantity-resource rec1) 20)\r\n" + 
				"	(= (quantity-resource rec2) 30)\r\n" + 
				"	(= (quantity-resource rec3) 40)\r\n" + 
				"\r\n" + 
				"	(= (valueLA A1) 90)\r\n" + 
				"	(= (valueLA A2) 50)\r\n" + 
				"	(= (valueLA A3) 30)\r\n" + 
				"	(= (valueLA A4) 30)\r\n" + 
				"	(= (valueLA A5) 40)\r\n" + 
				"	(= (valueLA A6) 40)\r\n" + 
				"	(= (valueLA A7) 40)\r\n" + 
				"	(= (valueLA A8) 30)\r\n" + 
				"	(= (valueLA A9) 50)\r\n" + 
				"	(= (valueLA A10) 100)\r\n" + 
				"	(= (valueLA A11) 20)\r\n" + 
				"	(= (valueLA A12) 50)\r\n" + 
				"\r\n" + 
				"	(= (DurationLA A1) 95)\r\n" + 
				"	(= (DurationLA A2) 55)\r\n" + 
				"	(= (DurationLA A3) 35)\r\n" + 
				"	(= (DurationLA A4) 35)\r\n" + 
				"	(= (DurationLA A5) 45)\r\n" + 
				"	(= (DurationLA A6) 45)\r\n" + 
				"	(= (DurationLA A7) 45)\r\n" + 
				"	(= (DurationLA A8) 35)\r\n" + 
				"	(= (DurationLA A9) 55)\r\n" + 
				"	(= (DurationLA A10) 98)\r\n" + 
				"	(= (DurationLA A11) 25)\r\n" + 
				"	(= (DurationLA A12) 55)\r\n" + 
				"\r\n" + 
				"	(isPartOfSubtheme A1 Binary)\r\n" + 
				"	(isPartOfSubtheme A2 Binary)\r\n" + 
				"	(isPartOfSubtheme A3 Binary)\r\n" + 
				"	(isPartOfSubtheme A4 Red-Black)\r\n" + 
				"	(isPartOfSubtheme A5 Red-Black)\r\n" + 
				"	(isPartOfSubtheme A6 Red-Black)\r\n" + 
				"	(isPartOfSubtheme A7 Quicksort)\r\n" + 
				"	(isPartOfSubtheme A8 Quicksort)\r\n" + 
				"	(isPartOfSubtheme A9 Quicksort)\r\n" + 
				"	(isPartOfSubtheme A10 TreeSort)\r\n" + 
				"	(isPartOfSubtheme A11 TreeSort)\r\n" + 
				"	(isPartOfSubtheme A12 TreeSort)\r\n" + 
				"\r\n" + 
				"	(isPartOfTheme Binary Trees)\r\n" + 
				"	(isPartOfTheme Red-Black Trees)\r\n" + 
				"	(isPartOfTheme Quicksort Sorting)\r\n" + 
				"	(isPartOfTheme TreeSort Sorting)\r\n" + 
				"\r\n" + 
				"	(isPartOfSubject Trees DataStructuresAlgs)\r\n" + 
				"	(isPartOfSubject Sorting DataStructuresAlgs)\r\n" + 
				"\r\n" + 
				"	(KindResourceLO A1 rec3)\r\n" + 
				"	(KindResourceLO A2 rec1)\r\n" + 
				"	(KindResourceLO A3 rec3)\r\n" + 
				"	(KindResourceLO A4 rec3)\r\n" + 
				"	(KindResourceLO A5 rec3)\r\n" + 
				"	(KindResourceLO A6 rec3)\r\n" + 
				"	(KindResourceLO A7 rec1)\r\n" + 
				"	(KindResourceLO A8 rec1)\r\n" + 
				"	(KindResourceLO A9 rec3)\r\n" + 
				"	(KindResourceLO A10 rec2)\r\n" + 
				"	(KindResourceLO A11 rec2)\r\n" + 
				"	(KindResourceLO A12 rec2)\r\n" + 
				"\r\n" + 
				"	(not-has-reqs A1)\r\n" + 
				"	(not-has-reqs A2)\r\n" + 
				"	(not-has-reqs A3)\r\n" + 
				"	(not-has-reqs A5)\r\n" + 
				"	(not-has-reqs A6)\r\n" + 
				"	(not-has-reqs A7)\r\n" + 
				"	(not-has-reqs A8)\r\n" + 
				"	(not-has-reqs A11)\r\n" + 
				"	(not-has-reqs A12)\r\n" + 
				"\r\n" + 
				"	(has-reqs A4 A1)\r\n" + 
				"	(has-reqs A9 A3)\r\n" + 
				"	(has-reqs A10 A9)\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"	(= (maxgrade-subtheme Binary) 100)\r\n" + 
				"	(= (maxgrade-subtheme Red-Black) 100)\r\n" + 
				"	(= (maxgrade-subtheme Quicksort) 100)\r\n" + 
				"	(= (maxgrade-subtheme TreeSort) 100)\r\n" + 
				"\r\n" + 
				")\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"(:goal (and\r\n" + 
				"		(pass-degree DataStructuresAlgs Mary)\r\n" + 
				"       )\r\n" + 
				")\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"(:metric minimize (total-time))\r\n" + 
				"\r\n" + 
				"\r\n" + 
				")\r\n" + 
				"\r\n" + 
				";;END\r\n" + 
				"");
		
		return problemPDDL.toString().getBytes();
	}

}
