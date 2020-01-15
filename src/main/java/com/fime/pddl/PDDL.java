package com.fime.pddl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PDDL {

	private static final String DOMAIN = "Domain_";
	private static final String PROBLEM = "Problem_";
	private static final String PDDL_EXTENSION = ".pddl";

	public static void main(String[] args) throws IOException {
		
		Subject dataStructures = new Subject(null, null);
		dataStructures.setSubjectName("DataStructuresAlgs");
		
		List<Subject> subjects = new ArrayList<>();
		subjects.add(dataStructures);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
		Date date = new Date();
		String formattedDate = formatter.format(date).toString();

		try {
			PDDLDomain domain = new PDDLDomain(subjects);
			Files.write(Paths.get(DOMAIN + formattedDate + PDDL_EXTENSION), domain.getPDDLDomain());
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
