package de.dhbw.stargreg.code;

import java.util.HashMap;
/**
 * Im Lager koennen beliebig viele Bauteile und fertige Raumschiffe aufgenommen werden, eine obere
 * Kapazitaetsschranke gibt es nicht. Verwaltet werden sie ueberber die gemeinsame Oberklasse 'ProduktTyp'. 
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
	 * Kosten pro LagerplatzEinheit
	 */
	private static double lagerPlatzEinheitKosten;
	
	/**
	 * zaehlt die belegten LagerplatzEinheiten
	 */
	private int lagerstand = 0;
	private final HashMap<ProduktTyp, Integer> bestand = new HashMap<ProduktTyp, Integer>();	
	
	/**
	 * Entnimmt eine Anzahl an Bauteilen und Raumschiffen aus dem Lager, sofern der Lagerbestand dies 
	 * erlaubt. Die ÔøΩnderungen des Lagerbestands werden gespeichert.
	 * @param produktTyp Referenz auf ein Objekt des Typs ProduktTyp
	 * @param anzahl Anzahl der zu entnehmenden Teile dieses Typs
	 * @return Meldung ???
	 */
	public boolean entnehmen (ProduktTyp produktTyp, int anzahl){
		int istAnzahl = bestand.get(produktTyp); 		
		if (anzahl > istAnzahl){
			System.err.println("Lagerbestand zu gering! Es k√∂nnen nur " + istAnzahl + " Teile entnommen werden.");
			return false;
		}
		//Achtung: Differenz bilden!
		bestand.put(produktTyp, istAnzahl - anzahl);
		this.lagerstand -= produktTyp.getLagerplatzEinheiten() * anzahl;
		return true;
	}//leeren
	
	/**
	 * Lagert neue Bauteile und Raumschiffe in das Lager ein. Da das Lager beliebig groﬂ ist, muss keine 
	 * Pruefung auf ausreichend freie Kapazitaet durchgefuehrt werden. Die ÔøΩnderungen des Lagerbestands 
	 * werden gespeichert.
	 * @param produktTyp Referenz auf ein Objekt des Typs ProduktTyp
	 * @param anzahl Anzahl der einzulagernden Teile dieses Typs
	 */
	public void einlagern (ProduktTyp produktTyp, int anzahl){
		//Achtung: Summe bilden!
		int istAnzahl = bestand.get(produktTyp);
	    bestand.put(produktTyp, anzahl + istAnzahl);
	    this.lagerstand += produktTyp.getLagerplatzEinheiten() * anzahl;
	}//einlagern
	
	public int getLagerstand () {		
		return this.lagerstand;
	}//getLagerstand
	
	public double getLagerkosten() {
		return lagerPlatzEinheitKosten * lagerstand;
	}//getLagerkosten
	
	public static void setLagerPlatzEinheitKosten(double lagerPlatzEinheitKosten) {
		LagerAbteilung.lagerPlatzEinheitKosten = lagerPlatzEinheitKosten;
	}
}
