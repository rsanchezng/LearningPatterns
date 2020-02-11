package com.fime.pddl;

import java.util.List;

public class PDDLDomain implements PDDL {

	List<Subject> subjects;
	StringBuilder header;

	public PDDLDomain(List<Subject> subjects) {
		header = new StringBuilder();
		this.subjects = subjects;
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

		getPlanInfo();
		header.append("\n\n");

		header.append(" )\n\n");
		header.append("\n\n");

		System.out.println(header.toString());

		return header.toString();

	}

	private void getPlanInfo() {

		final String IDENT = "        ";

		for (int i = 0; i <= subjects.size(); i++) {

			for (Subject subject : subjects) {
				header.append(IDENT + subject.getSubjectName());
				header.append("\n\n");

				if (i == subjects.size()) {
					for (Theme theme : subject.getThemes()) {
						header.append(IDENT + theme.getThemeName());
						header.append("\n\n");
					}
				}

			}
		}

	}
}
