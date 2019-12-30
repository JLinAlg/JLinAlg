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
package org.jlinalg.doublewrapper;

import static org.junit.Assert.assertEquals;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.Vector;
import org.jlinalg.testutil.VectorTestBase;
import org.junit.Assume;
import org.junit.Test;

/**
 * @author Georg Thimm, Andreas Keilhauer
 */
public class DoubleWrapperVectorTest
		extends VectorTestBase<DoubleWrapper>
{
	/**
	 * @see org.jlinalg.testutil.TestBaseInterface#getFactory()
	 */
	@Override
	public IRingElementFactory<DoubleWrapper> getFactory()
	{
		return DoubleWrapper.FACTORY;
	}

	@Test
	public void testDistance()
	{
		Vector<DoubleWrapper> vec = new Vector<>(vec_0_1_9,
				getFactory());
		Assume.assumeTrue(dataTypeHasNegativeValues());
		Vector<DoubleWrapper> vec2 = new Vector<>(vec_m1_m2_2,
				getFactory());
		assertEquals(getFactory().get("59").sqrt(), vec.distance(vec2));
	}
}