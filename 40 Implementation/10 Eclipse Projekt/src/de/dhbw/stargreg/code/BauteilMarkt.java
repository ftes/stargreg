package de.dhbw.stargreg.code;

import java.util.HashMap;
import java.util.Vector;

import de.dhbw.stargreg.util.Gruppierung;
import de.dhbw.stargreg.util.TableBuilder;
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
		System.out.println("Bauteilpreise");
		TableBuilder tb = new TableBuilder("BauteilTyp", "Grundpreis", "Delta in %", "Alter Preis", "Neuer Preis");
		// Umsätze für Bauteiltypen und Gesamtumsatz berechnen
		HashMap<BauteilTyp, Double> umsaetze = new HashMap<BauteilTyp, Double>();
		HashMap<BauteilTyp, Vector<Einkauf>> einkaeufe = Util.gruppiereVector(transaktionen, new Gruppierung<BauteilTyp, Einkauf>() {
			public BauteilTyp nach(Einkauf einkauf) {
				return einkauf.getTyp();
			}
		});
		for (BauteilTyp.Art art : BauteilTyp.Art.values()) {
			double gesamtUmsatz = 0;
			int anzahl = 0;
			for (BauteilTyp bauteilTyp : typen) {
				if (bauteilTyp.getArt() != art) continue;
				anzahl++;
				double umsatz = 0;
				if (einkaeufe.containsKey(bauteilTyp)) {
					for (Einkauf einkauf : einkaeufe.get(bauteilTyp)) {
						umsatz += einkauf.getPreis() * einkauf.getMenge();
					}
				}
				umsaetze.put(bauteilTyp, umsatz);
				gesamtUmsatz += umsatz;
			}
			
			double durchschnittsUmsatz = gesamtUmsatz / anzahl;
			
			// neue Preise berechnen
			for(BauteilTyp bauteilTyp : typen) {
				if (bauteilTyp.getArt() != art) continue;
				double abweichung = (umsaetze.get(bauteilTyp) - durchschnittsUmsatz) / durchschnittsUmsatz;
				tb.add(bauteilTyp,
						String.format("%.2f", bauteilTyp.getGrundPreis()),
						String.format("%.1f", 100 * abweichung) + " %",
						String.format("%.2f", bauteilTyp.getPreis()));
				bauteilTyp.berechnePreis(abweichung);
				tb.addNewRow(String.format("%.2f", bauteilTyp.getPreis()));
			}	
		}
		tb.print();
	}
	
	/**
	 * Simuliert den Markt, um ihn auf die nächste Runde vorzubereiten.
	 * Dabei müssen die Bauteilpreise neu berechnet werden.
	 */
	public Vector<Einkauf> simuliere() {
		berechnePreise();
		return super.simuliere();
	}
	
	public void gebePreiseAus() {
		System.out.println("Bauteilmarkt");
		TableBuilder tb = new TableBuilder("BauteilTyp", "Grundpreis", "Preis");
		for (BauteilTyp typ : typen) {
			tb.addNewRow(typ,
					String.format("%.2f", typ.getGrundPreis()),
					String.format("%.2f", typ.getPreis()));
		}
		tb.print();
	}
}