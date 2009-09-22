/**
 * 
 */
package org.jlinalg.rational;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.testutil.LinAlgFactoryTestBase;

/**
 * @author Georg Thimm
 */
public class RationalLinAlgFactoryTest
		extends LinAlgFactoryTestBase<Rational>
{
	@Override
	public IRingElementFactory<Rational> getFactory()
	{
		return Rational.FACTORY;
	}
}
