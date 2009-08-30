/**
 * 
 */
package org.jlinalg;

import static org.junit.Assert.assertTrue;

import org.jlinalg.Complex;
import org.jlinalg.Rational;
import org.jlinalg.Complex.ComplexFactory;
import org.jlinalg.Rational.RationalFactory;
import org.jlinalg.polynomial.Polynomial;
import org.jlinalg.polynomial.PolynomialFactory;
import org.junit.Test;

/**
 * @author mgeorg
 */
public class PolynomialTest
{
	@Test
	public void testCreateFactory()
	{
		RationalFactory rfac = Rational.FACTORY;
		PolynomialFactory<Rational> pfac_r = PolynomialFactory.getFactory(rfac);
		Polynomial<Rational> p1 = pfac_r.zero();
		assertTrue(p1.toString().equals("0"));
		p1 = pfac_r.one();
		assertTrue("p1='" + p1.toString(), p1.toString().equals("1"));
		p1 = pfac_r.m_one();
		assertTrue("p1='" + p1.toString() + "' instead of \'-1'", p1.toString()
				.replaceAll(" ", "").equals("-1"));

		ComplexFactory cfac = Complex.FACTORY;
		PolynomialFactory<Complex> pfac_c = PolynomialFactory.getFactory(cfac);
		Polynomial<Complex> c1 = pfac_c.zero();
		assertTrue(c1.toString().equals("0"));
		c1 = pfac_c.one();
		assertTrue(c1.toString().equals("1"));
		c1 = pfac_c.m_one();
		assertTrue(c1.toString() + "!= -1", c1.toString().equals("-1"));
	}
}
