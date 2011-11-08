package de.dhbw.stargreg.code;

import java.util.Vector;

import de.dhbw.stargreg.util.Filter;
import de.dhbw.stargreg.util.TableBuilder;
import de.dhbw.stargreg.util.Util;

/**
 * 
 * @author fredrik
 *
 */
public class VerkaufsAbteilung extends Abteilung {

	public VerkaufsAbteilung(Unternehmen unternehmen) {
		super(unternehmen);
	}

	/**
	 * Verkauft Raumschiffe in den übergebenen Mengen. Dabei werden die Raumschiffe aus dem Lager
	 * entnommen und der Ertrag wird dem Konto gutgeschrieben.
	 * @param verkaeufe {@code Vector} mit den zu tätigenden Verkäufen.
	 */
	public void verkaufe(Vector<Verkauf> verkaeufe) {
		if (verkaeufe == null) return;
		for (Verkauf verkauf : verkaeufe) {
			if (verkauf.getUnternehmen() != unternehmen) {
				System.err.printf("Nicht für %s bestimmte Verkäufe zum verkaufen erhalten\n", unternehmen);
				return;
			}
			if (! unternehmen.getLager().entnehmen(verkauf.getTyp(), verkauf.getMenge())) {
				System.err.printf("Weniger als %d %s vorhanden, Fehler in Verkäufen\n", verkauf.getMenge(), verkauf.getTyp());
				return;
			}
			unternehmen.getFinanzen().einzahlen(verkauf.getKosten());
		}
	}
	
	/**
	 * Gibt ein Angebot zu allen Raumschiffen im Lager (also inkl. der zu produzierenden) ab.
	 * @param raumschiffTyp
	 * @param preis
	 */
	public void macheAngebot(RaumschiffTyp raumschiffTyp, double preis) {
		int menge = unternehmen.getLager().getAnzahl(raumschiffTyp);
		Angebot angebot = new Angebot(raumschiffTyp, unternehmen, menge, preis);
		unternehmen.getSpiel().getRaumschiffMarkt().fuegeAngebotHinzu(angebot);
	}

	@Override
	public void gebeInformationenAus(boolean aktuelleSpielRunde) {
		if (! aktuelleSpielRunde) {
			SpielRunde spielRunde = getSpielRunde(aktuelleSpielRunde);
			if (spielRunde == null) return;
			Vector<Verkauf> verkaeufe = spielRunde.getTransaktionen(Verkauf.class, unternehmen);

			double gesamtUmsatz = Util.summiereTypTransaktionen(spielRunde.getTransaktionen(Verkauf.class, unternehmen));
			System.out.printf("Verkäufe (Gesamtumsatz: %.2f)\n", gesamtUmsatz);
			TableBuilder tb = new TableBuilder("RaumschiffTyp", "Menge", "Preis", "Umsatz");
			for (Verkauf verkauf : verkaeufe) {
				tb.addNewRow(verkauf.getTyp(),
						verkauf.getMenge(),
						String.format("%.2f", verkauf.getPreis()),
						String.format("%.2f", verkauf.getKosten()));
			}
			tb.print();
		} else {
			System.out.println("Verkaufsangebote");
			Vector<Angebot> angebote = Util.filtereVector(unternehmen.getSpiel().getRaumschiffMarkt().getAngebote(), new Filter<Angebot>() {
				public boolean nach(Angebot object) {
					if (object.getUnternehmen() == unternehmen) return true;
					return false;
				}
			});
			TableBuilder tb = new TableBuilder("RaumschiffTyp", "Menge", "Preis");
			for (Angebot angebot : angebote) {
				tb.addNewRow(angebot.getTyp(),
						angebot.getMenge(),
						String.format("%.2f", angebot.getPreis()));
			}
			tb.print();
		}
	}

}
