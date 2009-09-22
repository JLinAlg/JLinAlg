/**
 * 
 */
package org.jlinalg.complex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.jlinalg.InvalidOperationException;
import org.jlinalg.complex.Complex.ComplexFactory;
import org.jlinalg.rational.Rational;
import org.jlinalg.rational.Rational.RationalFactory;
import org.jlinalg.testutil.RingElementTestBase;
import org.junit.Test;

/**
 * @author Georg Thimm
 */
public class ComplexTest
		extends RingElementTestBase<Complex>
{
	static ComplexFactory f = Complex.FACTORY;

	static RationalFactory rf = Rational.FACTORY;

	/**
	 * Test method for {@link org.jlinalg.complex.Complex#getReal()}.
	 */
	@Test
	public final void testGetReal()
	{
		assertTrue(f.zero().getReal().equals(rf.zero()));
		assertTrue(f.one().getReal().equals(rf.one()));
		assertTrue(f.m_one().getReal().equals(rf.m_one()));
		assertTrue(f.get(5, 11).getReal().equals(rf.get(5)));
	}

	/**
	 * Test method for {@link org.jlinalg.complex.Complex#getImaginary()}.
	 */
	@Test
	public final void testGetImaginary()
	{
		assertTrue(f.zero().getImaginary().equals(rf.zero()));
		assertTrue(f.one().getImaginary().equals(rf.zero()));
		assertTrue(f.m_one().getImaginary().equals(rf.zero()));
		assertTrue(f.get(5, 11).getImaginary().equals(rf.get(11)));
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.complex.Complex#add(org.jlinalg.IRingElement)} .
	 */
	@Test
	public final void testAdd()
	{
		for (Complex c1 : Arrays.asList(new Complex[] {
				f.m_one(), f.get(2, 6), f.get(1.4, 6.8)
		}))
		{
			for (Complex c2 : Arrays.asList(new Complex[] {
					f.one(), f.get(21, 6), f.get(1.4777777, 6.899999)
			}))
			{
				Complex r = c1.add(c2);
				assertTrue(r.getReal().equals(c1.getReal().add(c2.getReal())));
				assertTrue(r.getImaginary().equals(
						c1.getImaginary().add(c2.getImaginary())));
			}
		}
	}

	/**
	 * Test method for {@link org.jlinalg.complex.Complex#negate()}.
	 */
	@Test
	public final void testNegate()
	{
		for (Complex c1 : Arrays.asList(new Complex[] {
				f.one(), f.zero(), f.m_one(), f.get(2, 6), f.get(1.4, 6.8)
		}))
		{
			Complex r = c1.negate();
			assertTrue(r.getReal().equals(c1.getReal().negate()));
			assertTrue(r.getImaginary().equals(c1.getImaginary().negate()));
		}
	}

	/**
	 * Test method for {@link org.jlinalg.complex.Complex#invert()}.
	 */
	@Test
	public final void testInvert()
	{
		for (Complex c1 : Arrays.asList(new Complex[] {
				f.one(), f.m_one(), f.get(2, 6), f.get(-66662, 0.111116),
				f.get(1.4, 6.8)
		}))
		{
			assertTrue(c1.multiply(c1.invert()).equals(Complex.FACTORY.one()));
		}
	}

	/**
	 * Test method for {@link org.jlinalg.complex.Complex#conjugate()}.
	 */
	@Test
	public final void testConjugate()
	{
		for (Complex c1 : Arrays.asList(new Complex[] {
				f.one(), f.zero(), f.m_one(), f.get(2, 6), f.get(1.4, 6.8)
		}))
		{
			Complex r = c1.conjugate();
			assertTrue(r.getReal().equals(c1.getReal()));
			assertTrue(r.getImaginary().equals(c1.getImaginary().negate()));
		}
	}

	/**
	 * Test method for {@link org.jlinalg.complex.Complex#toString()}.
	 */
	@Test
	public final void testToString()
	{
		assertTrue(f.zero().toString().length() > 0);
		assertTrue(f.get(123456).toString().startsWith("123456"));
		assertTrue(f.get(0, 54321).toString().contains("54321"));
		assertTrue(f.get(0, 54321).toString().contains("i"));
	}

	/**
	 * Test method for {@link org.jlinalg.complex.Complex#norm()}.
	 */
	@Override
	@Test
	public final void testNorm_base()
	{
		assertEquals(f.zero(), f.zero().norm());
		assertEquals(f.one(), f.one().norm());
		assertEquals(f.one(), f.m_one().norm());
		assertEquals(f.one(), f.get(0, 1).norm());
		assertEquals(f.one(), f.get(0, -1).norm());
		assertEquals(f.get("244/225", "0"), f.get("-4/5", "2/3").norm());
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.FieldElement#subtract(org.jlinalg.IRingElement)}.
	 */
	@Test
	public final void testSubtract()
	{
		assertEquals(f.get("-2"), f.m_one().subtract(f.one()));
		assertEquals(f.get("3", "-4"), f.get(2, 7).subtract(f.get(-1, 11)));
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.FieldElement#divide(org.jlinalg.IRingElement)}.
	 */
	@Test
	public final void testDivide()
	{
		assertEquals(f.get("11/25", "2/25"), f.get(1, 2).divide(f.get(3, 4)));
		assertEquals(f.get("-17/13", "-6/13"), f.get(-4, 3)
				.divide(f.get(2, -3)));
	}

	/**
	 * Test method for {@link org.jlinalg.FieldElement#abs()}.
	 */
	@SuppressWarnings("deprecation")
	@Test(expected = InvalidOperationException.class)
	public final void testAbs()
	{
		f.zero().abs();
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.RingElement#subtract(org.jlinalg.IRingElement)}.
	 */
	@Test
	public final void testSubtractIRingElement1()
	{
		assertEquals(f.get("1/2", "-1/3"), f.get("2", "-2/3").subtract(
				f.get("3/2", "-1/3")));
	}

	/**
	 * Test method for {@link org.jlinalg.RingElement#isOne()}.
	 */
	@Test
	public final void testIsOne()
	{
		assertFalse(f.get(0, 1).isOne());
		assertTrue(f.get(1, 0).isOne());
	}

	/**
	 * Test method for {@link java.lang.Object#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject1()
	{
		assertTrue(f.get(1, 2).equals(f.get(1, 2)));
		assertFalse(f.get(1, 2).equals(f.get(1, 4)));
		assertFalse(f.zero().equals(f.get(0, 1)));
	}

	@Override
	public ComplexFactory getFactory()
	{
		return Complex.FACTORY;
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.RingElement#gt(org.jlinalg.IRingElement)}.
	 */
	@Override
	@Test
	public void testGt_base()
	{
		assertTrue(getFactory().one().gt(getFactory().zero()));
		assertFalse(getFactory().one().gt(getFactory().one()));
		assertTrue(getFactory().one().gt(getFactory().zero()));
		assertTrue(getFactory().m_one().gt(getFactory().zero()));
		assertFalse(getFactory().zero().gt(getFactory().zero()));
		assertFalse(getFactory().zero().gt(getFactory().one()));
		assertFalse(getFactory().zero().gt(getFactory().m_one()));
	}

	@Override
	@Test
	public void testLt_base()
	{
		assertFalse(getFactory().one().lt(getFactory().one()));
		assertFalse(getFactory().one().lt(getFactory().zero()));
		assertFalse(getFactory().one().lt(getFactory().m_one()));
		assertFalse(getFactory().m_one().lt(getFactory().zero()));
		assertTrue(getFactory().zero().lt(getFactory().one()));
		assertTrue(getFactory().zero().lt(getFactory().m_one()));
		assertFalse(getFactory().zero().lt(getFactory().zero()));
	}

	@Override
	@Test
	public void testLe_base()
	{
		assertTrue(getFactory().one().le(getFactory().get(0., 3.)));
		assertFalse(getFactory().one().le(getFactory().zero()));
		assertTrue(getFactory().one().le(getFactory().m_one()));
		assertFalse(getFactory().m_one().le(getFactory().zero()));
		assertTrue(getFactory().zero().le(getFactory().one()));
		assertTrue(getFactory().zero().le(getFactory().m_one()));
		assertTrue(getFactory().zero().le(getFactory().zero()));
	}

	@Override
	@Test
	public void testGe_base()
	{
		assertTrue(getFactory().one().ge(getFactory().get(0.2, -0.2)));
		assertTrue(getFactory().get(-2, -1).ge(getFactory().get(0.2, -0.2)));
		assertTrue(getFactory().one().ge(getFactory().zero()));
		assertTrue(getFactory().one().ge(getFactory().m_one()));
		assertTrue(getFactory().m_one().ge(getFactory().zero()));
		assertFalse(getFactory().zero().ge(getFactory().one()));
		assertFalse(getFactory().zero().ge(getFactory().m_one()));
		assertTrue(getFactory().zero().ge(getFactory().zero()));
	}
}
