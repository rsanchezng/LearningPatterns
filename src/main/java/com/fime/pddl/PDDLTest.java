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

		Subject subject1 =  new Subject("Subject1", false, 0, 70);
		Subject subject2 =  new Subject("Subject2", false, 0, 70);
		Subject subject3 =  new Subject("Subject3", false, 0, 70);
		subjects.add(subject1);
		subjects.add(subject2);
		subjects.add(subject3);

		Theme theme1 = new Theme("Theme1", subject1, false);
		themes.add(theme1);

        Subtheme subtheme1 = new Subtheme("Theme1", theme1, false, 0, 50);
        subthemes.add(subtheme1);
        
        Resource res1 = new Resource();
        res1.setResourceName("Resource1");
        resources.add(res1);

        Activity activity1 = new Activity("Activity1", "Activity1", subtheme1, null, false, 15, 50, 0, resources);
        activites.add(activity1);

		LearningPattern learningPattern = new LearningPattern();
		learningPattern.generateLearningPatternPDDL(student, subjects, themes, subthemes, activites, resources);

	}

}
