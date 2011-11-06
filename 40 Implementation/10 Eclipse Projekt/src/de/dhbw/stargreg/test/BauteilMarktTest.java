package de.dhbw.stargreg.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.dhbw.stargreg.code.BauteilMarkt;
import de.dhbw.stargreg.code.BauteilTyp;
import de.dhbw.stargreg.code.Einkauf;

/**
 * @author Jan Schlenker
 * 
 */
public class BauteilMarktTest {

	private static BauteilMarkt bauteilMarkt = new BauteilMarkt();
	private static BauteilTyp rumpf;
	private static BauteilTyp hitzeschild;
	private static BauteilTyp triebwerk;
	
	/**
	 * @throws java.lang.Exception
	 * Initialisierung der drei Grundbauteile nach den Werten der Datenbasis
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		rumpf = new BauteilTyp("Rumpfbauteil", 1, 100.0, 40.0, BauteilTyp.Art.STANDARD);
		hitzeschild = new BauteilTyp("Hitzeschild", 2, 200.0, 80.0, BauteilTyp.Art.STANDARD);
		triebwerk = new BauteilTyp("Triebwerk", 5, 500.00, 200.00, BauteilTyp.Art.STANDARD);
		bauteilMarkt.fuegeTypHinzu(rumpf);
		bauteilMarkt.fuegeTypHinzu(hitzeschild);
		bauteilMarkt.fuegeTypHinzu(triebwerk);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * Test der Berechnung von Bauteilpreisen, nach dem verschiedene Einkäufe von Bauteilen getätigt wurden. 
	 * Für die Entwicklung der Preise ist nur entscheidend, welche Mengen der jeweiligen Bauteile eingekauft wurden. 
	 * Die Unternehmen der Einkäufe spielen keine Rolle, weshalb keine Unternehmensinstanzen erzeugt werden müssen.
	 */
	@Test
	public void TestBerechnePreise() {
		bauteilMarkt.fuegeTransaktionHinzu(new Einkauf(rumpf, null, 10, rumpf.getPreis()));
		bauteilMarkt.fuegeTransaktionHinzu(new Einkauf(hitzeschild, null, 20, hitzeschild.getPreis()));
		bauteilMarkt.fuegeTransaktionHinzu(new Einkauf(triebwerk, null, 20, triebwerk.getPreis()));
		bauteilMarkt.fuegeTransaktionHinzu(new Einkauf(triebwerk, null, 0, triebwerk.getPreis()));
		bauteilMarkt.simuliere();
		Assert.assertTrue(Math.round(rumpf.getPreis())==63.00);
		Assert.assertTrue(Math.round(hitzeschild.getPreis())==170.00);
		Assert.assertTrue(Math.round(triebwerk.getPreis())==693.00);
	}
	

	

}
