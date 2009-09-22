/**
 * 
 */
package org.jlinalg.bigdecimalwrapper;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.testutil.LinAlgFactoryTestBase;

/**
 * @author Georg Thimm
 */
public class BigDecimalLinAlgFactoryTest
		extends LinAlgFactoryTestBase<BigDecimalWrapper>
{

	private final IRingElementFactory<BigDecimalWrapper> factory = new BigDecimalWrapperFactory(
			30);

	@Override
	public IRingElementFactory<BigDecimalWrapper> getFactory()
	{
		return factory;
	}
}
