package com.fime.pddl;
import java.io.IOException;
import java.util.ArrayList;
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
		Subject subject1 =  new Subject();
		subjects.add(subject1);

		List<Object> dependencies = new ArrayList<Object>();
		dependencies.add(new Theme());
		dependencies.add(new Resource());

		LearningPattern learningPattern = new LearningPattern();
		learningPattern.generateLearningPatternPDDL(student, subjects, themes, subthemes, activites, resources);

	}

}
