package de.dhbw.stargreg.code;

import java.util.HashMap;
import java.util.Vector;


/**
 * 
 * @author fredrik
 *
 */
public class ProduktionsAbteilung extends Abteilung {
	
	private final Vector<ProduktionsAuftrag> auftraege = new Vector<ProduktionsAuftrag>();
	
	private final HashMap<BauteilTyp, Integer> benoetigteBauteile = new HashMap<BauteilTyp, Integer>();
	
	private int benoetigtesPersonal = 0;

	public ProduktionsAbteilung(Unternehmen unternehmen) {
		super(unternehmen);
	}

	public void produziere() {
		for (ProduktionsAuftrag auftrag : auftraege) {
			HashMap<BauteilTyp, Integer> bauteile = auftrag.getRaumschiffTyp().getBauteile();
			for (BauteilTyp bauteilTyp : bauteile.keySet()) {
				int anzahl = auftrag.getMenge() * bauteile.get(bauteilTyp);
				unternehmen.getLager().entnehmen(bauteilTyp, anzahl);
			}
			int menge = auftrag.getMenge() - berechneAusschuss(auftrag.getMenge());
			unternehmen.getLager().einlagern(auftrag.getRaumschiffTyp(), menge);
		}
		
		auftraege.clear();
		benoetigteBauteile.clear();
		benoetigtesPersonal = 0;
	}
	
	private int berechneAusschuss(int menge) {
		int ausschuss = 0;
		for (int i=0; i<menge; i++) {
			if (Math.random() > unternehmen.getPersonal().getDurchschnittlicheQualitaet()) {
				ausschuss++;
			}
		}
		return ausschuss;
	}
	
	public boolean fuegeAuftragHinzu(RaumschiffTyp raumschiffTyp, int menge) {
		//Erst prüfen, ob so viel produzierbar
		HashMap<BauteilTyp, Integer> bauteile = raumschiffTyp.getBauteile();
		
		for (BauteilTyp bauteilTyp : bauteile.keySet()) {
			int anzahlUebrig = unternehmen.getLager().getAnzahl(bauteilTyp) - benoetigteBauteile.get(bauteilTyp);
			if (menge * bauteile.get(bauteilTyp) > anzahlUebrig) {
				System.err.printf("Nicht genug %s auf Lager, um %d %s in Auftrag zu geben\n", bauteilTyp, menge, raumschiffTyp);
				return false;
			}
		}
		
		int personalUebrig = unternehmen.getPersonal().getAnzahlPersonal() - benoetigtesPersonal;
		if (menge * raumschiffTyp.getBenoetigtesPersonal() > personalUebrig) {
			System.err.printf("Nicht genug Personal, um %d %s in Auftrag zu geben\n", menge, raumschiffTyp);
		}
		
		//Dann speichern
		for (BauteilTyp bauteilTyp : bauteile.keySet()) {
			int anzahl = benoetigteBauteile.get(bauteilTyp);
			anzahl += menge * bauteile.get(bauteilTyp);
			benoetigteBauteile.put(bauteilTyp, anzahl);
		}
		
		benoetigtesPersonal += menge * raumschiffTyp.getBenoetigtesPersonal();
		
		auftraege.add(new ProduktionsAuftrag(raumschiffTyp, unternehmen, menge));
		
		return true;
	}

}
