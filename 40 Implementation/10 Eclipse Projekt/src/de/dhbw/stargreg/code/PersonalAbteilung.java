package de.dhbw.stargreg.code;

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
	private double personalkosten = 0.0;
	/*
	public static PersonalAbteilung einrichten () {
		return new PersonalAbteilung();
	}//einrichten
	
	private PersonalAbteilung () {
	}//Konstruktor
	*/
	public double erwerbenMit (PersonalTyp kategorie, int anzahlMit ){
		double erwerbKosten = 0.0;
		return erwerbKosten;
	}//erwerbenMit
	
	public void wegnehmenMit (int anzahlMit){
		
	}//wegnehmenMit
	
	public void schulenMit (int anzahlMit, PersonalTyp kategorie){
		
	}//schulenMit
	
	public int getMitAnzahl (){
		return summeMit;
	}//getMitAnzahl

<<<<<<< HEAD

	
	public double getPersonalkosten (){
		return this.personalkosten;
	}//getPersonalkosten
}//PersonalAbteilung
=======
	public void simuliere() {
		// TODO Auto-generated method stub
		
	}

}
>>>>>>> branch 'master' of git@github.com:ch3ck3r/Fallstudie.git
