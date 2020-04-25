package com.fime.pddl;

public class PDDLProblem implements PDDL {

	PDDLProperties properties;

	public PDDLProblem(PDDLProperties properties) {
		this.properties = properties;
	}

	@Override
	public byte[] generatePDDL() {

		StringBuilder problemPDDL = new StringBuilder();

		problemPDDL.append("(define (problem degree-example)\n" +
				" (:domain degree)\n" +
				" (:objects\n" +
				" 	" + properties.getStudent().getName() + " - Student\n" +
				" )\n" +
				"\n" +
				"(:init\n" +
				"	(free "  + properties.getStudent().getName() +  ")\n" +
				"	(= (total-credits-subject-gain " + properties.getStudent().getName() +  ") "  + properties.getStudent().getAcumulatedCredits() +  ")\n" +
				"	(= (available-credits " + properties.getStudent().getName() + ") " + properties.getStudent().getAvailableCredits() + " )\n" +
				"\n" +
				getAvailableSubjects(properties.getStudent().getName()) +
				"\n" +
				getActivitiesNotDone(properties.getStudent().getName()) +
				"\n" +
				getSubthemeScores(properties.getStudent().getName()) +
				"\n" +
				getResourcesQuantity(properties.getStudent().getName()) +
				"\n" +
				getActivitiesValue() +
				"\n" +
				getActivitiesDurations() +
				"\n" +
				getActivitiesParentSubtheme() +
				"\n" +
				getSubthemesParentTheme() +
				"\n" +
				getThemesParentSubject() +
				"\n" +
				getActivitiesResources() +
				"\n" +
				getListOfActivitiesWithReqsAndNoReqs() +
				"\n" +
				getSubthemeMaxGrade() +
				"\n" +
				")\n" +
				"\n" +
				"\n" +
				getPassDegreeGoals() +
				")\n" +
				"\n" +
				"\n" +
				"(:metric minimize (total-time))\n" +
				"\n" +
				"\n" +
				")\n" +
				"\n" +
				";;END\n" +
				"");

		return problemPDDL.toString().getBytes();
	}

	private String getAvailableSubjects(String studentName) {

		StringBuilder availableSubjects = new StringBuilder();
		properties.getSubjects().forEach(subject -> {

			availableSubjects.append("	(available-subject " + subject.getName() + " " + studentName + ")\n" +
									"	(= (credits-subject " + subject.getName() + ") " + subject.getCredits() + ")\n" +
									"	(= (mingrade " + subject.getName() + ") " + subject.getMinGrade() + ")\n" +
									"	(not-approved " + subject.getName() + " " + studentName + ")\n" +
									"	\n");
		});

		return availableSubjects.toString();
	}

	private String getActivitiesNotDone(String studentName) {
		StringBuilder activitiesNotDone = new StringBuilder();
		properties.getActivites().forEach(activity -> {
			if(!activity.isDone()) {
				activitiesNotDone.append("	(not-done-LA " + activity.getName() + " "
						+ activity.getParentSubtheme().getParentTheme().getName() + " " + studentName + ")\n");
			}
		});

		return activitiesNotDone.toString();
	}

	private String getSubthemeScores(String studentName) {
		StringBuilder subthemeScores = new StringBuilder();
		properties.getSubthemes().forEach(subtheme -> {
			subthemeScores.append("	(= (score " + subtheme.getName() + " " + studentName + ") "
					+ subtheme.getStudentScore() + ")\n");
		});

		return subthemeScores.toString();
	}

	private String getResourcesQuantity(String studentName) {
		StringBuilder subthemeScores = new StringBuilder();
		properties.getResources().forEach(resource -> {
			subthemeScores.append("	(= (quantity-resource " + resource.getName() + ") "
					+ resource.getQuantity() + ")\n");
		});

		return subthemeScores.toString();
	}

	private String getActivitiesValue() {
		StringBuilder activitiesValues = new StringBuilder();
		properties.getActivites().forEach(activity -> {
			activitiesValues
					.append("	(= (valueLA " + activity.getName() + ") " + activity.getValue() + ")\n");
		});

		return activitiesValues.toString();
	}

	private String getActivitiesDurations() {
		StringBuilder activitiesDurations = new StringBuilder();
		properties.getActivites().forEach(activity -> {
			activitiesDurations
					.append("	(= (DurationLA " + activity.getName() + ") " + activity.getDuration() + ")\n");
		});

		return activitiesDurations.toString();
	}

	private String getActivitiesParentSubtheme() {
		StringBuilder activitiesParentSubtheme = new StringBuilder();
		properties.getActivites().forEach(activity -> {
			activitiesParentSubtheme.append("	(isPartOfSubtheme " + activity.getName() + " "
					+ activity.getParentSubtheme().getName() + ")\n");
		});

		return activitiesParentSubtheme.toString();
	}

	private String getSubthemesParentTheme() {
		StringBuilder subthemesParentTheme = new StringBuilder();
		properties.getSubthemes().forEach(subtheme -> {
			subthemesParentTheme.append("	(isPartOfTheme " + subtheme.getParentTheme().getName() + " " + subtheme.getName() + ")\n");
		});

		return subthemesParentTheme.toString();
	}

	private String getThemesParentSubject() {
		StringBuilder themesParentSubject = new StringBuilder();
		properties.getThemes().forEach(theme -> {
			themesParentSubject.append("	(isPartOfSubject " + theme.getParentSubject().getName() + " "
					+ theme.getName() + ")\n");
		});

		return themesParentSubject.toString();
	}

	private String getActivitiesResources() {
		StringBuilder activitiesResources = new StringBuilder();
		properties.getActivites().forEach(activity -> {
			activity.getResources().forEach(resource -> {
				activitiesResources.append("	(KindResourceLO " + activity.getName() + " " + resource.getName() + ")\n");
			});
		});

		return activitiesResources.toString();
	}

	private String getListOfActivitiesWithReqsAndNoReqs() {

		StringBuilder activitiesWithReqs = new StringBuilder();
		StringBuilder activitiesWithNoReqs = new StringBuilder();

		properties.getActivites().forEach(activity -> {
			if(activity.getDependencies() == null || activity.getDependencies().isEmpty()) {
				activitiesWithReqs.append("	(not-has-reqs " + activity.getName() + ")\n");
			}
			else {
				activity.getDependencies().forEach(dependency ->{
					activitiesWithNoReqs.append("	(has-reqs "+ activity.getName() +" " + dependency + ")\n");
				});
			}
		});

		return activitiesWithReqs.append("\n").append(activitiesWithNoReqs).toString();
	}
	
	private String getSubthemeMaxGrade() {
		
		StringBuilder subthemeMaxGrade = new StringBuilder();
		
		properties.getSubthemes().forEach(subtheme ->{
			subthemeMaxGrade.append("	(= (maxgrade-subtheme " + subtheme.getName() + ") " + subtheme.getMaxGrade() +  ")\n");
		});
		
		return subthemeMaxGrade.toString();
	}
	
	private String getPassDegreeGoals() {
		
		StringBuilder passDegreeGoals = new StringBuilder();
		
		passDegreeGoals.append("(:goal (and\n");
		
		properties.getThemes().forEach(theme ->{
			passDegreeGoals.append("		(pass-degree " + theme.getName() + " " + properties.getStudent().getName() + ")\n");
		});
		
		passDegreeGoals.append("       )\n");
		
		
		return passDegreeGoals.toString();
	}
}
