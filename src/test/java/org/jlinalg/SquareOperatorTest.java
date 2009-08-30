/**
 * 
 */
package org.jlinalg;

import static org.junit.Assert.assertTrue;

import org.jlinalg.IRingElement;
import org.jlinalg.LinAlgFactory;
import org.jlinalg.MonadicOperator;
import org.jlinalg.Rational;
import org.jlinalg.SquareOperator;
import org.jlinalg.Vector;
import org.jlinalg.Rational.RationalFactory;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * tests for {@link SquareOperator}
 * 
 * @author Georg Thimm 2009
 */
public class SquareOperatorTest
{
	private static MonadicOperator<? extends IRingElement> operator;

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
	 * @param v
	 * @param one
	 */
	private void testSqrSum(Vector<Rational> v, Rational should)
	{
		Rational is = v.apply(operator).sum();
		assertTrue(is.equals(should));
	}
}
