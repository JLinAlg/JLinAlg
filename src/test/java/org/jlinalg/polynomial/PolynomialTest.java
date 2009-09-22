/**
 * 
 */
package org.jlinalg.polynomial;

import java.util.Arrays;
import java.util.Collection;

import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.complex.Complex;
import org.jlinalg.doublewrapper.DoubleWrapper;
import org.jlinalg.field_p.FieldPFactoryMap;
import org.jlinalg.rational.Rational;
import org.jlinalg.testutil.FactoryTestBase;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author Georg Thimm
 */
@RunWith(Parameterized.class)
public class PolynomialTest<RE extends IRingElement<RE>>
		extends FactoryTestBase<Polynomial<RE>>
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
					Rational.FACTORY
				}, {
					Complex.FACTORY
				}, {
					DoubleWrapper.FACTORY
				}, {
					FieldPFactoryMap.getFactory(Long.valueOf(17L))
				}, {
					FieldPFactoryMap.getFactory(prime1)
				}
		};
		return Arrays.asList(data_);
	}

	public PolynomialTest(IRingElementFactory<Polynomial<RE>> factory)
	{
		this.factory = factory;
	}

	private IRingElementFactory<Polynomial<RE>> factory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.testutil.TestBaseInterface#getFactory()
	 */
	@Override
	public IRingElementFactory<Polynomial<RE>> getFactory()
	{
		return factory;
	}

}
