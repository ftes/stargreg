package de.dhbw.stargreg.code;

import java.util.HashMap;

/**
 * 
 * @author fredrik
 *
 */
public class PersonalAbteilung extends Abteilung {
	private double kosten_r2d2             = 50.0;
	private double kosten_kampfdroide      = 100.0;
	private double kosten_droideka         = 200.0;
	
	private int summeMit = 0;
	private double personalkosten = 0.0;
	
	private HashMap<PersonalTyp, Integer> allePersonaltypen = new HashMap<PersonalTyp, Integer>();	

	public void erwerbenMit (PersonalTyp p, PersonalTyp q, int anzahlMit ){
		int anzahlMit_p = 0;
		int anzahlMit_q = 0;
				
		anzahlMit_q = anzahlMit_q + anzahlMit;
		anzahlMit_p = anzahlMit_p - anzahlMit;
		
		allePersonaltypen.put(p, anzahlMit_p);
		allePersonaltypen.put(q, anzahlMit_q);
	}//erwerbenMit
	
	public void wegnehmenMit (int anzahlMit){
		
	}//wegnehmenMit
	
	public void schulenMit (int anzahlMit, PersonalTyp kategorie){
		
	}//schulenMit
	
	public int getMitAnzahl (){
		return summeMit;
	}//getMitAnzahl
	
	public double getPersonalkosten (){
		return this.personalkosten;
	}//getPersonalkosten
	
	public void simuliere() {
		// TODO Auto-generated method stub
		
	}
}
