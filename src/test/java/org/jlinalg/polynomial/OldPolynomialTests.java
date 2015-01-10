/*
 * This file is part of JLinAlg (<http://jlinalg.sourceforge.net/>).
 * 
 * JLinAlg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 * 
 * JLinAlg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with JLinALg. If not, see <http://www.gnu.org/licenses/>.
 */
package org.jlinalg.polynomial;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.jlinalg.IRingElement;
import org.jlinalg.complex.Complex;
import org.jlinalg.complex.Complex.ComplexFactory;
import org.jlinalg.doublewrapper.DoubleWrapper;
import org.jlinalg.field_p.FieldPAbstractFactory;
import org.jlinalg.field_p.FieldPFactoryMap;
import org.jlinalg.rational.Rational;
import org.jlinalg.rational.Rational.RationalFactory;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Georg Thimm
 */
public class OldPolynomialTests
{

	static Polynomial<Rational> poly1;
	static String poly1Value = "1+2*x+3/5*x^2+1*x^3+(-1)*x^4";
	static Polynomial<Rational> poly2;
	static String poly2Value = "2+1*x";

	static PolynomialFactory<Rational> rationalPolyFactory;

	/**
	 * @throws java.lang.Exception
	 */
	@SuppressWarnings("boxing")
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

		Map<Integer, IRingElement<?>> coeff2 = new HashMap<Integer, IRingElement<?>>();
		coeff2.put(0, Rational.FACTORY.get(2));
		coeff2.put(1, Rational.FACTORY.get(1));
		poly2 = rationalPolyFactory.get(coeff2);

	}

	/**
	 * a prime number from the Woodall series
	 */
	final static String prime1 = "32212254719";
	/**
	 * a Wagstaff prime
	 */
	final String prime2 = "2932031007403";

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

	/**
	 * assert that a ring elements and a given String match.
	 * 
	 * @param ringElement
	 * @param s
	 */
	private void compareResult(IRingElement<?> ringElement, String s)
	{
		String p = ringElement.toString().replaceAll(" ", "");
		assertTrue(p + " should be: " + s, p.equals(s.replaceAll(" ", "")));
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
	 * test negation
	 */
	@Test
	public void negation()
	{
		compareResult(poly1.negate(), "-1+(-2)*x+(-3/5)*x^2+(-1)*x^3+1*x^4");
		compareResult(poly2.negate(), "-2+(-1)*x");
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

	/**
	 * test whether the factories are unique if the same base type DoubleWrapper
	 * is used for their creation.
	 */
	@Test
	public void testCompareFactoriesDoubleWrapper()
	{
		PolynomialFactory<DoubleWrapper> f1 = PolynomialFactory
				.getFactory(DoubleWrapper.FACTORY);
		PolynomialFactory<DoubleWrapper> f2 = PolynomialFactory
				.getFactory(DoubleWrapper.FACTORY);
		assertTrue(f1 == f2);
	}

	/**
	 * test whether the factories are unique if the same base type FieldP is
	 * used for their creation.
	 */
	@Test
	public void testCompareFactoriesFieldP()
	{
		FieldPAbstractFactory<?> f1 = FieldPFactoryMap.getFactory(Long
				.valueOf(17L));
		FieldPAbstractFactory<?> f2 = FieldPFactoryMap.getFactory(Long
				.valueOf(17L));
		assertTrue(f1.equals(f2));
		assertSame(f1, f2);
		f2 = FieldPFactoryMap.getFactory(Long.valueOf(19L));
		assertNotSame(f1, f2);
	}

	/**
	 * test whether the factories are unique if the same base type FieldP is
	 * used for their creation.
	 */
	@Test
	public void testCompareFactoriesFieldPBig()
	{
		FieldPAbstractFactory<?> f1 = FieldPFactoryMap.getFactory(prime1);
		FieldPAbstractFactory<?> f2 = FieldPFactoryMap.getFactory(prime1);
		assertTrue(f1.equals(f2));
		assertSame(f1, f2);
		f2 = FieldPFactoryMap.getFactory(prime2);
		assertNotSame(f1, f2);
	}

	@Test
	public void testCreateComplexPolynomial()
	{
		ComplexFactory cfac = Complex.FACTORY;
		PolynomialFactory<Complex> pfac_c = PolynomialFactory.getFactory(cfac);
		Polynomial<Complex> c1 = pfac_c.zero();
		assertEquals("0", c1.toString());
		c1 = pfac_c.one();
		assertEquals("1", c1.toString());
		c1 = pfac_c.m_one();
		assertEquals("-1", c1.toString());
	}

	@Test
	public void testCreateRationalPolynomial()
	{
		RationalFactory rfac = Rational.FACTORY;
		PolynomialFactory<Rational> pfac_r = PolynomialFactory.getFactory(rfac);
		Polynomial<Rational> p1 = pfac_r.zero();
		assertEquals("0", p1.toString());
		p1 = pfac_r.one();
		assertEquals("1", p1.toString());
		p1 = pfac_r.m_one();
		assertEquals("-1", p1.toString().replaceAll(" ", ""));
	}

}
