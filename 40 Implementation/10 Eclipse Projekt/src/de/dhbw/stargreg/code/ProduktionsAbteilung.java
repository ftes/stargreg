package de.dhbw.stargreg.code;

import java.util.HashMap;
import java.util.Vector;

import de.dhbw.stargreg.util.TableBuilder;


/**
 * 
 * @author fredrik
 *
 */
public class ProduktionsAbteilung extends Abteilung {
	
	private final Vector<ProduktionsAuftrag> auftraege = new Vector<ProduktionsAuftrag>();
	
	private int benoetigtesPersonal = 0;

	public ProduktionsAbteilung(Unternehmen unternehmen) {
		super(unternehmen);
	}

	public void produziere() {
		for (ProduktionsAuftrag auftrag : auftraege) {
			int menge = auftrag.getMenge();
			
			int fehlerhaft = berechneFehlerhafteMenge(menge);
			double fehlerKosten = fehlerhaft * auftrag.getTyp().getFehlerKosten();
			unternehmen.getFinanzen().abbuchen(fehlerKosten);
			unternehmen.getSpiel().getAktuelleSpielRunde().fuegeZahlungHinzu(new Zahlung(fehlerKosten, Zahlung.Art.FEHLER, unternehmen));
			
//			System.out.printf("%d fehlerhafte %s verursachen für %s %.2f Zusatzkosten\n", fehlerhaft, auftrag.getTyp(), unternehmen, fehlerKosten);
		}
		
		auftraege.clear();
		benoetigtesPersonal = 0;
	}
	
	private int berechneFehlerhafteMenge(int menge) {
		int fehlerhafteMenge = 0;
		for (int i=0; i<menge; i++) {
			if (Math.random() > unternehmen.getPersonal().getDurchschnittlicheQualitaet()) {
				fehlerhafteMenge++;
			}
		}
		return fehlerhafteMenge;
	}
	
	/**
	 * Fügt den Produktionsauftrag hinzu und entnimmt bereits die benötigten Bauteile
	 * aus dem Lager und fügt die Raumschiffe hinzu. Die Fehlerkosten werden allerdings
	 * erst bei der Simulation berechnet.
	 * @param raumschiffTyp
	 * @param menge
	 * @return
	 */
	public boolean fuegeAuftragHinzu(RaumschiffTyp raumschiffTyp, int menge) {
		//Erst prüfen, ob so viel produzierbar
		HashMap<BauteilTyp, Integer> bauteile = raumschiffTyp.getBauteile();
		
		for (BauteilTyp bauteilTyp : bauteile.keySet()) {
			int anzahlUebrig = unternehmen.getLager().getAnzahl(bauteilTyp);
			if (menge * bauteile.get(bauteilTyp) > anzahlUebrig) {
				System.err.printf("Nicht genug %s auf Lager, um %d %s in Auftrag zu geben\n", bauteilTyp, menge, raumschiffTyp);
				return false;
			}
		}
		
		int personalUebrig = unternehmen.getPersonal().getAnzahlPersonal() - benoetigtesPersonal;
		if (menge * raumschiffTyp.getBenoetigtesPersonal() > personalUebrig) {
			System.err.printf("Nicht genug Personal, um %d %s in Auftrag zu geben\n", menge, raumschiffTyp);
			return false;
		}
		
		//Dann speichern
		for (BauteilTyp bauteilTyp : bauteile.keySet()) {
			unternehmen.getLager().entnehmen(bauteilTyp, menge * bauteile.get(bauteilTyp));
		}
		
		benoetigtesPersonal += menge * raumschiffTyp.getBenoetigtesPersonal();
		
		auftraege.add(new ProduktionsAuftrag(raumschiffTyp, unternehmen, menge));
//		System.out.printf("%s hat %d %s in Auftrag gegeben\n", unternehmen, menge, raumschiffTyp);
		
		unternehmen.getLager().einlagern(raumschiffTyp, menge);
		
		return true;
	}

	@Override
	public void gebeInformationenAus(boolean aktuelleSpielRunde) {
		System.out.println("Produktionsaufträge");
		TableBuilder tb = new TableBuilder("RaumschiffTyp", "Menge");
		if (! aktuelleSpielRunde) {
			
		} else {
			for (ProduktionsAuftrag auftrag : auftraege) {
				tb.addNewRow(auftrag.getTyp(),
						auftrag.getMenge());
			}
		}
		tb.print();
	}
	
	public int getBenoetigtesPersonal() {
		return benoetigtesPersonal;
	}

}
