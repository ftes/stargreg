package de.dhbw.stargreg.code;

/**
 * Die Einkaufsabteilung eines Unternehmens.
 * @author fredrik
 *
 */
public class EinkaufsAbteilung extends Abteilung {
	
	public EinkaufsAbteilung(Unternehmen unternehmen) {
		super(unternehmen);
	}
	
	/**
	 * Kauft eine bestimmte Menge eines Bauteiltypen ein. Dabei wird der Kontostand entsprechend
	 * verringert und die Teile werden ins Lager gelegt.
	 * @param bauteilTyp
	 * @param menge
	 * @return
	 */
	public boolean kaufeEin(BauteilTyp bauteilTyp, int menge) {
		Einkauf einkauf = new Einkauf(bauteilTyp, unternehmen, menge, bauteilTyp.getPreis());
		double kosten = einkauf.getKosten();
		//Umstellen auf Kreditfähigkeit
		if (! unternehmen.getFinanzen().abbuchen(kosten)) {
			System.err.printf("Es konnten nicht %d %s eingekauft werden\n", menge, bauteilTyp);
			return false;
		}
		Spiel.INSTANCE.getBauteilMarkt().fuegeTransaktionHinzu(einkauf);
		unternehmen.getLager().einlagern(bauteilTyp, menge);
		return true;
	}

}
