package com.fime.pddl;

public class PDDLDomain implements PDDL {

	PDDLProperties properties;

	public PDDLDomain(PDDLProperties properties) {
		this.properties = properties;
	}

	@Override
	public byte[] generatePDDL() {

		StringBuilder domain = new StringBuilder();

		ConstantDomainPDDLFunctions pddlFunctions = new ConstantDomainPDDLFunctions();
		domain.append(createDomainHeader());
		domain.append(pddlFunctions.getPredicates());
		domain.append(pddlFunctions.getFunctions());
		domain.append(createSubjectEnrollments());
		domain.append(pddlFunctions.getLAWithNoReqs());
		domain.append(pddlFunctions.getLAWitReqSubthemes());
		domain.append(pddlFunctions.getLAWitReqLA());
		domain.append(pddlFunctions.getALWithMultipleLAReq());
		domain.append(getPassThemeActions());
		domain.append(getPassSubjectActions());
		domain.append(pddlFunctions.getSubjectPass());

		return domain.toString().getBytes();

	}

	private String createDomainHeader() {

		final String IDENT = "	";
		StringBuilder header = new StringBuilder();

		header.append("(define (domain degree)\n" + " (:requirements :durative-actions :typing :fluents :equality)\n"
				+ " (:types student resource - object\n" + "         subject Theme subtheme LA - LO)\n"
				+ " (:constants\n" + "\n");

		properties.getSubjects().forEach((subject) -> {
		    if( properties.getSubjects().indexOf(subject) != properties.getSubjects().size() - 1){
                header.append(IDENT + subject.getName() + "\n");
            } else {
                header.append(IDENT + subject.getName() + " - Subject\n");
            }
		});
		header.append("\n\n");

        properties.getThemes().forEach((theme) -> {
            if( properties.getThemes().indexOf(theme) != properties.getThemes().size() - 1){
                header.append(IDENT + theme.getName() + "\n");
            } else {
                header.append(IDENT + theme.getName() + " - Theme\n");
            }
        });
        header.append("\n\n");

        properties.getSubthemes().forEach((subtheme) -> {
            if( properties.getSubthemes().indexOf(subtheme) != properties.getSubthemes().size() - 1){
                header.append(IDENT + subtheme.getName() + "\n");
            } else {
                header.append(IDENT + subtheme.getName() + " - Subtheme\n");
            }
        });
        header.append("\n\n");

        properties.getActivites().forEach((activity) -> {
            if( properties.getActivites().indexOf(activity) != properties.getActivites().size() - 1){
                header.append(IDENT + activity.getName() + "\n");
            } else {
                header.append(IDENT + activity.getName() + " - Activity\n");
            }
        });
        header.append("\n\n");

        properties.getResources().forEach((resource) -> {
            if( properties.getResources().indexOf(resource) != properties.getResources().size() - 1){
                header.append(IDENT + resource.getName() + "\n");
            } else {
                header.append(IDENT + resource.getName() + " - Resource\n");
            }
        });
        header.append("\n\n");

		header.append(" )\n\n");
		header.append("\n\n");

		return header.toString();
	}

	private String createSubjectEnrollments() {

		StringBuilder subjectEnrollment = new StringBuilder();

		subjectEnrollment.append("\n;-----------------------------------------------------------\n");

		properties.getSubjects().forEach((subject) -> {

			subjectEnrollment.append("(:durative-action enroll-subject_" + subject.getName() +"\n" +
					" :parameters (?s - student)\n" +
					" :duration (= ?duration 1)\n" +
					" :condition (and\n" +
					"        (at start (available-subject " +  subject.getName() + " ?s))\n" +
					"        (at start (not-approved " +  subject.getName() + " ?s))\n" +
					"        (at start (<(credits-subject " +  subject.getName() + " )(available-credits ?s)))\n" +
					"        )\n" +
					" :effect (and\n" +
					"        (at end (enrollment ?s " +  subject.getName() + "))\n" +
					"        (at end (decrease (available-credits ?s)(credits-subject " +  subject.getName() + ")))\n" +
					"        (at end (not (available-subject " +  subject.getName() + " ?s)))\n" +
					"        )\n" +
					")\n");

		});
		subjectEnrollment.append("\n;-----------------------------------------------------------\n");
		subjectEnrollment.append("\n\n");

        return subjectEnrollment.toString();
	}

	private String getPassThemeActions() {

		StringBuilder subjectEnrollment = new StringBuilder();

		subjectEnrollment.append("\n;-----------------------------------------------------------\n");

		properties.getThemes().forEach((theme) -> {

			subjectEnrollment.append("(:durative-action PASS-"+ theme.getName() +"_" + theme.getParentSubject().getName() +
					"\n:parameters (?s - student)\n" +
					":duration (= ?duration 0)\n" +
					":condition (and \n" +
					"               (at start (enrollment ?s "+ theme.getParentSubject().getName() + "))\n" +
					getSubthemes(theme, theme.getParentSubject().getName()) +
					"           )\n" +
					":effect (and\n" +
					"                (at end (done-Theme "+ theme.getName() + " " + theme.getParentSubject().getName() + " ?s))\n" +
					"         )\n" +
					")\n");

		});
		subjectEnrollment.append("\n;-----------------------------------------------------------\n");
		subjectEnrollment.append("\n\n");

        return subjectEnrollment.toString();
	}

	private String getSubthemes(Theme theme, String subjectName) {

		StringBuilder conditions = new StringBuilder();

		properties.getSubthemes().forEach((subtheme) -> {
			if (subtheme.getParentTheme().equals(theme)) {
				conditions.append("               (at start (>= (score "+ subtheme.getName()  + " ?s)(mingrade " + subjectName + ")))\n ");
			}
		});

		return conditions.toString();
	}

	private String getPassSubjectActions() {

		StringBuilder passSubjectActions = new StringBuilder();

		properties.getSubjects().forEach((subject) -> {
			passSubjectActions.append("(:durative-action PASS-"+ subject.getName() + "\n" +
					":parameters (?s - student)\n" +
					":duration (= ?duration 1)\n" +
					":condition (and\n" +
					"                (at start (enrollment ?s " + subject.getName() + "))\n" +
					getThemes(subject, subject.getName()) +
					"            )\n" +
					":effect\n" +
					"        (at end (done-subject-LA "+ subject.getName() + " ?s))\n" +
					")\n\n");
		});
		passSubjectActions.append("\n;-----------------------------------------------------------\n");
		passSubjectActions.append("\n\n");

        return passSubjectActions.toString();
	}

	private String getThemes(Subject subject, String subjectName) {

		StringBuilder conditions = new StringBuilder();

		properties.getThemes().forEach((theme) -> {
			if (theme.getParentSubject().equals(subject)) {
				conditions.append("                (at start (done-Theme " + theme.getName() + " " + subjectName + " ?s))\n");
			}
		});

		return conditions.toString();
	}

}
