package com.fime.pddl;
import java.util.List;

public class PDDLDomain {

	private List<Subject> subjects;
	
	public PDDLDomain(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public byte[] getPDDLDomain() {

		StringBuilder domain = new StringBuilder();

		ConstantDomainPDDLFunctions pddlFunctions = new ConstantDomainPDDLFunctions();
		domain.append(createDomainHeader(subjects));
		domain.append(pddlFunctions.getPredicates());
		domain.append(pddlFunctions.getFunctions());
		domain.append(pddlFunctions.getLAWithNoReqs());
		domain.append(pddlFunctions.getLAWitReqSubthemes());
		domain.append(pddlFunctions.getLAWitReqLA());
		domain.append(pddlFunctions.getALWithMultipleLAReq());
		domain.append(pddlFunctions.getSubjectPass());
		
		return domain.toString().getBytes();

	}

	private String createDomainHeader(List<Subject> subjects2) {
		
		System.out.println(subjects.toString());
		
		String header = "(define (domain degree)\n" + 
				" (:requirements :durative-actions :typing :fluents :equality)\n" + 
				" (:types student resource - object\n" + 
				"         subject Theme subtheme LA - LO)\n" + 
				" (:constants\n" +
				"        DataStructuresAlgs - subject\n" + 
				"\n" + 
				"        Trees\n" + 
				"        Sorting - Theme\n" + 
				"\n" + 
				"        Binary\n" + 
				"        Red-Black\n" + 
				"        Quicksort\n" + 
				"        TreeSort - subtheme\n" + 
				"\n" + 
				"        A1\n" + 
				"        A2\n" + 
				"        A3\n" + 
				"        A4\n" + 
				"        A5\n" + 
				"        A6\n" + 
				"        A7\n" + 
				"        A8\n" + 
				"        A9\n" + 
				"        A10\n" + 
				"        A11\n" + 
				"        A12 - LA\n" + 
				"\n" + 
				"        rec0\n" + 
				"        rec1\n" + 
				"        rec2\n" + 
				"        rec3 - resource\n" + 
				"\n" + 
				" )\n\n";

		return header;
	
	}
}
