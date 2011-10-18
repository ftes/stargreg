package de.dhbw.stargreg.code;

/**
 * 
 * @author fredrik
 *
 */
public class EinkaufsAbteilung extends Abteilung {
	
	public EinkaufsAbteilung(Unternehmen unternehmen) {
		super(unternehmen);
	}
	
	public boolean kaufeEin(BauteilTyp bauteilTyp, int menge) {
		Einkauf einkauf = new Einkauf(bauteilTyp, unternehmen, menge, bauteilTyp.getPreis());
		double kosten = einkauf.getKosten();
		if (! unternehmen.getFinanzen().abbuchen(kosten)) {
			System.err.printf("Es konnten nicht %d %s eingekauft werden\n", menge, bauteilTyp);
			return false;
		}
		Spiel.INSTANCE.getBauteilMarkt().fuegeTransaktionHinzu(einkauf);
		unternehmen.getLager().einlagern(bauteilTyp, menge);
		return true;
	}

}
