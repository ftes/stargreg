package de.dhbw.stargreg.code;

import java.util.HashMap;

/**
 * 
 * @author fredrik
 *
 */
public class PersonalAbteilung extends Abteilung {
	private double kosten_Mit1 = 50.0;
	private double kosten_Mit2 = 100.0;
	private double kosten_Mit3 = 200.0;
	
	private int summeMit = 0;
	double werbKosten = 0.0;

	private double personalkosten = 0.0;
	
	private HashMap<PersonalTyp, Integer> allePersonaltypen = new HashMap<PersonalTyp, Integer>();	

	public double erwerbenMit (PersonalTyp p, int anzahlMit ){
		//Erwerbskosten berrechnen
		return werbKosten;
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
