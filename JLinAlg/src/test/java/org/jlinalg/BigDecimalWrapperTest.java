/**
 * 
 */
package org.jlinalg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.jlinalg.BigDecimalWrapper;
import org.jlinalg.BigDecimalWrapperFactory;
import org.jlinalg.InvalidOperationException;
import org.jlinalg.LinAlgFactory;
import org.jlinalg.Matrix;
import org.jlinalg.Rational;
import org.jlinalg.Vector;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Georg Thimm (2009)
 */
public class BigDecimalWrapperTest
{
	/**
	 * a variable used in the tests
	 */
	BigDecimalWrapper a;

	/**
	 * a variable used in the tests
	 */
	BigDecimalWrapper b;

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
	BigDecimalWrapperFactory factory = new BigDecimalWrapperFactory(77);

	/**
	 * setup the a BigDecimalWrapper numbers and vectors used in the tests
	 */
	@Before
	public void setup()
	{
		a = factory.get(1);
		b = factory.get(1);
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
	 * test {@link BigDecimalWrapper#invert()} for random values
	 */
	@Test
	public void invert()
	{
		for (BigDecimalWrapper dw1 : new RandomNumberList<BigDecimalWrapper>(
				factory, 20))
		{
			if (!dw1.equals(factory.zero())) {
				BigDecimalWrapper inv = dw1.invert();
				assertFalse(inv.equals(dw1));
				assertTrue(inv.invert().subtract(dw1).abs().le(
						factory.get(0.00001)));
			}
		}
	}

	/**
	 * quick test for {@link Matrix#inverse()}
	 */
	@Test
	public void matrixInverse()
	{
		// inverse for [2,-1;-1,1]
		Matrix<BigDecimalWrapper> m = new Matrix<BigDecimalWrapper>(2, 2,
				factory);
		m.set(1, 1, factory.get(2));
		m.set(1, 2, factory.get(-1));
		m.set(2, 1, factory.get(-1));
		m.set(2, 2, factory.get(1));

		Matrix<BigDecimalWrapper> inv = m.inverse();
		assertTrue("inv=\n" + inv, similar(inv.get(1, 1), 1));
		assertTrue("inv=\n" + inv, similar(inv.get(1, 2), 1));
		assertTrue("inv=\n" + inv, similar(inv.get(2, 1), 1));
		assertTrue("inv=\n" + inv, similar(inv.get(2, 2), 2));
	}

	/**
	 * @param bigDecimalWrapper
	 * @param i
	 * @return
	 */
	private boolean similar(BigDecimalWrapper v, int i)
	{
		return Math.abs(v.doubleValue() - i) < Math.abs(v.doubleValue() + i) / 1e7;
	}

	/**
	 * test {@link BigDecimalWrapper#lt(org.jlinalg.IRingElement)}
	 * {@link BigDecimalWrapper#gt(org.jlinalg.IRingElement)}
	 * {@link BigDecimalWrapper#compareTo(org.jlinalg.IRingElement)}
	 * {@link BigDecimalWrapper#equals(Object)}
	 */
	@Test
	public void misc()
	{
		assertTrue(a.lt(d));
		assertTrue(d.gt(a));
		assertTrue(a.compareTo(b) == 0);
		BigDecimalWrapper n = d.negate();
		assertTrue(n.lt(factory.zero()));
		assertTrue(n.negate().equals(d));
		assertTrue(n.abs().equals(d));
	}

	/**
	 * test {@link BigDecimalWrapper#subtract(org.jlinalg.IRingElement)} for
	 * random values.
	 */
	@Test
	public void subtract()
	{
		assertTrue(a + ".subtract(" + b + ")=" + a.subtract(b), a.subtract(b)
				.equals(factory.zero()));
		for (BigDecimalWrapper dw1 : new RandomNumberList<BigDecimalWrapper>(
				factory, 20))
		{
			assertTrue(dw1.toString(), dw1.equals(dw1));

			for (BigDecimalWrapper dw2 : new RandomNumberList<BigDecimalWrapper>(
					factory, 20))
			{
				double d1 = dw1.doubleValue();
				double d2 = dw2.doubleValue();

				assertTrue(dw1 + "-" + dw2 + "!=" + d1 + "-" + d2, Math.abs(dw1
						.subtract(dw2).doubleValue()
						- (d1 - d2)) < 0.0001);
			}
		}
	}

	/**
	 * test {@link BigDecimalWrapper#abs()}
	 */
	@Test
	public final void testAbs()
	{
		for (BigDecimalWrapper dw1 : new RandomNumberList<BigDecimalWrapper>(
				factory, 20))
		{
			double d1 = Math.abs(dw1.doubleValue());
			BigDecimalWrapper abs = (BigDecimalWrapper) dw1.abs();
			assertTrue(abs.ge(factory.zero()));
			assertTrue(d1 == abs.doubleValue());
		}
	}

	/**
	 * test {@link BigDecimalWrapper#add(org.jlinalg.IRingElement)}
	 */
	@Test
	public void testAdd()
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

				assertTrue(dw1 + "+" + dw2 + "!=" + d1 + d2, Math.abs(dw1.add(
						dw2).doubleValue()
						- (d1 + d2)) < 0.0001);
			}
		}
	}

	/**
	 * test {@link BigDecimalWrapper#compareTo(org.jlinalg.IRingElement)} for
	 * random values
	 */
	@Test
	public void testCompare()
	{
		for (BigDecimalWrapper dw1 : new RandomNumberList<BigDecimalWrapper>(
				factory, 20))
		{
			assertTrue(dw1.toString(), dw1.equals(dw1));

			for (BigDecimalWrapper dw2 : new RandomNumberList<BigDecimalWrapper>(
					factory, 20))
			{
				Double d1 = dw1.doubleValue();
				Double d2 = dw2.doubleValue();
				assertTrue(dw1 + ".compareTo(" + dw2 + ")=="
						+ dw1.compareTo(dw2) + " but " + d1 + ".compareTo("
						+ d2 + ")==" + d1.compareTo(d2),
						dw1.compareTo(dw2) == d1.compareTo(d2));
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
	 * test {@link BigDecimalWrapper#equals(Object)}
	 */
	@Test
	public final void testEqualsObject()
	{
		BigDecimalWrapper r1 = factory.get(-1);
		assertTrue(r1 + "=-1", r1.equals(factory.m_one()));
		r1 = factory.get(1);
		assertTrue(r1 + "!=1", r1.equals(factory.one()));
		for (BigDecimalWrapper dw1 : new RandomNumberList<BigDecimalWrapper>(
				factory, 20))
		{
			assertTrue(dw1.toString(), dw1.equals(dw1));

			for (BigDecimalWrapper dw2 : new RandomNumberList<BigDecimalWrapper>(
					factory, 20))
			{
				double d1 = dw1.doubleValue();
				double d2 = dw2.doubleValue();
				if (d1 != d2)
					assertFalse(dw1 + "==" + dw2, dw1.equals(dw2));
				else
					assertTrue(dw1 + "!=" + dw2, dw1.equals(dw2));
			}
		}
	}

	/**
	 * test whether {@link BigDecimalWrapperFactory#getArray(int)} does not
	 * retrun a null.
	 */
	@Test
	public void testGetArrayInt()
	{
		assertTrue("Cannot access \'BigDecimalWrapper\'-array", factory
				.getArray(5) != null);
	}

	/**
	 * test whether {@link BigDecimalWrapperFactory#get(int)} returns a zero
	 * value for a zero argument.
	 */
	@Test
	public void testGetObject()
	{
		assertTrue(factory.get(0).doubleValue() == 0.0);
	}

	/**
	 * test {@link BigDecimalWrapper#hashCode()}
	 */
	@Test
	public void testHashCode()
	{
		BigDecimalWrapper r1 = factory.get(1.2);
		BigDecimalWrapper r2 = factory.get(1.2);
		BigDecimalWrapper r3 = factory.get(666.2);
		assertEquals(r1.hashCode(), r1.hashCode());
		assertEquals(r1.hashCode(), r2.hashCode());
		assertNotSame("This may not allways be true!", r1.hashCode(), r3
				.hashCode());

		BigDecimalWrapper a = factory.get(10.11);
		BigDecimalWrapper b = factory.get(10.11);
		BigDecimalWrapper c = factory.get(1.1);
		BigDecimalWrapper d = factory.get(1.1);
		assertTrue("hashcode not equal: " + a + " & " + b, a.hashCode() == b
				.hashCode());
		assertTrue("hashcode not equal: " + c + " & " + d, c.hashCode() == d
				.hashCode());
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
	 * test {@link BigDecimalWrapper#invert()}
	 */
	@Test
	public final void testInvert()
	{
		BigDecimalWrapper d = factory.get(-1);
		assertTrue("1/-1)=-1", d.invert().equals(factory.m_one()));
		d = factory.get(1);
		assertTrue("1/1=1", d.invert().equals(factory.one()));

		for (BigDecimalWrapper d1 : new RandomNumberList<BigDecimalWrapper>(
				factory, 10))
		{
			BigDecimalWrapper d2 = d1.invert();
			assertTrue("1/(1/" + d1 + "))!=" + d2.invert(), d2.invert()
					.subtract(d1).abs().le(factory.get(1e-6)));
		}
	}

	/**
	 * test {@link Vector#isZero()}
	 */
	@Test
	public void testIsZero()
	{
		v1.set(2, factory.one());
		v2.set(3, factory.one());
		v3.set(1, factory.one());
		assertTrue("is not Zero: " + v1, !v1.isZero());
		assertTrue("is not Zero: " + v2, !v2.isZero());
		assertTrue("is not Zero: " + v3, !v3.isZero());
		assertTrue("is not Zero: " + v4, !v4.isZero());
		assertTrue("is   Zero: " + w, w.isZero());

	}

	/**
	 * test le(BigDecimalWrapper) and ge(BigDecimalWrapper)
	 */
	@Test
	public void testLeGe()
	{
		for (BigDecimalWrapper r1 : new RandomNumberList<BigDecimalWrapper>(
				factory, 10))
		{
			for (BigDecimalWrapper r2 : new RandomNumberList<BigDecimalWrapper>(
					factory, 10))
			{
				if (r1.doubleValue() <= r2.doubleValue()) {
					assertTrue(r1.le(r2));
					assertTrue(r2.ge(r1));
				}
				else {
					assertTrue(r2.le(r1));
					assertTrue(r1.ge(r2));
				}
			}
		}
		assertTrue(factory.get(1.4E-12).le(factory.get(1e-6)));
	}

	/**
	 * test various ways of getting the minus one value.
	 */
	@Test
	public final void testM_one()
	{
		assertTrue(factory.m_one().doubleValue() == -1.0);
		assertTrue(factory.get("-1").doubleValue() == -1.0);
		assertTrue(factory.get(Rational.FACTORY.get(-1)).doubleValue() == -1.0);
	}

	/**
	 * test {@link BigDecimalWrapper#multiply(org.jlinalg.IRingElement)}
	 */
	@Test
	public final void testMultiplyDivide()
	{
		assertTrue(a.equals(b));
		assertTrue(a.equals(b.multiply(b)));
		assertTrue(a.equals(b.multiply(a)));
		assertTrue(b.equals(a));
		assertSmallDiff(e, d.multiply(c));
		assertSmallDiff(e, c.multiply(d));
		assertSmallDiff(factory.get(0), factory.zero().multiply(e));
		assertSmallDiff(factory.zero(), factory.zero().multiply(e));
		assertSmallDiff(factory.zero(), factory.get(0).multiply(e));
		assertSmallDiff(factory.zero(), b.multiply(factory.zero()));
		assertSmallDiff(factory.zero(), a.multiply(factory.get(0)));

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
		double a1 = ((BigDecimalWrapper) a.abs()).doubleValue();
		double b1 = ((BigDecimalWrapper) b.abs()).doubleValue();
		assertTrue(a + "!=" + b, Math.abs(a1 - b1) <= (Math.abs(a1) + Math
				.abs(b1)) * 0.00001);
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
		BigDecimalWrapper b = l.add(a);
	}

	/**
	 * test whether numbers of distinct precisions can be subtracted
	 */
	@Test(expected = org.jlinalg.InvalidOperationException.class)
	public void testSubtractionWithDistinctPercision()
	{
		BigDecimalWrapperFactory dfac = new BigDecimalWrapperFactory(10);
		BigDecimalWrapper l = dfac.get(0);
		BigDecimalWrapper b = l.subtract(a);
	}

	/**
	 * test whether numbers of distinct precisions can be multiplied
	 */
	@Test(expected = org.jlinalg.InvalidOperationException.class)
	public void testMultiplyWithDistinctPercision()
	{
		BigDecimalWrapperFactory dfac = new BigDecimalWrapperFactory(10);
		BigDecimalWrapper l = dfac.get(0);
		BigDecimalWrapper b = l.multiply(a);
	}

	/**
	 * test whether numbers of distinct precisions can be multiplied
	 */
	@Test(expected = org.jlinalg.InvalidOperationException.class)
	public void testDivideWithDistinctPercision()
	{
		BigDecimalWrapperFactory dfac = new BigDecimalWrapperFactory(10);
		BigDecimalWrapper l = dfac.get(0);
		BigDecimalWrapper b = l.divide(a);
	}

	/**
	 * test {@link BigDecimalWrapper#sqrt()}
	 */
	@Test
	public void testSqrt()
	{
		double[] d =
		{
				0.0, 0.1, 0.9, 1.0, 2, 90, 100, 1e20, 1e-20, 163
		};
		for (int i = 0; i < d.length; i++) {
			BigDecimalWrapper n = factory.get(d[i]);
			BigDecimalWrapper s = n.multiply(n);
			BigDecimalWrapper sq = s.sqrt();
			assertTrue(sq.subtract(n).abs().le(
					n.divide(factory.get(factory.getMathContext()
							.getPrecision() - 2))));
			// just to be sure....
			assertTrue(Math.abs(sq.doubleValue() - sq.doubleValue()) <= Math
					.abs(sq.doubleValue() + sq.doubleValue()) * 1e-7);
			System.out.print("is   =" + n + "\nshould=" + sq + "\ndiff ="
					+ n.subtract(sq).abs() + "\n\n");
		}
	}
}
