package de.dhbw.stargreg.code;

import java.util.HashMap;

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
		unternehmen.getFinanzen().abbuchen(kosten);
		unternehmen.getSpiel().getBauteilMarkt().fuegeTransaktionHinzu(einkauf);
		unternehmen.getLager().einlagern(bauteilTyp, menge);
//		System.out.printf("%s hat %d %s zu je %.2f eingekauft\n", unternehmen, menge, bauteilTyp, bauteilTyp.getPreis());
	}
	
	/**
	 * Ausreichend Bauteile zun Bau von Raumschiffen einkaufen. Berücksichtigt keine Lagerbestände.
	 * @param raumschiffTyp
	 * @param menge
	 */
	public void kaufeEinFuer(RaumschiffTyp raumschiffTyp, int menge) {
		HashMap<BauteilTyp, Integer> bauteile = raumschiffTyp.getBauteile();
		for (BauteilTyp bauteilTyp : bauteile.keySet()) {
			kaufeEin(bauteilTyp, bauteile.get(bauteilTyp) * menge);
		}
	}

	@Override
	public void gebeInformationenAus(boolean aktuelleSpielRunde) {
		// TODO Auto-generated method stub
		
	}

}
