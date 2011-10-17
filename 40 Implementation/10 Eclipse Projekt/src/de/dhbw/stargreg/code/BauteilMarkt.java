package de.dhbw.stargreg.code;

import java.util.HashMap;
import java.util.Vector;

import de.dhbw.stargreg.util.Gruppierung;
import de.dhbw.stargreg.util.Util;

/**
 * Auf dem Bauteilmarkt kaufen die Unternehmen Bauteile f�r die Raumschiffe ein.
 * Da die Preise f�r die Bauteile von Periode zu Periode schwanken, werden sie hier gespeichert.
 * Zudem wird eine Liste mit allen Eink�ufen der Unternehmen f�r diese Runde gef�hrt, um anhand
 * dieser die Bauteilpreise f�r die n�chste Runde zu berechnen.
 * @author fredrik
 *
 */
public class BauteilMarkt extends Markt<BauteilTyp, Einkauf> {
	/**
	 * Berechnet die neuen Bauteilpreise abh�nging von den Ums�tzen in der letzten Spielrunde
	 * @param alterMarkt Bauteilmarkt der letzten Spielrunde, aus dem die alten Ums�tze ausgelesen werden
	 */
	private void berechnePreise() {
		// Ums�tze f�r Bauteiltypen und Gesamtumsatz berechnen
		HashMap<BauteilTyp, Double> umsaetze = new HashMap<BauteilTyp, Double>();
		HashMap<BauteilTyp, Vector<Einkauf>> einkaeufe = Util.gruppiereVector(transaktionen, new Gruppierung<BauteilTyp, Einkauf>() {
			public BauteilTyp nach(Einkauf einkauf) {
				return einkauf.getBauteilTyp();
			}
		});
		double gesamtUmsatz = 0;
		for (BauteilTyp bauteilTyp : einkaeufe.keySet()) {
			double umsatz = 0;
			for (Einkauf einkauf : einkaeufe.get(bauteilTyp)) {
				umsatz += einkauf.getPreis() * einkauf.getMenge();
			}
			umsaetze.put(bauteilTyp, umsatz);
			gesamtUmsatz += umsatz;
		}
		
		double durchschnittsUmsatz = gesamtUmsatz / typen.size();
		
		// neue Preise berechnen
		for(BauteilTyp bauteilTyp : typen) {
			double abweichung = (umsaetze.get(bauteilTyp) - durchschnittsUmsatz) / durchschnittsUmsatz;
			bauteilTyp.berechnePreis(abweichung);
		}
	}
	
	public Vector<Einkauf> simuliere() {
		berechnePreise();
		return super.simuliere();
	}
}