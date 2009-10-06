/**
 * 
 */
package org.jlinalg.fastrational;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.testutil.LinAlgFactoryTestBase;

/**
 * @author Georg Thimm
 */
public class FastRationalLinAlgFactoryTest
		extends LinAlgFactoryTestBase<FastRational>
{
	@Override
	public IRingElementFactory<FastRational> getFactory()
	{
		return FastRational.FACTORY;
	}
}
