/**
 * 
 */
package org.jlinalg.doublewrapper;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.testutil.LinAlgFactoryTestBase;

/**
 * @author Georg Thimm
 */
public class DoubleWrapperLinAlgFactoryTest
		extends LinAlgFactoryTestBase<DoubleWrapper>
{
	@Override
	public IRingElementFactory<DoubleWrapper> getFactory()
	{
		return DoubleWrapper.FACTORY;
	}
}
