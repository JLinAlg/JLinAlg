/*
 * This file is part of JLinAlg (<http://jlinalg.sourceforge.net/>).
 * 
 * JLinAlg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 * 
 * JLinAlg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with JLinALg. If not, see <http://www.gnu.org/licenses/>.
 */
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
