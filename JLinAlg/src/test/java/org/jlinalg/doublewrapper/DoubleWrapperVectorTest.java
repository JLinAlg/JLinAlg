/**
 * 
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
		Vector<DoubleWrapper> vec = new Vector<DoubleWrapper>(vec_0_1_9,
				getFactory());
		Assume.assumeTrue(dataTypeHasNegativeValues());
		Vector<DoubleWrapper> vec2 = new Vector<DoubleWrapper>(vec_m1_m2_2,
				getFactory());
		assertEquals(getFactory().get("59").sqrt(), vec.distance(vec2));
	}
}