package de.dhbw.stargreg.code;

import de.dhbw.stargreg.util.IntegerHashMap;
import de.dhbw.stargreg.util.TableBuilder;

/**
 * Hier werden die Personal der drei Qualitätsstufen über den gemeinsamen Obertyp 'Personaltyp' 
 * verwaltet. Das Personal kann erworben bzw. eingestellt, entlassen und aufgerüstet werden.  
 * @author Britta
 *
 */
public class PersonalAbteilung extends Abteilung {
	/**
	 * Anzahl des derzeit eingestellten Personals. Wichtig für Kapazität der Produktion!
	 */
	private int anzahlPersonal = 0;
	
	/**
	 * Laufende Kosten für Personal.
	 */
	
	/**
	 * Verwaltet die Anzahlen des eingestellten Personals für die jeweiligen Typen.
	 */
	private final IntegerHashMap<PersonalTyp> personal = new IntegerHashMap<PersonalTyp>();	

	public PersonalAbteilung(Unternehmen unternehmen) {
		super(unternehmen);
	}
	
	/**
	 * Rüstet das Personal auf, d.h. eine Anzahl an Personal wird aus ihrer aktuellen Qualitätsstufe
	 * entnommen und der nächsten Stufe hinzugefügt. Dabei kann die Qualität immer nur um eine 
	 * Stufe erhöht werden. Die Änderungen der laufenden Kosten durch die neuen Kosten werden 
	 * gespeichert; Aufrüstungskosten werden abgebucht.
	 * @param von Ist-Qualitätsstufe
	 * @param anzahl Anzahl des aufzurüstenden Personals
	 */
	public boolean aufruesten (PersonalTyp von, int anzahl) {
		if (personal.get(von) < anzahl) {
			System.err.printf("Weniger als %d %s zum aufrüsten vorhanden\n", anzahl, von);
			return false;
		}
		
		PersonalTyp nach = von.getNaechsterPersonalTyp();
		if (nach == null) {
			System.err.printf("%s kann nicht weiter aufgerüstet werden\n", von);
			return false;
		}
		Aufruestung aufruestung = new Aufruestung(von, unternehmen, anzahl, von.getAufruestungsKosten());
		unternehmen.getFinanzen().abbuchen(aufruestung.getGesamtBetrag());
		
		personal.subtract(von, anzahl);
		personal.add(nach, anzahl);
		
		unternehmen.getSpiel().getPersonalMarkt().fuegeTransaktionHinzu(aufruestung);
		
		if (personal.get(von) == 0) personal.remove(von);
		
		return true;
	}
	
	/**
	 * Entlässt Personal. Dabei wird die Kapazität verringert und die laufenden
	 * Kosten nehmen ab.
	 * @param personalTyp PersonalTyp
	 * @param anzahl Anzahl des einzustellenden Personals
	 */
	public boolean entlassen (PersonalTyp personalTyp, int anzahl){
		if (! personal.subtract(personalTyp, anzahl)) {
			System.err.printf("Weniger als %d %s zum entlassen vorhanden\n", anzahl, personalTyp);
			return false;
		}
		
		int personalUebrig = anzahlPersonal - unternehmen.getProduktion().getBenoetigtesPersonal();
		if (personalUebrig < anzahl) {
			System.err.printf("Personalkapazitäten durch Produktion ausgeschöpft\n");
			return false;
		}

		anzahlPersonal -= anzahl;
		
		Entlassung entlassung = new Entlassung(personalTyp, unternehmen, anzahl);
		unternehmen.getSpiel().getPersonalMarkt().fuegeTransaktionHinzu(entlassung);
		
		if (personal.get(personalTyp) == 0) personal.remove(personalTyp);
		
//		System.out.printf("%s hat %d %s entlassen\n", unternehmen, anzahl, personalTyp);
		return true;
	}
	

	/**
	 * Stellt neues Personal ein. Dadurch erhöht sich die Kapazität und die laufenden
	 * Kosten nehmen zu.
	 * @param personalTyp Einzustellender Personaltyp.
	 * @param anzahl Anzahl des einzustellenden Personals.
	 */
	public void einstellen (PersonalTyp personalTyp, int anzahl){
		Einstellung einstellung = new Einstellung(personalTyp, unternehmen, anzahl, personalTyp.getWerbungsKosten());
		
		unternehmen.getFinanzen().abbuchen(einstellung.getGesamtBetrag());
		
		personal.add (personalTyp, anzahl);
		anzahlPersonal += anzahl;
		
		unternehmen.getSpiel().getPersonalMarkt().fuegeTransaktionHinzu(einstellung);
//		System.out.printf("%s hat %d %s eingestellt\n", unternehmen, anzahl, personalTyp);
	}
	
	public int getAnzahlPersonal (){
		return anzahlPersonal;
	}//getMitAnzahl
	
	public int getAnzahl(PersonalTyp personalTyp){
		return personal.get(personalTyp);
	}//getTypMitAnzahl
	
	public double getLaufendeKosten (){
		double kosten = 0;
		for (PersonalTyp personalTyp : personal.keySet()) {
			kosten += personalTyp.getLaufendeKosten() * personal.get(personalTyp);
		}
		return kosten;
	}//getlaufendeKosten
	
	public double getLaufendeKosten(PersonalTyp personalTyp) {
		return personalTyp.getLaufendeKosten() * personal.get(personalTyp);
	}//getTypPerosnalkosten
	
	/**
	 * Berechnet die durchschnittliche Personal-Qualität als gewichtetes Mittel.
	 * Dies ist entscheidend für die Fehlerrate und die dadurch entstehenden
	 * Zusatzkosten.
	 * @return Durchschnittliche Qualität des Personals, 0 < x < 1.
	 */
	public double getDurchschnittlicheQualitaet() {
		double qualitaet = 0;
		for (PersonalTyp personalTyp : personal.keySet()) {
			qualitaet += personalTyp.getQualitaet() * personal.get(personalTyp);
		}
		qualitaet = qualitaet / getAnzahlPersonal();
		return qualitaet;
	}
	
	/**
	 * Bucht die laufenden Kosten für das Personal vom Konto ab.
	 */
	public void simuliere() {
		unternehmen.getFinanzen().abbuchen(getLaufendeKosten());
		unternehmen.getSpiel().getAktuelleSpielRunde().fuegeTransaktionHinzu(new Zahlung(getLaufendeKosten(), Zahlung.Art.PERSONAL, unternehmen));
//		System.out.printf("%.2f laufende Personalkosten abgebucht\n", getLaufendeKosten());
	}

	@Override
	public void gebeInformationenAus(boolean aktuelleSpielRunde) {
		if (! aktuelleSpielRunde) return;
		System.out.printf("Personal (Kosten pro Runde: %.2f)\n", getLaufendeKosten());
		TableBuilder tb = new TableBuilder("PersonalTyp", "Anzahl", "Kosten");
		for (PersonalTyp personalTyp : personal.keySet()) {
			tb.addNewRow(personalTyp,
					personal.get(personalTyp),
					String.format("%.2f", getLaufendeKosten(personalTyp)));
		}
		tb.print();
	}
}
