package org.jlinalg.testutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.jlinalg.InvalidOperationException;
import org.jlinalg.testutil.StringWrapper.StringWrapperFactory;
import org.junit.Ignore;
import org.junit.Test;

public class StringWrapperTest
		extends RingElementTestBase<StringWrapper>
{
	@Override
	public StringWrapperFactory getFactory()
	{
		return StringWrapper.FACTORY;
	}

	/**
	 * A shorthand for the factory
	 */
	static StringWrapperFactory fac = StringWrapper.FACTORY;
	private final static StringWrapper value1 = fac.get("123");

	private final static StringWrapper value2 = fac.get("-321");

	@Test(expected = InvalidOperationException.class)
	public void testAdd()
	{
		value1.add(value2);
	}

	@Test
	public void testCompareTo()
	{
		assertTrue(0 > value2.compareTo(value1));
		assertTrue(0 < value1.compareTo(value2));
		assertTrue(0 > fac.zero().compareTo(value1));
		assertEquals(0, value1.compareTo(value1));
	}

	@Test
	public void testGetFactory()
	{
		assertNotNull(fac.zero().getFactory());
		assertEquals(StringWrapperFactory.class, fac.zero().getFactory()
				.getClass());
	}

	@Test(expected = InvalidOperationException.class)
	public void testInvert()
	{
		fac.one().invert();
	}

	@Test(expected = InvalidOperationException.class)
	public void testMultiply()
	{
		fac.one().multiply(fac.zero());
	}

	@Test(expected = InvalidOperationException.class)
	public void testNegate()
	{
		value1.negate();
	}

	@Test(expected = InvalidOperationException.class)
	public void testSubtractIRingElement()
	{
		value1.subtract(value2);
	}

	@Test(expected = InvalidOperationException.class)
	public void testDivideIRingElement()
	{
		value1.divide(value2);
	}

	@Test(expected = InvalidOperationException.class)
	public void testAbs()
	{
		value1.abs();
	}

	@Test(expected = InvalidOperationException.class)
	public void testNorm()
	{
		value1.norm();
	}

	@Test
	public void testIsZero()
	{
		assertTrue(fac.zero().isZero());
		assertTrue(fac.get("0").isZero());
		assertFalse(value1.isZero());
	}

	@Test(expected = InvalidOperationException.class)
	public void testSubtractIRingElement1()
	{
		value1.subtract(value2);
	}

	@Test
	public void testIsOne()
	{
		assertTrue(fac.one().isOne());
		assertTrue(fac.get(1).isOne());
		assertFalse(value1.isOne());
	}

	@Test
	public void testEqualsObject()
	{
		assertTrue(fac.one().equals(fac.one()));
		assertTrue(fac.one().equals(fac.get(1)));
		assertFalse(fac.one().equals(fac.get(123)));
	}

	@Override
	@Ignore
	@Test
	public void testAdd_base()
	{
	}

	@Override
	@Ignore
	@Test
	public void testNorm_base()
	{
	}

	@Override
	@Ignore
	@Test
	public void testSubtract_base()
	{
	}

	@Override
	@Ignore
	@Test
	public void testAbs_base()
	{
	}

	@Override
	@Ignore
	@Test
	public void testDivide_base()
	{
	}

	@Override
	@Ignore
	@Test
	public void testInvert_base()
	{
	}

	@Override
	@Ignore
	@Test(expected = org.jlinalg.DivisionByZeroException.class)
	public void invertZero_base()
	{
	}

	@Override
	@Ignore
	@Test
	public void testMultiply_base()
	{
	}

	@Override
	@Ignore
	@Test
	public void testNegate_base()
	{
	}

	@Override
	@Ignore
	@Test
	public void testIsZero_base()
	{
	}
}
