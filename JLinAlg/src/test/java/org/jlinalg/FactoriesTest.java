package org.jlinalg;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.jlinalg.Complex;
import org.jlinalg.DoubleWrapper;
import org.jlinalg.F2;
import org.jlinalg.Rational;
import org.jlinalg.RingElement;
import org.jlinalg.RingElementFactory;
import org.jlinalg.Complex.ComplexFactory;
import org.jlinalg.field_p.FieldPFactoryMap;
import org.junit.Test;

/**
 * Test the creation of factories.
 * 
 * @author Georg Thimm
 */
public class FactoriesTest
		extends TestFactoryBase
{
	/**
	 * test whether the singletons Rational.FACTORY, Complex.FACTORY, and
	 * F2.FACTORY exist.
	 */
	@Test
	public void testDirectCreation()
	{
		assertTrue("Cannot access factory \'Rational\'",
				Rational.FACTORY != null);
		assertTrue("Cannot access factory \'Complex\'", Complex.FACTORY != null);
		assertTrue("Cannot access factory \'F2\'", F2.FACTORY != null);
	}

	/**
	 * test whether the factories create arrays,
	 */
	@Test
	public void testCreateArrays()
	{
		assertTrue("Cannot create F2-array", F2.FACTORY.getArray(5) != null);
		assertTrue("Cannot access \'Rational\'-array", Rational.FACTORY
				.getArray(5) != null);
		assertTrue("Cannot access \'Complex\'-array", Complex.FACTORY
				.getArray(5) != null);

	}

	/**
	 * test whether Rational.FACTORY creates 0, 1, -1
	 */
	@Test
	public void testRational()
	{
		assertTrue(Rational.FACTORY.get(0) != null);
		assertTrue(Rational.FACTORY.one() != null);
		assertTrue(Rational.FACTORY.zero() != null);
		assertTrue(Rational.FACTORY.m_one() != null);
		assertFalse(Rational.FACTORY.zero().equals(Rational.FACTORY.get(1e-6)));
	}

	/**
	 * test ComplexFactory#get(Object,Object)
	 */
	@Test
	public void testComplexGetObjectObject()
	{
		ComplexFactory f = Complex.FACTORY;
		assertTrue(f.get(new Double(1), new Integer(0)).equals(f.one()));
		assertTrue(f.get(new BigInteger("11"), Rational.FACTORY.get(55))
				.equals(f.get(11, 55)));
	}

	/**
	 * test the randomValue(randonm,min,max) methods
	 */
	@Test
	public void testRandomMinMax()
	{
		// rationals
		RingElementFactory<? extends RingElement> f = Rational.FACTORY;
		testRandomMinMax(f, f.get(-0.3), f.get(3));
		testRandomMinMax(f, f.get(77), f.get(113));
		// double wrappers
		f = DoubleWrapper.FACTORY;
		testRandomMinMax(f, f.get(-0.3), f.get(3));
		testRandomMinMax(f, f.get(77), f.get(113));
		// filedplong
		f = FieldPFactoryMap.getFactory(71L);
		testRandomMinMax(f, f.get(12), f.get(30));
		testRandomMinMax(f, f.get(7), f.get(70));
	}

	/**
	 * test the functions {@link IRingElement#le(IRingElement)} and
	 * {@link IRingElement#ge(IRingElement)} for several factories
	 */
	@Test
	public void testLeGe()
	{
		testGeLe(Rational.FACTORY, 0.0, 500);
		testGeLe(DoubleWrapper.FACTORY, Double.MIN_VALUE, Double.MAX_VALUE);
		testGeLe(FieldPFactoryMap.getFactory(31L), 0.0, 31.0);
		testGeLe(FieldPFactoryMap.getFactory(331L), 0.0, 331.0);
	}
}
