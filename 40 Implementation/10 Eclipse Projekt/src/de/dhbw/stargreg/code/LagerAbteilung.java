package de.dhbw.stargreg.code;

import java.util.HashMap;

public class LagerAbteilung {
	private double speKosten = 1;
	private int lagerstand = 0; //zählt die belegten Stellplatzeinheiten
	private double lagerkosten = 0.0;
	//HashMap
	private HashMap<ProduktTyp, Integer> alleProdukttypen = new HashMap<ProduktTyp, Integer>();	
	/*
	public static LagerAbteilung einrichtenLager(){
		return new LagerAbteilung;
	}//einrichtenLager
	
	private LagerAbteilung (){

	}//Lager
	*/
	public String leeren (ProduktTyp p, int anzahl){
		Integer istAnzahl = alleProdukttypen.get(p); 		
		if (istAnzahl < anzahl){
			return "Lagerbestand zu gering!";
		}
		alleProdukttypen.put(p, anzahl);
		this.lagerstand -= p.getLagerplatzEinheiten();
		return null;
	}//leeren
	
	public void einlagern (ProduktTyp p, int anzahl){
	    alleProdukttypen.put(p ,anzahl);
	}//einlagern
	
	public int getLagerstand () {		
		return this.lagerstand;
	}//getLagerstand
	
	public double getLagerkosten() {
		this.lagerkosten = speKosten * lagerstand;
		return this.lagerkosten;
	}//getLagerkosten
}
