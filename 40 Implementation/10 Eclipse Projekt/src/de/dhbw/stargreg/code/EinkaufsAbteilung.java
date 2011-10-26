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
	public void kaufeEin(BauteilTyp bauteilTyp, int menge) {
		Einkauf einkauf = new Einkauf(bauteilTyp, unternehmen, menge, bauteilTyp.getPreis());
		double kosten = einkauf.getKosten();
		//Umstellen auf Kreditfähigkeit
		unternehmen.getFinanzen().abbuchen(kosten);
		unternehmen.getSpiel().getBauteilMarkt().fuegeTransaktionHinzu(einkauf);
		unternehmen.getLager().einlagern(bauteilTyp, menge);
	}

	@Override
	public void gebeInformationenAus(boolean aktuelleSpielRunde) {
		// TODO Auto-generated method stub
		
	}

}
