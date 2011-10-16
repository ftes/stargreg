package de.dhbw.stargreg.code;


/**
 * Eine Spielrunde verwaltet die M�rkte im aktuellen Zustand, und somit indirekt alle
 * f�r diese Runde wichtigen Daten.
 * @author fredrik
 *
 */
public class SpielRunde {
	/**
	 * Bauteilmarkt f�r diese Spielrunde
	 */
	private BauteilMarkt bauteilMarkt = new BauteilMarkt();
	
	/**
	 * Raumschiffmarkt f�r diese Spielrunde
	 */
	private RaumschiffMarkt raumschiffMarkt = new RaumschiffMarkt();
	
	/**
	 * Personalmarkt f�r diese Spielrunde
	 */
	private PersonalMarkt personalMarkt = new PersonalMarkt();
	
	/**
	 * F�hrt die Simulationen auf allen M�rkten aus
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
	 * laufenden und Werbungskosten f�r das Personal.
	 * Es wurde bewusst auf die Verwednung des {@code Clonable} Interfaces verzichtet, da dies lediglich
	 * ein Shallow-Copy durchf�hrt und einige Probleme mit sich bringt.
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
