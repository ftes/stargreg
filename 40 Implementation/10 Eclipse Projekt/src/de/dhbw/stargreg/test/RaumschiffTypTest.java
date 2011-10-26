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
	
	public static RaumschiffTyp xwing;
	private static BauteilTyp rumpf;
	private static BauteilTyp hitzeschild;
	private static BauteilTyp triebwerk;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		xwing = new RaumschiffTyp("xwing", 5, 900.00);
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
		Assert.assertTrue(xwing.getBauteile().containsKey(hitzeschild));
	}
	
	@Test
	public void testGetLagerplatzEinheiten() {
	}

	

}
