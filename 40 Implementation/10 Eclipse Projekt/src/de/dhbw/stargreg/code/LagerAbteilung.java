package de.dhbw.stargreg.code;

import de.dhbw.stargreg.util.IntegerHashMap;
/**
 * Im Lager koennen beliebig viele Bauteile und fertige Raumschiffe aufgenommen werden, eine obere
 * Kapazitaetsschranke gibt es nicht. Verwaltet werden sie ueber die gemeinsame Oberklasse 'ProduktTyp'. 
 * Dabei setzten sich die belegten Stellplatzeinheiten aus den jeweiligen benoetigten SPEs der 
 * Bautteiltypen (vgl. Datenbasis) zusammen. 
 * @author Britta
 *
 */

// Frage: Werden ProduktTypen direkt nach der Produktion eingelagert und fallen dann die Lagerkosten an,
// auch wenn sie noch in der selben Periode wieder ausgelagert werden (durch Verkauf)?

public class LagerAbteilung extends Abteilung {
	public LagerAbteilung(Unternehmen unternehmen) {
		super(unternehmen);
	}

	
	/**
	 * zaehlt die belegten LagerplatzEinheiten
	 */
	private int lagerstand = 0;
	private final IntegerHashMap<ProduktTyp> bestand = new IntegerHashMap<ProduktTyp>();	
	
	/**
	 * Entnimmt eine Anzahl an Bauteilen und Raumschiffen aus dem Lager, sofern der Lagerbestand dies 
	 * erlaubt. Die Änderungen des Lagerbestands werden gespeichert.
	 * @param produktTyp produktTyp von ProduktTyp
	 * @param anzahl Anzahl der zu entnehmenden Teile dieses Typs
	 * @return Meldung ???
	 */
	public boolean entnehmen (ProduktTyp produktTyp, int anzahl) {		
		if (! bestand.subtract(produktTyp, anzahl)){
			System.err.println("Lagerbestand zu gering! Es koennen nur " + bestand.get(produktTyp) + " Teile entnommen werden.");
			return false;
		}
		this.lagerstand -= produktTyp.getLagerplatzEinheiten() * anzahl;
		return true;
	}//leeren
	
	/**
	 * Lagert neue Bauteile und Raumschiffe in das Lager ein. Da das Lager beliebig groß ist, muss keine 
	 * Pruefung auf ausreichend freie Kapazitaet durchgefuehrt werden. Die Änderungen des Lagerbestands 
	 * werden gespeichert.
	 * @param produktTyp produktTyp von ProduktTyp
	 * @param anzahl Anzahl der einzulagernden Teile dieses Typs
	 */
	public void einlagern (ProduktTyp produktTyp, int anzahl) {
	    bestand.add(produktTyp, anzahl);
	    this.lagerstand += produktTyp.getLagerplatzEinheiten() * anzahl;
	}//einlagern
	
	public int getLagerstand () {		
		return this.lagerstand;
	}//getLagerstand
	
	public double getLagerkosten() {
		return unternehmen.getSpiel().getBauteilMarkt().getLagerplatzEinheitKosten() * lagerstand;
	}//getLagerkosten
	
	public int getAnzahl(ProduktTyp produktTyp) {
		return bestand.get(produktTyp);
	}

	/**
	 * Kosten für belegte LpE abziehen.
	 */
	public void simuliere() {
		unternehmen.getFinanzen().abbuchen(getLagerkosten());
	}

	@Override
	public void gebeInformationenAus(boolean aktuelleSpielRunde) {
		if (! aktuelleSpielRunde) return;
		for (ProduktTyp produktTyp : bestand.keySet()) {
			System.out.printf("%d %s belegen %d LagerPlatz-Einheiten\n", bestand.get(produktTyp), produktTyp, bestand.get(produktTyp) * produktTyp.getLagerplatzEinheiten());
		}
		System.out.printf("Insgesamt sind %d LagerPlatz-Einheiten belegt\n", lagerstand);
		System.out.printf("Dies verursacht %.2f Lagerkosten\n", getLagerkosten());
	}
}
