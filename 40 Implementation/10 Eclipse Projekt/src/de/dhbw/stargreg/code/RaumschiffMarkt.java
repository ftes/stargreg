package de.dhbw.stargreg.code;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

/**
 * Beschreibt den Raumschiffmarkt, der f�r die Spieler den Absatzmarkt darstellt
 * Die Absatzmengen sind abh�ngig von den jeweiligen Angebots-Mengen und Preisen,
 * und werden zus�tzlich mit einem als sinnvoll angenommenem Vielfachen der Bauteilkosten
 * f�r ein Raumschiff verglichen, um zu hohe Preise zu verhindern
 * 
 * @author fredrik
 *
 */
public class RaumschiffMarkt extends Markt {
	
	/**
	 * Liste mit allen Raumschifftypen
	 */
	private static final Vector<RaumschiffTyp> raumschifftypen = new Vector<RaumschiffTyp>();
	
	/**
	 * Beschreibt die Grundnachfrage nach jedem Raumschifftyp in dieser Periode
	 */
	private HashMap<RaumschiffTyp, Integer> nachfragen = new HashMap<RaumschiffTyp, Integer>();

	/**
	 * Zuordnung aller Angebote zum entsprechenden Raumschifftyp
	 */
	private HashMap<RaumschiffTyp, Vector<Angebot>> angebote = new HashMap<RaumschiffTyp, Vector<Angebot>>();
	
	/**
	 * Liste aller Verk�ufe
	 */
	private Vector<Verkauf> verkaeufe = new Vector<Verkauf>();
	
	/**
	 * Fuegt einen Raumschifftyp zur Liste hinzu
	 * @param typ Hinzuzuf�gender Raumschifftyp
	 */
	public static void fuegeRaumschifftypHinzu(RaumschiffTyp typ) {
		raumschifftypen.add(typ);
		System.out.printf("%s zum Raumschiffmarkt hinzugef�gt\n", typ);
	}

	/**
	 * Setzt die Nachfrage f�r den gegebenen Raumschifftyp
	 * @param typ Raumschifftyp
	 * @param nachfrage Nachfragemenge f�r {@code typ}
	 */
	public void setEinzelNachfrage(RaumschiffTyp typ, int nachfrage) {
		this.nachfragen.put(typ, nachfrage);			//wenn bereits vorhanden, dann �berschreibt dies den alten Wert
		System.out.printf("Nachfrage nach %s zum Raumschiffmarkt hinzugef�gt: %d St�ck\n", typ, nachfrage);
	}
	
	/**
	 * Setzt die gesamte Nachfrage auf einmal
	 * @param nachfragen {@code HashMap} mit der Zuordnung {@code Raumschifftyp} zu Nachfragemenge
	 */
	public void setNachfragen(HashMap<RaumschiffTyp, Integer> nachfragen) {
		this.nachfragen = nachfragen;
	}
	
	/**
	 * F�gt Angebot in HashMap dem entsprechenden Eintrag f�r den Raumschifftyp hinzu
	 * @param angebot Angebot, das eingetragen werden soll
	 */
	public void fuegeAngebotHinzu(Angebot angebot) {
		RaumschiffTyp raumschifftyp = angebot.getRaumschifftyp();
		if (! angebote.containsKey(raumschifftyp)) {
			angebote.put(raumschifftyp, new Vector<Angebot>());
		}
		angebote.get(raumschifftyp).add(angebot);
		System.out.printf("%s zu Raumschiffmarkt hinzugef�gt\n", angebot);
	}
	
	/**
	 * Berechnet die Absatzmengen f�r die jeweiligen Spieler (Angebote) f�r diese Periode
	 * @param raumschifftyp Raumschifftyp, f�r den die Absatzmengen bestimmt werden sollen (wichtig wegen der Elementarkosten)
	 * @param angebote {@code Vector} mit Angeboten der Unternehmen, die Menge und Preis beinhalten
	 * @return R�ckgabe eines {@code Vector} mit allen {@code Verkauf}-Objekten, die die jeweilige Absatzmenge enthalten
	 */
	public Vector<Verkauf> berechneTypAbsatz(RaumschiffTyp raumschifftyp, Vector<Angebot> angebote) {
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
		System.out.printf("Typabs�tze f�r %s:\n", raumschifftyp);
		for (Angebot angebot : angebote) {
			int menge = (int) Math.round(angebot.getAnteil() / anteilSumme * nachfrage) + uebertrag;
			if (menge <= angebot.getMenge()) {
				verkaeufe.add(angebot.kloneVerkauf(menge));
				uebertrag = 0;
			} else {
				verkaeufe.add(angebot.kloneVerkauf(angebot.getMenge()));
				uebertrag = menge - angebot.getMenge();
			}
			System.out.printf("   %s kann %s verkaufen\n", verkaeufe.lastElement().getUnternehmen(), verkaeufe.lastElement());
		}
		
		return verkaeufe;
	}

	/**
	 * Gruppiert alle Angebote nach Raumschifftypen, berechnet f�r diese jeweils mit {@code berechneTypAbsatz()} den Absatz f�r die
	 * jeweiligen Unternehmen und gibt alle Verk�ufe in einem {@code Vector} zur�ck
	 * @return {@code Vector} mit allen Verk�ufen
	 */
	public Vector<Verkauf> berechneGesamtAbsatz() {
		System.out.println("Absatzmengen im Raumschiffmarkt:");
		//Abs�tze f�r Raumschifftypen berechnen
		for (RaumschiffTyp raumschifftyp : angebote.keySet()) {
			Vector<Verkauf> typVerkaeufe = berechneTypAbsatz(raumschifftyp, angebote.get(raumschifftyp));
			verkaeufe.addAll(typVerkaeufe);
		}		
		return verkaeufe;
	}

}