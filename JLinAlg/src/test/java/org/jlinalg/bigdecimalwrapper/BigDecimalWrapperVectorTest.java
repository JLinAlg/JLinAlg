/**
 * 
 */
package org.jlinalg.bigdecimalwrapper;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.testutil.VectorTestBase;

/**
 * @author Georg Thimm
 */
public class BigDecimalWrapperVectorTest
		extends VectorTestBase<BigDecimalWrapper>
{
	private IRingElementFactory<BigDecimalWrapper> factory = new BigDecimalWrapperFactory(
			50);

	/**
	 * @see org.jlinalg.testutil.TestBaseInterface#getFactory()
	 */
	@Override
	public IRingElementFactory<BigDecimalWrapper> getFactory()
	{
		return factory;
	}
}