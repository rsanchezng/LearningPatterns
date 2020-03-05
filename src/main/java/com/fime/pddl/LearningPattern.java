package com.fime.pddl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LearningPattern {
	
	List<Subject> subjects;
	private static final String DOMAIN = "Domain_";
	private static final String PROBLEM = "Problem_";
	private static final String PDDL_EXTENSION = ".pddl";
	
	public void generateLearningPatternPDDL(Student student, List<Subject> subjects, List<Theme> themes, List<Subtheme> subthemes,
			List<Activity> activites, List<Resource> resources) {
		
		PDDLProperties properties =  new PDDLProperties(student, subjects, themes, subthemes, activites, resources);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
		Date date = new Date();
		String formattedDate = formatter.format(date).toString();
		
		try {
			PDDLDomain domain = new PDDLDomain(properties);
			Files.write(Paths.get(DOMAIN + formattedDate + PDDL_EXTENSION), domain.generatePDDL());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			PDDLProblem problem = new PDDLProblem(properties);
			Files.write(Paths.get(PROBLEM + formattedDate + PDDL_EXTENSION), problem.getPDDLProblem());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
