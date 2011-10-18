package de.dhbw.stargreg.code;

import java.util.HashMap;
/**
 * Im Lager k�nnen beliebig viele Bauteile und fertige Raumschiffe aufgenommen werden, eine obere
 * Kapazit�tsschranke gibt es nicht. Verwaltet werden sie �ber die gemeinsame Oberklasse 'ProduktTyp'. 
 * Dabei setzten sich die belegten Stellplatzeinheiten (SPEs) aus den jeweiligen ben�tigten SPEs der 
 * Bautteiltypen (vgl. Datenbasis) zusammen. 
 * @author Britta
 *
 */

public class LagerAbteilung {
	/**
	 * Kosten pro LagerplatzEinheit
	 */
	private static double lagerPlatzEinheitKosten;
	
	/**
	 * zählt die belegten LagerplatzEinheiten
	 */
	private int lagerstand = 0;
	private final HashMap<ProduktTyp, Integer> bestand = new HashMap<ProduktTyp, Integer>();	
	
	/**
	 * Entnimmt eine Anzahl an Bauteilen und Raumschiffen aus dem Lager, sofern der Lagerbestand dies 
	 * erlaubt. Die �nderungen des Lagerbestands werden gespeichert.
	 * @param produktTyp Referenz auf ein Objekt des Typs ProduktTyp
	 * @param anzahl Anzahl der zu entnehmenden Teile dieses Typs
	 * @return Meldung ???
	 */
	public boolean entnehmen (ProduktTyp produktTyp, int anzahl){
		int istAnzahl = bestand.get(produktTyp); 		
		if (anzahl > istAnzahl){
			System.err.println("Lagerbestand zu gering! Es können nur " + istAnzahl + " Teile entnommen werden.");
			return false;
		}
		//Achtung: Differenz bilden!
		bestand.put(produktTyp, istAnzahl - anzahl);
		this.lagerstand -= produktTyp.getLagerplatzEinheiten() * anzahl;
		return true;
	}//leeren
	
	/**
	 * Lagert neue Bauteile und Raumschiffe in das Lager ein. Da das Lager beliebig gro� ist, muss keine 
	 * Pr�fung auf ausreichend freie Kapazit�t durchgef�hrt werden. Die �nderungen des Lagerbestands 
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
