package com.fime.pddl;

import com.fime.pddl.ConstantDomainPDDLFunctions;

public class PDDLDomain implements PDDL {

	PDDLProperties properties;
	StringBuilder header;

	public PDDLDomain(PDDLProperties properties) {
		header = new StringBuilder();
		this.properties = properties;
	}

	@Override
	public byte[] generatePDDL() {

		StringBuilder domain = new StringBuilder();

		ConstantDomainPDDLFunctions pddlFunctions = new ConstantDomainPDDLFunctions();
		domain.append(createDomainHeader());
		domain.append(pddlFunctions.getPredicates());
		domain.append(pddlFunctions.getFunctions());
		domain.append(pddlFunctions.getLAWithNoReqs());
		domain.append(pddlFunctions.getLAWitReqSubthemes());
		domain.append(pddlFunctions.getLAWitReqLA());
		domain.append(pddlFunctions.getALWithMultipleLAReq());
		domain.append(pddlFunctions.getSubjectPass());

		return domain.toString().getBytes();

	}

	private String createDomainHeader() {
		
		final String IDENT = "	";

		header.append("(define (domain degree)\n" + " (:requirements :durative-actions :typing :fluents :equality)\n"
				+ " (:types student resource - object\n" + "         subject Theme subtheme LA - LO)\n"
				+ " (:constants\n" + "\n");
		
		for (Subject subject : properties.getSubjects()) {
			header.append(IDENT + subject.getSubjectName() + "\n");
		}
		header.append("\n\n");
		
		for (Theme theme : properties.getThemes()) {
			header.append(IDENT + theme.getThemeName() + "\n");
		}
		header.append("\n\n");
		
		for (Subtheme subtheme : properties.getSubthemes()) {
			header.append(IDENT + subtheme.getSubthemeName() + "\n");
		}
		header.append("\n\n");

		for (Activity activity : properties.getActivites() ) {
			header.append(IDENT + activity.getActivityName() + "\n");
		}
		header.append("\n\n");

		for (Resource resource : properties.getResources()) {
			header.append(IDENT + resource.getResourceName() + "\n");
		}
		header.append("\n\n");
		header.append(" )\n\n");
		header.append("\n\n");

		return header.toString();
	}

}
