package com.fime.pddl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PDDLTest {

	public static void main(String[] args) throws IOException {

		Student student = new Student();
		student.setStudentName("Adrian");
		
		List<Subject> subjects = null;
		List<Theme> themes = null;
		List<Subtheme> subthemes = null;
		List<Activity> activites = null;
		List<Resource> resources = null;
		
		LearningPattern learningPattern = new LearningPattern();
		learningPattern.generateLearningPatternPDDL(student, subjects, themes, subthemes, activites, resources);
		
	}

}
