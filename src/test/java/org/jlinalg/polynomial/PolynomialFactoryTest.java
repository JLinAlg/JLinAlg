package org.jlinalg.polynomial;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.jlinalg.DoubleWrapper;
import org.jlinalg.field_p.FieldPAbstractFactory;
import org.jlinalg.field_p.FieldPFactoryMap;
import org.junit.Test;

public class PolynomialFactoryTest
{
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
		FieldPAbstractFactory f1 = FieldPFactoryMap.getFactory(Long
				.valueOf(17L));
		FieldPAbstractFactory f2 = FieldPFactoryMap.getFactory(Long
				.valueOf(17L));
		assertTrue(f1.equals(f2));
		assertSame(f1, f2);
		f2 = FieldPFactoryMap.getFactory(Long.valueOf(19L));
		assertNotSame(f1, f2);
	}

	/**
	 * a prime number from the Woodall series
	 */
	final String prime1 = "32212254719";

	/**
	 * a Wagstaff prime
	 */
	final String prime2 = "2932031007403";

	/**
	 * test whether the factories are unique if the same base type FieldP is
	 * used for their creation.
	 */
	@Test
	public void testCompareFactoriesFieldPBig()
	{
		FieldPAbstractFactory f1 = FieldPFactoryMap.getFactory(prime1);
		FieldPAbstractFactory f2 = FieldPFactoryMap.getFactory(prime1);
		assertTrue(f1.equals(f2));
		assertSame(f1, f2);
		f2 = FieldPFactoryMap.getFactory(prime2);
		assertNotSame(f1, f2);
	}

}
