package de.dhbw.stargreg.code;

public final class BauteilTyp extends ProduktTyp {
	private Double preis;
	private Double startpreis;
	
	public BauteilTyp(String name_c, int lagerplatzeinheiten_c, Double preis_c, Double startpreis_c ){
		super(name_c, lagerplatzeinheiten_c);
		this.preis = preis_c;
		this.startpreis = startpreis_c;
	}
	
	public Double getPreis(){
		return this.preis;
	}
	
	public Double getStartpreis(){
		return this.startpreis;
	}
	
	public void änderePreis(Double preis_l){
		this.preis = preis_l;
	}
	
	
}
