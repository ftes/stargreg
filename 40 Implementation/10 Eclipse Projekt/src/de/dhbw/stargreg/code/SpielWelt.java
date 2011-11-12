package de.dhbw.stargreg.code;

import java.util.Vector;

import de.dhbw.stargreg.util.Util;

public class SpielWelt {
	/**
	 * Liste aller Unternehmen
	 */
	private final Vector<Unternehmen> unternehmen = new Vector<Unternehmen>();
	
	private RaumschiffMarkt raumschiffMarkt = new RaumschiffMarkt();
	
	private PersonalMarkt personalMarkt = new PersonalMarkt();
	
	private BauteilMarkt bauteilMarkt = new BauteilMarkt();
	
	private KapitalMarkt kapitalMarkt = new KapitalMarkt();
	
	private Spiel spiel;
	
	public SpielWelt(Spiel spiel) {
		this.spiel = spiel;
	}
	
	public int getAnzahlUnternehmen() {
		return unternehmen.size();
	}
	
	public void fuegeUnternehmenHinzu(Unternehmen unternehmen) {
		this.unternehmen.add(unternehmen);
	}
	
	public Vector<Unternehmen> getUnternehmen() {
		return unternehmen;
	}
	
	public Vector<Transaktion> simuliere() {
		Vector<Transaktion> transaktionen = new Vector<Transaktion>();
		
		Util.printHeading("Simulation der Spielrunde");
		
		for (Markt<?> markt : getMaerkte()) {
			markt.simuliere();
		}
		
		for (Unternehmen unternehmen : this.unternehmen) {
			unternehmen.simuliere();
		}
		
		for (Markt<?> markt : getMaerkte()) {
			transaktionen.addAll(markt.getTransaktionen());
			markt.loescheTransaktionen();
		}
		transaktionen.addAll(raumschiffMarkt.getAngebote());
		raumschiffMarkt.loescheAngebote();

		Util.pause();
		
		return transaktionen;
	}
	
	public RaumschiffMarkt getRaumschiffMarkt() {
		return raumschiffMarkt;
	}
	
	public BauteilMarkt getBauteilMarkt() {
		return bauteilMarkt;
	}
	
	public PersonalMarkt getPersonalMarkt() {
		return personalMarkt;
	}
	
	public KapitalMarkt getKapitalMarkt() {
		return kapitalMarkt;
	}
	
	public Vector<SpielRunde> getSpielRunden() {
		return spiel.getSpielRunden();
	}
	
	public SpielRunde getAktuelleSpielRunde() {
		return spiel.getAktuelleSpielRunde();
	}
	
	public SpielRunde getVorherigeSpielRunde() {
		return spiel.getVorherigeSpielRunde();
	}
	
	public RaumschiffTyp getStarDerLetztenRunde() {
		return spiel.getStarDerLetztenRunde();
	}
	
	public Vector<Markt<?>> getMaerkte() {
		Vector<Markt<?>> vector = new Vector<Markt<?>>();
		vector.add(bauteilMarkt);
		vector.add(raumschiffMarkt);
		vector.add(personalMarkt);
		vector.add(kapitalMarkt);
		return vector;
	}
}
