package com.fime.pddl;

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
		
		for (String subject : properties.getSubjects()) {
			header.append(IDENT + subject + "\n");
		}
		header.append("\n\n");
		
		for (String theme : properties.getThemes()) {
			header.append(IDENT + theme + "\n");
		}
		header.append("\n\n");
		
		for (String subtheme : properties.getSubthemes()) {
			header.append(IDENT + subtheme + "\n");
		}
		header.append("\n\n");

		for (String activity : properties.getActivites()) {
			header.append(IDENT + activity + "\n");
		}
		header.append("\n\n");

		for (String resource : properties.getResources()) {
			header.append(IDENT + resource + "\n");
		}
		header.append("\n\n");
		
		header.append(" )\n\n");
		header.append("\n\n");

		return header.toString();

	}

}
