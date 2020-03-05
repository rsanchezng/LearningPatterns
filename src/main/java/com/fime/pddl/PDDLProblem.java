package com.fime.pddl;

public class PDDLProblem {

	PDDLProperties properties;
	StringBuilder header;

	public PDDLProblem(PDDLProperties properties) {
		header = new StringBuilder();
		this.properties = properties;
	}
	
	public byte[] getPDDLProblem() {
		
		StringBuilder problemPDDL = new StringBuilder();
		
		problemPDDL.append("Problem");
		
		return problemPDDL.toString().getBytes();
	}

}
