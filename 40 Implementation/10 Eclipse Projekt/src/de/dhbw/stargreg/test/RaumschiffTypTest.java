package de.dhbw.stargreg.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.dhbw.stargreg.code.BauteilTyp;
import de.dhbw.stargreg.code.RaumschiffTyp;

public class RaumschiffTypTest {
	
	private static RaumschiffTyp xwing;
	private static RaumschiffTyp corellian_corvette;
	private static RaumschiffTyp millenium_falke;
	
	private static BauteilTyp rumpf;
	private static BauteilTyp hitzeschild;
	private static BauteilTyp triebwerk;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		xwing = new RaumschiffTyp("xwing", 5, 900.00);
		corellian_corvette = new RaumschiffTyp("Correlian Corvette", 10, 1800.00);
		millenium_falke = new RaumschiffTyp("Millenium Falke", 15, 2700.00);
		rumpf = new BauteilTyp("Rumpfbauteil", 1, 100.0, 40.0);
		hitzeschild = new BauteilTyp("Hitzeschild", 2, 200.0, 80.0);
		triebwerk = new BauteilTyp("Triebwerk", 5, 500.00, 200.00);
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFuegeBauteilHinzu() {
		xwing.fuegeBauteilHinzu(rumpf, 18);
		xwing.fuegeBauteilHinzu(hitzeschild, 6);
		xwing.fuegeBauteilHinzu(triebwerk, 4);
		Assert.assertTrue(xwing.getBauteile().containsKey(rumpf));
		Assert.assertTrue(xwing.getBauteile().containsKey(hitzeschild));
		Assert.assertTrue(xwing.getBauteile().containsKey(triebwerk));
		Assert.assertTrue(xwing.getBauteile().get(rumpf) == 18);
		Assert.assertTrue(xwing.getBauteile().get(hitzeschild) == 6);
		Assert.assertTrue(xwing.getBauteile().get(triebwerk) == 4);	
	}
	
	@Test
	public void testGetKosten(){
		corellian_corvette.fuegeBauteilHinzu(rumpf, 38);
		corellian_corvette.fuegeBauteilHinzu(hitzeschild, 16);
		corellian_corvette.fuegeBauteilHinzu(triebwerk, 6);
		Assert.assertTrue(corellian_corvette.getKosten() == 10000.00);
	}
	
	@Test
	public void testGetLagerplatzEinheiten() {
		millenium_falke.fuegeBauteilHinzu(rumpf, 40);
		millenium_falke.fuegeBauteilHinzu(hitzeschild, 30);
		millenium_falke.fuegeBauteilHinzu(triebwerk, 10);
		Assert.assertTrue(millenium_falke.getLagerplatzEinheiten() == 150);
	}
	

	
	

	

}
