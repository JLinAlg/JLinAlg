package org.jlinalg.complex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.complex.Complex.ComplexFactory;
import org.jlinalg.rational.Rational;
import org.jlinalg.testutil.FactoryTestBase;
import org.junit.Test;

public class ComplexFactoryTest
		extends FactoryTestBase<Complex>
{
	/**
	 * test whether the factory creates arrays,
	 */
	@Test
	public void testCreateArrays()
	{
		assertNotNull("Cannot access \'Complex\'-array", Complex.FACTORY
				.getArray(5));
	}

	/**
	 * test ComplexFactory#get(Object,Object)
	 */
	@Test
	public void testComplexGetObjectObject()
	{
		ComplexFactory f = Complex.FACTORY;
		assertEquals(f.one(), f.get(new Double(1), new Integer(0)));
		assertEquals(f.get(11, 55), f.get(new BigInteger("11"),
				Rational.FACTORY.get(55)));
	}

	@Override
	public IRingElementFactory<Complex> getFactory()
	{
		return Complex.FACTORY;
	}

	@Test
	public void testGet()
	{
		assertEquals(getFactory().zero(), Complex.FACTORY.get(0.0, 0.0));
		assertEquals(getFactory().one(), Complex.FACTORY.get(1.0, 0.0));
		assertEquals(getFactory().m_one(), Complex.FACTORY.get(-1.0, 0.0));
	}
}
