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
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.jlinalg.LinAlgFactory;
import org.jlinalg.Matrix;
import org.jlinalg.Vector;
import org.jlinalg.doublewrapper.DoubleWrapper;
import org.jlinalg.doublewrapper.DoubleWrapper.DoubleWrapperFactory;
import org.jlinalg.testutil.RandomNumberList;
import org.junit.Test;

/**
 * Test the type Rational extensively.
 * 
 * @author georg
 */
public class RationalBlackBoxTest
{

	private static RationalFactory rFac = Rational.FACTORY;

	@Test
	public void testDivision()
	{
		for (int k = 0; k < 200; k++) {
			int a = (int) (Math.random() * 100 - 50);
			int b = (int) (Math.random() * 100 - 50);
			if (b == 0) b = 123;
			int c = (int) (Math.random() * 100 - 50);
			if (c == 0) c = 442;
			int d = (int) (Math.random() * 100 - 50);
			if (d == 0) d = 221;
			Rational r1 = rFac.get(a, b);
			Rational r2 = rFac.get(c, d);
			Rational vv = r1.divide(r2);
			assertTrue("denominator negative: " + vv.getDenominator(),
					vv.getDenominator().longValue() > 0);
			double v = ((double) a / b) / ((double) c / d);
			assertTrue(r1 + "/" + r2 + "!=" + v,
					Math.abs(vv.getNumerator().doubleValue()
							/ vv.getDenominator().doubleValue() - v) < 0.0001);
		}
	}

	/**
	 * test {@link Rational#invert()} for random values
	 */
	@Test
	public void invert()
	{
		for (int i = 0; i < 200; i++) {
			int a = (int) (Math.random() * Integer.MAX_VALUE
					- Integer.MAX_VALUE / 2);
			int b = (int) (Math.random() * Integer.MAX_VALUE
					- Integer.MAX_VALUE / 2);
			Rational r1 = rFac.get(a, b);
			Rational r2 = r1.invert();
			assertTrue("1/(1/" + r1 + "))!=" + r2.invert(),
					r2.invert().equals(r1));
		}

		for (int k = 0; k < 200; k++) {
			int a = (int) (Math.random() * 100 - 50);
			int b = (int) (Math.random() * 100 - 50);
			if (b == 0) b = 123;
			if (a == 0) a = 777;
			Rational r1 = rFac.get(a, b);
			assertTrue("denominator negative: " + r1.getDenominator(),
					r1.getDenominator().longValue() > 0);

			Rational vv = r1.invert();
			assertTrue("denominator negative: " + vv.getDenominator(),
					vv.getDenominator().longValue() > 0);
			double v = ((double) b / a);
			assertTrue("1/(" + r1 + ")!=" + v,
					Math.abs(vv.getNumerator().doubleValue()
							/ vv.getDenominator().doubleValue() - v) < 0.0001);
		}
	}

	/**
	 * test {@link Rational#subtract(org.jlinalg.IRingElement)} for random
	 * values.
	 */
	@Test
	public void subtract()
	{
		for (int k = 0; k < 20; k++) {
			int a = (int) (Math.random() * 100 - 50);
			int b = (int) (Math.random() * 100 - 50);
			if (b == 0) b = 123;
			int c = (int) (Math.random() * 100 - 50);
			int d = (int) (Math.random() * 100 - 50);
			if (d == 0) d = 221;
			Rational r1 = rFac.get(a, b);
			Rational r2 = rFac.get(c, d);
			Rational vv = r1.subtract(r2);
			assertTrue("denominator negative: " + vv.getDenominator(),
					vv.getDenominator().longValue() > 0);
			double v = (double) a / b - (double) c / d;
			assertTrue(r1 + "-" + r2 + "!=" + v,
					Math.abs(vv.getNumerator().doubleValue()
							/ vv.getDenominator().doubleValue() - v) < 0.0001);
		}
	}

	/**
	 * test {@link Rational#add(org.jlinalg.IRingElement)}
	 */
	@Test
	public void testAdd()
	{
		for (int k = 0; k < 200; k++) {
			int a = (int) (Math.random() * 100 - 50);
			int b = (int) (Math.random() * 100 - 50);
			if (b == 0) b = 123;
			int c = (int) (Math.random() * 100 - 50);
			int d = (int) (Math.random() * 100 - 50);
			if (d == 0) d = 773;
			Rational r1 = rFac.get(a, b);
			Rational r2 = rFac.get(c, d);
			Rational vv = r1.add(r2);
			assertTrue("denominator negative: " + vv.getDenominator(),
					vv.getDenominator().longValue() > 0);
			double v = ((double) a / b) + ((double) c / d);
			assertTrue(r1 + "+" + r2 + "!=" + v,
					Math.abs(vv.getNumerator().doubleValue()
							/ vv.getDenominator().doubleValue() - v) < 0.0001);
		}
	}

	/**
	 * Test Rational.multiply for large denominators
	 */
	@Test
	public void testBigDenominators()
	{
		Rational r = rFac.one();
		int end = 1200;

		for (long i = 1000; i < end; i++) {
			r = r.multiply(rFac.get(end, i));
			assertTrue("r=" + r.toString(), r.gt(rFac.one()));
		}
		for (long i = 1000; i < end; i++) {
			assertTrue("r=" + r.toString(), r.gt(rFac.one()));
			r = r.divide(rFac.get(end, i));
		}
		assertEquals(rFac.one(), r);
	}

	/**
	 * Test Rational.add for large denominators
	 */
	@Test
	public void testBigDenominatorsAdd()
	{
		Rational r = rFac.one();
		int end = 1200;
		for (long i = 1000; i < end; i++) {
			r = r.add(rFac.get(1, i));
			assertTrue("r=" + r.toString(), r.gt(rFac.one()));
		}
		for (long i = 1000; i < end; i++) {
			assertTrue("r=" + r.toString(), r.gt(rFac.one()));
			r = r.add(rFac.get(-1, i));
		}
		assertEquals(rFac.one(), r);

	}

	/**
	 * test {@link Rational#compareTo(org.jlinalg.IRingElement)} for random
	 * values
	 */
	@Test
	public void testCompare()
	{

		for (int k = 0; k < 200; k++) {
			int a = (int) (Math.random() * 100 - 50);
			int b = (int) (Math.random() * 100 - 50);
			if (b == 0) b = 123;
			int c = (int) (Math.random() * 100 - 50);
			int d = (int) (Math.random() * 100 - 50);
			if (d == 0) d = 221;
			Rational r1 = rFac.get(a, b);
			Rational r2 = rFac.get(c, d);
			Double d1 = Double.valueOf((double) a / b);
			if (d1.doubleValue() == -0.0) d1 = Double.valueOf(0.0);
			Double d2 = Double.valueOf((double) c / d);
			if (d2.doubleValue() == -0.0) d2 = Double.valueOf(0.0);
			assertTrue(
					r1 + ".compareTo(" + r2 + ")==" + r1.compareTo(r2) + " but "
							+ d1 + ".compareTo(" + d2 + ")=="
							+ d1.compareTo(d2),
					r1.compareTo(r2) == d1.compareTo(d2));
		}
	}

	/**
	 * test {@link RationalFactory#get(long, long)}
	 */
	@Test
	public final void testDoubleValue()
	{
		for (int i = 0; i < 100; i++) {
			int a = (int) (Math.random() * Integer.MAX_VALUE
					- Integer.MAX_VALUE / 2);
			int b = (int) (Math.random() * Integer.MAX_VALUE
					- Integer.MAX_VALUE / 2);
			Rational r = rFac.get(a, b);
			assertTrue(r.doubleValue() + "!=" + ((double) a / (double) b),
					Math.abs(r.doubleValue() - (double) a / (double) b) < Math
							.abs(0.000001 * a / b));
		}
	}

	/**
	 * test {@link Rational#equals(Object)}
	 */
	@Test
	public final void testEqualsObject()
	{

		for (int i = 0; i < 100; i++) {
			int a = (int) (Math.random() * Integer.MAX_VALUE
					- Integer.MAX_VALUE / 2);
			int b = (int) (Math.random() * Integer.MAX_VALUE
					- Integer.MAX_VALUE / 2);
			Rational r1 = rFac.get(a, b);
			Rational r2 = rFac.get(a, b);
			assertTrue(r1 + "!=" + r2, r2.equals(r1));
		}
	}

	/**
	 * test le(Rational) and ge(Rational)
	 */
	@Test
	public void testLeGe()
	{
		for (Rational r1 : new RandomNumberList<>(rFac, 10)) {
			for (Rational r2 : new RandomNumberList<>(rFac, 10)) {
				if (r1.doubleValue() <= r2.doubleValue()) {
					assertTrue(r1.le(r2));
					assertTrue(r2.ge(r1));
				}
				else {
					assertTrue(r2.le(r1));
					assertTrue(r1.ge(r2));
				}
			}
		}
	}

	/**
	 * test {@link Rational#multiply(org.jlinalg.IRingElement)}
	 */
	@Test
	public final void testMultiply()
	{
		for (int i = 0; i < 100; i++) {
			int a = (int) (Math.random() * Integer.MAX_VALUE
					- Integer.MAX_VALUE / 2);
			int b = (int) (Math.random() * Integer.MAX_VALUE
					- Integer.MAX_VALUE / 2);
			int c = (int) (Math.random() * Integer.MAX_VALUE
					- Integer.MAX_VALUE / 2);
			int d = (int) (Math.random() * Integer.MAX_VALUE
					- Integer.MAX_VALUE / 2);
			Rational r1 = rFac.get(a, b);
			r1 = r1.multiply(rFac.get(c, d));
			Rational r2 = rFac.get(a, d);
			r2 = r2.multiply(rFac.get(c, b));
			assertTrue(
					a + "/" + b + "*" + c + "/" + d + "!=" + a + "/" + d + "*"
							+ c + "/" + b + "    " + r1 + "!=" + r2,
					r1.equals(r2));
		}

		for (int k = 0; k < 100; k++) {
			int a = (int) (Math.random() * 100 - 50);
			int b = (int) (Math.random() * 100 - 50);
			if (b == 0) b = 123;
			int c = (int) (Math.random() * 100 - 50);
			int d = (int) (Math.random() * 100 - 50);
			if (d == 0) d = 221;
			Rational r1 = rFac.get(a, b);
			Rational r2 = rFac.get(c, d);
			Rational vv = r1.multiply(r2);
			assertTrue("denominator negative: " + vv.getDenominator(),
					vv.getDenominator().longValue() > 0);
			double v = ((double) a / b) * ((double) c / d);
			assertTrue(r1 + "*" + r2 + "!=" + v,
					Math.abs(vv.getNumerator().doubleValue()
							/ vv.getDenominator().doubleValue() - v) < 0.0001);
		}
	}

	/**
	 * test {@link Rational#equals(Object)}
	 */
	@Test
	public final void testRationalDouble()
	{
		for (int i = 0; i < 100; i++) {
			double d = (0.5 - Math.random()) * 11111111111.0;
			Rational r = rFac.get(d);
			assertTrue(r + "=" + r.doubleValue() + "!=" + d,
					Math.abs(r.getNumerator().doubleValue()
							/ r.getDenominator().doubleValue() - d) < 0.000001
									* Math.abs(d));
		}
	}

	/**
	 * test {@link Rational#negate()}
	 */
	@Test
	public final void testNegate()
	{
		for (int i = 0; i < 100; i++) {
			int a = (int) (Math.random() * Integer.MAX_VALUE
					- Integer.MAX_VALUE / 2);
			int b = (int) (Math.random() * Integer.MAX_VALUE
					- Integer.MAX_VALUE / 2);
			Rational r1 = rFac.get(a, b);
			Rational r2 = r1.negate();
			assertEquals("-(-" + r2 + ")!=" + r1.negate(), r1, r2.negate());
		}
	}

	/**
	 * Test the conversion from {@link Rational} to {@link DoubleWrapper}
	 * vectors
	 */
	@Test
	public void rationalToDoubleWrapperVectors() throws Exception
	{
		DoubleWrapperFactory fac = DoubleWrapper.FACTORY;

		LinAlgFactory<Rational> rfac = new LinAlgFactory<>(
				Rational.FACTORY);

		// for a vector
		Vector<Rational> vRational = rfac.gaussianNoise(10);
		Vector<DoubleWrapper> vDouble = fac.convert(vRational);
		for (int row = 1; row <= vRational.length(); row++) {
			DoubleWrapper d = vDouble.getEntry(row);
			Rational r = vRational.getEntry(row);
			assertTrue(Math.abs(d.doubleValue() - r.doubleValue()) < (d.abs())
					.doubleValue() / 1000);
		}
	}

	/**
	 * Test the conversion from {@link DoubleWrapper} to {@link Rational} using
	 * {@link org.jlinalg.RingElementFactory#get(Object)}. objects
	 */
	@Test
	public void doubleToRationalWrapper() throws Exception
	{
		RationalFactory fac = Rational.FACTORY;
		for (DoubleWrapper d : new RandomNumberList<>(
				DoubleWrapper.FACTORY, 20))
		{
			Rational r = fac.get(d);
			assertTrue(d + "!=" + r + "(=" + r.doubleValue() + ")",
					Math.abs(d.doubleValue() - r.doubleValue()) <= (d.abs())
							.doubleValue() / 1000);
		}
	}

	/**
	 * Test the conversion from {@link Rational} to {@link DoubleWrapper}
	 * matrices
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void doubleToRationalMatrices() throws Exception
	{
		RationalFactory fac = Rational.FACTORY;

		LinAlgFactory<DoubleWrapper> rfac = new LinAlgFactory<>(
				DoubleWrapper.FACTORY);

		// for a matrix
		Matrix<DoubleWrapper> mDouble = rfac.gaussianNoise(10, 5, new Random());
		Matrix<Rational> mRational = fac.convert(mDouble);
		for (int row = 1; row <= mRational.getRows(); row++) {
			for (int col = 1; col <= mRational.getCols(); col++) {
				DoubleWrapper d = mDouble.get(row, col);
				Rational r = mRational.get(row, col);
				assertTrue(d + "!=" + r + "(=" + r.doubleValue() + ")",
						Math.abs(d.doubleValue() - r.doubleValue()) <= (d.abs())
								.doubleValue() / 1000);
			}
		}
	}

	/**
	 * Test the conversion from {@link Rational} to {@link DoubleWrapper}
	 * vectors
	 */
	@Test
	public void doubleToRationalVectors() throws Exception
	{
		RationalFactory rationalFac = Rational.FACTORY;

		LinAlgFactory<DoubleWrapper> doubleFac = new LinAlgFactory<>(
				DoubleWrapper.FACTORY);

		// for a vector
		Vector<DoubleWrapper> vDouble = doubleFac.gaussianNoise(10);
		Vector<Rational> vRational = rationalFac.convert(vDouble);
		for (int row = 1; row <= vRational.length(); row++) {
			DoubleWrapper d = vDouble.getEntry(row);
			Rational r = vRational.getEntry(row);
			assertTrue(d + "!=" + r + "(=" + r.doubleValue() + ")",
					Math.abs(d.doubleValue() - r.doubleValue()) <= (d.abs())
							.doubleValue() / 1000);
		}
	}

}
