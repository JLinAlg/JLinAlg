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
package org.jlinalg.fastrational;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.jlinalg.IRingElement;
import org.jlinalg.Vector;
import org.jlinalg.doublewrapper.DoubleWrapper;
import org.jlinalg.doublewrapper.DoubleWrapperFactory;
import org.junit.Test;

/**
 * Test class {@link FastRational}
 * 
 * @author Georg Thimm
 */
public class FastRationalTest
		extends
		FastRationalTestBase
{

	private static FastRationalFactory rFac = FastRational.FACTORY;

	private static FastRational zero = rFac.get(0, 1);

	private static FastRational one = rFac.get(1, 1);

	private static FastRational one_third = rFac.get(1, 3);

	private static FastRational five_over_two = rFac.get(5, 2);

	private static FastRational ten = rFac.get(10);

	private static FastRational five_over_six = rFac.get(5, 6);

	/**
	 * test {@link FastRational#divide(org.jlinalg.IRingElement)} for random
	 * values
	 */
	@Test
	public void testDivide()
	{
		assertEquals(one, one.divide(rFac.one()));
		assertEquals(one_third, one_third.divide(one));
		assertEquals(rFac.get(3, 1), one.divide(one_third));
		assertEquals(zero, zero.divide(one));
		assertEquals(rFac.get(15, 2), five_over_two.divide(one_third));
		FastRational r = one.divide(rFac.m_one());
		assertTrue("denominator negative: " + r.getDenominator(),
				r.getDenominator() > 0L);
		assertTrue(r.getNumerator() == -1);
		assertEquals(rFac.get(13 * 4, 7 * 3),
				rFac.get(13, 7).divide(rFac.get(3, 4)));
		assertEquals(rFac.get(-13 * 4, 7 * 3),
				rFac.get(13, 7).divide(rFac.get(-3, 4)));
	}

	/**
	 * test {@link FastRational#invert()} for random values
	 */
	@Test
	public void TestInvert()
	{
		assertEquals("1/1=1", rFac.one(), rFac.one().invert());
		assertEquals(rFac.get(3), one_third.invert());
		assertEquals(rFac.get(2, 5), five_over_two.invert());
		assertEquals(rFac.get(-2, 5), five_over_two.negate().invert());
	}

	/**
	 * test {@link FastRational#lt(org.jlinalg.IRingElement)}
	 * {@link FastRational#gt(org.jlinalg.IRingElement)}
	 * {@link FastRational#compareTo(org.jlinalg.IRingElement)}
	 * {@link FastRational#equals(Object)}
	 */
	@Test
	public void misc()
	{
		assertTrue(one.lt(five_over_two));
		assertTrue(five_over_two.gt(one));
		assertTrue(one.compareTo(rFac.one()) == 0);
		FastRational n = five_over_two.negate();
		assertTrue(n.lt(rFac.zero()));
		assertTrue(n.negate().equals(five_over_two));
		assertTrue(n.abs().equals(five_over_two));
	}

	/**
	 * test {@link FastRational#subtract(org.jlinalg.IRingElement)} for selected
	 * values.
	 */
	@Test
	public void TestSubtract()
	{
		assertEquals(rFac.get(2, 3), one.subtract(one_third));
		assertEquals(rFac.get(-13, 6), one_third.subtract(five_over_two));
		assertEquals(rFac.m_one(), zero.subtract(one));
	}

	/**
	 * test {@link FastRational#abs()}
	 */
	@Test
	public final void testAbs()
	{
		FastRational r1 = rFac.get(-4, 5);
		FastRational r2 = rFac.get(4, 5);
		assertTrue(r1.abs().equals(r2));
		assertEquals(rFac.get(91, 73), rFac.get(-91, 73).abs());
	}

	/**
	 * test {@link FastRational#add(org.jlinalg.IRingElement)}
	 */
	@Test
	public void testAdd()
	{
		assertEquals(rFac.get(11, 6), one.add(five_over_six));
		FastRational r = rFac.get(1, 6);
		r = r.add(rFac.get(1, 3));
		r = r.add(rFac.get(1, 2));
		assertEquals(one, r);
	}

	/**
	 * test {@link FastRational#compareTo(org.jlinalg.IRingElement)} for some
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
		FastRational mhalf = rFac.get(-1, 2);
		assertEquals(-1, mhalf.compareTo(getFactory().one()));
	}

	/**
	 * test {@link FastRational#compareTo(org.jlinalg.IRingElement)}
	 */
	@SuppressWarnings({
			"unchecked", "rawtypes"
	})
	@Test(expected = ClassCastException.class)
	public void testCompareToFieldsExceptions()
	{
		FastRational one = rFac.get(1, 1);
		DoubleWrapper done = DoubleWrapperFactory.INSTANCE.one();
		((IRingElement) one).compareTo(done); // force the use of compare.
	}

	/**
	 * test {@link FastRationalFactory#get(long, long)}
	 */
	@Test
	public final void testDoubleValue()
	{
		assertTrue(rFac.zero().doubleValue() + "!=0.0",
				rFac.zero().doubleValue() == 0.0);
		assertTrue(rFac.one().doubleValue() + "!=1.0",
				rFac.one().doubleValue() == 1.0);
		assertTrue(rFac.m_one().doubleValue() + "!=-1.0",
				rFac.m_one().doubleValue() == -1.0);
	}

	/**
	 * test {@link FastRational#equals(Object)}
	 */
	@Test
	public final void testEqualsObject()
	{
		assertEquals(rFac.m_one(), rFac.get(-1, 1));
		assertEquals(rFac.one(), rFac.get(1, 1));
	}

	/**
	 * test {@link FastRationalFactory#get(long, long)}
	 */
	@Test
	public final void testGetNumeratorDenominator()
	{
		FastRational r = rFac.get(100, 99);
		assertTrue(r.getNumerator() == 100);
		assertTrue(r.getDenominator() == 99);
	}

	/**
	 * test {@link FastRational.FastRationalFactory#get(Object)} for Strings
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
						"-123.54e3", rFac.get(-123540)
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
		final FastRationalFactory fac = rFac;
		for (int i = 0; i < tests.length; i++) {
			String s = (String) tests[i][0];
			FastRational r = (FastRational) tests[i][1];
			FastRational sr = fac.get(s);
			assertEquals(r, sr);
		}
	}

	/**
	 * test {@link FastRational#hashCode()}
	 */
	@Test
	public void testHashCode()
	{
		FastRational r1 = rFac.get(1, 2);
		FastRational r2 = rFac.get(1, 2);
		assertEquals(r1.hashCode(), r1.hashCode());
		assertEquals(r1.hashCode(), r2.hashCode());

		FastRational a = rFac.get(10, 11);
		FastRational b = rFac.get(10, 11);
		FastRational c = rFac.get(1, 1);
		FastRational d = rFac.get(1, 1);
		assertTrue("hashcode not equal: " + a + " & " + b,
				a.hashCode() == b.hashCode());
		assertTrue("hashcode not equal: " + c + " & " + d,
				c.hashCode() == d.hashCode());
		Vector<FastRational> v1 = new Vector<>(2, rFac);
		v1.set(1, a);
		v1.set(2, c);
		Vector<FastRational> v2 = new Vector<>(2, rFac);
		v2.set(1, b);
		v2.set(2, d);
		assertTrue("hashcode not equal: " + v1 + " & " + v2,
				v1.hashCode() == v2.hashCode());
	}

	/**
	 * test le(FastRational) and ge(FastRational)
	 */
	@Test
	public void testLeGe()
	{
		assertTrue(one.le(five_over_two));
		assertFalse(one.ge(five_over_two));
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
		assertEquals(-1, rFac.m_one().getNumerator());
		assertEquals(1, rFac.m_one().getDenominator());
		assertEquals(-1.0, rFac.m_one().doubleValue(), 0.0);
	}

	/**
	 * test {@link FastRational#multiply(org.jlinalg.IRingElement)}
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
		assertEquals(rFac.get(-6, 15),
				rFac.get(1, 3).multiply(rFac.get(-6, 5)));
	}

	/**
	 * test {@link FastRational#negate()}
	 */
	@Test
	public final void testNegate()
	{
		assertEquals(rFac.get(3, 2), rFac.get(-3, 2).negate());
	}

	/**
	 * check whether {@link FastRational#norm()} and {@link FastRational#abs()}
	 * yield
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
		assertEquals(1, rFac.one().getNumerator());
		assertEquals(1, rFac.one().getDenominator());
		assertEquals(1, rFac.one().getNumerator());
		assertEquals(1, rFac.one().getDenominator());
		assertEquals(1.0, rFac.one().doubleValue(), 0.0);
	}

	/**
	 * test {@link FastRationalFactory#get}
	 */
	@Test
	public final void testFastRationalBigInteger()
	{
		assertEquals(rFac.zero(), rFac.get(BigInteger.ZERO));
		FastRational r = rFac.get(10, 1);
		assertEquals(ten, r);
	}

	/**
	 * test {@link FastRational#get(long,long,boolean)}
	 */
	@Test
	public final void testGetLongLongBoolean()
	{
		assertEquals(rFac.zero(), rFac.get(0L, 1L, true));
		FastRational r = rFac.get(10L, 1L, true);
		assertEquals(r, ten);
		r = rFac.get(100L, 10L, true);
		assertEquals(r, ten);
		r = rFac.get(3024L, 33264L, true);
		assertTrue(r.equals(rFac.get(1, 11)));
	}

	/**
	 * test {@link FastRational#get(double)}
	 */
	@Test
	public final void testFastRationalDouble()
	{
		assertEquals(rFac.zero(), rFac.get(0.0));
		assertEquals(one, rFac.get(1.0));
		assertEquals(rFac.m_one(), rFac.get(-1.0));
		assertEquals(rFac.get(-1, 2), rFac.get(-0.5));
	}

	/**
	 * test {@link FastRational#toString()}
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
		assertEquals(0, rFac.zero().getNumerator());
		assertEquals(1, rFac.zero().getDenominator());
	}

	/**
	 * verify that the denominator is not becoming zero.
	 */
	@Test
	public void testNonZeroDenominator()
	{
		assertEquals(1, rFac.zero().getDenominator());
		FastRational r = rFac.get(1, 77);
		FastRational p = rFac.get(1, 77);
		assertEquals("1/77*0", 1, r.multiply(rFac.zero()).getDenominator());
		assertEquals("1/77-1/77", 1, r.subtract(p).getDenominator());
	}

	@Test
	public void testInvert()
	{
		assertEquals(rFac.one(), rFac.one().invert());
		assertEquals(rFac.m_one(), rFac.m_one().invert());
		assertEquals(rFac.get(13, 7), rFac.get(7, 13).invert());
		assertEquals(rFac.get(-13, 7), rFac.get(-7, 13).invert());
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

	@Test
	public void floor()
	{
		assertEquals(rFac.one(), rFac.one().floor());
		assertEquals(rFac.zero(), one_third.floor());
		assertEquals(rFac.get(2), rFac.get(13, 5).floor());
		assertEquals(rFac.get(-4), rFac.get(-17, 5).floor());
	}
}
