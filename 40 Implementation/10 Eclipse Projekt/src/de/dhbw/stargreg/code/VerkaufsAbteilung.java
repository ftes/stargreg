package de.dhbw.stargreg.code;

import java.util.Vector;

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
			if (! unternehmen.getLager().entnehmen(verkauf.getRaumschiffTyp(), verkauf.getMenge())) {
				System.err.printf("Weniger als %d %s vorhanden, Fehler in Verkäufen\n", verkauf.getMenge(), verkauf.getRaumschiffTyp());
				return;
			}
			unternehmen.getFinanzen().einzahlen(verkauf.getKosten());
		}
	}

	@Override
	public void gebeInformationenAus(boolean aktuelleSpielRunde) {
		SpielRunde spielRunde = getSpielRunde(aktuelleSpielRunde);
		if (spielRunde == null) return;
		Vector<Verkauf> verkaeufe = spielRunde.get(Verkauf.class, unternehmen);

		System.out.println("Verkäufe der letzten Runde:");
		for (Verkauf verkauf : verkaeufe) {
			System.out.printf("%d %s zu %.2f pro Stück verkauft\n", verkauf.getMenge(), verkauf.getRaumschiffTyp(), verkauf.getPreis());
		}		
	}

}
