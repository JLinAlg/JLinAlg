/**
 * 
 */
package org.jlinalg.testutil;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.doublewrapper.DoubleWrapper;
import org.jlinalg.f2.F2;
import org.jlinalg.f2.F2.F2Factory;
import org.jlinalg.field_p.FieldPFactoryMap;
import org.jlinalg.rational.Rational;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author Georg Thimm
 */
@RunWith(Parameterized.class)
public class RandomTests<RE extends IRingElement<RE>>
		extends TestBaseFixture<RE>
{
	final RE min;
	final RE max;
	private IRingElementFactory<RE> factory;

	@Parameters
	public static Collection<Object[]> data1()
	{
		Object[][] data = {
				{
						Rational.FACTORY, "-1/3", "7/6"
				},
				{
						DoubleWrapper.FACTORY,
						Double.toString(Double.MIN_VALUE),
						Double.toString(Double.MAX_VALUE)
				},
				{
						F2.FACTORY, "0m2", "1m2"
				},
				{
						FieldPFactoryMap.getFactory("113"), "0", "112"
				},
				{
						FieldPFactoryMap.getFactory("2932031007403"), "0",
						"22932031007402"
				}
		};

		return Arrays.asList(data);
	}

	static Random random = new Random();

	public RandomTests(IRingElementFactory<RE> factory, String min_, String max_)
	{
		this.factory = factory;
		min = factory.get(min_);
		max = factory.get(max_);
	}

	/**
	 * Test the functions {@link IRingElement#le(IRingElement)}
	 * {@link IRingElement#ge(IRingElement)} executed only if
	 * {@link IRingElementFactory#randomValue(IRingElement, IRingElement)} is
	 * not depreciated.
	 * 
	 * @throws SecurityException
	 *             If the reflective examination of the factory fails
	 * @throws NoSuchMethodException
	 *             If the reflective examination of the factory fails
	 */
	@Test
	public void test_ge_le_gt_lt()
	{
		if (isRandomValueMinMaxDepreceated()) {
			System.out.println("Skipping test_ge_le_gt_lt for factory "
					+ getFactory().getClass().getName());
			return;
		}
		for (int i = 0; i < 500; i++) {
			RE r1 = getFactory().randomValue(min, max);
			RE r2 = getFactory().randomValue(min, max);

			if (r1.gt(r2)) {
				RE x = r1;
				r1 = r2;
				r2 = x;
			}
			assertTrue(r1.le(r1));
			assertTrue(r1.ge(r1));
			assertFalse(r1.gt(r1));
			assertFalse(r1.lt(r1));
			assertTrue("failed: " + r1.toString() + "<=" + r2.toString()
					+ " (created from " + r1 + "," + r2 + ")", r1.le(r2));
			assertTrue("failed: " + r2.toString() + ">=" + r1.toString()
					+ " (created from " + r2 + "," + r1 + ")", r2.ge(r1));
			if (!r1.equals(r2)) {
				assertTrue("failed: " + r1.toString() + "<" + r2.toString()
						+ " (created from " + r1 + "," + r2 + ")", r1.lt(r2));
				assertTrue("failed: " + r2.toString() + ">" + r1.toString()
						+ " (created from " + r2 + "," + r1 + ")", r2.gt(r1));

			}
		}
	}

	/**
	 * This test is only applied if
	 * {@link IRingElementFactory#randomValue(IRingElement, IRingElement)} is
	 * not annotated as depreciated.
	 * 
	 * @throws SecurityException
	 *             If the reflective examination of the factory fails
	 * @throws NoSuchMethodException
	 *             If the reflective examination of the factory fails
	 */
	@Test
	public void testRandomMinMax()
	{
		Assume.assumeTrue(!isRandomValueMinMaxDepreceated());

		RE min1 = max;
		RE max1 = min;
		for (int i = 0; i < 500; i++) {
			RE b = getFactory().randomValue(min, max);
			assertTrue("min exceeded: " + b + "<" + min, b.ge(min));
			assertTrue("max exceeded: " + b + ">" + max, b.le(max));
			if (b.lt(min1)) min1 = b;
			if (b.gt(max1)) max1 = b;
		}

		Assume.assumeTrue(!(getFactory() instanceof F2Factory));
		RE maxDiff = max.subtract(min).divide(getFactory().get(20));
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.testutil.TestBaseInterface#getFactory()
	 */
	@Override
	public IRingElementFactory<RE> getFactory()
	{
		return factory;
	}

	/**
	 * examine the factory and decide whether its method {@code
	 * randomValue(min,max)} is depreceated.
	 * 
	 * @throws SecurityException
	 *             If the reflective examination of the factory fails
	 * @throws NoSuchMethodException
	 *             If the reflective examination of the factory fails
	 */
	boolean isRandomValueMinMaxDepreceated()
	{
		return methodIsDepreciated(getFactory().getClass(), "randomValue",
				new Class<?>[] {
						// IRingElement.class, IRingElement.class
						getFactory().one().getClass(),
						getFactory().one().getClass()
				});
	}
}
