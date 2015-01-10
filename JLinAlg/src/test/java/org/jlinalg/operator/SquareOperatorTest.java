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
package org.jlinalg.operator;

import static org.junit.Assume.assumeTrue;

import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.Vector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * tests for {@link SquareOperator}
 * 
 * @author Georg Thimm 2009
 */
@RunWith(Parameterized.class)
public class SquareOperatorTest<RE extends IRingElement<RE>>
		extends OperatorTestBase<RE>
{
	final MonadicOperator<RE> operator;

	/**
	 * Constructor: used by the test suite runner.
	 */
	@SuppressWarnings("unchecked")
	public SquareOperatorTest(IRingElementFactory<RE> factory)
	{
		super(factory);
		operator = (MonadicOperator<RE>) SquareOperator.getInstance();
	}

	@Test
	public void test()
	{
		assumeTrue(!methodIsDepreciated(getFactory().one(), "abs", null));
		IRingElementFactory<RE> f = getFactory();
		testSqrSum(getLinAlgFactory().ones(5), f.get(5));
		testSqrSum(getLinAlgFactory().ones(1), f.one());
		testSqrSum(getLinAlgFactory().zeros(1), f.zero());
		testSqrSum(getLinAlgFactory().zeros(10), f.zero());
	}

	@Test
	public void testOnFractions()
	{
		assumeTrue(!methodIsDepreciated(getFactory().one(), "abs", null));
		assumeTrue(!dataTypeIsDiscreet());
		IRingElementFactory<RE> f = getFactory();
		testSqrSum(getLinAlgFactory().ones(5).multiply(f.get("1/2")), f
				.get("5/4"));
		testSqrSum(getLinAlgFactory().ones(7).multiply(f.get("1/3")), f
				.get("7/9"));
	}

	/**
	 * apply the operator to {@code v} and compare the result with the expected
	 * value
	 * 
	 * @param v
	 * @param should
	 */
	private void testSqrSum(Vector<RE> v, RE should)
	{
		RE is = v.apply(operator).sum();
		assertSimilar(should, is, "0.0000001");
	}

}
