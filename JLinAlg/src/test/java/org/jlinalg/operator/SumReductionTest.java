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

import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.Matrix;
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
public class SumReductionTest<RE extends IRingElement<RE>>
		extends
		OperatorTestBase<RE>
{

	private final SumReduction<RE> red = new SumReduction<>();

	/**
	 * Constructor: used by the test suite runner.
	 */
	public SumReductionTest(IRingElementFactory<RE> factory)
	{
		super(factory);
	}

	@Test
	public void testMethod()
	{
		Vector<RE> v = new Vector<>(s_1to5, getFactory());
		RE result = v.apply(red);
		assertSimilar(getFactory().get("15"), result, "0.000001");

	}

	@Test
	public void testVectorOperator()
	{
		Vector<RE> v = new Vector<>(s_1to5, getFactory());
		RE result = red.apply(v);
		assertSimilar(getFactory().get("15"), result, "0.000001");
	}

	@Test
	public void testMatrixOperator()
	{
		Matrix<RE> m = getFactory().convert(new String[][] {
				{
						"-1", "5"
				}, {
						"2", "2"
				}, {
						"0", "4"
				}
		});

		RE result = red.apply(m);

		assertSimilar(getFactory().get("12"), result, "0.000001");
	}

}
