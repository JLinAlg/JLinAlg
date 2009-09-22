/**
 * 
 */
package org.jlinalg.f2;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.testutil.MatrixTestBase;

/**
 * @author Georg Thimm
 */
public class F2MatrixTest
		extends MatrixTestBase<F2>
{
	@Override
	public IRingElementFactory<F2> getFactory()
	{
		return F2.FACTORY;
	}
}
