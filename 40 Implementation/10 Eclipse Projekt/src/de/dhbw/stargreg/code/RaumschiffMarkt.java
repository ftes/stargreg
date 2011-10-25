package de.dhbw.stargreg.code;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

import de.dhbw.stargreg.util.Filter;
import de.dhbw.stargreg.util.Gruppierung;
import de.dhbw.stargreg.util.Util;

/**
 * Beschreibt den Raumschiffmarkt, der für die Spieler den Absatzmarkt darstellt
 * Die Absatzmengen sind abhängig von den jeweiligen Angebots-Mengen und Preisen,
 * und werden zusätzlich mit einem als sinnvoll angenommenem Vielfachen der Bauteilkosten
 * für ein Raumschiff verglichen, um zu hohe Preise zu verhindern
 * 
 * @author fredrik
 *
 */
public class RaumschiffMarkt extends Markt<RaumschiffTyp, Verkauf> {

	private final Vector<Angebot> angebote = new Vector<Angebot>();
	
	private final Vector<Verkauf> verkaeufe = transaktionen;
	
	public void fuegeAngebotHinzu(Angebot angebot) {
		angebote.add(angebot);
	}
	
	public void fuegeVerkaufHinzu(Verkauf verkauf) {
		fuegeTransaktionHinzu(verkauf);
	}
	
	/**
	 * Berechnet die Absatzmengen für die jeweiligen Spieler (Angebote) für diese Periode
	 * @param raumschiffTyp Raumschifftyp, für den die Absatzmengen bestimmt werden sollen (wichtig wegen der Elementarkosten)
	 * @param angebote {@code Vector} mit Angeboten der Unternehmen, die Menge und Preis beinhalten
	 * @return Rückgabe eines {@code Vector} mit allen {@code Verkauf}-Objekten, die die jeweilige Absatzmenge enthalten
	 */
	public Vector<Verkauf> berechneTypAbsatz(RaumschiffTyp raumschiffTyp, Vector<Angebot> angebote) {
		//To-Do: Was passiert bei gleichen Preisen
		//To-Do: Beachten, dass maximal verfügbare Gesamtmenge auch verkauft wird
		
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
		
		//Neue Nachfrage basierend auf niedrigstem Preis berechnen, abhängig von Spielerzahl
		int nachfrage = raumschiffTyp.getNachfrage();
		nachfrage = (int) Math.floor(nachfrage * (1 - Math.pow(niedrigsterPreis / (raumschiffTyp.getKosten() * 3.5), 4)));
		if (nachfrage < 0) {
			nachfrage = 0;
		}
		int uebertrag = 0;
		
		//Jeweilige Verkaufsmengen berechnen
		System.out.printf("Typabsätze für %s:\n", raumschiffTyp);
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
	 * Gruppiert alle Angebote nach Raumschifftypen, berechnet für diese jeweils mit {@code berechneTypAbsatz()} den Absatz für die
	 * jeweiligen Unternehmen und gibt alle Verkäufe in einem {@code Vector} zuräck
	 * @return {@code Vector} mit allen Verkäufen
	 */
	public Vector<Verkauf> berechneGesamtAbsatz() {
		HashMap<RaumschiffTyp, Vector<Angebot>> angebote = Util.gruppiereVector(this.angebote, new Gruppierung<RaumschiffTyp, Angebot>() {
			public RaumschiffTyp nach(Angebot angebot) {
				return angebot.getRaumschiffTyp();
			}
		});
		
		System.out.println("Absatzmengen im Raumschiffmarkt:");
		//Absätze für Raumschifftypen berechnen
		for (RaumschiffTyp raumschiffTyp : angebote.keySet()) {
			Vector<Verkauf> typVerkaeufe = berechneTypAbsatz(raumschiffTyp, angebote.get(raumschiffTyp));
			verkaeufe.addAll(typVerkaeufe);
		}		
		return verkaeufe;
	}
	
	public Vector<Angebot> getAngebote() {
		return angebote;
	}
	
	public Vector<Verkauf> simuliere() {
		angebote.clear();
		return super.simuliere();
	}
	
	public Vector<Verkauf> getVerkaeufe(final Unternehmen unternehmen) {
		return Util.filtereVector(verkaeufe, new Filter<Verkauf>() {
			public boolean nach(Verkauf verkauf) {
				if (verkauf.getUnternehmen() == unternehmen) {
					return true;
				}
				return false;
			}
		});
	}
}
