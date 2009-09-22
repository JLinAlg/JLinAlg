package org.jlinalg.polynomial;

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

	@Parameters
	public static Collection<Object[]> data()
	{
		return PolynomialTest.data();
	}

	private IRingElementFactory<Polynomial<RE>> factory;

	public PolynomialFactoryTest(IRingElementFactory<Polynomial<RE>> factory)
	{
		this.factory = factory;
	}

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
