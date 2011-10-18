package de.dhbw.stargreg.code;

import java.util.HashMap;

/**
 * Hier werden die Mitarbeiter der drei Qualit�tsstufen �ber den gemeinsamen Obertyp 'Personaltyp' 
 * verwaltet. Das Personal kann erworben bzw. eingestellt, entlassen und geschult werden.  
 * @author Britta
 *
 */
public class PersonalAbteilung extends Abteilung {

	private int summeMit = 0;
	private double personalkosten = 0.0;
	
	private HashMap<PersonalTyp, Integer> allePersonaltypen = new HashMap<PersonalTyp, Integer>();	

/*	public PersonalAbteilung einrichten () {
		return new PersonalAbteilung();
	}//eroeffnen
*/	
	public PersonalAbteilung () {
	}//Konstruktor
	
	/**
	 * Erwirbt und stellt eine Anzahl an Mitarbeitern in einer bestimmten Qualit�tsstufe ein. 
	 * Die Anzahl aller Mitarbeiter und die neuen Personalkosten werden gespeichert; die 
	 * Werbungskosten werden abgebucht.
	 * @param p Qualit�tsstufe der einzustellenden Mitarbeiter
	 * @param anzahlMit Anzahl der einzustellenden Mitarbeiter 
	 */
	public void erwerbenMit (PersonalTyp p, int anzahlMit, FinanzAbteilung konto){
		double neuePersKosten = personalkosten += (p.getLaufendeKosten()+ p.getWerbungsKosten() )*anzahlMit;
		if (konto.getKontostand() < neuePersKosten ){
			// Kapital zu gering!
			return;
		}
		int anzahlMit_p = allePersonaltypen.get(p);
		allePersonaltypen.put (p, (anzahlMit_p + anzahlMit));
		konto.abbuchen(p.getWerbungsKosten()*anzahlMit);

		personalkosten += p.getLaufendeKosten() * anzahlMit;
		summeMit += anzahlMit;
		
		
	}//erwerbenMit
	
	/**
	 * Entl�sst eine Anzahl an Mitarbeitern in einer bestimmten Qualit�tsstufe. Die �nderungen an
	 * der Gesamtmitarbeiteranzahl und den Personalkosten werden gespeichert.
	 * @param p Qualit�tsstufe der zu entlassenen Mitarbeiter
	 * @param anzahlMit Anzahl der zu entlassenen Mitarbeiter
	 */
	public void entlassenMit (PersonalTyp p, int anzahlMit){
		int anzahlMit_p = allePersonaltypen.get(p);
		allePersonaltypen.put(p, (anzahlMit_p - anzahlMit));
		summeMit -= anzahlMit;
		personalkosten -= p.getLaufendeKosten()* anzahlMit;
	}//entlassenMit

	/**
	 * Schult Mitarbeiter, d.h. eine Anzahl an Mitarbietern werden aus ihrer aktuellen Qualit�tsstufe
	 * entnommen und der n�chsten Stufe hinzugef�gt. Dabei kann die Qualit�t immer nur um eine Stufe erh�ht
	 * werden. Die �nderungen der Personalkosten durch die neuen laufenden Kostenwerden gespeichert;
	 * die Schulungskosten werdne abgebucht.
	 * @param p Ist-Qualit�tsstufe
	 * @param q Soll-Qualit�tsstufe 
	 * @param anzahlMit Anzahl der zu schulenden Mitarbeiter
	 */
	public void schulenMit (PersonalTyp p, PersonalTyp q, int anzahlMit, FinanzAbteilung konto ){ 
		// ToDo: Parameter q wegnehmen; "schulbar-Pr�fung" erg�nnzen
		double neuePersKosten = (q.getLaufendeKosten()-p.getLaufendeKosten() )* anzahlMit;
		if (konto.getKontostand() < (p.getSchulungsKosten() + neuePersKosten) ) {
			// Kapital zu gering!
			return;
		}
		
		int anzahlMit_p = allePersonaltypen.get(p);
		int anzahlMit_q = allePersonaltypen.get(q);
		anzahlMit_q = anzahlMit_q + anzahlMit;
		anzahlMit_p = anzahlMit_p - anzahlMit;
		
		allePersonaltypen.put(p, anzahlMit_p);
		allePersonaltypen.put(q, anzahlMit_q);
		konto.abbuchen(p.getSchulungsKosten()*anzahlMit);
		//Personalkosten neu berechnen
		personalkosten += neuePersKosten;
	}//schulenMit
	
	public int getMitAnzahl (){
		return summeMit;
	}//getMitAnzahl
	
	public int getTypMitAnzahl (PersonalTyp p){
		return allePersonaltypen.get(p);
	}//getTypMitAnzahl
	
	public double getPersonalkosten (){
		return this.personalkosten;
	}//getPersonalkosten
	
	public double getTypPersonalkosten (PersonalTyp p) {
		return p.getLaufendeKosten()*allePersonaltypen.get(p);
	}//getTypPerosnalkosten
	
	public void simuliere() {
		// TODO Auto-generated method stub
	
	}
}
