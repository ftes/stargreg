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
		// TODO Auto-generated method stub
		
	}

}
