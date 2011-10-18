package de.dhbw.stargreg.code;

import java.util.HashMap;

//Hey, wichtig wäre noch eine einheitlich Gesamt-Terminologie. Mitarbeiter heißen Personal, weil es Roboter
//sind. Außerdem sind wirklich sprechende Namen wichtig für die anderen, die dran programmieren. [Fredrik]

/**
 * 
 * @author Britta
 *
 */
public class PersonalAbteilung extends Abteilung {

	private int anzahlPersonal = 0;
	private double laufendeKosten = 0.0;
	
	private final HashMap<PersonalTyp, Integer> personal = new HashMap<PersonalTyp, Integer>();	

	/**
	 * Erzeugt eine Personalabteilung. Eine Anfangsmenge Personal ist war angedacht, sollte aber nicht über
	 * den Konstruktor eingepflegt werden, da dieser Parameter dann über Unternehmen() weitergereicht werden
	 * müsste. Stattdessen wird nach Erzeugung eines Unternehmens die Mitarbeiter-Menge per {@code einstellen()}
	 * erweitert.
	 */
	public PersonalAbteilung(Unternehmen unternehmen) {
		super(unternehmen);
	}//Konstruktor


	/**
	 * Schult Mitarbeiter, d.h. entnimmt Mitarbieter aus der aktuellen Kategorie und f�gt sie in die h�here Kategorie ein. Die Personalkosten werden neu berrechnet.
	 * @param von aktuelle Kategorie
	 * @param nach n�chste h�here Kategorie
	 * @param anzahl Anzahl der zu schulenden Mitarbeiter
	 */
	public void schulen (PersonalTyp von, PersonalTyp nach, int anzahl){
		//Was ist mit den Schulungskosten?!
		int anzahlVonAlt = personal.get(von);
		int anzahlNachAlt = personal.get(nach);
		anzahlNachAlt = anzahlNachAlt + anzahl;
		anzahlVonAlt = anzahlVonAlt - anzahl;
		
		personal.put(von, anzahlVonAlt);
		personal.put(nach, anzahlNachAlt);
		//Personalkosten neu berechnen
		laufendeKosten -= von.getLaufendeKosten()* anzahl;
		laufendeKosten += nach.getLaufendeKosten()* anzahl;
	}//erwerbenMit
	
	/**
	 * Entlässt Mitarbeiter (Name angepasst an Gesamt-Terminologie)
	 * @param personalTyp PersonalTyp
	 * @param anzahl Anzahl der Mitarbeiter
	 */
	public void entlassen (PersonalTyp personalTyp, int anzahl){
		int anzahlMit_p = personal.get(personalTyp);
		personal.put(personalTyp, (anzahlMit_p - anzahl));
		anzahlPersonal -= anzahl;
		laufendeKosten -= personalTyp.getLaufendeKosten()* anzahl;
	}//wegnehmenMit
	
	//nicht auf einmal Parameter umdrehen!
	public void einstellen (PersonalTyp personalTyp, int anzahl){
		//Werbungskosten?!
		int anzahlAlt = personal.get(personalTyp);
		personal.put (personalTyp, (anzahlAlt + anzahl));
		anzahlPersonal += anzahl;
		laufendeKosten += personalTyp.getLaufendeKosten()* anzahl;
	}//schulenMit
	
	public int getAnzahlPersonal (){
		return anzahlPersonal;
	}//getMitAnzahl
	
	public int getAnzahl(PersonalTyp personalTyp){
		return personal.get(personalTyp);
	}//getTypMitAnzahl
	
	public double getLaufendeKosten (){
		return this.laufendeKosten;
	}//getPersonalkosten
	
	public double getLaufendeKosten (PersonalTyp personalTyp) {
		return personalTyp.getLaufendeKosten()*personal.get(personalTyp);
	}//getTypPerosnalkosten
	
	public double getDurchschnittlicheQualitaet() {
		double qualitaet = 0;
		for (PersonalTyp personalTyp : personal.keySet()) {
			qualitaet += personalTyp.getQualitaet() * personal.get(personalTyp);
		}
		qualitaet = qualitaet / getAnzahlPersonal();
		return qualitaet;
	}
	
	public void simuliere() {
		// TODO Auto-generated method stub
		
	}
}
