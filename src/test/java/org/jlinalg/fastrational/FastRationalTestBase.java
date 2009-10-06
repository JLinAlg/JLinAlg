package org.jlinalg.fastrational;

import org.jlinalg.testutil.RingElementTestBase;

/**
 * Convinience class to build other tests for fast rationals
 * 
 * @author Georg Thimm
 */
class FastRationalTestBase
		extends RingElementTestBase<FastRational>
{
	@Override
	public FastRationalFactory getFactory()
	{
		return FastRational.FACTORY;
	}

}
