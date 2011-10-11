package de.dhbw.stargreg.code;

import java.util.HashMap;
import java.util.Vector;

/**
 * Auf dem Bauteilmarkt kaufen die Unternehmen Bauteile für die Raumschiffe ein.
 * Da die Preise für die Bauteile von Periode zu Periode schwanken, werden sie hier gespeichert.
 * Zudem wird eine Liste mit allen Einkäufen der Unternehmen für diese Runde geführt, um anhand
 * dieser die Bauteilpreise für die nächste Runde zu berechnen.
 * @author fredrik
 *
 */
public class BauteilMarkt extends Markt{
	
	/**
	 * Zuordnung der Grundpreise zu den jeweiligen Bauteiltypen
	 */
	private static HashMap<BauteilTyp, Double> grundPreise;
	
	/**
	 * Liste mit allen BauteilTypen
	 */
	private static Vector<BauteilTyp> bauteilTypen = new Vector<BauteilTyp>();
	
	/**
	 * Zuordnung der aktuellen Preise zu den jeweiligen Bauteiltypen
	 */
	private HashMap<BauteilTyp, Double> preise = grundPreise;
	
	/**
	 * Zuordnung der Einkäufe zu den Bauteiltypen
	 */
	private HashMap<BauteilTyp, Vector<Einkauf>> einkaeufe = new HashMap<BauteilTyp, Vector<Einkauf>>();
	
	/**
	 * Statische Methode, um zu Beginn einen Grundpreis festzulegen
	 * @param typ Bauteiltyp
	 * @param preis Preis
	 */
	public static void setGrundPreis(BauteilTyp typ, double preis) {
		grundPreise.put(typ, preis);
	}
	
	/**
	 * Fügt einen Einkauf zur Liste hinzu, damit am Ende der Runde die neuen Preise
	 * berechnet werden können
	 * @param einkauf Zu speichernder Einkauf
	 */
	public void fuegeEinkaufHinzu(Einkauf einkauf) {
		BauteilTyp bauteilTyp = einkauf.getBauteilTyp();
		if (! einkaeufe.containsKey(bauteilTyp)) {
			einkaeufe.put(bauteilTyp, new Vector<Einkauf>());
		}
		einkaeufe.get(bauteilTyp).add(einkauf);
	}
	
	/**
	 * Berechnet die neuen Bauteilpreise abhänging von den Umsätzen in der letzten Spielrunde
	 * @param alterMarkt Bauteilmarkt der letzten Spielrunde, aus dem die alten Umsätze ausgelesen werden
	 */
	public void berechnePreise(BauteilMarkt alterMarkt) {
		// Umsätze für Bauteiltypen und Gesamtumsatz berechnen
		HashMap<BauteilTyp, Double> umsaetze = new HashMap<BauteilTyp, Double>();
		double gesamtUmsatz = 0;
		for (BauteilTyp bauteilTyp : bauteilTypen) {
			double umsatz = 0;
			for (Einkauf einkauf : alterMarkt.einkaeufe.get(bauteilTyp)) {
				umsatz += einkauf.getPreis() * einkauf.getMenge();
			}
			umsaetze.put(bauteilTyp, umsatz);
			gesamtUmsatz += umsatz;
		}
		
		double durchschnittsUmsatz = gesamtUmsatz / bauteilTypen.size();
		
		// neue Preise berechnen
		for(BauteilTyp bauteilTyp : bauteilTypen) {
			double abweichung = (umsaetze.get(bauteilTyp) - durchschnittsUmsatz) / durchschnittsUmsatz;
			double maxPreisDelta = bauteilTyp.getMaxPreisDelta();
			double neuerPreis = grundPreise.get(bauteilTyp) - maxPreisDelta
					+ 2 * Math.pow(maxPreisDelta, 2)
					/ (maxPreisDelta
							+ maxPreisDelta
								/ (Math.pow(2 / maxPreisDelta + 1,
										2 * abweichung * maxPreisDelta)));
			preise.put(bauteilTyp, neuerPreis);
		}
	}
	
	public HashMap<BauteilTyp, Double> getPreise() {
		return preise;
	}

	public double getPreis(BauteilTyp bauteilTyp) {
		return preise.get(bauteilTyp);
	}
}