package de.dhbw.stargreg.code;

public class RaumschiffTyp extends ProduktTyp {

		public RaumschiffTyp(String name, int lagereinheiten) {
			super(name, lagereinheiten);
		}
		
		public String toString() {
			return "Raumschiff: " + this.name;
		}
		
		public double getKosten() {
			return 20;
		}
}
