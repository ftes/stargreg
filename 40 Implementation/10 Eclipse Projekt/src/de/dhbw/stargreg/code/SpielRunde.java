package de.dhbw.stargreg.code;


/**
 * Eine Spielrunde verwaltet die Märkte im aktuellen Zustand, und somit indirekt alle
 * für diese Runde wichtigen Daten.
 * @author fredrik
 *
 */
public class SpielRunde {
	/**
	 * Bauteilmarkt für diese Spielrunde
	 */
	private BauteilMarkt bauteilMarkt = new BauteilMarkt();
	
	/**
	 * Raumschiffmarkt für diese Spielrunde
	 */
	private RaumschiffMarkt raumschiffMarkt = new RaumschiffMarkt();
	
	/**
	 * Personalmarkt für diese Spielrunde
	 */
	private PersonalMarkt personalMarkt = new PersonalMarkt();
	
	/**
	 * Führt die Simulationen auf allen Märkten aus
	 */
	public void simuliere() {

	}

	public BauteilMarkt getBauteilMarkt() {
		return bauteilMarkt;
	}

	public void setBauteilMarkt(BauteilMarkt bauteilMarkt) {
		this.bauteilMarkt = bauteilMarkt;
	}

	public RaumschiffMarkt getRaumschiffMarkt() {
		return raumschiffMarkt;
	}

	public void setRaumschiffMarkt(RaumschiffMarkt raumschiffMarkt) {
		this.raumschiffMarkt = raumschiffMarkt;
	}

	public PersonalMarkt getPersonalMarkt() {
		return personalMarkt;
	}

	public void setPersonalMarkt(PersonalMarkt personalMarkt) {
		this.personalMarkt = personalMarkt;
	}
	
	/**
	 * Erzeugt eine Kopie der Spielrunde mit gleicher Nachfrage auf dem Raumschiffmarkt und gleichen
	 * laufenden und Werbungskosten für das Personal.
	 * Es wurde bewusst auf die Verwednung des {@code Clonable} Interfaces verzichtet, da dies lediglich
	 * ein Shallow-Copy durchführt und einige Probleme mit sich bringt.
	 */
	public SpielRunde clone() {
		SpielRunde spielRunde = new SpielRunde();
		for (RaumschiffTyp raumschiffTyp : RaumschiffMarkt.getRaumschiffTypen()) {
			int nachfrage = getRaumschiffMarkt().getNachfrage(raumschiffTyp);
			spielRunde.getRaumschiffMarkt().setNachfrage(raumschiffTyp, nachfrage);
		}
		for (PersonalTyp personalTyp : PersonalMarkt.getPersonalTypen()) {
			double laufendeKosten = getPersonalMarkt().getLaufendeKosten(personalTyp);
			double werbungsKosten = getPersonalMarkt().getWerbungsKosten(personalTyp);
			spielRunde.getPersonalMarkt().setLaufendeKosten(personalTyp, laufendeKosten);
			spielRunde.getPersonalMarkt().setWerbungsKosten(personalTyp, werbungsKosten);
		}
		return spielRunde;
	}

}
