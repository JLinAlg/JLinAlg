/**
 * 
 */
package org.jlinalg.bigdecimalwrapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.InvalidOperationException;
import org.jlinalg.LinAlgFactory;
import org.jlinalg.Vector;
import org.jlinalg.rational.Rational;
import org.jlinalg.testutil.RandomNumberList;
import org.jlinalg.testutil.RingElementTestBase;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Georg Thimm (2009)
 */
public class BigDecimalWrapperTest
		extends RingElementTestBase<BigDecimalWrapper>
{
	/**
	 * a variable used in the tests
	 */
	BigDecimalWrapper c;

	/**
	 * a variable used in the tests
	 */
	BigDecimalWrapper d;

	/**
	 * a variable used in the tests
	 */
	BigDecimalWrapper e;

	/**
	 * a BigDecimalWrapper vector used in the tests. Value: [0,0,0]
	 */
	private Vector<BigDecimalWrapper> v1;

	/**
	 * a BigDecimalWrapper vector used in the tests. Value: [0,0,0]
	 */
	private Vector<BigDecimalWrapper> v2;

	/**
	 * a BigDecimalWrapper vector used in the tests. Value: [0,0,1/2]
	 */
	private Vector<BigDecimalWrapper> v3;

	/**
	 * a BigDecimalWrapper vector used in the tests. Value: [0,0,-1/2]
	 */
	private Vector<BigDecimalWrapper> v4;

	/**
	 * a BigDecimalWrapper vector used in the tests. Value: [0,0,0,0]
	 */
	private Vector<BigDecimalWrapper> w;

	/**
	 * The factory that will be used to create values used the tests.
	 */
	final BigDecimalWrapperFactory factory = new BigDecimalWrapperFactory(77);

	/**
	 * setup the a BigDecimalWrapper numbers and vectors used in the tests
	 */
	@Before
	public void setup()
	{
		c = factory.get(1.0 / 3.0);
		d = factory.get(2.5);
		e = factory.get(5.0 / 6.0);
		v1 = new Vector<BigDecimalWrapper>(3, factory);
		for (int i = 1; i <= 3; i++)
			v1.set(i, factory.zero());
		v2 = v1.copy();
		v3 = v1.copy();
		v3.set(3, factory.get(0.5));
		v4 = v1.copy();
		v4.set(3, factory.get(-0.5));

		w = new Vector<BigDecimalWrapper>(4, factory);
		for (int i = 1; i <= 4; i++)
			w.set(i, factory.zero());

	}

	/**
	 * test {@link BigDecimalWrapper#subtract(org.jlinalg.IRingElement)} for
	 * random values.
	 */
	@Test
	public void testSubtract()
	{
		for (BigDecimalWrapper dw1 : new RandomNumberList<BigDecimalWrapper>(
				factory, 20))
		{
			assertTrue(dw1.toString(), dw1.equals(dw1));

			for (BigDecimalWrapper dw2 : new RandomNumberList<BigDecimalWrapper>(
					factory, 20))
			{
				double d1 = dw1.doubleValue();
				double d2 = dw2.doubleValue();

				assertTrue(
						dw1 + "-" + dw2 + "!=" + d1 + "-" + d2,
						Math.abs(dw1.subtract(dw2).doubleValue() - (d1 - d2)) < 0.0001);
			}
		}
	}

	/**
	 * verify whether a comparison between a different size vectors throws an
	 * exception.
	 * 
	 * @throws InvalidOperationException
	 *             which is expected.
	 */
	@Test(expected = InvalidOperationException.class)
	public void testCompareToExceptions() throws InvalidOperationException
	{
		v1.compareTo(w);
	}

	/**
	 * test {@link BigDecimalWrapper#compareTo(org.jlinalg.IRingElement)}
	 */
	@Test
	public void testCompareToVectors()
	{
		assertTrue(v1 + "!=" + v2, v1.compareTo(v2) == 0);
		assertTrue("v1!=v1  (v1=" + v1 + ")", v1.compareTo(v1) == 0);
		assertTrue("v1<v3", v1.compareTo(v3) == -1);
		assertTrue("v1<v3 (inv)", v3.compareTo(v1) == 1);
		assertTrue("v1>v4", v1.compareTo(v4) == 1);
		assertTrue("v1>v4 (inv)", v4.compareTo(v1) == -1);
	}

	/**
	 * test {@link BigDecimalWrapper#hashCode()}
	 */
	@Test
	public void testHashCode()
	{
		BigDecimalWrapper r1 = factory.get(1.2);
		BigDecimalWrapper r2 = factory.get(1.2);
		assertEquals(r1.hashCode(), r1.hashCode());
		assertEquals(r1.hashCode(), r2.hashCode());

		BigDecimalWrapper a = factory.get(10.11);
		BigDecimalWrapper b = factory.get(10.11);
		BigDecimalWrapper c = factory.get(1.1);
		BigDecimalWrapper d = factory.get(1.1);
		assertTrue("hashcode not equal: " + a + " & " + b,
				a.hashCode() == b.hashCode());
		assertTrue("hashcode not equal: " + c + " & " + d,
				c.hashCode() == d.hashCode());
		Vector<BigDecimalWrapper> v1 = new Vector<BigDecimalWrapper>(2, factory);
		v1.set(1, a);
		v1.set(2, c);
		Vector<BigDecimalWrapper> v2 = new Vector<BigDecimalWrapper>(2, factory);
		v2.set(1, b);
		v2.set(2, d);
		assertTrue("hashcode not equal: " + v1 + " & " + v2,
				v1.hashCode() == v2.hashCode());
	}

	/**
	 * test various {@link BigDecimalWrapperFactory#get(double))}
	 */
	@Test
	public final void testM_one()
	{
		assertEquals(-1.0, factory.m_one().doubleValue(), 0.0);
		assertEquals(-1.0, factory.get("-1").doubleValue(), 0.0);
		assertEquals(-1.0, factory.get(Rational.FACTORY.get(-1)).doubleValue(),
				0.0);
	}

	/**
	 * test {@link BigDecimalWrapper#multiply(org.jlinalg.IRingElement)}
	 */
	@Test
	public final void testMultiplyDivide()
	{
		assertSmallDiff(e, d.multiply(c));
		assertSmallDiff(e, c.multiply(d));
		assertSmallDiff(factory.get(0), factory.zero().multiply(e));
		assertSmallDiff(factory.zero(), factory.zero().multiply(e));
		assertSmallDiff(factory.zero(), factory.get(0).multiply(e));

		for (BigDecimalWrapper dw1 : new RandomNumberList<BigDecimalWrapper>(
				factory, 20))
		{
			for (BigDecimalWrapper dw2 : new RandomNumberList<BigDecimalWrapper>(
					factory, 20))
			{
				double d1 = dw1.doubleValue();
				double d2 = dw2.doubleValue();
				assertTrue(dw1 + "*" + dw2 + "!=" + d1 + "*" + d2, dw1
						.multiply(dw2).doubleValue() == d1 * d2);
				if (d2 != 0.0)
					assertTrue(dw1 + "/" + dw2 + "!=" + d1 + "/" + d2, dw1
							.divide(dw2).doubleValue() == d1 / d2);
			}
		}
	}

	/**
	 * @param a
	 * @param b
	 */
	private void assertSmallDiff(BigDecimalWrapper a, BigDecimalWrapper b)
	{
		double a1 = (a.abs()).doubleValue();
		double b1 = (b.abs()).doubleValue();
		assertTrue(a + "!=" + b,
				Math.abs(a1 - b1) <= (Math.abs(a1) + Math.abs(b1)) * 0.00001);
	}

	/**
	 * test {@link BigDecimalWrapper#negate()}
	 */
	@Test
	public final void testNegate()
	{
		BigDecimalWrapper r1 = factory.get(-1);
		assertTrue("-(-1)=1", r1.negate().equals(factory.one()));
		r1 = factory.get(1);
		assertTrue("-(1)=-1", r1.negate().equals(factory.m_one()));
		r1 = factory.get(0);
		assertTrue("-(0)=0", r1.negate().equals(factory.zero()));
		for (BigDecimalWrapper d : new RandomNumberList<BigDecimalWrapper>(
				factory, 30))
		{
			BigDecimalWrapper e = d.negate();
			if (!d.equals(factory.zero())) assertFalse(d.equals(e));
			assertTrue("-(-" + d + ")!=" + e.negate(), e.negate().equals(d));
		}
	}

	/**
	 * test various ways of getting the one value.
	 */
	@Test
	public final void testConstants()
	{
		assertTrue(factory.one().doubleValue() == 1.0);
		assertTrue(factory.m_one().doubleValue() == -1.0);
		assertTrue(factory.zero().doubleValue() == 0.0);
	}

	/**
	 * test {@link BigDecimalWrapperFactory#get}
	 */
	@Test
	public final void testBigDecimalWrapper()
	{
		BigDecimalWrapper r = factory.get(10);
		assertTrue(r.equals(factory.get("10")));
		r = factory.get(Rational.FACTORY.get(123.456));
		assertTrue(r.equals(factory.get("123.456")));
		r = factory.get(3024.33264);
		assertTrue(r.equals(factory.get("3024.33264")));
	}

	/**
	 * test {@link BigDecimalWrapper#toString()}
	 */
	@Test
	public final void testToString()
	{
		BigDecimalWrapper r = factory.get(1.234);
		assertTrue(r.toString().equals("1.234"));
		r = factory.get(-789.123);
		assertTrue(r.toString().equals("-789.123"));
	}

	/**
	 * Test {@link Vector#L2Norm}
	 */
	@Test
	public void testL2Norm() throws Exception
	{
		BigDecimalWrapperFactory dfac = factory;
		LinAlgFactory<BigDecimalWrapper> linFac = new LinAlgFactory<BigDecimalWrapper>(
				dfac);
		Vector<BigDecimalWrapper> v = linFac.zeros(4);
		assertEquals(dfac.zero(), v.L2Norm());
		v.setAll(dfac.get(4));
		assertSmallDiff(dfac.get(8), v.L2Norm());
		v.set(1, dfac.one());
		v.set(2, dfac.get(5));
		v.set(3, dfac.get(11));
		assertSmallDiff(dfac.get(Math.sqrt(1 + 25 + 121 + 16)), v.L2Norm());
	}

	/**
	 * test whether numbers of distinct precisions can be added
	 */
	@Test(expected = org.jlinalg.InvalidOperationException.class)
	public void testAdditionWithDistinctPercision()
	{
		BigDecimalWrapperFactory dfac = new BigDecimalWrapperFactory(10);
		BigDecimalWrapper l = dfac.get(0);
		l.add(c);
	}

	/**
	 * test whether numbers of distinct precisions can be subtracted
	 */
	@Test(expected = org.jlinalg.InvalidOperationException.class)
	public void testSubtractionWithDistinctPercision()
	{
		BigDecimalWrapperFactory dfac = new BigDecimalWrapperFactory(10);
		BigDecimalWrapper l = dfac.get(0);
		l.subtract(c);
	}

	/**
	 * test whether numbers of distinct precisions can be multiplied
	 */
	@Test(expected = org.jlinalg.InvalidOperationException.class)
	public void testMultiplyWithDistinctPercision()
	{
		BigDecimalWrapperFactory dfac = new BigDecimalWrapperFactory(10);
		BigDecimalWrapper l = dfac.get(0);
		l.multiply(c);
	}

	/**
	 * test whether numbers of distinct precisions can be multiplied
	 */
	@Test(expected = org.jlinalg.InvalidOperationException.class)
	public void testDivideWithDistinctPercision()
	{
		BigDecimalWrapperFactory dfac = new BigDecimalWrapperFactory(10);
		BigDecimalWrapper l = dfac.get(0);
		l.divide(c);
	}

	/**
	 * test {@link BigDecimalWrapper#sqrt()}
	 */
	@Test
	public void testSqrt()
	{
		double[] d = {
				0.0, 0.1, 0.9, 1.0, 2, 90, 100, 1e20, 1e-20, 163
		};

		String diff = BigDecimal.ONE.divide(
				BigDecimal.TEN.pow((int) ((factory.getMathContext()
						.getPrecision() - 3) / (Math.log(10) / Math.log(2))),
						factory.mathContext)).toString();
		for (int i = 0; i < d.length; i++) {
			BigDecimalWrapper n = factory.get(d[i]);
			BigDecimalWrapper s = n.multiply(n);
			BigDecimalWrapper sq = s.sqrt();
			assertSimilar(sq, n, diff);
		}
	}

	@Override
	public IRingElementFactory<BigDecimalWrapper> getFactory()
	{
		return factory;
	}
}
