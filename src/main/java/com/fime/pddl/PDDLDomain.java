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

		properties.getSubjects().forEach((subject) -> {
		    if( properties.getSubjects().indexOf(subject) != properties.getSubjects().size() - 1){
                header.append(IDENT + subject.getSubjectName() + "\n");
            } else {
                header.append(IDENT + subject.getSubjectName() + " - subject\n");
            }
		});
		header.append("\n\n");

        properties.getThemes().forEach((theme) -> {
            if( properties.getThemes().indexOf(theme) != properties.getThemes().size() - 1){
                header.append(IDENT + theme.getThemeName() + "\n");
            } else {
                header.append(IDENT + theme.getThemeName() + " - theme\n");
            }
        });
        header.append("\n\n");

        properties.getSubthemes().forEach((subtheme) -> {
            if( properties.getThemes().indexOf(subtheme) != properties.getThemes().size() - 1){
                header.append(IDENT + subtheme.getSubthemeName() + "\n");
            } else {
                header.append(IDENT + subtheme.getSubthemeName() + " - subtheme\n");
            }
        });
        header.append("\n\n");

        properties.getActivites().forEach((activity) -> {
            if( properties.getActivites().indexOf(activity) != properties.getActivites().size() - 1){
                header.append(IDENT + activity.getActivityName() + "\n");
            } else {
                header.append(IDENT + activity.getActivityName() + " - activity\n");
            }
        });
        header.append("\n\n");

        properties.getResources().forEach((resource) -> {
            if( properties.getResources().indexOf(resource) != properties.getResources().size() - 1){
                header.append(IDENT + resource.getResourceName() + "\n");
            } else {
                header.append(IDENT + resource.getResourceName() + " - resource\n");
            }
        });
        header.append("\n\n");

		header.append(" )\n\n");
		header.append("\n\n");

		return header.toString();
	}

}
