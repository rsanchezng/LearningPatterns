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

		header.append("(define (domain degree)\n" + " (:requirements :durative-actions :typing :fluents :equality)\n"
				+ " (:types student resource - object\n" + "         subject Theme subtheme LA - LO)\n"
				+ " (:constants\n" + "\n");

		header.append(properties.subjects);
		header.append("\n\n");
		header.append(properties.themes);
		header.append("\n\n");
		header.append(properties.subthemes);
		header.append("\n\n");
		header.append(properties.activites);
		header.append("\n\n");
		header.append(properties.resources);
		header.append("\n\n");
		
		header.append(" )\n\n");
		header.append("\n\n");

		System.out.println(header.toString());

		return header.toString();

	}

}
