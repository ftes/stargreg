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
	 * Erwirbt und stellt eine Anzahl an Personaln in einer bestimmten Qualit�tsstufe ein. 
	 * Die Anzahl aller Personal und die neuen laufendeKosten werden gespeichert; die 
	 * Werbungskosten werden abgebucht.
	 * @param p Qualit�tsstufe der einzustellenden Personal
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
	 * Entl�sst eine Anzahl an Personal in einer bestimmten Qualit�tsstufe. Die �nderungen an
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
	 * Schult Personal, d.h. eine Anzahl an Personal wird aus ihrer aktuellen Qualit�tsstufe
	 * entnommen und der n�chsten Stufe hinzugef�gt. Dabei kann die Qualit�t immer nur um eine 
	 * Stufe erh�ht werden. Die �nderungen der laufenden Kosten durch die neuen Kosten werden 
	 * gespeichert; Schulungskosten werden abgebucht.
	 * @param von Ist-Qualit�tsstufe
	 * @param nach Soll-Qualit�tsstufe 
	 * @param anzahlPers Anzahl der zu schulenden Personal
	 */
	public void schulen (PersonalTyp von, PersonalTyp nach, int anzahl, FinanzAbteilung konto ){ 
		// ToDo: Parameter q wegnehmen; "schulbar-Pr�fung" erg�nnzen
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
