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

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
		extends
		RingElementTestBase<Polynomial<RE>>
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
				}, {
						PolynomialFactory.getFactory(Complex.FACTORY)
				}, {
						PolynomialFactory.getFactory(DoubleWrapper.FACTORY)
				}, {
						PolynomialFactory.getFactory(
								FieldPFactoryMap.getFactory(Long.valueOf(17L)))
				}, {
						PolynomialFactory
								.getFactory(FieldPFactoryMap.getFactory(prime1))
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

	@Test
	public void testGcd()
	{
		assertEquals(getFactory().get(10),
				getFactory().get(15).gcd(getFactory().get(10)));
		assertEquals(getFactory().get(5),
				getFactory().get(0).gcd(getFactory().get(5)));
		assertEquals(getFactory().get(1),
				getFactory().get(1).gcd(getFactory().get(5)));

		Map<Integer, RE> maps = new HashMap<Integer, RE>();
		maps.put(1, getFactory().getBaseFactory().get(10));
		Polynomial<RE> p = new Polynomial<RE>(maps,
				getFactory().getBaseFactory());
		Polynomial<RE> left = p.divide(p.getHighestCoefficient());
		Polynomial<RE> right = p.multiply(getFactory().get(3))
				.gcd(p.multiply(getFactory().get(2)));
		System.out.println(left);
		System.out.println(right);
		assertEquals(left, right);
	}
}
