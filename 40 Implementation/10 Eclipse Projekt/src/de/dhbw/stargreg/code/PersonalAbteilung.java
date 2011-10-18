package de.dhbw.stargreg.code;

import java.util.HashMap;

import de.dhbw.stargreg.code.FinanzAbteilung.Finanzen;

/**
 * 
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
	public PersonalAbteilung (HashMap<PersonalTyp, Integer> hm) {
		
		this.allePersonaltypen = hm;
		for( PersonalTyp p : allePersonaltypen.keySet()) {
			this.summeMit += allePersonaltypen.get(p);
			this.personalkosten += p.getLaufendeKosten()*allePersonaltypen.get(p);
			// personalkosten_1/2/3 für jeden Typ
		}

	}//Konstruktor


	/**
	 * Schult Mitarbeiter, d.h. entnimmt Mitarbieter aus der aktuellen Kategorie und fügt sie in die höhere Kategorie ein. Die Personalkosten werden neu berrechnet.
	 * @param p aktuelle Kategorie
	 * @param q nächste höhere Kategorie
	 * @param anzahlMit Anzahl der zu schulenden Mitarbeiter
	 */
	public void schulenMit (PersonalTyp p, PersonalTyp q, int anzahlMit ){
		
		int anzahlMit_p = allePersonaltypen.get(p);
		int anzahlMit_q = allePersonaltypen.get(q);
		anzahlMit_q = anzahlMit_q + anzahlMit;
		anzahlMit_p = anzahlMit_p - anzahlMit;
		
		allePersonaltypen.put(p, anzahlMit_p);
		allePersonaltypen.put(q, anzahlMit_q);
		//Personalkosten neu berechnen
		personalkosten -= p.getLaufendeKosten()* anzahlMit;
		personalkosten += q.getLaufendeKosten()* anzahlMit;
	}//erwerbenMit
	
	public void wegnehmenMit (PersonalTyp p, int anzahlMit){
		int anzahlMit_p = allePersonaltypen.get(p);
		allePersonaltypen.put(p, (anzahlMit_p - anzahlMit));
		summeMit -= anzahlMit;
		personalkosten -= p.getLaufendeKosten()* anzahlMit;
	}//wegnehmenMit
	
	public void erwerbenMit (int anzahlMit, PersonalTyp p){
		int anzahlMit_p = allePersonaltypen.get(p);
		allePersonaltypen.put (p, (anzahlMit_p + anzahlMit));
		summeMit += anzahlMit;
		personalkosten += p.getLaufendeKosten()* anzahlMit;
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
