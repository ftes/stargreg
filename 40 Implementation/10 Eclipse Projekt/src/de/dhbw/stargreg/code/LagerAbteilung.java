package de.dhbw.stargreg.code;

import java.util.HashMap;
/**
 * Im Lager können beliebig viele Bauteile und fertige Raumschiffe aufgenommen werden, eine obere
 * Kapazitätsschranke gibt es nicht. Verwaltet werden sie über die gemeinsame Oberklasse 'ProduktTyp'. 
 * Dabei setzten sich die belegten Stellplatzeinheiten (SPEs) aus den jeweiligen benötigten SPEs der 
 * Bautteiltypen (vgl. Datenbasis) zusammen. 
 * @author Britta
 *
 */

public class LagerAbteilung {
	
	private static double speKosten; // spe = Stellplatzeinheiten
	private int lagerstand = 0; //zählt die belegten Stellplatzeinheiten
	private double lagerkosten = 0.0;
	private HashMap<ProduktTyp, Integer> alleProdukttypen = new HashMap<ProduktTyp, Integer>();	
	
	public LagerAbteilung (HashMap<ProduktTyp, Integer> hm, double speKosten) {
		this.alleProdukttypen = hm;
		for( ProduktTyp p : alleProdukttypen.keySet()) {
			this.lagerstand += p.getLagerplatzEinheiten()*alleProdukttypen.get(p);
			this.lagerkosten += lagerstand*speKosten;
			// personalkosten_1/2/3 für jeden Typ
		}//for
	}//Konstruktor
	/**
	 * Entnimmt eine Anzahl an Bauteilen und Raumschiffen aus dem Lager, sofern der Lagerbestand dies 
	 * erlaubt. Die Änderungen des Lagerbestands werden gespeichert.
	 * @param p Referenz auf ein Objekt des Typs ProduktTyp
	 * @param anzahl Anzahl der zu entnehmenden Teile dieses Typs
	 * @return Meldung ???
	 */
	public String leeren (ProduktTyp p, int anzahl){
		Integer istAnzahl = alleProdukttypen.get(p); 		
		if (anzahl > istAnzahl){
			return "Lagerbestand zu gering! Es können nur " + istAnzahl + " Teile entnommen werden.";
		}
		alleProdukttypen.put(p, anzahl);
		this.lagerstand -= p.getLagerplatzEinheiten()*anzahl;
		return null; // "positive" Meldung??? 
	}//leeren
	
	/**
	 * Lagert neue Bauteile und Raumschiffe in das Lager ein. Da das Lager beliebig groß ist, muss keine 
	 * Prüfung auf ausreichend freie Kapazität durchgeführt werden. Die Änderungen des Lagerbestands 
	 * werden gespeichert.
	 * @param p Referenz auf ein Objekt des Typs ProduktTyp
	 * @param anzahl Anzahl der einzulagernden Teile dieses Typs
	 */
	public void einlagern (ProduktTyp p, int anzahl){
	    alleProdukttypen.put(p ,anzahl);
	    this.lagerstand += p.getLagerplatzEinheiten()*anzahl;
	}//einlagern
	
	public int getLagerstand () {		
		return this.lagerstand;
	}//getLagerstand
	
	public double getLagerkosten() {
		this.lagerkosten = speKosten * lagerstand;
		return this.lagerkosten;
	}//getLagerkosten
}
