package de.dhbw.stargreg.code;

import java.util.HashMap;

/**
 * Hier werden die Mitarbeiter der drei Qualitätsstufen über den gemeinsamen Obertyp 'Personaltyp' 
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
	 * Erwirbt und stellt eine Anzahl an Mitarbeitern in einer bestimmten Qualitätsstufe ein. 
	 * Die Anzahl aller Mitarbeiter und die neuen Personalkosten werden gespeichert; die 
	 * Werbungskosten werden abgebucht.
	 * @param p Qualitätsstufe der einzustellenden Mitarbeiter
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
	 * Entlässt eine Anzahl an Mitarbeitern in einer bestimmten Qualitätsstufe. Die Änderungen an
	 * der Gesamtmitarbeiteranzahl und den Personalkosten werden gespeichert.
	 * @param p Qualitätsstufe der zu entlassenen Mitarbeiter
	 * @param anzahlMit Anzahl der zu entlassenen Mitarbeiter
	 */
	public void entlassenMit (PersonalTyp p, int anzahlMit){
		int anzahlMit_p = allePersonaltypen.get(p);
		allePersonaltypen.put(p, (anzahlMit_p - anzahlMit));
		summeMit -= anzahlMit;
		personalkosten -= p.getLaufendeKosten()* anzahlMit;
	}//entlassenMit

	/**
	 * Schult Mitarbeiter, d.h. eine Anzahl an Mitarbietern werden aus ihrer aktuellen Qualitätsstufe
	 * entnommen und der nächsten Stufe hinzugefügt. Dabei kann die Qualität immer nur um eine Stufe erhöht
	 * werden. Die Änderungen der Personalkosten durch die neuen laufenden Kostenwerden gespeichert;
	 * die Schulungskosten werdne abgebucht.
	 * @param p Ist-Qualitätsstufe
	 * @param q Soll-Qualitätsstufe 
	 * @param anzahlMit Anzahl der zu schulenden Mitarbeiter
	 */
	public void schulenMit (PersonalTyp p, PersonalTyp q, int anzahlMit, FinanzAbteilung konto ){ 
		// ToDo: Parameter q wegnehmen; "schulbar-Prüfung" ergännzen
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
