/**
 * 
 */
package org.jlinalg.operator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

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
public class AbsOperatorTest<RE extends IRingElement<RE>>
		extends
		OperatorTestBase<RE>
{
	final MonadicOperator<RE> operator;

	/**
	 * Constructor: used by the test suite runner.
	 */
	public AbsOperatorTest(IRingElementFactory<RE> factory)
	{
		super(factory);
		operator = factory.getAbsOperator();
	}

	final static String[] s_abs = new String[] {
			"2", "1", "0", "1", "1"
	};

	@Test
	public void testAbs()
	{
		assumeTrue(!methodIsDepreciated(getFactory().one(), "abs", null)
				&& dataTypeHasNegativeValues());
		Vector<RE> v = new Vector<>(s_m2to2, getFactory());
		Vector<RE> res = v.apply(operator);
		Vector<RE> should = new Vector<>(s_abs, getFactory());
		assertEquals(getFactory().toString(), should, res);
	}

}
