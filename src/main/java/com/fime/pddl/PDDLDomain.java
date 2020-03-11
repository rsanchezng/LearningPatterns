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
                header.append(IDENT + subject.getSubjectName() + "\n");
            } else {
                header.append(IDENT + subject.getSubjectName() + " - Subject\n");
            }
		});
		header.append("\n\n");

        properties.getThemes().forEach((theme) -> {
            if( properties.getThemes().indexOf(theme) != properties.getThemes().size() - 1){
                header.append(IDENT + theme.getThemeName() + "\n");
            } else {
                header.append(IDENT + theme.getThemeName() + " - Theme\n");
            }
        });
        header.append("\n\n");

        properties.getSubthemes().forEach((subtheme) -> {
            if( properties.getSubthemes().indexOf(subtheme) != properties.getSubthemes().size() - 1){
                header.append(IDENT + subtheme.getSubthemeName() + "\n");
            } else {
                header.append(IDENT + subtheme.getSubthemeName() + " - Subtheme\n");
            }
        });
        header.append("\n\n");

        properties.getActivites().forEach((activity) -> {
            if( properties.getActivites().indexOf(activity) != properties.getActivites().size() - 1){
                header.append(IDENT + activity.getActivityName() + "\n");
            } else {
                header.append(IDENT + activity.getActivityName() + " - Activity\n");
            }
        });
        header.append("\n\n");

        properties.getResources().forEach((resource) -> {
            if( properties.getResources().indexOf(resource) != properties.getResources().size() - 1){
                header.append(IDENT + resource.getResourceName() + "\n");
            } else {
                header.append(IDENT + resource.getResourceName() + " - Resource\n");
            }
        });
        header.append("\n\n");

		header.append(" )\n\n");
		header.append("\n\n");

		return header.toString();
	}
	
	private String createSubjectEnrollments() {
		
		final String IDENT = "	";
		StringBuilder subjectEnrollment = new StringBuilder();

		subjectEnrollment.append("\n;-----------------------------------------------------------\n");
		
		properties.getSubjects().forEach((subject) -> {
			
			subjectEnrollment.append("(:durative-action enroll-subject_" + subject.getSubjectName() +"\n" + 
					" :parameters (?s - student)\n" + 
					" :duration (= ?duration 0)\n" + 
					" :condition (and\n" + 
					"        (at start (available-subject " +  subject.getSubjectName() + " ?s))\n" + 
					"        (at start (not-approved " +  subject.getSubjectName() + " ?s))\n" + 
					"        (at start (<(credits-subject " +  subject.getSubjectName() + " )(available-credits ?s)))\n" + 
					"        )\n" + 
					" :effect (and\n" + 
					"        (at end (enrollment ?s " +  subject.getSubjectName() + "))\n" + 
					"        (at end (decrease (available-credits ?s)(credits-subject " +  subject.getSubjectName() + ")))\n" + 
					"        (at end (not (available-subject " +  subject.getSubjectName() + " ?s)))\n" + 
					"        )\n" + 
					")\n");
			
		});
		subjectEnrollment.append("\n;-----------------------------------------------------------\n");
		subjectEnrollment.append("\n\n");

        return subjectEnrollment.toString();
	}

}
