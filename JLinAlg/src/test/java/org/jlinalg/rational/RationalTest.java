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
package org.jlinalg.rational;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.Vector;
import org.jlinalg.doublewrapper.DoubleWrapper;
import org.jlinalg.rational.Rational.RationalFactory;
import org.jlinalg.testutil.RingElementTestBase;
import org.junit.Test;

/**
 * Test class {@link Rational}
 * 
 * @author Georg Thimm
 */
public class RationalTest
		extends RingElementTestBase<Rational>
{

	private static RationalFactory rFac = Rational.FACTORY;

	private static Rational zero = rFac.get(0, 1);

	private static Rational one = rFac.get(1, 1);

	private static Rational one_third = rFac.get(1, 3);

	private static Rational five_over_two = rFac.get(5, 2);

	private static Rational ten = rFac.get(BigInteger.TEN);

	private static Rational five_over_six = rFac.get(5, 6);

	/**
	 * test {@link Rational#subtract(org.jlinalg.IRingElement)} for selected
	 * values.
	 */
	@Test
	public void testSubtract()
	{
		assertEquals(rFac.get(2, 3), one.subtract(one_third));
		assertEquals(rFac.get(-13, 6), one_third.subtract(five_over_two));
		assertEquals(rFac.m_one(), zero.subtract(one));
	}

	/**
	 * test {@link Rational#abs()}
	 */
	@Test
	public final void testAbs()
	{
		Rational r1 = rFac.get(-4, 5);
		Rational r2 = rFac.get(4, 5);
		assertEquals(r2, r1.abs());
		assertEquals(r2, r2.abs());
		assertEquals(rFac.get(91, 73), rFac.get(-91, 73).abs());
	}

	/**
	 * test {@link Rational#add(org.jlinalg.IRingElement)}
	 */
	@Test
	public void testAdd()
	{
		assertEquals(rFac.get(11, 6), one.add(five_over_six));
		Rational r = rFac.get(1, 6);
		r = r.add(rFac.get(1, 3));
		r = r.add(rFac.get(1, 2));
		assertEquals(one, r);
	}

	/**
	 * test {@link Rational#compareTo(org.jlinalg.IRingElement)} for some
	 * values
	 */
	@Test
	public void testCompare()
	{
		assertEquals(0, five_over_two.compareTo(five_over_two));
		assertFalse(0 == five_over_two.compareTo(one));
		assertTrue(five_over_two.compareTo(one) > 0);
		assertTrue(one_third.compareTo(five_over_two) < 0);
		assertTrue(one_third.negate().compareTo(one) < 0);
		Rational mhalf = rFac.get(-1, 2);
		assertEquals(-1, mhalf.compareTo(getFactory().one()));
	}

	/**
	 * test {@link Rational#compareTo(org.jlinalg.IRingElement)}
	 */
	@SuppressWarnings("unchecked")
	@Test(expected = ClassCastException.class)
	public void testCompareToFieldsExceptions()
	{
		Rational one = rFac.get(1, 1);
		DoubleWrapper done = new DoubleWrapper(1);
		((IRingElement) one).compareTo(done); // force the use of compare.
	}

	/**
	 * test {@link RationalFactory#get(long, long)}
	 */
	@Test
	public final void testDoubleValue()
	{
		assertTrue(rFac.zero().doubleValue() + "!=0.0", rFac.zero()
				.doubleValue() == 0.0);
		assertTrue(rFac.one().doubleValue() + "!=1.0",
				rFac.one().doubleValue() == 1.0);
		assertTrue(rFac.m_one().doubleValue() + "!=-1.0", rFac.m_one()
				.doubleValue() == -1.0);
	}

	/**
	 * test {@link Rational#equals(Object)}
	 */
	@Test
	public final void testEqualsObject()
	{
		assertEquals(rFac.m_one(), rFac.get(-1, 1));
		assertEquals(rFac.one(), rFac.get(1, 1));
	}

	/**
	 * test {@link RationalFactory#get(long, long)}
	 */
	@Test
	public final void testGetNumeratorDeniminator()
	{
		Rational r = rFac.get(100, 99);
		assertTrue(r.getNumerator().intValue() == 100);
		assertTrue(r.getDenominator().intValue() == 99);
	}

	/**
	 * test {@link Rational.RationalFactory#get(Object)} for Strings
	 */
	@Test
	public void testGetStrings()
	{
		final Object[][] tests = {
				{
						"18", rFac.get(18, 1)
				}, {
						"1e8", rFac.get(100000000, 1)
				}, {
						"22e-4", rFac.get(22, 10000)
				}, {
						"4.5e2", rFac.get(450)
				}, {
						"-123.5432e7", rFac.get(-1235432000)
				}, {
						"-3.5e5", rFac.get(-350000)
				}, {
						"-7770000.0e-5", rFac.get(-7770000, 100000)
				}, {
						"-5.6e-2", rFac.get(-56, 1000)
				}, {
						"-5.65432e-2", rFac.get(-565432, 10000000)
				}, {
						"1/8", rFac.get(1, 8)
				}, {
						"100/-8", rFac.get(100, -8)
				}, {
						"-18/-82", rFac.get(-18, -82)
				}
		};
		final RationalFactory fac = rFac;
		for (int i = 0; i < tests.length; i++) {
			String s = (String) tests[i][0];
			Rational r = (Rational) tests[i][1];
			Rational sr = fac.get(s);
			assertEquals(r, sr);
		}
	}

	/**
	 * test {@link Rational#hashCode()}
	 */
	@Test
	public void testHashCode()
	{
		Rational r1 = rFac.get(1, 2);
		Rational r2 = rFac.get(1, 2);
		assertEquals(r1.hashCode(), r1.hashCode());
		assertEquals(r1.hashCode(), r2.hashCode());

		Rational a = rFac.get(10, 11);
		Rational b = rFac.get(10, 11);
		Rational c = rFac.get(1, 1);
		Rational d = rFac.get(1, 1);
		assertTrue("hashcode not equal: " + a + " & " + b, a.hashCode() == b
				.hashCode());
		assertTrue("hashcode not equal: " + c + " & " + d, c.hashCode() == d
				.hashCode());
		Vector<Rational> v1 = new Vector<Rational>(2, rFac);
		v1.set(1, a);
		v1.set(2, c);
		Vector<Rational> v2 = new Vector<Rational>(2, rFac);
		v2.set(1, b);
		v2.set(2, d);
		assertTrue("hashcode not equal: " + v1 + " & " + v2,
				v1.hashCode() == v2.hashCode());
	}

	/**
	 * test le(Rational) and ge(Rational)
	 */
	@Test
	public void testLeGe()
	{
		assertTrue(one.le(five_over_two));
		assertFalse(one.ge(rFac.get(five_over_two)));
		assertTrue(rFac.m_one().le(five_over_two));
		assertFalse(rFac.m_one().ge(rFac.get(five_over_two)));
		assertTrue(rFac.get(1.4E-12).le(rFac.get(1, 100000)));
		assertFalse(rFac.get(1.4E-12).ge(rFac.get(1, 100000)));
	}

	/**
	 * test various ways of getting the minus one value.
	 */
	@Test
	public final void testM_one()
	{
		BigInteger m_one = new BigInteger("-1");
		assertTrue(rFac.m_one().getNumerator().equals(m_one));
		assertTrue(rFac.one().getDenominator().equals(BigInteger.ONE));
		assertTrue((rFac.m_one()).getNumerator().equals(m_one));
		assertTrue((rFac.m_one()).getDenominator().equals(BigInteger.ONE));
		assertTrue(rFac.m_one().doubleValue() == -1.0);
	}

	/**
	 * test {@link Rational#multiply(org.jlinalg.IRingElement)}
	 */
	@Test
	public final void testMultiply()
	{
		assertSame(one, rFac.one().multiply(rFac.one()));
		assertSame(zero, rFac.one().multiply(zero));

		assertEquals(five_over_six, five_over_two.multiply(one_third));
		assertEquals(five_over_six, one_third.multiply(five_over_two));
		assertEquals(zero, rFac.zero().multiply(five_over_six));
		assertEquals(rFac.zero(), rFac.zero().multiply(five_over_six));
		assertEquals(rFac.zero(), zero.multiply(five_over_six));
		assertEquals(zero, rFac.one().multiply(rFac.zero()));
		assertEquals(rFac.zero(), rFac.one().multiply(rFac.zero()));
		assertEquals(rFac.zero(), one.multiply(zero));
		assertEquals(rFac.get(-6, 15), rFac.get(1, 3).multiply(rFac.get(-6, 5)));
	}

	/**
	 * test {@link Rational#negate()}
	 */
	@Test
	public final void testNegate()
	{
		assertEquals(rFac.get(3, 2), rFac.get(-3, 2).negate());
	}

	/**
	 * check whether {@link Rational#norm()} and {@link Rational#abs()} yield
	 * the same results.
	 */
	@Test
	public void testNorm()
	{
		assertEquals(rFac.one(), rFac.one().norm());
		assertEquals(rFac.one(), rFac.m_one().norm());
		assertEquals(rFac.get(-3, 7).abs(), rFac.get(-3, 7).norm());
		assertEquals(rFac.get(-30, 7).abs(), rFac.get(-30, 7).norm());
		assertEquals(rFac.get(77, 9).abs(), rFac.get(77, 9).norm());
	}

	/**
	 * test various ways of getting the one value.
	 */
	@Test
	public final void testOne()
	{
		assertTrue(rFac.one().getNumerator().equals(BigInteger.ONE));
		assertTrue(rFac.one().getDenominator().equals(BigInteger.ONE));
		assertTrue(rFac.one().getNumerator().equals(BigInteger.ONE));
		assertTrue(rFac.one().getDenominator().equals(BigInteger.ONE));
		assertTrue(rFac.one().doubleValue() == 1.0);
	}

	/**
	 * test {@link RationalFactory#get}
	 */
	@Test
	public final void testRationalBigInteger()
	{
		assertEquals(rFac.zero(), rFac.get(BigInteger.ZERO));
		Rational r = rFac.get(10, 1);
		assertEquals(ten, r);
	}

	/**
	 * test {@link Rational#equals(Object)}
	 */
	@Test
	public final void testRationalBigIntegerBigIntegerBoolean()
	{
		assertEquals(rFac.zero(), rFac.get(BigInteger.ZERO,
				new BigInteger("1"), true));
		Rational r = rFac.get(new BigInteger("10"), BigInteger.ONE, true);
		assertEquals(r, ten);
		r = rFac.get(new BigInteger("100"), BigInteger.TEN, true);
		assertEquals(r, ten);
		r = rFac.get(new BigInteger("3024"), new BigInteger("33264"), true);
		assertTrue(r.equals(rFac.get(1, 11)));
	}

	/**
	 * test {@link Rational#equals(Object)}
	 */
	@Test
	public final void testRationalDouble()
	{
		assertEquals(rFac.zero(), rFac.get(0.0));
		assertEquals(one, rFac.get(1.0));
		assertEquals(rFac.m_one(), rFac.get(-1.0));
		assertEquals(rFac.get(-1, 2), rFac.get(-0.5));
	}

	/**
	 * test {@link Rational#toString()}
	 */
	@Test
	public final void testToString()
	{
		assertEquals("1", one.toString());
		assertEquals("100/99", rFac.get(100, 99).toString());
		assertEquals("10", ten.toString());
		assertEquals("-1", rFac.get(-99, 99).toString());
		assertEquals("-1/2", rFac.get(-1, 2).toString());
	}

	/**
	 * test various ways of getting the zero value.
	 */
	@Test
	public final void testZero()
	{
		assertEquals(BigInteger.ZERO, rFac.zero().getNumerator());
		assertEquals(BigInteger.ONE, rFac.zero().getDenominator());
	}

	/**
	 * verify that the denominator is not becoming zero.
	 */
	@Test
	public void testNonZeroDenominator()
	{
		assertEquals(1, rFac.zero().getDenominator().intValue());
		Rational r = rFac.get(1, 77);
		Rational p = rFac.get(1, 77);
		assertEquals("1/77*0", 1, r.multiply(rFac.zero()).getDenominator()
				.intValue());
		assertEquals("1/77-1/77", 1, r.subtract(p).getDenominator().intValue());
	}

	@Override
	public IRingElementFactory<Rational> getFactory()
	{
		return rFac;
	}

	@Test
	public void testInvert()
	{
		assertEquals(rFac.get(3), one_third.invert());
		assertEquals(rFac.get(2, 5), five_over_two.invert());
		assertEquals(rFac.get(-2, 5), five_over_two.negate().invert());
		assertEquals(rFac.get(13, 7), rFac.get(7, 13).invert());
		assertEquals(rFac.get(-13, 7), rFac.get(-7, 13).invert());
	}

	@Test
	public void testDivide()
	{
		assertEquals(one_third, one_third.divide(one));
		assertEquals(rFac.get(3, 1), one.divide(one_third));
		assertEquals(rFac.get(15, 2), five_over_two.divide(one_third));
		assertEquals(rFac.get(13 * 4, 7 * 3), rFac.get(13, 7).divide(
				rFac.get(3, 4)));
		assertEquals(rFac.get(-13 * 4, 7 * 3), rFac.get(13, 7).divide(
				rFac.get(-3, 4)));
	}

	/**
	 * test whether the inversion of zero results in an exception
	 */
	@Override
	@Test(expected = org.jlinalg.DivisionByZeroException.class)
	public void invertZero_base()
	{
		getFactory().zero().invert();
	}
}
