package de.dhbw.stargreg.code;

import java.util.HashMap;
import java.util.Vector;

import de.dhbw.stargreg.util.Gruppierung;
import de.dhbw.stargreg.util.Util;

/**
 * Auf dem Bauteilmarkt kaufen die Unternehmen Bauteile für die Raumschiffe ein.
 * Da die Preise für die Bauteile von Periode zu Periode schwanken, werden sie hier gespeichert.
 * Zudem wird eine Liste mit allen Einkäufen der Unternehmen für diese Runde geführt, um anhand
 * dieser die Bauteilpreise für die nächste Runde zu berechnen.
 * @author fredrik
 *
 */
public class BauteilMarkt extends Markt<BauteilTyp, Einkauf> {

	/**
	 * Kosten pro LagerplatzEinheit
	 */
	private double lagerplatzEinheitKosten = -1;
	
	public double getLagerplatzEinheitKosten() {
		if (lagerplatzEinheitKosten == -1) {
			System.err.println("LagerplatzEinheit Kosten wurden noch nicht gesetzt");
		}
		return lagerplatzEinheitKosten;
	}
	
	public void setLagerPlatzEinheitKosten(double lagerplatzEinheitKosten) {
		this.lagerplatzEinheitKosten = lagerplatzEinheitKosten;
	}
	
	/**
	 * Berechnet die neuen Bauteilpreise abhänging von den Umsätzen in der letzten Spielrunde.
	 */
	private void berechnePreise() {
		// Umsätze für Bauteiltypen und Gesamtumsatz berechnen
		HashMap<BauteilTyp, Double> umsaetze = new HashMap<BauteilTyp, Double>();
		HashMap<BauteilTyp, Vector<Einkauf>> einkaeufe = Util.gruppiereVector(transaktionen, new Gruppierung<BauteilTyp, Einkauf>() {
			public BauteilTyp nach(Einkauf einkauf) {
				return einkauf.getBauteilTyp();
			}
		});
		double gesamtUmsatz = 0;
		for (BauteilTyp bauteilTyp : typen) {
			double umsatz = 0;
			if (einkaeufe.containsKey(bauteilTyp)) {
				for (Einkauf einkauf : einkaeufe.get(bauteilTyp)) {
					umsatz += einkauf.getPreis() * einkauf.getMenge();
				}
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
	
	/**
	 * Simuliert den Markt, um ihn auf die nächste Runde vorzubereiten.
	 * Dabei müssen die Bauteilpreise neu berechnet werden.
	 */
	public Vector<Einkauf> simuliere() {
		berechnePreise();
		return super.simuliere();
	}
}