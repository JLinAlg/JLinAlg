/**
 * 
 */
package org.jlinalg.f2;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.testutil.LinAlgFactoryTestBase;

/**
 * @author Georg Thimm
 */
public class F2FactoryTest
		extends LinAlgFactoryTestBase<F2>
{
	@Override
	public IRingElementFactory<F2> getFactory()
	{
		return F2.FACTORY;
	}
}
