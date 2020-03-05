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
		
		subjects = new ArrayList<>();
		Subject subject1 =  new Subject("Subject1");
		subjects.add(subject1);
		
		List<Object> dependencies = new ArrayList<Object>();
		dependencies.add(new Theme("Theme1"));
		dependencies.add(new Resource("Resource1"));
		subject1.setDependencies(dependencies);
		
		
		LearningPattern learningPattern = new LearningPattern();
		learningPattern.generateLearningPatternPDDL(student, subjects, themes, subthemes, activites, resources);
		
	}

}
