package de.dhbw.stargreg.code;

import java.util.HashMap;

/**
 * Hier werden die Personal der drei Qualitätsstufen über den gemeinsamen Obertyp 'Personaltyp' 
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
	 * mÃ¼sste. Stattdessen wird nach Erzeugung eines Unternehmens die Personal-Menge per {@code einstellen()}
	 * erweitert.
	 */
	public PersonalAbteilung(Unternehmen unternehmen) {
		super(unternehmen);
	}//Konstruktor
	
	/**
	 * Erwirbt und stellt eine Anzahl an Personaln in einer bestimmten Qualitätsstufe ein. 
	 * Die Anzahl aller Personal und die neuen laufendeKosten werden gespeichert; die 
	 * Werbungskosten werden abgebucht.
	 * @param p Qualitätsstufe der einzustellenden Personal
	 * @param anzahlPers Anzahl der einzustellenden Personal 
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
	 * Entlässt eine Anzahl an Personal in einer bestimmten Qualitätsstufe. Die Änderungen an
	 * der gesamten Personalanzahl und den laufenden Kosten werden gespeichert.
	 * @param p personalTyp von Personaltyp
	 * @param anzahlPers Anzahl des zu entlassenen Personals
	 */
	public void entlassen (PersonalTyp personalTyp, int anzahl){
		int anzahlPers = personal.get(personalTyp);
		personal.put(personalTyp, (anzahlPers - anzahl));
		anzahlPersonal -= anzahl;
		laufendeKosten -= personalTyp.getLaufendeKosten()* anzahl;
	}//entlassen

	/**
	 * Schult Personal, d.h. eine Anzahl an Personal wird aus ihrer aktuellen Qualitätsstufe
	 * entnommen und der nächsten Stufe hinzugefügt. Dabei kann die Qualität immer nur um eine 
	 * Stufe erhöht werden. Die Änderungen der laufenden Kosten durch die neuen Kosten werden 
	 * gespeichert; Schulungskosten werden abgebucht.
	 * @param von Ist-Qualitätsstufe
	 * @param nach Soll-Qualitätsstufe 
	 * @param anzahlPers Anzahl der zu schulenden Personal
	 */
	public void schulen (PersonalTyp von, PersonalTyp nach, int anzahl, FinanzAbteilung konto ){ 
		// ToDo: Parameter q wegnehmen; "schulbar-Prüfung" ergännzen
		double neuePersKosten = (nach.getLaufendeKosten()-von.getLaufendeKosten() )* anzahl;
		if (konto.getKontostand() < (von.getSchulungsKosten() + neuePersKosten) ) {
			// Kapital zu gering!
			return;
		}
		int anzahlVonAlt = personal.get(von);
		int anzahlNachAlt = personal.get(nach);
		anzahlNachAlt = anzahlNachAlt + anzahl;
		anzahlVonAlt = anzahlVonAlt - anzahl;
		
		personal.put(von, anzahlVonAlt);
		personal.put(nach, anzahlNachAlt);
		konto.abbuchen(von.getSchulungsKosten()*anzahl);
		//laufendeKosten neu berechnen
		laufendeKosten += neuePersKosten;
	}
/*	
	//nicht auf einmal Parameter umdrehen!
	public void einstellen (PersonalTyp personalTyp, int anzahl){
		//Werbungskosten?!
		int anzahlAlt = personal.get(personalTyp);
		personal.put (personalTyp, (anzahlAlt + anzahl));
		anzahlPersonal += anzahl;
		laufendeKosten += personalTyp.getLaufendeKosten()* anzahl;
	}
*/	
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
