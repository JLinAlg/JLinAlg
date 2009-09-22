/**
 * 
 */
package org.jlinalg.doublewrapper;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.testutil.VectorTestBase;

/**
 * @author Georg Thimm
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
}