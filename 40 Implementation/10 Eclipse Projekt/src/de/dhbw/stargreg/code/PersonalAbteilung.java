package de.dhbw.stargreg.code;

import java.util.HashMap;

/**
 * Hier werden die Mitarbeiter der drei Qualitätsstufen über den gemeinsamen Obertyp 'Personaltyp' 
 * verwaltet. Das Personal kann erworben bzw. eingestellt, entlassen und geschult werden.  
 * @author Britta
 *
 */
public class PersonalAbteilung extends Abteilung {

	private int anzahlPersonal = 0;
	private double laufendeKosten = 0.0;
		
	private final HashMap<PersonalTyp, Integer> personal = new HashMap<PersonalTyp, Integer>();	

	/**
	 * Erzeugt eine Personalabteilung. Eine Anfangsmenge Personal ist war angedacht, sollte aber nicht Ã¼ber
	 * den Konstruktor eingepflegt werden, da dieser Parameter dann Ã¼ber Unternehmen() weitergereicht werden
	 * mÃ¼sste. Stattdessen wird nach Erzeugung eines Unternehmens die Mitarbeiter-Menge per {@code einstellen()}
	 * erweitert.
	 */
	public PersonalAbteilung(Unternehmen unternehmen) {
		super(unternehmen);
	}//Konstruktor
	
	/**
	 * Erwirbt und stellt eine Anzahl an Mitarbeitern in einer bestimmten Qualitätsstufe ein. 
	 * Die Anzahl aller Mitarbeiter und die neuen laufendeKosten werden gespeichert; die 
	 * Werbungskosten werden abgebucht.
	 * @param p Qualitätsstufe der einzustellenden Mitarbeiter
	 * @param anzahlPers Anzahl der einzustellenden Mitarbeiter 
	 */
	public void erwerbenMit (PersonalTyp p, int anzahlPers, FinanzAbteilung konto){
		double neuePersKosten = laufendeKosten += (p.getLaufendeKosten()+ p.getWerbungsKosten() )*anzahlPers;
		if (konto.getKontostand() < neuePersKosten ){
			// Kapital zu gering!
			return;
		}
		int anzahlPers_p = personal.get(p);
		personal.put (p, (anzahlPers_p + anzahlPers));
		konto.abbuchen(p.getWerbungsKosten()*anzahlPers);

		laufendeKosten += p.getLaufendeKosten() * anzahlPers;
		anzahlPersonal += anzahlPers;
		
		
	}//erwerbenMit
	
	/**
	 * Entlässt eine Anzahl an Mitarbeitern in einer bestimmten Qualitätsstufe. Die Änderungen an
	 * der Gesamtmitarbeiteranzahl und den laufendeKosten werden gespeichert.
	 * @param p Qualitätsstufe der zu entlassenen Mitarbeiter
	 * @param anzahlPers Anzahl der zu entlassenen Mitarbeiter
	 */
	public void entlassenMit (PersonalTyp p, int anzahlPers){
		int anzahlPers_p = personal.get(p);
		personal.put(p, (anzahlPers_p - anzahlPers));
		anzahlPersonal -= anzahlPers;
		laufendeKosten -= p.getLaufendeKosten()* anzahlPers;
	}//entlassenMit

	/**
<<<<<<< HEAD
	 * Schult Mitarbeiter, d.h. eine Anzahl an Mitarbietern werden aus ihrer aktuellen Qualitätsstufe
	 * entnommen und der nächsten Stufe hinzugefügt. Dabei kann die Qualität immer nur um eine Stufe erhöht
	 * werden. Die Änderungen der laufendeKosten durch die neuen laufenden Kostenwerden gespeichert;
	 * die Schulungskosten werdne abgebucht.
	 * @param p Ist-Qualitätsstufe
	 * @param q Soll-Qualitätsstufe 
	 * @param anzahlPers Anzahl der zu schulenden Mitarbeiter
	 */
	public void schulenMit (PersonalTyp p, PersonalTyp q, int anzahlPers, FinanzAbteilung konto ){ 
		// ToDo: Parameter q wegnehmen; "schulbar-Prüfung" ergännzen
		double neuePersKosten = (q.getLaufendeKosten()-p.getLaufendeKosten() )* anzahlPers;
		if (konto.getKontostand() < (p.getSchulungsKosten() + neuePersKosten) ) {
			// Kapital zu gering!
			return;
		}
		
		int anzahlPers_p = personal.get(p);
		int anzahlPers_q = personal.get(q);
		anzahlPers_q = anzahlPers_q + anzahlPers;
		anzahlPers_p = anzahlPers_p - anzahlPers;
		
		personal.put(p, anzahlPers_p);
		personal.put(q, anzahlPers_q);
		konto.abbuchen(p.getSchulungsKosten()*anzahlPers);
		//laufendeKosten neu berechnen
		laufendeKosten += neuePersKosten;
		
		/**
		 * Schult Mitarbeiter, d.h. entnimmt Mitarbieter aus der aktuellen Kategorie und fuegt
		 * sie in die nächste hoehere Qualitätsstufe ein. Eine Schulung kann immer nur um eine 
		 * Qualitätsstufe erhöhen. Die laufendeKosten werden neu berrechnet.
		 * @param von Ist-Qualitätsstufe
		 * @param nach Soll-Qualitätsstufe
		 * @param anzahl Anzahl des zu schulenden Personals
		 */
	public void schulen (PersonalTyp von, PersonalTyp nach, int anzahl){
		//Was ist mit den Schulungskosten?!
		int anzahlVonAlt = personal.get(von);
		int anzahlNachAlt = personal.get(nach);
		anzahlNachAlt = anzahlNachAlt + anzahl;
		anzahlVonAlt = anzahlVonAlt - anzahl;
		
		personal.put(von, anzahlVonAlt);
		personal.put(nach, anzahlNachAlt);
		//laufendeKosten neu berechnen
		laufendeKosten -= von.getLaufendeKosten()* anzahl;
		laufendeKosten += nach.getLaufendeKosten()* anzahl;
	}//erwerbenMit
	
	/**
	 * EntlÃ¤sst Mitarbeiter (Name angepasst an Gesamt-Terminologie)
	 * @param personalTyp PersonalTyp
	 * @param anzahl Anzahl der Mitarbeiter
	 */
	public void entlassen (PersonalTyp personalTyp, int anzahl){
		int anzahlPers_p = personal.get(personalTyp);
		personal.put(personalTyp, (anzahlPers_p - anzahl));
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
	
	public int getAnzahlPersonalTyp (PersonalTyp personalTyp){
		return personal.get(personalTyp);
	}//getTypMitAnzahl
	
	public double getLaufendeKosten (){
		return this.laufendeKosten;
	}//getlaufendeKosten
	
	public double getLaufendeKostenPersonalTyp (PersonalTyp personalTyp) {
		return personalTyp.getLaufendeKosten()*personal.get(personalTyp);
	}//getTypPerosnalkosten
	
	public void simuliere() {
		// TODO Auto-generated method stub
	
	}
}
