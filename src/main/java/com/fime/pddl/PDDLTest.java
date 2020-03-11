package com.fime.pddl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDDLTest {

	public static void main(String[] args) throws IOException {

		Student student = new Student();
		student.setStudentName("Adrian");

		List<Subject> subjects  = new ArrayList<>();
		List<Theme> themes = new ArrayList<>();
		List<Subtheme> subthemes = new ArrayList<>();
		List<Activity> activites = new ArrayList<>();
		List<Resource> resources = new ArrayList<>();

		Subject subject1 =  new Subject();
		subjects.add(subject1);

		Theme theme1 = new Theme();
		theme1.setThemeName("Theme1");
		themes.add(theme1);

        Subtheme subtheme1 = new Subtheme();
        subtheme1.setSubthemeName("Theme1");
        subthemes.add(subtheme1);

        Activity activity1 = new Activity();
        activity1.setActivityName("Activity1");
        activites.add(activity1);

        Resource res1 = new Resource();
        res1.setResourceName("Resource1");
        resources.add(res1);

		LearningPattern learningPattern = new LearningPattern();
		learningPattern.generateLearningPatternPDDL(student, subjects, themes, subthemes, activites, resources);

	}

}
