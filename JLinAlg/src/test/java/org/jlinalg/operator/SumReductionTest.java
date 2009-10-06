/**
 * 
 */
package org.jlinalg.operator;

import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.Vector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * tests for {@link SquareOperator}
 * 
 * @author Georg Thimm 2009
 */
@RunWith(Parameterized.class)
public class SumReductionTest<RE extends IRingElement<RE>>
		extends OperatorTestBase<RE>
{

	/**
	 * Constructor: used by the test suite runner.
	 */
	public SumReductionTest(IRingElementFactory<RE> factory)
	{
		super(factory);
	}

	@Test
	public void test()
	{
		Vector<RE> v = new Vector<RE>(s_1to5, getFactory());
		SumReduction<RE> red = new SumReduction<RE>();
		RE result = v.reduce(red);
		assertSimilar(getFactory().get("15"), result, "0.000001");
	}

}
