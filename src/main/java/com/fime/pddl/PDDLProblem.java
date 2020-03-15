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
				" 	" + properties.getStudent().getStudentName() + " - Student\n" + 
				" )\n" + 
				"\n" + 
				"(:init\n" + 
				"	(free Mary)\n" + 
				"	(= (total-credits-subject-gain " + properties.getStudent().getStudentName() +  ") "  + properties.getStudent().getAcumulatedCredits() +  ")\n" + 
				"	(= (available-credits " + properties.getStudent().getStudentName() + ") " + properties.getStudent().getAvailableCredits() + " )\n" + 
				"\n" + 
				getAvailableSubjects(properties.getStudent().getStudentName()) +
				"\n" + 
				getActivitiesNotDone(properties.getStudent().getStudentName()) +
				"\n" + 
				getSubthemeScores(properties.getStudent().getStudentName()) +
				"\n" + 
				getResourcesQuantity(properties.getStudent().getStudentName()) + 
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
				"	(= (maxgrade-subtheme Binary) 100)\n" + 
				"	(= (maxgrade-subtheme Red-Black) 100)\n" + 
				"	(= (maxgrade-subtheme Quicksort) 100)\n" + 
				"	(= (maxgrade-subtheme TreeSort) 100)\n" + 
				"\n" + 
				")\n" + 
				"\n" + 
				"\n" + 
				"(:goal (and\n" + 
				"		(pass-degree DataStructuresAlgs Mary)\n" + 
				"       )\n" + 
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
			
			availableSubjects.append("	(available-subject " + subject.getSubjectName() + " " + studentName + ")\n" + 
									"	(= (credits-subject " + subject.getSubjectName() + ") " + subject.getCredits() + ")\n" +
									"	(= (mingrade " + subject.getSubjectName() + ") " + subject.getMinGrade() + ")\n" +
									"	(not-approved " + subject.getSubjectName() + " " + studentName + ")\n" + 
									"	\n");
		});
		
		return availableSubjects.toString();
	}

	private String getActivitiesNotDone(String studentName) {
		StringBuilder activitiesNotDone = new StringBuilder();
		properties.getActivites().forEach(activity -> {
			if(!activity.isActivityDone()) {
				activitiesNotDone.append("	(not-done-LA " + activity.getActivityName() + " "
						+ activity.getParentSubtheme().getParentTheme().getThemeName() + " " + studentName + ")\n");
			}
		});

		return activitiesNotDone.toString();
	}
	
	private String getSubthemeScores(String studentName) {
		StringBuilder subthemeScores = new StringBuilder();
		properties.getSubthemes().forEach(subtheme -> {
			subthemeScores.append("	(= (score " + subtheme.getSubthemeName() + " " + studentName + ") "
					+ subtheme.getStudentScore() + ")\n");
		});

		return subthemeScores.toString();
	}
	
	private String getResourcesQuantity(String studentName) {
		StringBuilder subthemeScores = new StringBuilder();
		properties.getResources().forEach(resource -> {
			subthemeScores.append("	(= (quantity-resource " + resource.getResourceName() + ") "
					+ resource.getResourceQuantity() + ")\n");
		});

		return subthemeScores.toString();
	}
	
	private String getActivitiesValue() {
		StringBuilder activitiesValues = new StringBuilder();
		properties.getActivites().forEach(activity -> {
			activitiesValues
					.append("	(= (valueLA " + activity.getActivityName() + ") " + activity.getValue() + ")\n");
		});

		return activitiesValues.toString();
	}
	
	private String getActivitiesDurations() {
		StringBuilder activitiesDurations = new StringBuilder();
		properties.getActivites().forEach(activity -> {
			activitiesDurations
					.append("	(= (DurationLA " + activity.getActivityName() + ") " + activity.getDuration() + ")\n");
		});

		return activitiesDurations.toString();
	}
	
	private String getActivitiesParentSubtheme() {
		StringBuilder activitiesParentSubtheme = new StringBuilder();
		properties.getActivites().forEach(activity -> {
			activitiesParentSubtheme.append("	(isPartOfSubtheme " + activity.getActivityName() + " "
					+ activity.getParentSubtheme().getSubthemeName() + ")\n");
		});

		return activitiesParentSubtheme.toString();
	}
	
	private String getSubthemesParentTheme() {
		StringBuilder subthemesParentTheme = new StringBuilder();
		properties.getSubthemes().forEach(subtheme -> {
			subthemesParentTheme.append("	(isPartOfTheme " + subtheme.getParentTheme().getThemeName() + " " + subtheme.getSubthemeName() + ")\n");
		});

		return subthemesParentTheme.toString();
	}
	
	private String getThemesParentSubject() {
		StringBuilder themesParentSubject = new StringBuilder();
		properties.getThemes().forEach(theme -> {
			themesParentSubject.append("	(isPartOfSubject " + theme.getThemeName() + " "
					+ theme.getParentSubject().getSubjectName() + ")\n");
		});

		return themesParentSubject.toString();
	}
	
	private String getActivitiesResources() {
		StringBuilder activitiesResources = new StringBuilder();
		properties.getActivites().forEach(activity -> {
			activity.getResources().forEach(resource -> {
				activitiesResources.append("	(KindResourceLO " + activity.getActivityName() + " " + resource.getResourceName() + ")\n");
			});
		});

		return activitiesResources.toString();
	}
	
	private String getListOfActivitiesWithReqsAndNoReqs() {
		
		StringBuilder activitiesWithReqs = new StringBuilder();
		StringBuilder activitiesWithNoReqs = new StringBuilder();

		properties.getActivites().forEach(activity -> {
			if(activity.getDependencies().equals(null) || activity.getDependencies().isEmpty()) {
				activitiesWithReqs.append("	(not-has-reqs " + activity.getActivityName() + ")\n");
			} 
			else {
				activitiesWithNoReqs.append("	(has-reqs "+ activity.getActivityName() +" Binary)\n");
			}
		});

		return activitiesWithReqs.append(activitiesWithNoReqs).toString();
	}
}