package com.fime.pddl;

public class ConstantDomainPDDLFunctions {
	
	public String getPredicates() {
		return " (:predicates \n" + 
				"	(available-subject ?subj - subject ?s - student) \n" + 
				"	(free ?s - student) \n" + 
				"	(pass-degree ?subj - subject ?s - student) \n" + 
				"	(enrollment ?s - student ?subj - subject) \n" + 
				"	(done-Theme ?t - Theme ?subj -subject ?s - student)\n" + 
				"	(done-subject-LA ?subj - subject ?s - student)\n" + 
				"	(not-done-LA ?oa - LA ?subj - subject ?s - student)\n" + 
				"	(not-approved ?subj - subject ?s - student)\n" + 
				"	(isPartOfSubtheme ?oa - LA ?subt - subtheme)\n" + 
				"	(isPartOfTheme ?subt - subtheme ?t - Theme)\n" + 
				"	(isPartOfSubject ?t - Theme ?subj - subject)\n" + 
				"	(KindResourceLO ?oa - LA ?eq - resource)\n" + 
				"	(not-has-reqs ?oa - LA)\n" + 
				"	(has-reqs ?oa - LA ?req - LO)\n" + 
				"	(has-multiple-reqs ?oa - LA ?req - LO)\n" + 
				"	(done ?oa - LA)\n" + 
				"\n" + 
				" )\n\n";
	}
	
	public String getFunctions() {
		return " (:functions\n" + 
				"	(credits-subject ?subj - subject)\n" + 
				"	(total-credits-subject-gain ?s - student)\n" + 
				"	(available-credits ?s - student)\n" + 
				"	(score ?req - LO ?s - student)\n" + 
				"	(quantity-resource ?eq - resource)\n" + 
				"	(valueLA ?oa - LA)\n" + 
				"	(mingrade ?subj - subject)\n" + 
				"	(DurationLA ?oa - LA)\n" + 
				"	(maxgrade-subtheme ?subt - subtheme)\n" + 
				"	(amount-in-subtheme ?oa - LA)\n" + 
				"\n" + 
				" )\n\n";
	}
	
	public String getLAWithNoReqs(){
		return " (:durative-action CHOOSE-LA-nothasreqs\n" + 
				"			:parameters (?s - student ?oa - LA ?subt - subtheme ?t - Theme ?subj - subject ?eq - resource)\n" + 
				"			:duration (= ?duration (DurationLA ?oa))\n" + 
				"			:condition (and\n" + 
				"			                (at start (free ?s))\n" + 
				"			                (at start (enrollment ?s ?subj))\n" + 
				"			                (at start (not-done-LA ?oa ?subj ?s))\n" + 
				"			                (at start (isPartOfSubtheme ?oa ?subt))\n" + 
				"			                (at start (isPartOfTheme ?subt ?t))\n" + 
				"			                (at start (isPartOfSubject ?t ?subj))\n" + 
				"			                (at start (KindResourceLO ?oa ?eq))\n" + 
				"			                (at start (> (quantity-resource ?eq) 0))\n" + 
				"			                (at start (not-has-reqs ?oa))\n" + 
				"			                (at start (> (maxgrade-subtheme ?subt)(valueLA ?oa)))\n" + 
				"			                )\n" + 
				"			:effect (and\n" + 
				"			                (at start (not(free ?s)))\n" + 
				"			                (at start (decrease (quantity-resource ?eq) 1))\n" + 
				"			                (at end (increase (quantity-resource ?eq) 1))\n" + 
				"			                (at end (not (not-done-LA ?oa ?subj ?s)))\n" + 
				"			                (at end (increase (score ?subt ?s) (valueLA ?oa)))\n" + 
				"			                (at end (free ?s))\n" + 
				"			                (at end (decrease (maxgrade-subtheme ?subt)(valueLA ?oa)))\n" + 
				"			                (at end (done ?oa))\n" + 
				"			        )\n" + 
				"			)\n\n" ;
	}
	
	public String getLAWitReqSubthemes(){
		
		return " (:durative-action CHOOSE-LA-hasreqsSubtheme\n" + 
				"			:parameters (?s - student ?oa - LA ?subt - subtheme ?t - Theme ?subj - subject ?eq - resource ?req - LO)\n" + 
				"			:duration (= ?duration (DurationLA ?oa))\n" + 
				"			:condition (and\n" + 
				"			                (at start (free ?s))\n" + 
				"			                (at start (enrollment ?s ?subj))\n" + 
				"			                (at start (not-done-LA ?oa ?subj ?s))\n" + 
				"			                (at start (isPartOfSubtheme ?oa ?subt))\n" + 
				"			                (at start (isPartOfTheme ?subt ?t))\n" + 
				"			                (at start (isPartOfSubject ?t ?subj))\n" + 
				"			                (at start (KindResourceLO ?oa ?eq))\n" + 
				"			                (at start (> (quantity-resource ?eq) 0))\n" + 
				"			                (at start (has-reqs ?oa ?req))\n" + 
				"			                (at start (> (score ?req ?s) (amount-in-subtheme ?oa)))\n" + 
				"			                (at start (> (maxgrade-subtheme ?subt)(valueLA ?oa)))\n" + 
				"			                )\n" + 
				"			:effect (and\n" + 
				"			                (at start (not(free ?s)))\n" + 
				"			                (at start (decrease (quantity-resource ?eq) 1))\n" + 
				"			                (at end (increase (quantity-resource ?eq) 1))\n" + 
				"			                (at end (not (not-done-LA ?oa ?subj ?s)))\n" + 
				"			                (at end (increase (score ?subt ?s) (valueLA ?oa)))\n" + 
				"			                (at end (free ?s))\n" + 
				"			                (at end (decrease (maxgrade-subtheme ?subt)(valueLA ?oa)))\n" + 
				"			                (at end (done ?oa))\n" + 
				"			        )\n" + 
				"			)\n\n";
	}
	
	public String getLAWitReqLA(){
		
		return " (:durative-action CHOOSE-LA-hasreqsLA\n" + 
				"			:parameters (?s - student ?oa - LA ?subt - subtheme ?t - Theme ?subj - subject ?eq - resource ?req - LA)\n" + 
				"			:duration (= ?duration (DurationLA ?oa))\n" + 
				"			:condition (and\n" + 
				"			                (at start (free ?s))\n" + 
				"			                (at start (enrollment ?s ?subj))\n" + 
				"			                (at start (not-done-LA ?oa ?subj ?s))\n" + 
				"			                (at start (isPartOfSubtheme ?oa ?subt))\n" + 
				"			                (at start (isPartOfTheme ?subt ?t))\n" + 
				"			                (at start (isPartOfSubject ?t ?subj))\n" + 
				"			                (at start (KindResourceLO ?oa ?eq))\n" + 
				"			                (at start (> (quantity-resource ?eq) 0))\n" + 
				"			                (at start (has-reqs ?oa ?req))\n" + 
				"			                (at start (done ?req))\n" + 
				"			                (at start (> (maxgrade-subtheme ?subt)(valueLA ?oa)))\n" + 
				"			                )\n" + 
				"			:effect	(and\n" + 
				"			                (at start (not(free ?s)))\n" + 
				"			                (at start (decrease (quantity-resource ?eq) 1))\n" + 
				"			                (at end (increase (quantity-resource ?eq) 1))\n" + 
				"			                (at end (not (not-done-LA ?oa ?subj ?s)))\n" + 
				"			                (at end (increase (score ?subt ?s) (valueLA ?oa)))\n" + 
				"			                (at end (free ?s))\n" + 
				"			                (at end (decrease (maxgrade-subtheme ?subt)(valueLA ?oa)))\n" + 
				"			                (at end (done ?oa))\n" + 
				"			        )\n" + 
				"			)\n\n";
	}

	public String getALWithMultipleLAReq(){
	
		return " (:durative-action CHOOSE-LA-hasreqs-multipleLA2\n" + 
				"			:parameters (?s - student ?oa - LA ?subt - subtheme ?t - Theme ?subj - subject ?eq - resource ?req1 - LA ?req2 - LA)\n" + 
				"			:duration (= ?duration (DurationLA ?oa))\n" + 
				"			:condition (and\n" + 
				"			                (at start (not(= ?req1 ?req2)))\n" + 
				"			                (at start (free ?s))\n" + 
				"			                (at start (enrollment ?s ?subj))\n" + 
				"			                (at start (not-done-LA ?oa ?subj ?s))\n" + 
				"			                (at start (isPartOfSubtheme ?oa ?subt))\n" + 
				"			                (at start (isPartOfTheme ?subt ?t))\n" + 
				"			                (at start (isPartOfSubject ?t ?subj))\n" + 
				"			                (at start (KindResourceLO ?oa ?eq))\n" + 
				"			                (at start (> (quantity-resource ?eq) 0))\n" + 
				"			                (at start (done ?req1))\n" + 
				"			                (at start (done ?req2))\n" + 
				"			                (at start (has-multiple-reqs ?oa ?req1))\n" + 
				"			                (at start (has-multiple-reqs ?oa ?req2))\n" + 
				"			                (at start (> (maxgrade-subtheme ?subt)(valueLA ?oa)))\n" + 
				"			                )\n" + 
				"			:effect	(and\n" + 
				"			                (at start (not(free ?s)))\n" + 
				"			                (at start (decrease (quantity-resource ?eq) 1))\n" + 
				"			                (at end (increase (quantity-resource ?eq) 1))\n" + 
				"			                (at end (not (not-done-LA ?oa ?subj ?s)))\n" + 
				"			                (at end (increase (score ?subt ?s) (valueLA ?oa)))\n" + 
				"			                (at end (free ?s))\n" + 
				"			                (at end (decrease (maxgrade-subtheme ?subt)(valueLA ?oa)))\n" + 
				"			                (at end (done ?oa))\n" + 
				"			                )\n" + 
				"			)\n\n";
			
	}	
	
	public String getSubjectPass() {
		return " 	(:durative-action take-subject-pass\r\n" + 
				"				:parameters (?s - student ?subj - subject)\r\n" + 
				"				:duration (= ?duration 1)\r\n" + 
				"				:condition 	(and\r\n" + 
				"							(at start (enrollment ?s ?subj))\r\n" + 
				"							(at start (done-subject-LA ?subj ?s))\r\n" + 
				"							)\r\n" + 
				"\r\n" + 
				"				:effect (and\r\n" + 
				"						(at end (not (not-approved ?subj ?s)))\r\n" + 
				"						(at end (increase (total-credits-subject-gain ?s) (credits-subject ?subj)))\r\n" + 
				"						(at end (not (available-subject ?subj ?s)))\r\n" + 
				"						(at end (pass-degree ?subj ?s))\r\n" + 
				"						)\r\n" + 
				"	)		\r\n" + 
				")\r\n" + 
				";;END";
	}
}