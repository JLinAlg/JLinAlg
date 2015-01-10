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
		extends OperatorTestBase<RE>
{

	/**
	 * Constructor: used by the test suite runner.
	 */
	public SumReductionTest(IRingElementFactory<RE> factory)
	{
		super(factory);
	}

	@Test
	public void test()
	{
		Vector<RE> v = new Vector<RE>(s_1to5, getFactory());
		SumReduction<RE> red = new SumReduction<RE>();
		RE result = v.reduce(red);
		assertSimilar(getFactory().get("15"), result, "0.000001");
	}

}
