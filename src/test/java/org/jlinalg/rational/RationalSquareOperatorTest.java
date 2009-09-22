/**
 * 
 */
package org.jlinalg.rational;

import static org.junit.Assert.assertEquals;

import org.jlinalg.IRingElement;
import org.jlinalg.LinAlgFactory;
import org.jlinalg.Vector;
import org.jlinalg.operator.MonadicOperator;
import org.jlinalg.operator.SquareOperator;
import org.jlinalg.rational.Rational.RationalFactory;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * tests for {@link SquareOperator}
 * 
 * @author Georg Thimm 2009
 */
public class RationalSquareOperatorTest
{
	private static MonadicOperator<? extends IRingElement<?>> operator;

	@BeforeClass
	static public void setup()
	{
		operator = SquareOperator.getInstance();
	}

	@Test
	public void test()
	{
		RationalFactory f = Rational.FACTORY;
		LinAlgFactory<Rational> factory = new LinAlgFactory<Rational>(f);
		testSqrSum(factory.ones(5), f.get(5));
		testSqrSum(factory.ones(1), f.one());
		testSqrSum(factory.zeros(1), f.zero());
		testSqrSum(factory.zeros(10), f.zero());
		testSqrSum(factory.ones(5).multiply(f.get(1L, 2L)), f.get(5, 4));
		testSqrSum(factory.ones(7).multiply(f.get(1L, 3L)), f.get(7, 9));
	}

	/**
	 * apply the operator to {@code v} and compare the result with the expected
	 * value
	 * 
	 * @param v
	 * @param should
	 */
	@SuppressWarnings("unchecked")
	private void testSqrSum(Vector<Rational> v, Rational should)
	{
		Rational is = v.apply((MonadicOperator<Rational>) operator).sum();
		assertEquals(should, is);
	}
}
