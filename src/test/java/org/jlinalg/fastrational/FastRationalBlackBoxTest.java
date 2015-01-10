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
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.jlinalg.LinAlgFactory;
import org.jlinalg.Vector;
import org.jlinalg.doublewrapper.DoubleWrapper;
import org.junit.Test;

/**
 * Test the type FastRational extensively.
 * 
 * @author georg
 */
public class FastRationalBlackBoxTest
{

	static Random random = new Random();

	private static FastRationalFactory rFac = FastRational.FACTORY;

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
			FastRational r1 = rFac.get(a, b);
			FastRational r2 = rFac.get(c, d);
			FastRational vv = r1.divide(r2);
			assertTrue("denominator negative: " + vv.getDenominator(), vv
					.getDenominator() > 0);
			double v = ((double) a / b) / ((double) c / d);
			assertEquals(v, vv.doubleValue(), 0.0001);
		}
	}

	/**
	 * test {@link FastRational#invert()} for random values
	 */
	@Test
	public void invert()
	{
		for (int i = 0; i < 200; i++) {
			int a = (int) (Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE / 2);
			int b = (int) (Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE / 2);
			FastRational r1 = rFac.get(a, b);
			FastRational r2 = r1.invert();
			assertTrue("1/(1/" + r1 + "))!=" + r2.invert(), r2.invert().equals(
					r1));
		}

		for (int k = 0; k < 200; k++) {
			int a = (int) (Math.random() * 100 - 50);
			int b = (int) (Math.random() * 100 - 50);
			if (b == 0) b = 123;
			if (a == 0) a = 777;
			FastRational r1 = rFac.get(a, b);
			assertTrue("denominator negative: " + r1.getDenominator(), r1
					.getDenominator() > 0);

			FastRational vv = r1.invert();
			assertTrue("denominator negative: " + vv.getDenominator(), vv
					.getDenominator() > 0);
			double v = ((double) b / a);
			assertEquals(v, vv.doubleValue(), 0.0001);
		}
	}

	/**
	 * test {@link FastRational#subtract(org.jlinalg.IRingElement)} for random
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
			FastRational r1 = rFac.get(a, b);
			FastRational r2 = rFac.get(c, d);
			FastRational vv = r1.subtract(r2);
			assertTrue("denominator negative: " + vv.getDenominator(), vv
					.getDenominator() > 0);
			double v = (double) a / b - (double) c / d;
			assertEquals(v, vv.doubleValue(), 0.0001);
		}
	}

	/**
	 * test {@link FastRational#add(org.jlinalg.IRingElement)}
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
			FastRational r1 = rFac.get(a, b);
			FastRational r2 = rFac.get(c, d);
			FastRational vv = r1.add(r2);
			assertTrue("denominator negative: " + vv.getDenominator(), vv
					.getDenominator() > 0);
			double v = ((double) a / b) + ((double) c / d);
			assertEquals(v, vv.doubleValue(), 0.0001);
		}
	}

	/**
	 * test {@link FastRational#compareTo(org.jlinalg.IRingElement)} for random
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
			FastRational r1 = rFac.get(a, b);
			FastRational r2 = rFac.get(c, d);
			Double d1 = new Double((double) a / b);
			if (d1.doubleValue() == -0.0) d1 = Double.valueOf(0.0);
			Double d2 = new Double((double) c / d);
			if (d2.doubleValue() == -0.0) d2 = Double.valueOf(0.0);
			assertTrue(r1 + ".compareTo(" + r2 + ")==" + r1.compareTo(r2)
					+ " but " + d1 + ".compareTo(" + d2 + ")=="
					+ d1.compareTo(d2), r1.compareTo(r2) == d1.compareTo(d2));
		}
	}

	/**
	 * test {@link RationalFactory#get(long, long)}
	 */
	@Test
	public final void testDoubleValue()
	{
		for (int i = 0; i < 100; i++) {
			int a = (int) (Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE / 2);
			int b = (int) (Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE / 2);
			FastRational r = rFac.get(a, b);
			// assertTrue(r.doubleValue() + "!=" + ((double) a / (double) b),
			// Math
			// .abs(r.doubleValue() - (double) a / (double) b) < Math
			// .abs(0.000001 * a / b));
			assertEquals(((double) a / (double) b), r.doubleValue(), 0.000001);
		}
	}

	/**
	 * test {@link FastRational#equals(Object)}
	 */
	@Test
	public final void testEqualsObject()
	{

		for (int i = 0; i < 100; i++) {
			int a = (int) (Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE / 2);
			int b = (int) (Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE / 2);
			FastRational r1 = rFac.get(a, b);
			FastRational r2 = rFac.get(a, b);
			assertTrue(r1 + "!=" + r2, r2.equals(r1));
		}
	}

	/**
	 * test le(FastRational) and ge(FastRational)
	 */
	@Test
	public void testLeGe()
	{
		FastRational r1 = FastRational.FACTORY.one();
		for (int i = 0; i < 1000; i++) {
			{
				FastRational r2 = FastRational.FACTORY
						.get(random.nextInt(10000) - 5000, random
								.nextInt(10000) + 1, true);
				if (r1.doubleValue() <= r2.doubleValue()) {
					assertTrue("r2=" + r2 + " r1=" + r1, r1.le(r2));
					assertTrue("r2=" + r2 + " r1=" + r1, r2.ge(r1));
				}
				else {
					assertTrue("r2=" + r2 + " r1=" + r1, r2.le(r1));
					assertTrue("r2=" + r2 + " r1=" + r1, r1.ge(r2));
				}
				r1 = r2;
			}
		}
	}

	/**
	 * test {@link FastRational#multiply(org.jlinalg.IRingElement)}
	 */
	@Test
	public final void testMultiply()
	{
		for (int i = 0; i < 100; i++) {
			int a = (int) (Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE / 2);
			int b = (int) (Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE / 2);
			int c = (int) (Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE / 2);
			int d = (int) (Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE / 2);
			FastRational r1 = rFac.get(a, b);
			r1 = r1.multiply(rFac.get(c, d));
			FastRational r2 = rFac.get(a, d);
			r2 = r2.multiply(rFac.get(c, b));
			assertTrue(a + "/" + b + "*" + c + "/" + d + "!=" + a + "/" + d
					+ "*" + c + "/" + b + "    " + r1 + "!=" + r2, r1
					.equals(r2));
		}

		for (int k = 0; k < 100; k++) {
			int a = (int) (Math.random() * 100 - 50);
			int b = (int) (Math.random() * 100 - 50);
			if (b == 0) b = 123;
			int c = (int) (Math.random() * 100 - 50);
			int d = (int) (Math.random() * 100 - 50);
			if (d == 0) d = 221;
			FastRational r1 = rFac.get(a, b);
			FastRational r2 = rFac.get(c, d);
			FastRational vv = r1.multiply(r2);
			assertTrue("denominator negative: " + vv.getDenominator(), vv
					.getDenominator() > 0);
			double v = ((double) a / b) * ((double) c / d);
			assertEquals(v, vv.doubleValue(), 0.0001);
		}
	}

	/**
	 * test {@link FastRational#equals(Object)}
	 */
	@Test
	public final void testRationalDouble()
	{
		for (int i = 0; i < 100; i++) {
			double d = (0.5 - Math.random()) * 17111.0;
			FastRational r = rFac.get(d);
			assertEquals(d, r.doubleValue(), 0.00001);
		}
	}

	/**
	 * test {@link FastRational#negate()}
	 */
	@Test
	public final void testNegate()
	{
		for (int i = 0; i < 100; i++) {
			int a = (int) (Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE / 2);
			int b = (int) (Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE / 2);
			FastRational r1 = rFac.get(a, b);
			FastRational r2 = r1.negate();
			assertEquals("-(-" + r2 + ")!=" + r1.negate(), r1, r2.negate());
		}
	}

	/**
	 * Test the conversion from {@link DoubleWrapper} to {@link FastRational}
	 * using {@link org.jlinalg.RingElementFactory#get(Object)}. objects
	 */
	@Test
	public void doubleToRationalWrapper() throws Exception
	{
		FastRationalFactory fac = FastRational.FACTORY;
		for (int i = 0; i < 30; i++) {
			DoubleWrapper d = DoubleWrapper.FACTORY
					.get(random.nextDouble() * 10 - 5);
			FastRational r = fac.get(d);
			assertEquals(d.doubleValue(), r.doubleValue(), 0.0000001);
		}
	}

	/**
	 * Test the conversion from {@link FastRational} to {@link DoubleWrapper}
	 * vectors
	 */
	@Test
	public void doubleToRationalVectors() throws Exception
	{
		FastRationalFactory rationalFac = FastRational.FACTORY;

		LinAlgFactory<DoubleWrapper> doubleFac = new LinAlgFactory<DoubleWrapper>(
				DoubleWrapper.FACTORY);

		// for a vector
		Vector<DoubleWrapper> vDouble = doubleFac.gaussianNoise(10);
		Vector<FastRational> vRational = rationalFac.convert(vDouble);
		for (int row = 1; row <= vRational.length(); row++) {
			DoubleWrapper d = vDouble.getEntry(row);
			FastRational r = vRational.getEntry(row);
			assertEquals(d + "!=" + r + "(=" + r.doubleValue() + ")", d
					.doubleValue(), r.doubleValue(),
					(d.abs()).doubleValue() / 1000);
		}
	}

}
