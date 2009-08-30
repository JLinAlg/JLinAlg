package org.jlinalg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.jlinalg.DoubleWrapper;
import org.jlinalg.InvalidOperationException;
import org.jlinalg.Matrix;
import org.jlinalg.Rational;
import org.jlinalg.Vector;
import org.jlinalg.Rational.RationalFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class {@link Rational}
 * 
 * @author Georg Thimm
 */
public class RationalTest
{
	/**
	 * a variable used in the tests
	 */
	static Rational a;

	/**
	 * a variable used in the tests
	 */
	static Rational b;

	/**
	 * a variable used in the tests
	 */
	static Rational c;

	/**
	 * a variable used in the tests
	 */
	static Rational d;

	/**
	 * a variable used in the tests
	 */
	static Rational e;

	/**
	 * a rational vector used in the tests. Value: [0,0,0]
	 */
	private static Vector<Rational> v1;

	/**
	 * a rational vector used in the tests. Value: [0,0,0]
	 */
	private static Vector<Rational> v2;

	/**
	 * a rational vector used in the tests. Value: [0,0,1/2]
	 */
	private static Vector<Rational> v3;

	/**
	 * a rational vector used in the tests. Value: [0,0,-1/2]
	 */
	private static Vector<Rational> v4;

	/**
	 * a rational vector used in the tests. Value: [0,0,0,0]
	 */
	private static Vector<Rational> w;

	static/**
			 * a variable used in the tests
			 */
	Rational zero;

	/**
	 * setup the a rational numbers and vectors used in the tests
	 */
	@Before
	public void setup()
	{
		a = Rational.FACTORY.get(1, 1);
		b = Rational.FACTORY.get(7, 7);
		c = Rational.FACTORY.get(1, 3);
		d = Rational.FACTORY.get(5, 2);
		e = Rational.FACTORY.get(5, 6);
		zero = Rational.FACTORY.get(0, 1);

		v1 = new Vector<Rational>(3, Rational.FACTORY);
		for (int i = 1; i <= 3; i++)
			v1.set(i, Rational.FACTORY.zero());
		v2 = v1.copy();
		v3 = v1.copy();
		v3.set(3, Rational.FACTORY.get(1, 2));
		v4 = v1.copy();
		v4.set(3, Rational.FACTORY.get(1, -2));

		w = new Vector<Rational>(4, Rational.FACTORY);
		for (int i = 1; i <= 4; i++)
			w.set(i, Rational.FACTORY.zero());

	}

	/**
	 * test {@link Rational#divide(org.jlinalg.IRingElement)} for random values
	 */
	@Test
	public void divide()
	{
		assertTrue(a.divide(b).equals(a));
		assertTrue(c.divide(a).equals(c));
		assertTrue(a.divide(c).equals(Rational.FACTORY.get(3, 1)));
		assertTrue(zero.divide(a).equals(zero));
		assertTrue(d.divide(c) + "!=" + Rational.FACTORY.get(2, 15), d
				.divide(c).equals(Rational.FACTORY.get(15, 2)));

		Rational r = (Rational) a.divide(Rational.FACTORY.m_one());
		assertTrue("denominator negative: " + r.getDenominator(), r
				.getDenominator().longValue() > 0);
		assertTrue(r.getNumerator().longValue() == -1);
		for (int k = 0; k < 200; k++) {
			int a = (int) (Math.random() * 100 - 50);
			int b = (int) (Math.random() * 100 - 50);
			if (b == 0) b = 123;
			int c = (int) (Math.random() * 100 - 50);
			if (c == 0) c = 442;
			int d = (int) (Math.random() * 100 - 50);
			if (d == 0) d = 221;
			Rational r1 = Rational.FACTORY.get(a, b);
			Rational r2 = Rational.FACTORY.get(c, d);
			Rational vv = (Rational) r1.divide(r2);
			assertTrue("denominator negative: " + vv.getDenominator(), vv
					.getDenominator().longValue() > 0);
			double v = ((double) a / b) / ((double) c / d);
			assertTrue(r1 + "/" + r2 + "!=" + v, Math.abs(vv.getNumerator()
					.doubleValue()
					/ vv.getDenominator().doubleValue() - v) < 0.0001);
		}
	}

	/**
	 * test {@link Rational#invert()} for random values
	 */
	@Test
	public void invert()
	{
		for (int k = 0; k < 200; k++) {
			int a = (int) (Math.random() * 100 - 50);
			int b = (int) (Math.random() * 100 - 50);
			if (b == 0) b = 123;
			if (a == 0) a = 777;
			Rational r1 = Rational.FACTORY.get(a, b);
			assertTrue("denominator negative: " + r1.getDenominator(), r1
					.getDenominator().longValue() > 0);

			Rational vv = r1.invert();
			assertTrue("denominator negative: " + vv.getDenominator(), vv
					.getDenominator().longValue() > 0);
			double v = ((double) b / a);
			assertTrue("1/(" + r1 + ")!=" + v, Math.abs(vv.getNumerator()
					.doubleValue()
					/ vv.getDenominator().doubleValue() - v) < 0.0001);
		}
	}

	/**
	 * quick test for {@link Matrix#inverse()}
	 */
	@Test
	public void matrixInverse()
	{
		// inverse for [2,-1;-1,1]
		Matrix<Rational> m = new Matrix<Rational>(2, 2, Rational.FACTORY);
		m.set(1, 1, Rational.FACTORY.get(2));
		m.set(1, 2, Rational.FACTORY.get(-1));
		m.set(2, 1, Rational.FACTORY.get(-1));
		m.set(2, 2, Rational.FACTORY.get(1));

		Matrix<Rational> inv = m.inverse();
		assertTrue(inv.get(1, 1).equals(Rational.FACTORY.get(1)));
		assertTrue(inv.get(1, 2).equals(Rational.FACTORY.get(1)));
		assertTrue(inv.get(2, 1).equals(Rational.FACTORY.get(1)));
		assertTrue(inv.get(2, 2).equals(Rational.FACTORY.get(2)));
	}

	/**
	 * test {@link Rational#lt(org.jlinalg.IRingElement)}
	 * {@link Rational#gt(org.jlinalg.IRingElement)}
	 * {@link Rational#compareTo(org.jlinalg.IRingElement)}
	 * {@link Rational#equals(Object)}
	 */
	@Test
	public void misc()
	{
		assertTrue(Rational.FACTORY.zero().equals(zero));

		assertTrue(a.lt(d));
		assertTrue(d.gt(a));
		assertTrue(a.compareTo(b) == 0);
		Rational n = d.negate();
		assertTrue(n.lt(Rational.FACTORY.zero()));
		assertTrue(n.negate().equals(d));
		assertTrue(n.abs().equals(d));
	}

	/**
	 * test {@link Rational#subtract(org.jlinalg.IRingElement)} for random
	 * values.
	 */
	@Test
	public void subtract()
	{
		assertTrue(a.subtract(b).equals(zero));
		assertTrue(a.subtract(c).equals(Rational.FACTORY.get(2, 3)));
		assertTrue(c.subtract(d).equals(Rational.FACTORY.get(-13, 6)));
		assertTrue(zero.subtract(a).equals(Rational.FACTORY.m_one()));
		for (int k = 0; k < 20; k++) {
			int a = (int) (Math.random() * 100 - 50);
			int b = (int) (Math.random() * 100 - 50);
			if (b == 0) b = 123;
			int c = (int) (Math.random() * 100 - 50);
			int d = (int) (Math.random() * 100 - 50);
			if (d == 0) d = 221;
			Rational r1 = Rational.FACTORY.get(a, b);
			Rational r2 = Rational.FACTORY.get(c, d);
			Rational vv = (Rational) r1.subtract(r2);
			assertTrue("denominator negative: " + vv.getDenominator(), vv
					.getDenominator().longValue() > 0);
			double v = (double) a / b - (double) c / d;
			assertTrue(r1 + "-" + r2 + "!=" + v, Math.abs(vv.getNumerator()
					.doubleValue()
					/ vv.getDenominator().doubleValue() - v) < 0.0001);
		}
	}

	/**
	 * test {@link Rational#abs()}
	 */
	@Test
	public final void testAbs()
	{
		Rational r1 = Rational.FACTORY.get(-4, 5);
		Rational r2 = Rational.FACTORY.get(4, 5);
		assertTrue(r1.abs().equals(r2));
		assertTrue(Rational.FACTORY.m_one().abs()
				.equals(Rational.FACTORY.one()));
	}

	/**
	 * test {@link Rational#add(org.jlinalg.IRingElement)}
	 */
	@Test
	public void testAdd()
	{
		Rational r = Rational.FACTORY.get(1, 6);
		r = r.add(Rational.FACTORY.get(1, 3));
		r = r.add(Rational.FACTORY.get(1, 2));
		assertTrue("should be 1", r.equals(Rational.FACTORY.one()));
		assertTrue(a.equals(zero.add(a)));
		assertTrue(zero.add(b).equals(b));
		for (int k = 0; k < 200; k++) {
			int a = (int) (Math.random() * 100 - 50);
			int b = (int) (Math.random() * 100 - 50);
			if (b == 0) b = 123;
			int c = (int) (Math.random() * 100 - 50);
			int d = (int) (Math.random() * 100 - 50);
			if (d == 0) d = 773;
			Rational r1 = Rational.FACTORY.get(a, b);
			Rational r2 = Rational.FACTORY.get(c, d);
			Rational vv = r1.add(r2);
			assertTrue("denominator negative: " + vv.getDenominator(), vv
					.getDenominator().longValue() > 0);
			double v = ((double) a / b) + ((double) c / d);
			assertTrue(r1 + "+" + r2 + "!=" + v, Math.abs(vv.getNumerator()
					.doubleValue()
					/ vv.getDenominator().doubleValue() - v) < 0.0001);
		}
	}

	/**
	 * Test Rational.add & multiply for large denominators
	 */
	@Test
	public void testBigDenominators()
	{
		Rational r = a;
		int end = 1200;
		for (long i = 1000; i < end; i++) {
			r = r.add(Rational.FACTORY.get(1, i));
			assertTrue("r=" + r.toString(), r.gt(a));
		}
		for (long i = 1000; i < end; i++) {
			assertTrue("r=" + r.toString(), r.gt(a));
			r = r.add(Rational.FACTORY.get(-1, i));
		}
		assertEquals(a, r);
		for (long i = 1000; i < end; i++) {
			r = r.multiply(Rational.FACTORY.get(end, i));
			assertTrue("r=" + r.toString(), r.gt(a));
		}
		for (long i = 1000; i < end; i++) {
			assertTrue("r=" + r.toString(), r.gt(a));
			r = (Rational) r.divide(Rational.FACTORY.get(end, i));
		}
	}

	/**
	 * test {@link Rational#compareTo(org.jlinalg.IRingElement)} for random
	 * values
	 */
	@Test
	public void testCompare()
	{

		for (int k = 0; k < 200; k++) {
			int a = (int) (Math.random() * 100 - 50);
			int b = (int) (Math.random() * 100 - 50);
			if (b == 0) b = 123;
			int c = (int) (Math.random() * 100 - 50);
			int d = (int) (Math.random() * 100 - 50);
			if (d == 0) d = 221;
			Rational r1 = Rational.FACTORY.get(a, b);
			Rational r2 = Rational.FACTORY.get(c, d);
			Double d1 = new Double((double) a / b);
			if (d1 == -0.0) d1 = 0.0;
			Double d2 = new Double((double) c / d);
			if (d2 == -0.0) d2 = 0.0;
			assertTrue(r1 + ".compareTo(" + r2 + ")==" + r1.compareTo(r2)
					+ " but " + d1 + ".compareTo(" + d2 + ")=="
					+ d1.compareTo(d2), r1.compareTo(r2) == d1.compareTo(d2));
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
	 * test {@link Rational#compareTo(org.jlinalg.IRingElement)}
	 */
	@Test
	public void testCompareToFields()
	{
		Rational one = Rational.FACTORY.get(1, 1);
		Rational one_ = Rational.FACTORY.get(-1, -1);
		Rational one__ = Rational.FACTORY.get(1);

		assertTrue("1==1", one.compareTo(one_) == 0);
		assertTrue("1==1 b", one.compareTo(one_) == 0);
		assertTrue("1==1 c", one.compareTo(one__) == 0);
		assertTrue("1==1 d", one__.compareTo(one_) == 0);

		Rational mhalf = Rational.FACTORY.get(-1, 2);
		assertTrue("-1/2<1", mhalf.compareTo(one) == -1);
	}

	/**
	 * test {@link Rational#compareTo(org.jlinalg.IRingElement)}
	 */
	@Test(expected = ClassCastException.class)
	public void testCompareToFieldsExceptions()
	{
		Rational one = Rational.FACTORY.get(1, 1);
		DoubleWrapper done = new DoubleWrapper(1);
		one.compareTo(done);
	}

	/**
	 * test {@link Rational#compareTo(org.jlinalg.IRingElement)}
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
	 * test {@link RationalFactory#get(long, long)}
	 */
	@Test
	public final void testDoubleValue()
	{
		assertTrue(Rational.FACTORY.zero().doubleValue() + "!=0.0",
				Rational.FACTORY.zero().doubleValue() == 0.0);
		assertTrue(Rational.FACTORY.one().doubleValue() + "!=1.0",
				Rational.FACTORY.one().doubleValue() == 1.0);
		assertTrue(Rational.FACTORY.m_one().doubleValue() + "!=-1.0",
				Rational.FACTORY.m_one().doubleValue() == -1.0);
		for (int i = 0; i < 100; i++) {
			int a = (int) (Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE / 2);
			int b = (int) (Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE / 2);
			Rational r = Rational.FACTORY.get(a, b);
			assertTrue(r.doubleValue() + "!=" + ((double) a / (double) b), Math
					.abs(r.doubleValue() - (double) a / (double) b) < Math
					.abs(0.000001 * a / b));
		}
	}

	/**
	 * test {@link Rational#equals(Object)}
	 */
	@Test
	public final void testEqualsObject()
	{
		Rational r1 = Rational.FACTORY.get(-1, 1);
		assertTrue(r1 + "=-1", r1.equals(Rational.FACTORY.m_one()));
		r1 = Rational.FACTORY.get(1, 1);
		assertTrue(r1 + "!=1", r1.equals(Rational.FACTORY.one()));

		for (int i = 0; i < 100; i++) {
			int a = (int) (Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE / 2);
			int b = (int) (Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE / 2);
			r1 = Rational.FACTORY.get(a, b);
			Rational r2 = Rational.FACTORY.get(a, b);
			assertTrue(r1 + "!=" + r2, r2.equals(r1));
		}
	}

	/**
	 * test {@link RationalFactory} for creating {@link Rational}s, in
	 * particular 1,0, and -1
	 */
	@Test
	public void testFactory()
	{
		assertTrue(Rational.FACTORY.get(new Double(11)).doubleValue() == 11.0);
		assertTrue(Rational.FACTORY.get(new Double(1)) == Rational.FACTORY
				.one());
		assertTrue(Rational.FACTORY.get(new Double(-1)) == Rational.FACTORY
				.m_one());
		assertTrue(Rational.FACTORY.get(new Double(11)).doubleValue() == 11.0);
		assertTrue(Rational.FACTORY.get(new Integer(1)) == Rational.FACTORY
				.one());
		assertTrue(Rational.FACTORY.get(new Integer(-1)) == Rational.FACTORY
				.m_one());
		assertTrue(Rational.FACTORY.get(new Integer(0)) == Rational.FACTORY
				.zero());
		assertTrue(Rational.FACTORY.get(new Integer(0)) == Rational.FACTORY
				.zero());
	}

	/**
	 * test whether {@link RationalFactory#getArray(int)} does not retrun a
	 * null.
	 */
	@Test
	public void testGetArrayInt()
	{
		assertTrue("Cannot access \'Rational\'-array", Rational.FACTORY
				.getArray(5) != null);
	}

	/**
	 * test {@link RationalFactory#get(long, long)}
	 */
	@Test
	public final void testGetNumeratorDeniminator()
	{
		Rational r = Rational.FACTORY.get(100, 99);
		assertTrue(r.getNumerator().intValue() == 100);
		assertTrue(r.getDenominator().intValue() == 99);
	}

	/**
	 * test whether {@link RationalFactory#get(int)} returns a zero value for a
	 * zero argument.
	 */
	@Test
	public void testGetObject()
	{
		assertTrue(Rational.FACTORY.get(0).doubleValue() == 0.0);
		assertTrue(Rational.FACTORY.get(0.0).doubleValue() == 0.0);
	}

	/**
	 * test {@link Rational.RationalFactory#get(Object)} for Strings
	 */
	@Test
	public void testGetStrings()
	{
		final Object[][] tests =
		{
				{
						"18", Rational.FACTORY.get(18, 1)
				},
				{
						"1e8", Rational.FACTORY.get(100000000, 1)
				},
				{
						"22e-4", Rational.FACTORY.get(22, 10000)
				},
				{
						"4.5e2", Rational.FACTORY.get(450)
				},
				{
						"-123.5432e7", Rational.FACTORY.get(-1235432000)
				},
				{
						"-3.5e5", Rational.FACTORY.get(-350000)
				},
				{
						"-7770000.0e-5", Rational.FACTORY.get(-7770000, 100000)
				},
				{
						"-5.6e-2", Rational.FACTORY.get(-56, 1000)
				},
				{
						"-5.65432e-2", Rational.FACTORY.get(-565432, 10000000)
				},
				{
						"1/8", Rational.FACTORY.get(1, 8)
				},
				{
						"100/-8", Rational.FACTORY.get(100, -8)
				},
				{
						"-18/-82", Rational.FACTORY.get(-18, -82)
				}
		};
		final RationalFactory fac = Rational.FACTORY;
		for (int i = 0; i < tests.length; i++) {
			String s = (String) tests[i][0];
			Rational r = (Rational) tests[i][1];
			Rational sr = fac.get(s);
			assertEquals(r, sr);
		}
	}

	/**
	 * test {@link Rational#hashCode()}
	 */
	@Test
	public void testHashCode()
	{
		Rational r1 = Rational.FACTORY.get(1, 2);
		Rational r2 = Rational.FACTORY.get(1, 2);
		Rational r3 = Rational.FACTORY.get(666, 2);
		assertEquals(r1.hashCode(), r1.hashCode());
		assertEquals(r1.hashCode(), r2.hashCode());
		assertNotSame("This may not allways be true!", r1.hashCode(), r3
				.hashCode());

		Rational a = Rational.FACTORY.get(10, 11);
		Rational b = Rational.FACTORY.get(10, 11);
		Rational c = Rational.FACTORY.get(1, 1);
		Rational d = Rational.FACTORY.get(1, 1);
		assertTrue("hashcode not equal: " + a + " & " + b, a.hashCode() == b
				.hashCode());
		assertTrue("hashcode not equal: " + c + " & " + d, c.hashCode() == d
				.hashCode());
		Vector<Rational> v1 = new Vector<Rational>(2, Rational.FACTORY);
		v1.set(1, a);
		v1.set(2, c);
		Vector<Rational> v2 = new Vector<Rational>(2, Rational.FACTORY);
		v2.set(1, b);
		v2.set(2, d);
		assertTrue("hashcode not equal: " + v1 + " & " + v2,
				v1.hashCode() == v2.hashCode());
	}

	/**
	 * test {@link Rational#invert()}
	 */
	@Test
	public final void testInvert()
	{
		Rational r1 = Rational.FACTORY.get(-1, 1);
		assertTrue("1/-1)=-1", r1.invert().equals(Rational.FACTORY.m_one()));
		r1 = Rational.FACTORY.get(1, 1);
		assertTrue("1/1=1", r1.invert().equals(Rational.FACTORY.one()));

		for (int i = 0; i < 100; i++) {
			int a = (int) (Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE / 2);
			int b = (int) (Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE / 2);
			r1 = Rational.FACTORY.get(a, b);
			Rational r2 = r1.invert();
			assertTrue("1/(1/" + r1 + "))!=" + r2.invert(), r2.invert().equals(
					r1));
		}
	}

	/**
	 * test {@link Vector#isZero()}
	 */
	@Test
	public void testIsZero()
	{
		v1.set(2, Rational.FACTORY.one());
		v2.set(3, Rational.FACTORY.one());
		v3.set(1, Rational.FACTORY.one());
		assertTrue("is not Zero: " + v1, !v1.isZero());
		assertTrue("is not Zero: " + v2, !v2.isZero());
		assertTrue("is not Zero: " + v3, !v3.isZero());
		assertTrue("is not Zero: " + v4, !v4.isZero());
		assertTrue("is   Zero: " + w, w.isZero());

	}

	/**
	 * test le(Rational) and ge(Rational)
	 */
	@Test
	public void testLeGe()
	{
		for (Rational r1 : new RandomNumberList<Rational>(Rational.FACTORY, 10))
		{
			for (Rational r2 : new RandomNumberList<Rational>(Rational.FACTORY,
					10))
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

		assertTrue(Rational.FACTORY.get(1.4E-12).le(
				Rational.FACTORY.get(1, 100000)));
	}

	/**
	 * test various ways of getting the minus one value.
	 */
	@Test
	public final void testM_one()
	{
		BigInteger m_one = new BigInteger("-1");
		assertTrue(Rational.FACTORY.m_one().getNumerator().equals(m_one));
		assertTrue(Rational.FACTORY.one().getDenominator().equals(
				BigInteger.ONE));
		assertTrue((Rational.FACTORY.m_one()).getNumerator().equals(m_one));
		assertTrue((Rational.FACTORY.m_one()).getDenominator().equals(
				BigInteger.ONE));
		assertTrue(Rational.FACTORY.m_one().doubleValue() == -1.0);
	}

	/**
	 * test {@link Rational#multiply(org.jlinalg.IRingElement)}
	 */
	@Test
	public final void testMultiply()
	{
		for (int i = 0; i < 100; i++) {
			int a = (int) (Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE / 2);
			int b = (int) (Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE / 2);
			int c = (int) (Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE / 2);
			int d = (int) (Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE / 2);
			Rational r1 = Rational.FACTORY.get(a, b);
			r1 = r1.multiply(Rational.FACTORY.get(c, d));
			Rational r2 = Rational.FACTORY.get(a, d);
			r2 = r2.multiply(Rational.FACTORY.get(c, b));
			assertTrue(a + "/" + b + "*" + c + "/" + d + "!=" + a + "/" + d
					+ "*" + c + "/" + b + "    " + r1 + "!=" + r2, r1
					.equals(r2));
		}

		assertTrue(a.equals(b));
		assertTrue(a.equals(b.multiply(b)));
		assertTrue(a.equals(b.multiply(a)));
		assertTrue(b.equals(a));
		assertTrue(e.equals(d.multiply(c)));
		assertTrue(e.equals(c.multiply(d)));
		assertTrue(zero.equals(Rational.FACTORY.zero().multiply(e)));
		assertTrue(Rational.FACTORY.zero().equals(
				Rational.FACTORY.zero().multiply(e)));
		assertTrue(Rational.FACTORY.zero().equals(zero.multiply(e)));
		assertTrue(zero.equals(b.multiply(Rational.FACTORY.zero())));
		assertTrue(Rational.FACTORY.zero().equals(
				b.multiply(Rational.FACTORY.zero())));
		assertTrue(Rational.FACTORY.zero().equals(a.multiply(zero)));

		assertTrue(Rational.FACTORY.get(1, 3).multiply(
				Rational.FACTORY.get(-6, 5)).equals(
				Rational.FACTORY.get(-6, 15)));
		for (int k = 0; k < 100; k++) {
			int a = (int) (Math.random() * 100 - 50);
			int b = (int) (Math.random() * 100 - 50);
			if (b == 0) b = 123;
			int c = (int) (Math.random() * 100 - 50);
			int d = (int) (Math.random() * 100 - 50);
			if (d == 0) d = 221;
			Rational r1 = Rational.FACTORY.get(a, b);
			Rational r2 = Rational.FACTORY.get(c, d);
			Rational vv = r1.multiply(r2);
			assertTrue("denominator negative: " + vv.getDenominator(), vv
					.getDenominator().longValue() > 0);
			double v = ((double) a / b) * ((double) c / d);
			assertTrue(r1 + "*" + r2 + "!=" + v, Math.abs(vv.getNumerator()
					.doubleValue()
					/ vv.getDenominator().doubleValue() - v) < 0.0001);
		}
	}

	/**
	 * test {@link Rational#negate()}
	 */
	@Test
	public final void testNegate()
	{
		Rational r1 = Rational.FACTORY.get(-1, 1);
		assertTrue("-(-1)=1", r1.negate().equals(Rational.FACTORY.one()));
		r1 = Rational.FACTORY.get(1, 1);
		assertTrue("-(1)=-1", r1.negate().equals(Rational.FACTORY.m_one()));
		r1 = Rational.FACTORY.get(0, 1);
		assertTrue("-(0)=0", r1.negate().equals(Rational.FACTORY.zero()));
		for (int i = 0; i < 100; i++) {
			int a = (int) (Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE / 2);
			int b = (int) (Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE / 2);
			r1 = Rational.FACTORY.get(a, b);
			Rational r2 = r1.negate();
			assertTrue("-(-" + r1 + ")!=" + r2.negate(), r2.negate().equals(r1));
		}
	}

	/**
	 * test various ways of getting the one value.
	 */
	@Test
	public final void testOne()
	{
		assertTrue(Rational.FACTORY.one().getNumerator().equals(BigInteger.ONE));
		assertTrue(Rational.FACTORY.one().getDenominator().equals(
				BigInteger.ONE));
		assertTrue(Rational.FACTORY.one().getNumerator().equals(BigInteger.ONE));
		assertTrue(Rational.FACTORY.one().getDenominator().equals(
				BigInteger.ONE));
		assertTrue(Rational.FACTORY.one().doubleValue() == 1.0);
	}

	/**
	 * test {@link RationalFactory#get}
	 */
	@Test
	public final void testRational()
	{
		Rational r = Rational.FACTORY.get(10, 1);
		assertTrue(r.equals(Rational.FACTORY.get(BigInteger.TEN)));
		r = Rational.FACTORY.get(100, 10);
		assertTrue(r.equals(Rational.FACTORY.get(BigInteger.TEN)));
		r = Rational.FACTORY.get(3024, 33264);
		assertTrue(r.equals(Rational.FACTORY.get(1, 11)));
	}

	/**
	 * test {@link RationalFactory#get}
	 */
	@Test
	public final void testRationalBigInteger()
	{
		assertTrue(Rational.FACTORY.zero().equals(
				Rational.FACTORY.get(BigInteger.ZERO)));
		Rational r = Rational.FACTORY.get(10, 1);
		assertTrue(r.equals(Rational.FACTORY.get(BigInteger.TEN)));
	}

	/**
	 * test {@link Rational#equals(Object)}
	 */
	@Test
	public final void testRationalBigIntegerBigIntegerBoolean()
	{
		assertTrue(Rational.FACTORY.zero().equals(
				Rational.FACTORY
						.get(BigInteger.ZERO, new BigInteger("1"), true)));
		Rational r = Rational.FACTORY.get(new BigInteger("10"), BigInteger.ONE,
				true);
		assertTrue(r.equals(Rational.FACTORY.get(BigInteger.TEN)));
		r = Rational.FACTORY.get(new BigInteger("100"), BigInteger.TEN, true);
		assertTrue(r.equals(Rational.FACTORY.get(BigInteger.TEN)));
		r = Rational.FACTORY.get(new BigInteger("3024"),
				new BigInteger("33264"), true);
		assertTrue(r.equals(Rational.FACTORY.get(1, 11)));
	}

	/**
	 * test {@link Rational#equals(Object)}
	 */
	@Test
	public final void testRationalDouble()
	{
		Rational r = Rational.FACTORY.get(0.0);
		assertTrue(r.equals(Rational.FACTORY.zero()));
		r = Rational.FACTORY.get(1.0);
		assertTrue(r.equals(Rational.FACTORY.one()));
		r = Rational.FACTORY.get(-1.0);
		assertTrue(r.equals(Rational.FACTORY.m_one()));
		for (int i = 0; i < 100; i++) {
			double d = (0.5 - Math.random()) * 11111111111.0;
			r = Rational.FACTORY.get(d);
			assertTrue(r + "=" + r.doubleValue() + "!=" + d, Math.abs(r
					.getNumerator().doubleValue()
					/ r.getDenominator().doubleValue() - d) < 0.000001 * Math
					.abs(d));
		}
	}

	/**
	 * test {@link Rational#toString()}
	 */
	@Test
	public final void testToString()
	{
		Rational r = Rational.FACTORY.get(100, 99);
		assertTrue(r.toString().equals("100/99"));
		r = Rational.FACTORY.get(100, 100);
		assertTrue(r.toString().equals("1"));
		r = Rational.FACTORY.get(-99, 99);
		assertTrue(r.toString().equals("-1"));
	}

	/**
	 * test various ways of getting the zero value.
	 */
	@Test
	public final void testZero()
	{
		assertTrue(Rational.FACTORY.zero().getNumerator().equals(
				BigInteger.ZERO));
		assertTrue(Rational.FACTORY.zero().getNumerator().equals(
				BigInteger.ZERO));
		assertTrue(Rational.FACTORY.zero().doubleValue() == 0.0);
	}

	/**
	 * verify that the denominator is not becoming zero.
	 */
	@Test
	public void testZeroDenominator()
	{
		assertTrue("ZERO",
				Rational.FACTORY.zero().getDenominator().intValue() == 1);
		Rational r = Rational.FACTORY.get(1, 77);
		Rational p = Rational.FACTORY.get(1, 77);
		assertTrue("1/77*0", (r.multiply(Rational.FACTORY.zero()))
				.getDenominator().intValue() == 1);
		assertTrue("1/77-1/77", ((Rational) r.subtract(p)).getDenominator()
				.intValue() == 1);
	}

}
