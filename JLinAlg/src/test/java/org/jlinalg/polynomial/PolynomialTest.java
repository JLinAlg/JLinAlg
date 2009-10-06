/**
 * 
 */
package org.jlinalg.polynomial;

import static org.junit.Assume.assumeTrue;

import java.util.Arrays;
import java.util.Collection;

import org.jlinalg.IRingElement;
import org.jlinalg.complex.Complex;
import org.jlinalg.complex.Complex.ComplexFactory;
import org.jlinalg.doublewrapper.DoubleWrapper;
import org.jlinalg.field_p.FieldPFactoryMap;
import org.jlinalg.rational.Rational;
import org.jlinalg.testutil.RingElementTestBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author Georg Thimm
 */
@RunWith(Parameterized.class)
public class PolynomialTest<RE extends IRingElement<RE>>
		extends RingElementTestBase<Polynomial<RE>>
{
	/**
	 * a prime number from the Woodall series
	 */
	final static String prime1 = "32212254719";

	@Parameters
	public static Collection<Object[]> data()
	{
		Object[][] data_ = {
				{
					PolynomialFactory.getFactory(Rational.FACTORY)
				},
				{
					PolynomialFactory.getFactory(Complex.FACTORY)
				},
				{
					PolynomialFactory.getFactory(DoubleWrapper.FACTORY)
				},
				{
					PolynomialFactory.getFactory(FieldPFactoryMap
							.getFactory(Long.valueOf(17L)))
				},
				{
					PolynomialFactory.getFactory(FieldPFactoryMap
							.getFactory(prime1))
				}
		};
		return Arrays.asList(data_);
	}

	/**
	 * @param factory
	 *            a factory for a polynomial
	 */
	public PolynomialTest(PolynomialFactory<RE> factory)
	{
		this.factory = factory;
	}

	private PolynomialFactory<RE> factory;

	/**
	 * @see org.jlinalg.testutil.TestBaseInterface#getFactory()
	 */
	@Override
	public PolynomialFactory<RE> getFactory()
	{
		return factory;
	}

	@Override
	@Test
	public void testLt_base()
	{
		assumeTrue(!(getFactory().getBaseFactory() instanceof ComplexFactory));
		super.testLt_base();
	}

	@Override
	@Test
	public void testGt_base()
	{
		assumeTrue(!(getFactory().getBaseFactory() instanceof ComplexFactory));
		super.testGt_base();
	}

	@Override
	@Test
	public void testGe_base()
	{
		assumeTrue(!(getFactory().getBaseFactory() instanceof ComplexFactory));
		super.testGe_base();
	}

	@Override
	@Test
	public void testLe_base()
	{
		assumeTrue(!(getFactory().getBaseFactory() instanceof ComplexFactory));
		super.testLe_base();
	}
}
