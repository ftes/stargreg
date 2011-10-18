package de.dhbw.stargreg.code;

import java.util.HashMap;

/**
 * Hier werden die Personal der drei Qualit�tsstufen �ber den gemeinsamen Obertyp 'Personaltyp' 
 * verwaltet. Das Personal kann erworben bzw. eingestellt, entlassen und geschult werden.  
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
	 * müsste. Stattdessen wird nach Erzeugung eines Unternehmens die Personal-Menge per {@code einstellen()}
	 * erweitert.
	 */
	public PersonalAbteilung(Unternehmen unternehmen) {
		super(unternehmen);
	}//Konstruktor
	

	/**
	 * Schult Personal, d.h. eine Anzahl an Personal wird aus ihrer aktuellen Qualit�tsstufe
	 * entnommen und der n�chsten Stufe hinzugef�gt. Dabei kann die Qualit�t immer nur um eine 
	 * Stufe erh�ht werden. Die �nderungen der laufenden Kosten durch die neuen Kosten werden 
	 * gespeichert; Schulungskosten werden abgebucht.
	 * @param von Ist-Qualit�tsstufe
	 * @param nach Soll-Qualit�tsstufe 
	 * @param anzahlPers Anzahl der zu schulenden Personal
	 */
	public boolean schulen (PersonalTyp von, int anzahl) {
		if (personal.get(von) < anzahl) {
			System.err.printf("Weniger als %d %s zum schulen vorhanden\n", anzahl, von);
			return false;
		}
		
		PersonalTyp nach = von.getNaechsterPersonalTyp();
		Schulung schulung = new Schulung(von, unternehmen, anzahl, von.getSchulungsKosten());
		if (! unternehmen.getFinanzen().abbuchen(schulung.getKosten())) {
			System.err.printf("Nicht genug Geld, um %d %s zu schulen\n", anzahl, von);
			return false;
		}
		
		int anzahlVon = personal.get(von) - anzahl;
		int anzahlNach = personal.get(nach) + anzahl;
		personal.put(von, anzahlVon);
		personal.put(nach, anzahlNach);
		//Personalkosten neu berechnen
		laufendeKosten -= von.getLaufendeKosten() * anzahl;
		laufendeKosten += nach.getLaufendeKosten() * anzahl;
		
		Spiel.INSTANCE.getPersonalMarkt().fuegeTransaktionHinzu(schulung);
		return true;
	}
	
	/**
	 * Entlässt Mitarbeiter (Name angepasst an Gesamt-Terminologie)
	 * @param personalTyp PersonalTyp
	 * @param anzahl Anzahl der Mitarbeiter
	 */
	public boolean entlassen (PersonalTyp personalTyp, int anzahl){
		int vorhanden = personal.get(personalTyp);
		
		if (vorhanden < anzahl) {
			System.err.printf("Weniger als %d %s zum entlassen vorhanden\n", anzahl, personalTyp);
			return false;
		}
		
		personal.put(personalTyp, (vorhanden - anzahl));
		anzahlPersonal -= anzahl;
		laufendeKosten -= personalTyp.getLaufendeKosten()* anzahl;
		
		Entlassung entlassung = new Entlassung(personalTyp, unternehmen, anzahl);
		Spiel.INSTANCE.getPersonalMarkt().fuegeTransaktionHinzu(entlassung);
		return true;
	}//wegnehmenMit
	

	//nicht auf einmal Parameter umdrehen!
	public boolean einstellen (PersonalTyp personalTyp, int anzahl){
		Einstellung einstellung = new Einstellung(personalTyp, unternehmen, anzahl, personalTyp.getWerbungsKosten());
		
		if (! unternehmen.getFinanzen().abbuchen(einstellung.getKosten())) {
			System.err.printf("Nicht genug Geld, um %d %s einzustellen\n", anzahl, personalTyp);
			return false;
		}
		
		int vorhanden = personal.get(personalTyp);
		personal.put (personalTyp, (vorhanden + anzahl));
		anzahlPersonal += anzahl;

		laufendeKosten += personalTyp.getLaufendeKosten()* anzahl;
		
		Spiel.INSTANCE.getPersonalMarkt().fuegeTransaktionHinzu(einstellung);
		return true;
	}//schulenMit
	
	public int getAnzahlPersonal (){
		return anzahlPersonal;
	}//getMitAnzahl
	
	public int getAnzahl(PersonalTyp personalTyp){
		return personal.get(personalTyp);
	}//getTypMitAnzahl
	
	public double getLaufendeKosten (){
		return this.laufendeKosten;
	}//getlaufendeKosten
	
	public double getLaufendeKosten (PersonalTyp personalTyp) {
		return personalTyp.getLaufendeKosten() * personal.get(personalTyp);
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
