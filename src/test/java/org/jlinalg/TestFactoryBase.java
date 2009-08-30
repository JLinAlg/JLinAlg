/**
 * 
 */
package org.jlinalg;

import static org.junit.Assert.assertTrue;

import java.util.Random;

/**
 * @author mgeorg
 */
abstract public class TestFactoryBase
{
	Random random = new Random();

	/**
	 * Test the functions {@link IRingElement#le(IRingElement)}
	 * {@link IRingElement#ge(IRingElement)}
	 * 
	 * @param factory
	 * @param min
	 *            the minimum value to compared
	 * @param max
	 *            the maximal value to be compared
	 */
	public void testGeLe(RingElementFactory<?> factory, double min, double max)
	{
		for (int i = 0; i < 500; i++) {
			double a = (max - min) * random.nextDouble() + min;
			double b = (max - min) * random.nextDouble() + min;
			if (a > b) {
				double x = a;
				a = b;
				b = x;
			}
			IRingElement af = factory.get(a);
			IRingElement bf = factory.get(b);
			assertTrue(af.toString() + ">" + bf.toString() + " (created from "
					+ a + "," + b + ")", af.le(bf));
			assertTrue(bf.toString() + ">" + af.toString() + " (created from "
					+ b + "," + a + ")", bf.ge(af));
		}
	}

	public <R extends RingElement, Q extends RingElement> void testRandomMinMax(
			RingElementFactory<R> f, Q min, Q max)
	{
		final RingElement maxDiff = max.subtract(min).divide(f.get(20));
		RingElement min1 = max;
		RingElement max1 = min;
		for (int i = 0; i < 500; i++) {
			RingElement b = f.randomValue(min, max);
			assertTrue("min exceeded: " + b + "<" + min, b.ge(min));
			assertTrue("max exceeded: " + b + ">" + max, b.le(max));
			if (b.lt(min1)) min1 = b;
			if (b.gt(max1)) max1 = b;
		}
		assertTrue("min1=" + min1.toString() + " >= min=" + min.toString(),
				min1.ge(min));
		assertTrue("difference min1=" + min1.toString() + "   min="
				+ min.toString() + " is too big.", min1.le(min.add(maxDiff)));
		assertTrue("max1=" + max1.toString() + "> max=" + max.toString(), max1
				.le(max));
		assertTrue("difference max=" + max.toString() + " max1="
				+ max1.toString() + " is too big.", max1.ge(max
				.subtract(maxDiff)));
	}
}
