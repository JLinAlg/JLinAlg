/*
 * This file is part of JLinAlg (<http://jlinalg.sourceforge.net/>).
 * 
 * JLinAlg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 * 
 * JLinAlg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with JLinALg. If not, see <http://www.gnu.org/licenses/>.
 */
package org.jlinalg.doublewrapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.InvalidOperationException;
import org.jlinalg.LinAlgFactory;
import org.jlinalg.Matrix;
import org.jlinalg.Vector;
import org.jlinalg.rational.Rational;
import org.jlinalg.testutil.RandomNumberList;
import org.jlinalg.testutil.RingElementTestBase;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Georg Thimm (2009)
 */
public class DoubleWrapperTest
		extends
		RingElementTestBase<DoubleWrapper>
{
	/**
	 * a variable used in the tests
	 */
	DoubleWrapper a;

	/**
	 * a variable used in the tests
	 */
	DoubleWrapper b;

	/**
	 * a variable used in the tests
	 */
	DoubleWrapper c;

	/**
	 * a variable used in the tests
	 */
	DoubleWrapper d;

	/**
	 * a variable used in the tests
	 */
	DoubleWrapper e;

	/**
	 * a DoubleWrapper vector used in the tests. Value: [0,0,0]
	 */
	private Vector<DoubleWrapper> v1;

	/**
	 * a DoubleWrapper vector used in the tests. Value: [0,0,0]
	 */
	private Vector<DoubleWrapper> v2;

	/**
	 * a DoubleWrapper vector used in the tests. Value: [0,0,1/2]
	 */
	private Vector<DoubleWrapper> v3;

	/**
	 * a DoubleWrapper vector used in the tests. Value: [0,0,-1/2]
	 */
	private Vector<DoubleWrapper> v4;

	/**
	 * a DoubleWrapper vector used in the tests. Value: [0,0,0,0]
	 */
	private Vector<DoubleWrapper> w;

	/**
	 * setup the a DoubleWrapper numbers and vectors used in the tests
	 */
	@Before
	public void setup()
	{
		a = DoubleWrapper.FACTORY.get(1);
		b = DoubleWrapper.FACTORY.get(1);
		c = DoubleWrapper.FACTORY.get(1.0 / 3.0);
		d = DoubleWrapper.FACTORY.get(2.5);
		e = DoubleWrapper.FACTORY.get(5.0 / 6.0);

		v1 = new Vector<>(3, DoubleWrapper.FACTORY);
		for (int i = 1; i <= 3; i++)
			v1.set(i, DoubleWrapper.FACTORY.zero());
		v2 = v1.copy();
		v3 = v1.copy();
		v3.set(3, DoubleWrapper.FACTORY.get(0.5));
		v4 = v1.copy();
		v4.set(3, DoubleWrapper.FACTORY.get(-0.5));

		w = new Vector<>(4, DoubleWrapper.FACTORY);
		for (int i = 1; i <= 4; i++)
			w.set(i, DoubleWrapper.FACTORY.zero());

	}

	/**
	 * test {@link DoubleWrapper#invert()} for random values
	 */
	@Test
	public void invert()
	{
		for (DoubleWrapper dw1 : new RandomNumberList<>(DoubleWrapper.FACTORY,
				20))
		{
			if (!dw1.equals(DoubleWrapper.FACTORY.zero())) {
				DoubleWrapper inv = dw1.invert();
				assertFalse(inv.equals(dw1));
				assertTrue(inv.invert().subtract(dw1).abs()
						.le(DoubleWrapper.FACTORY.get(0.00001)));
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
		Matrix<DoubleWrapper> m = new Matrix<>(2, 2, DoubleWrapper.FACTORY);
		m.set(1, 1, DoubleWrapper.FACTORY.get(2));
		m.set(1, 2, DoubleWrapper.FACTORY.get(-1));
		m.set(2, 1, DoubleWrapper.FACTORY.get(-1));
		m.set(2, 2, DoubleWrapper.FACTORY.get(1));

		Matrix<DoubleWrapper> inv = m.inverse();
		assertTrue(inv.get(1, 1).equals(DoubleWrapper.FACTORY.get(1)));
		assertTrue(inv.get(1, 2).equals(DoubleWrapper.FACTORY.get(1)));
		assertTrue(inv.get(2, 1).equals(DoubleWrapper.FACTORY.get(1)));
		assertTrue(inv.get(2, 2).equals(DoubleWrapper.FACTORY.get(2)));
	}

	/**
	 * test {@link DoubleWrapper#lt(org.jlinalg.IRingElement)}
	 * {@link DoubleWrapper#gt(org.jlinalg.IRingElement)}
	 * {@link DoubleWrapper#compareTo(org.jlinalg.IRingElement)}
	 * {@link DoubleWrapper#equals(Object)}
	 */
	@Test
	public void misc()
	{
		assertTrue(a.lt(d));
		assertTrue(d.gt(a));
		assertTrue(a.compareTo(b) == 0);
		DoubleWrapper n = d.negate();
		assertTrue(n.lt(DoubleWrapper.FACTORY.zero()));
		assertTrue(n.negate().equals(d));
		assertTrue(n.abs().equals(d));
	}

	/**
	 * test {@link DoubleWrapper#subtract(org.jlinalg.IRingElement)} for random
	 * values.
	 */
	@Test
	public void subtract()
	{
		assertTrue(a.subtract(b).equals(DoubleWrapper.FACTORY.zero()));
		for (DoubleWrapper dw1 : new RandomNumberList<>(DoubleWrapper.FACTORY,
				20))
		{
			assertTrue(dw1.toString(), dw1.equals(dw1));

			for (DoubleWrapper dw2 : new RandomNumberList<>(
					DoubleWrapper.FACTORY, 20))
			{
				double d1 = dw1.doubleValue();
				double d2 = dw2.doubleValue();

				assertTrue(dw1 + "-" + dw2 + "!=" + d1 + "-" + d2, Math.abs(
						dw1.subtract(dw2).doubleValue() - (d1 - d2)) < 0.0001);
			}
		}
	}

	/**
	 * test {@link DoubleWrapper#abs()}
	 */
	@Test
	public final void testAbs()
	{
		for (DoubleWrapper dw1 : new RandomNumberList<>(DoubleWrapper.FACTORY,
				20))
		{
			double d1 = Math.abs(dw1.doubleValue());
			DoubleWrapper abs = dw1.abs();
			assertTrue(abs.ge(DoubleWrapper.FACTORY.zero()));
			assertTrue(d1 == abs.doubleValue());
		}
	}

	/**
	 * test {@link DoubleWrapper#add(org.jlinalg.IRingElement)}
	 */
	@Test
	public void testAdd()
	{
		for (DoubleWrapper dw1 : new RandomNumberList<>(DoubleWrapper.FACTORY,
				20))
		{
			assertTrue(dw1.toString(), dw1.equals(dw1));

			for (DoubleWrapper dw2 : new RandomNumberList<>(
					DoubleWrapper.FACTORY, 20))
			{
				double d1 = dw1.doubleValue();
				double d2 = dw2.doubleValue();

				assertTrue(dw1 + "+" + dw2 + "!=" + d1 + d2, Math
						.abs(dw1.add(dw2).doubleValue() - (d1 + d2)) < 0.0001);
			}
		}
	}

	/**
	 * test {@link DoubleWrapper#compareTo(org.jlinalg.IRingElement)} for random
	 * values
	 */
	@Test
	public void testCompare()
	{
		for (DoubleWrapper dw1 : new RandomNumberList<>(DoubleWrapper.FACTORY,
				20))
		{
			assertTrue(dw1.toString(), dw1.equals(dw1));

			for (DoubleWrapper dw2 : new RandomNumberList<>(
					DoubleWrapper.FACTORY, 20))
			{
				Double d1 = Double.valueOf(dw1.doubleValue());
				Double d2 = Double.valueOf(dw2.doubleValue());
				assertTrue(
						dw1 + ".compareTo(" + dw2 + ")==" + dw1.compareTo(dw2)
								+ " but " + d1 + ".compareTo(" + d2 + ")=="
								+ d1.compareTo(d2),
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
	 * test {@link DoubleWrapper#compareTo(org.jlinalg.IRingElement)}
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
	 * test {@link DoubleWrapper#equals(Object)}
	 */
	@Test
	public final void testEqualsObject()
	{
		DoubleWrapper r1 = DoubleWrapper.FACTORY.get(-1);
		assertTrue(r1 + "=-1", r1.equals(DoubleWrapper.FACTORY.m_one()));
		r1 = DoubleWrapper.FACTORY.get(1);
		assertTrue(r1 + "!=1", r1.equals(DoubleWrapper.FACTORY.one()));
		for (DoubleWrapper dw1 : new RandomNumberList<>(DoubleWrapper.FACTORY,
				20))
		{
			assertTrue(dw1.toString(), dw1.equals(dw1));

			for (DoubleWrapper dw2 : new RandomNumberList<>(
					DoubleWrapper.FACTORY, 20))
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
	 * test whether {@link DoubleWrapperFactory#getArray(int)} does not retrun a
	 * null.
	 */
	@Test
	public void testGetArrayInt()
	{
		assertTrue("Cannot access \'DoubleWrapper\'-array",
				DoubleWrapper.FACTORY.getArray(5) != null);
	}

	/**
	 * test whether {@link DoubleWrapperFactory#get(int)} returns a zero value
	 * for a zero argument.
	 */
	@Test
	public void testGetObject()
	{
		assertTrue(DoubleWrapper.FACTORY.get(0).doubleValue() == 0.0);
	}

	/**
	 * test {@link DoubleWrapper#hashCode()}
	 */
	@Test
	public void testHashCode()
	{
		DoubleWrapper r1 = DoubleWrapper.FACTORY.get(1.2);
		DoubleWrapper r2 = DoubleWrapper.FACTORY.get(1.2);
		assertEquals(r1.hashCode(), r1.hashCode());
		assertEquals(r1.hashCode(), r2.hashCode());

		DoubleWrapper a = DoubleWrapper.FACTORY.get(10.11);
		DoubleWrapper b = DoubleWrapper.FACTORY.get(10.11);
		DoubleWrapper c = DoubleWrapper.FACTORY.get(1.1);
		DoubleWrapper d = DoubleWrapper.FACTORY.get(1.1);
		assertTrue("hashcode not equal: " + a + " & " + b,
				a.hashCode() == b.hashCode());
		assertTrue("hashcode not equal: " + c + " & " + d,
				c.hashCode() == d.hashCode());
		Vector<DoubleWrapper> v1 = new Vector<>(2, DoubleWrapper.FACTORY);
		v1.set(1, a);
		v1.set(2, c);
		Vector<DoubleWrapper> v2 = new Vector<>(2, DoubleWrapper.FACTORY);
		v2.set(1, b);
		v2.set(2, d);
		assertTrue("hashcode not equal: " + v1 + " & " + v2,
				v1.hashCode() == v2.hashCode());
	}

	/**
	 * test {@link DoubleWrapper#invert()}
	 */
	@Test
	public final void testInvert()
	{
		DoubleWrapper d = DoubleWrapper.FACTORY.get(-1);
		assertTrue("1/-1)=-1",
				d.invert().equals(DoubleWrapper.FACTORY.m_one()));
		d = DoubleWrapper.FACTORY.get(1);
		assertTrue("1/1=1", d.invert().equals(DoubleWrapper.FACTORY.one()));

		for (DoubleWrapper d1 : new RandomNumberList<>(DoubleWrapper.FACTORY,
				10))
		{
			DoubleWrapper d2 = d1.invert();
			assertTrue("1/(1/" + d1 + "))!=" + d2.invert(), d2.invert()
					.subtract(d1).abs().le(DoubleWrapper.FACTORY.get(1e-6)));
		}
	}

	/**
	 * test {@link Vector#isZero()}
	 */
	@Test
	public void testIsZero()
	{
		v1.set(2, DoubleWrapper.FACTORY.one());
		v2.set(3, DoubleWrapper.FACTORY.one());
		v3.set(1, DoubleWrapper.FACTORY.one());
		assertTrue("is not Zero: " + v1, !v1.isZero());
		assertTrue("is not Zero: " + v2, !v2.isZero());
		assertTrue("is not Zero: " + v3, !v3.isZero());
		assertTrue("is not Zero: " + v4, !v4.isZero());
		assertTrue("is   Zero: " + w, w.isZero());

	}

	/**
	 * test le(DoubleWrapper) and ge(DoubleWrapper)
	 */
	@Test
	public void testLeGe()
	{
		for (DoubleWrapper r1 : new RandomNumberList<>(DoubleWrapper.FACTORY,
				10))
		{
			for (DoubleWrapper r2 : new RandomNumberList<>(
					DoubleWrapper.FACTORY, 10))
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
		assertTrue(DoubleWrapper.FACTORY.get(1.4E-12)
				.le(DoubleWrapper.FACTORY.get(1e-6)));
	}

	/**
	 * test various ways of getting the minus one value.
	 */
	@Test
	public final void testM_one()
	{
		assertTrue(DoubleWrapper.FACTORY.m_one().doubleValue() == -1.0);
		assertTrue(DoubleWrapper.FACTORY.get("-1").doubleValue() == -1.0);
		assertTrue(DoubleWrapper.FACTORY.get(Rational.FACTORY.get(-1))
				.doubleValue() == -1.0);
	}

	/**
	 * test {@link DoubleWrapper#multiply(org.jlinalg.IRingElement)}
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
		assertTrue(DoubleWrapper.FACTORY.get(0)
				.equals(DoubleWrapper.FACTORY.zero().multiply(e)));
		assertTrue(DoubleWrapper.FACTORY.zero()
				.equals(DoubleWrapper.FACTORY.zero().multiply(e)));
		assertTrue(DoubleWrapper.FACTORY.zero()
				.equals(DoubleWrapper.FACTORY.get(0).multiply(e)));
		assertTrue(DoubleWrapper.FACTORY.zero()
				.equals(b.multiply(DoubleWrapper.FACTORY.zero())));
		assertTrue(DoubleWrapper.FACTORY.zero()
				.equals(a.multiply(DoubleWrapper.FACTORY.get(0))));

		for (DoubleWrapper dw1 : new RandomNumberList<>(DoubleWrapper.FACTORY,
				20))
		{
			for (DoubleWrapper dw2 : new RandomNumberList<>(
					DoubleWrapper.FACTORY, 20))
			{
				double d1 = dw1.doubleValue();
				double d2 = dw2.doubleValue();
				assertTrue(dw1 + "*" + dw2 + "!=" + d1 + "*" + d2,
						dw1.multiply(dw2).doubleValue() == d1 * d2);
				if (d2 != 0.0)
					assertTrue(dw1 + "/" + dw2 + "!=" + d1 + "/" + d2,
							dw1.divide(dw2).doubleValue() == d1 / d2);
			}
		}
	}

	/**
	 * @param e2
	 * @param multiply
	 */
	private void assertSmallDiff(DoubleWrapper a, DoubleWrapper b)
	{
		double a1 = (a.abs()).doubleValue();
		double b1 = (b.abs()).doubleValue();
		assertTrue(a + "!=" + b, Math.abs(a1 - b1) < 0.00001);
	}

	/**
	 * test {@link DoubleWrapper#negate()}
	 */
	@Test
	public final void testNegate()
	{
		DoubleWrapper r1 = DoubleWrapper.FACTORY.get(-1);
		assertTrue("-(-1)=1", r1.negate().equals(DoubleWrapper.FACTORY.one()));
		r1 = DoubleWrapper.FACTORY.get(1);
		assertTrue("-(1)=-1",
				r1.negate().equals(DoubleWrapper.FACTORY.m_one()));
		r1 = DoubleWrapper.FACTORY.get(0);
		assertTrue("-(0)=0", r1.negate().equals(DoubleWrapper.FACTORY.zero()));
		for (DoubleWrapper d : new RandomNumberList<>(DoubleWrapper.FACTORY,
				30))
		{
			DoubleWrapper e = d.negate();
			if (!d.equals(DoubleWrapper.FACTORY.zero()))
				assertFalse(d.equals(e));
			assertTrue("-(-" + d + ")!=" + e.negate(), e.negate().equals(d));
		}
	}

	/**
	 * test various ways of getting the one value.
	 */
	@Test
	public final void testConstants()
	{
		assertTrue(DoubleWrapper.FACTORY.one().doubleValue() == 1.0);
		assertTrue(DoubleWrapper.FACTORY.m_one().doubleValue() == -1.0);
		assertTrue(DoubleWrapper.FACTORY.zero().doubleValue() == 0.0);
	}

	/**
	 * test {@link DoubleWrapperFactory#get}
	 */
	@Test
	public final void testDoubleWrapper()
	{
		DoubleWrapper r = DoubleWrapper.FACTORY.get(10);
		assertTrue(r.equals(DoubleWrapper.FACTORY.get("10")));
		r = DoubleWrapper.FACTORY.get(Rational.FACTORY.get(123.456));
		assertTrue(r.equals(DoubleWrapper.FACTORY.get("123.456")));
		r = DoubleWrapper.FACTORY.get(3024.33264);
		assertTrue(r.equals(DoubleWrapper.FACTORY.get("3024.33264")));
	}

	/**
	 * test {@link DoubleWrapper#toString()}
	 */
	@Test
	public final void testToString()
	{
		DoubleWrapper r = DoubleWrapper.FACTORY.get(1.234);
		assertTrue(r.toString().equals("1.234"));
		r = DoubleWrapper.FACTORY.get(-789.123);
		assertTrue(r.toString().equals("-789.123"));
	}

	/**
	 * Test {@link Vector#L2Norm}
	 */
	@Test
	public void testL2Norm() throws Exception
	{
		DoubleWrapperFactory dfac = DoubleWrapper.FACTORY;
		LinAlgFactory<DoubleWrapper> linFac = new LinAlgFactory<>(dfac);
		Vector<DoubleWrapper> v = linFac.zeros(4);
		assertEquals(dfac.zero(), v.L2Norm());
		v.setAll(dfac.get(4));
		assertEquals(dfac.get(8), v.L2Norm());
		v.set(1, dfac.one());
		v.set(2, dfac.get(5));
		v.set(3, dfac.get(11));
		assertEquals(dfac.get(Math.sqrt(1 + 25 + 121 + 16)), v.L2Norm());
	}

	@Override
	public IRingElementFactory<DoubleWrapper> getFactory()
	{
		return DoubleWrapper.FACTORY;
	}
}
