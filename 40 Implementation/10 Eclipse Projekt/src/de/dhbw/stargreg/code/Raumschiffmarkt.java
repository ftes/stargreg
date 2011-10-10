package de.dhbw.stargreg.code;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

import de.dhbw.stargreg.util.Gruppierung;
import de.dhbw.stargreg.util.Util;

/**
 * Beschreibt den Raumschiffmarkt, der f�r die Spieler den Absatzmarkt darstellt
 * Die Absatzmengen sind abh�ngig von den jeweiligen Angebots-Mengen und Preisen,
 * und werden zus�tzlich mit einem als sinnvoll angenommenem Vielfachen der Bauteilkosten
 * f�r ein Raumschiff verglichen, um zu hohe Preise zu verhindern
 * 
 * @author fredrik
 *
 */
public class Raumschiffmarkt {

	/**
	 * Beschreibt die Grundnachfrage nach jedem Raumschifftyp in dieser Periode
	 */
	private HashMap<Raumschifftyp, Integer> nachfragen = new HashMap<Raumschifftyp, Integer>();
	
	/**
	 * Liste mit allen Raumschifftypen
	 */
	private final Vector<Raumschifftyp> raumschifftypen = new Vector<Raumschifftyp>();
	
	/**
	 * Fuegt einen Raumschifftyp zur Liste hinzu
	 * @param typ Hinzuzuf�gender Raumschifftyp
	 */
	public void fuegeRaumschifftypHinzu(Raumschifftyp typ) {
		this.raumschifftypen.add(typ);
		System.out.printf("%s zum Raumschiffmarkt hinzugef�gt\n", typ);
	}

	/**
	 * Setzt die Nachfrage f�r den gegebenen Raumschifftyp
	 * @param typ Raumschifftyp
	 * @param nachfrage Nachfragemenge f�r {@code typ}
	 */
	public void setNachfrage(Raumschifftyp typ, int nachfrage) {
		this.nachfragen.put(typ, nachfrage);			//wenn bereits vorhanden, dann �berschreibt dies den alten Wert
		System.out.printf("Nachfrage nach %s zum Raumschiffmarkt hinzugef�gt: %d St�ck\n", typ, nachfrage);
	}
	
	/**
	 * Berechnet die Absatzmengen f�r die jeweiligen Spieler (Angebote) f�r diese Periode
	 * @param raumschifftyp Raumschifftyp, f�r den die Absatzmengen bestimmt werden sollen (wichtig wegen der Elementarkosten)
	 * @param angebote {@code Vector} mit Angeboten der Unternehmen, die Menge und Preis beinhalten
	 * @return R�ckgabe eines {@code Vector} mit allen {@code Verkauf}-Objekten, die die jeweilige Absatzmenge enthalten
	 */
	public Vector<Verkauf> berechneTypAbsatz(Raumschifftyp raumschifftyp, Vector<Angebot> angebote) {
		//To-Do: Was passiert bei gleichen Preisen
		//To-Do: Beachten, dass maximal verf�gbare Gesamtmenge auch verkauft wird
		
		//Angebote nach aufsteigendem Preis sortieren
		Collections.sort(angebote, new Comparator<Angebot>() {
			public int compare(Angebot a1, Angebot a2) {	//1: a1<a2, 0: a1=a2, -1: a1>a2
				return ((Double) a1.getPreis()).compareTo(a2.getPreis());
			}
		});
		
		Vector<Verkauf> verkaeufe = new Vector<Verkauf>();
		double anteilSumme = 0;
		double niedrigsterPreis = Double.MAX_VALUE;
		
		//Niedrigsten Preis finden und Anteile berechnen
		for (Angebot angebot : angebote) {
			double anteil = 1.0 / Math.pow(angebot.getPreis(), 3);
			angebot.setAnteil(anteil);
			anteilSumme += anteil;
			if (angebot.getPreis() < niedrigsterPreis) {
				niedrigsterPreis = angebot.getPreis();
			}
		}
		
		//Neue Nachfrage basierend auf niedrigstem Preis berechnen
		int nachfrage = (int) Math.floor(nachfragen.get(raumschifftyp) * (1 - Math.pow(niedrigsterPreis / (raumschifftyp.getKosten() * 3.5), 4)));
		if (nachfrage < 0) {
			nachfrage = 0;
		}
		int uebertrag = 0;
		
		//Jeweilige Verkaufsmengen berechnen
		for (Angebot angebot : angebote) {
			int menge = (int) Math.round(angebot.getAnteil() / anteilSumme * nachfrage) + uebertrag;
			if (menge <= angebot.getMenge()) {
				verkaeufe.add(angebot.kloneVerkauf(menge));
				uebertrag = 0;
			} else {
				verkaeufe.add(angebot.kloneVerkauf(angebot.getMenge()));
				uebertrag = menge - angebot.getMenge();
			}
		}
		
		return verkaeufe;
	}

	/**
	 * Gruppiert alle Angebote nach Raumschifftypen, berechnet f�r diese jeweils mit {@code berechneTypAbsatz()} den Absatz f�r die
	 * jeweiligen Unternehmen und gibt alle Verk�ufe in einem {@code Vector} zur�ck
	 * @param angebote {@code Vector} mit allen Angeboten aller Spieler zu allen Raumschifftypen
	 * @return {@code Vector} mit allen Verk�ufen
	 */
	public Vector<Verkauf> berechneGesamtAbsatz(Vector<Angebot> angebote) {
		Vector<Verkauf> verkaeufe = new Vector<Verkauf>();
		
		//Angebote nach Raumschifftyp gruppieren
		HashMap<Raumschifftyp, Vector<Angebot>> map = Util.gruppiereVector(angebote, new Gruppierung<Raumschifftyp, Angebot>() {
			@Override
			public Raumschifftyp nach(Angebot angebot) {
				return angebot.getRaumschifftyp();
			}
		});
		
		//Abs�tze f�r Raumschifftypen berechnen NEU MACHEN MIT KEYS AUS HASHMAP
		for (Raumschifftyp raumschifftyp : raumschifftypen) {
			Vector<Verkauf> typVerkaeufe = berechneTypAbsatz(raumschifftyp, map.get(raumschifftyp));
			verkaeufe.addAll(typVerkaeufe);
		}
		
		return verkaeufe;
	}
}
