/**
 * 
 */
package org.jlinalg.polynomial;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.jlinalg.IRingElement;
import org.jlinalg.Rational;
import org.jlinalg.polynomial.Polynomial;
import org.jlinalg.polynomial.PolynomialFactory;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Georg Thimm
 */
public class PolynomialOperationsTest
{
	static PolynomialFactory<Rational> rationalPolyFactory;

	static Polynomial<Rational> poly1;

	static String poly1Value = "1+2*x+3/5*x^2+1*x^3+(-1)*x^4";

	static Polynomial<Rational> poly2;

	static String poly2Value = "2+1*x";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		rationalPolyFactory = PolynomialFactory.getFactory(Rational.FACTORY);

		Map<Integer, Rational> coeff1 = new HashMap<Integer, Rational>();
		coeff1.put(0, Rational.FACTORY.get(1));
		coeff1.put(1, Rational.FACTORY.get(2));
		coeff1.put(2, Rational.FACTORY.get(3, 5));
		coeff1.put(3, Rational.FACTORY.one());
		coeff1.put(4, Rational.FACTORY.m_one());
		poly1 = rationalPolyFactory.get(coeff1, Rational.FACTORY);
		System.out.println("poly1: " + poly1);

		Map<Integer, IRingElement> coeff2 = new HashMap<Integer, IRingElement>();
		coeff2.put(0, Rational.FACTORY.get(2));
		coeff2.put(1, Rational.FACTORY.get(1));
		poly2 = rationalPolyFactory.get(coeff2);

	}

	/**
	 * test whether the creation of the underlying polynomials is OK.
	 */
	@Test
	public void polyOK()
	{
		compareResult(poly1, poly1Value);
		compareResult(poly2, poly2Value);
	}

	/**
	 * test negation
	 */
	@Test
	public void negation()
	{
		compareResult(poly1.negate(), "-1+(-2)*x+(-3/5)*x^2+(-1)*x^3+1*x^4");
		compareResult(poly2.negate(), "-2+(-1)*x");
	}

	@Test
	public void addition()
	{
		compareResult(poly1.add(rationalPolyFactory.zero()), poly1Value);
		compareResult(poly2.add(rationalPolyFactory.zero()), poly2Value);
		compareResult(poly1.add(poly2), "3+3*x+3/5*x^2+1*x^3+(-1)*x^4");
		compareResult(poly2.add(poly1), "3+3*x+3/5*x^2+1*x^3+(-1)*x^4");
		compareResult(poly1.add(poly2).add(rationalPolyFactory.zero()),
				"3+3*x+3/5*x^2+1*x^3+(-1)*x^4");
		compareResult(poly2.add(poly1).add(rationalPolyFactory.zero()),
				"3+3*x+3/5*x^2+1*x^3+(-1)*x^4");
		compareResult(poly2.add(rationalPolyFactory.one()), "3+1*x");
		compareResult(poly2.add(rationalPolyFactory.one()).add(
				rationalPolyFactory.m_one()), poly2Value);
		compareResult(poly1.add(rationalPolyFactory.one()).add(
				rationalPolyFactory.m_one()), poly1Value);
		compareResult(rationalPolyFactory.m_one().add(poly2).add(
				rationalPolyFactory.one()), poly2Value);
		compareResult(rationalPolyFactory.m_one().add(poly1).add(
				rationalPolyFactory.one()), poly1Value);
	}

	@Test
	public void subtraction()
	{
		compareResult(poly1.subtract(rationalPolyFactory.zero()), poly1Value);
		compareResult(poly2.subtract(rationalPolyFactory.zero()), poly2Value);
		compareResult(poly1.subtract(poly2), "-1+1*x+3/5*x^2+1*x^3+(-1)*x^4");
		compareResult(poly2.subtract(poly1),
				"1+(-1)*x+(-3/5)*x^2+(-1)*x^3+1*x^4");
		compareResult(poly1.subtract(poly2)
				.subtract(rationalPolyFactory.zero()),
				"-1+1*x+3/5*x^2+1*x^3+(-1)*x^4");
		compareResult(poly2.subtract(poly1)
				.subtract(rationalPolyFactory.zero()),
				"1+(-1)*x+(-3/5)*x^2+(-1)*x^3+1*x^4");
		compareResult(poly2.subtract(rationalPolyFactory.one()), "1+1*x");
		compareResult(poly2.subtract(rationalPolyFactory.one()).subtract(
				rationalPolyFactory.m_one()), poly2Value);
		compareResult(poly1.subtract(rationalPolyFactory.one()).subtract(
				rationalPolyFactory.m_one()), poly1Value);
		compareResult(rationalPolyFactory.m_one().subtract(poly2).add(
				rationalPolyFactory.one()).negate(), poly2Value);
		compareResult(rationalPolyFactory.m_one().subtract(poly1).add(
				rationalPolyFactory.one()).negate(), poly1Value);
	}

	@Test
	public void multiplication()
	{
		compareResult(poly1.multiply(rationalPolyFactory.zero()), "0");
		compareResult(poly2.multiply(rationalPolyFactory.zero()), "0");
		compareResult(poly1.multiply(rationalPolyFactory.one()), poly1Value);
		compareResult(poly2.multiply(rationalPolyFactory.one()), poly2Value);
		compareResult(poly1.multiply(rationalPolyFactory.m_one()).negate(),
				poly1Value);
		compareResult(poly2.multiply(rationalPolyFactory.m_one()).negate(),
				poly2Value);
		compareResult(poly2.multiply(poly2), "4+4*x+1*x^2");
		compareResult(poly1.multiply(poly2),
				"2+5*x+16/5*x^2+13/5*x^3+(-1)*x^4+(-1)*x^5");
	}

	/**
	 * assert that a ring elements and a given String match.
	 * 
	 * @param ringElement
	 * @param s
	 */
	private void compareResult(IRingElement ringElement, String s)
	{
		String p = ringElement.toString().replaceAll(" ", "");
		assertTrue(p + " should be: " + s, p.equals(s.replaceAll(" ", "")));
	}

}
