package org.jlinalg.polynomial;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.testutil.FactoryTestBase;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class PolynomialFactoryTest<RE extends IRingElement<RE>>
		extends FactoryTestBase<Polynomial<RE>>
{
	/**
	 * @see PolynomialTest#data()
	 */
	@Parameters
	public static Collection<Object[]> data()
	{
		return PolynomialTest.data();
	}

	/**
	 * The fixture for this test.
	 */
	private IRingElementFactory<Polynomial<RE>> factory;

	/**
	 * The constructor for this test
	 * 
	 * @param factory
	 *            a factory for polynomials
	 */
	public PolynomialFactoryTest(IRingElementFactory<Polynomial<RE>> factory)
	{
		this.factory = factory;
		assertTrue(this.factory instanceof PolynomialFactory);
	}

	@Override
	public IRingElementFactory<Polynomial<RE>> getFactory()
	{
		return factory;
	}

}
