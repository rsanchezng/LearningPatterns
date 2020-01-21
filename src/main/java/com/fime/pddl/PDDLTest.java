package com.fime.pddl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PDDLTest {

	private static final String DOMAIN = "Domain_";
	private static final String PROBLEM = "Problem_";
	private static final String PDDL_EXTENSION = ".pddl";

	public static void main(String[] args) throws IOException {
		
		Resource dataStructuresResource = new Resource("Rec1");
		List<Resource> resources = new ArrayList<>();
		resources.add(dataStructuresResource);
		Activity dataStructuresActivities = new Activity("1" ,"Act1", resources);
		List<Activity> activities = new ArrayList<>();
		activities.add(dataStructuresActivities);
		Subtheme dataStructuresSubthemes = new Subtheme("Subtheme1", activities);
		List<Subtheme> subthemes = new ArrayList<>();
		subthemes.add(dataStructuresSubthemes);
		Theme dataStructuresThemes = new Theme("Theme1", subthemes);
		List<Theme> themes = new ArrayList<>();
		themes.add(dataStructuresThemes);
		Subject dataStructures = new Subject("DataStructuresAlgs", themes);
		
		Resource dataStructuresResource2 = new Resource("Rec2");
		List<Resource> resources2 = new ArrayList<>();
		resources2.add(dataStructuresResource2);
		Activity dataStructuresActivities2 = new Activity("2" ,"Act2", resources2);
		List<Activity> activities2 = new ArrayList<>();
		activities2.add(dataStructuresActivities2);
		Subtheme dataStructuresSubthemes2 = new Subtheme("Subtheme2", activities2);
		List<Subtheme> subthemes2 = new ArrayList<>();
		subthemes2.add(dataStructuresSubthemes2);
		Theme dataStructuresThemes2 = new Theme("Theme2", subthemes2);
		List<Theme> themes2 = new ArrayList<>();
		themes2.add(dataStructuresThemes2);
		Subject dataStructures2 = new Subject("DataStructuresAlgs", themes2);
		
		Resource dataStructuresResource3 = new Resource("Rec3");
		List<Resource> resources3 = new ArrayList<>();
		resources3.add(dataStructuresResource3);
		Activity dataStructuresActivities3 = new Activity("3" ,"Act3", resources3);
		List<Activity> activities3 = new ArrayList<>();
		activities3.add(dataStructuresActivities3);
		Subtheme dataStructuresSubthemes3 = new Subtheme("Subtheme3", activities3);
		List<Subtheme> subthemes3 = new ArrayList<>();
		subthemes3.add(dataStructuresSubthemes3);
		Theme dataStructuresThemes3 = new Theme("Theme3", subthemes3);
		List<Theme> themes3 = new ArrayList<>();
		themes3.add(dataStructuresThemes3);
		Subject dataStructures3 = new Subject("DataStructuresAlgs", themes3);
		
		List<Subject> subjects = new ArrayList<>();
		subjects.add(dataStructures);
		subjects.add(dataStructures2);
		subjects.add(dataStructures3);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
		Date date = new Date();
		String formattedDate = formatter.format(date).toString();

		try {
			PDDLDomain domain = new PDDLDomain(subjects);
			Files.write(Paths.get(DOMAIN + formattedDate + PDDL_EXTENSION), domain.generatePDDL());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			PDDLProblem problem = new PDDLProblem();
			Files.write(Paths.get(PROBLEM + formattedDate + PDDL_EXTENSION), problem.getPDDLProblem());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
