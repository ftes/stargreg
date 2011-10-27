/**
 * 
 */
package de.dhbw.stargreg.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.dhbw.stargreg.code.BauteilTyp;

/**
 * @author Jan Schlenker
 *
 */
public class BauteilTypTest {

	public static BauteilTyp rumpfbauteil;
	/**
	 * @throws java.lang.Exception
	 * Initialisierung eines Grundbauteils nach den Werten der Datenbasis
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		rumpfbauteil = new BauteilTyp("rumpfbauteil", 1, 100.00, 40.00);
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
	 * Test der Preisveränderung eines Bauteils bei gegebener Abweichung
	 */
	@Test
	public void testBerechnePreis() {
		rumpfbauteil.berechnePreis(10);
		Assert.assertTrue(Math.round(rumpfbauteil.getPreis()) == 140.00);
	}

}
