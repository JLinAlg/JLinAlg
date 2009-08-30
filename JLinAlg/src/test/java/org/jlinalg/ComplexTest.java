/**
 * 
 */
package org.jlinalg;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jlinalg.Complex.ComplexFactory;
import org.jlinalg.Rational.RationalFactory;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Georg Thimm
 */
public class ComplexTest
{
	static ComplexFactory f = Complex.FACTORY;

	static RationalFactory rf = Rational.FACTORY;

	/**
	 * Test method for {@link org.jlinalg.Complex#hashCode()}.
	 */
	@Test
	@Ignore
	public final void testHashCode()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.jlinalg.Complex#equals(java.lang.Object)}.
	 */
	@Test
	@Ignore
	public final void testEqualsObject()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.jlinalg.Complex#getReal()}.
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
	 * Test method for {@link org.jlinalg.Complex#getImaginary()}.
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
	 * Test method for {@link org.jlinalg.Complex#add(org.jlinalg.IRingElement)}
	 * .
	 */
	@Test
	public final void testAdd()
	{
		for (Complex c1 : new RandomNumberList<Complex>(f, 10)) {
			for (Complex c2 : new RandomNumberList<Complex>(f, 10)) {
				Complex r = c1.add(c2);
				assertTrue(r.getReal().equals(c1.getReal().add(c2.getReal())));
				assertTrue(r.getImaginary().equals(
						c1.getImaginary().add(c2.getImaginary())));
			}
		}
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.Complex#multiply(org.jlinalg.IRingElement)}.
	 */
	@Test
	public final void testMultiply()
	{
		for (Complex c1 : new RandomNumberList<Complex>(f, 10)) {
			for (Complex c2 : new RandomNumberList<Complex>(f, 10)) {
				Complex r = c1.multiply(c2);
				assertTrue(r.norm().equals(c1.norm().multiply(c2.norm())));
			}
		}
	}

	/**
	 * Test method for {@link org.jlinalg.Complex#negate()}.
	 */
	@Test
	public final void testNegate()
	{
		for (Complex c1 : new RandomNumberList<Complex>(f, 10)) {
			Complex r = c1.negate();
			assertTrue(r.getReal().equals(c1.getReal().negate()));
			assertTrue(r.getImaginary().equals(c1.getImaginary().negate()));
		}
	}

	/**
	 * Test method for {@link org.jlinalg.Complex#invert()}.
	 */
	@Test
	public final void testInvert()
	{
		for (Complex c1 : new RandomNumberList<Complex>(f, 10)) {
			assertTrue(c1.multiply(c1.invert()).equals(Complex.FACTORY.one()));
		}
	}

	/**
	 * Test method for {@link org.jlinalg.Complex#conjugate()}.
	 */
	@Test
	public final void testConjugate()
	{
		for (Complex c1 : new RandomNumberList<Complex>(f, 10)) {
			Complex r = c1.conjugate();
			assertTrue(r.getReal().equals(c1.getReal()));
			assertTrue(r.getImaginary().equals(c1.getImaginary().negate()));
		}
	}

	/**
	 * Test method for {@link org.jlinalg.Complex#toString()}.
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
	 * Test method for
	 * {@link org.jlinalg.Complex#compareTo(org.jlinalg.IRingElement)}.
	 */
	@Test
	public final void testCompareTo()
	{
		for (Complex c1 : new RandomNumberList<Complex>(f, 10)) {
			if (c1.equals(Complex.FACTORY.zero()))
				assertTrue(c1.compareTo(Complex.FACTORY.zero()) == 0);
			else
				assertTrue(c1.compareTo(Complex.FACTORY.zero()) > 0);
			for (Complex c2 : new RandomNumberList<Complex>(f, 10)) {
				if (c1.getReal().equals(c2.getReal())
						&& c1.getImaginary().equals(c2.getImaginary()))
					assertTrue(c1.compareTo(c2) == 0);
				else {
					if (c1.compareTo(c2) < 0) assertTrue(c2.compareTo(c1) > 0);
				}
			}
		}
	}

	/**
	 * Test method for {@link org.jlinalg.Complex#magnitude()}.
	 */
	@Test
	public final void testMagnitude()
	{
		assertTrue(f.zero().norm().equals(Rational.FACTORY.zero()));
		assertTrue(f.one().norm().equals(Rational.FACTORY.one()));
		assertTrue(f.m_one().norm().equals(Rational.FACTORY.one()));
		assertTrue(f.get(0, 1).norm().equals(Rational.FACTORY.one()));
		assertTrue(f.get(0, -1).norm().equals(Rational.FACTORY.one()));
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.FieldElement#subtract(org.jlinalg.IRingElement)}.
	 */
	@Test
	public final void testSubtractIRingElement()
	{
		for (Complex c1 : new RandomNumberList<Complex>(f, 10)) {
			for (Complex c2 : new RandomNumberList<Complex>(f, 10)) {
				Complex r = (Complex) c1.subtract(c2);
				assertTrue(c2.negate().add(c1).equals(r));
			}
		}
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.FieldElement#divide(org.jlinalg.IRingElement)}.
	 */
	@Test
	public final void testDivideIRingElement()
	{
		for (Complex c1 : new RandomNumberList<Complex>(f, 10)) {
			for (Complex c2 : new RandomNumberList<Complex>(f, 10)) {
				Complex r = (Complex) c1.divide(c2);
				assertTrue(r.multiply(c2).equals(c1));
			}
		}
	}

	/**
	 * Test method for {@link org.jlinalg.FieldElement#abs()}.
	 */
	@Test(expected = InvalidOperationException.class)
	public final void testAbs()
	{
		f.zero().abs();
	}

	/**
	 * Test method for {@link org.jlinalg.RingElement#isZero()}.
	 */
	@Test
	public final void testIsZero()
	{
		assertTrue(f.get(0).isZero());
		assertTrue(f.zero().isZero());
		for (Complex c : new RandomNumberList<Complex>(f, 20)) {
			if (!c.equals(f.zero())) assertFalse(c.isZero());
		}
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.RingElement#subtract(org.jlinalg.IRingElement)}.
	 */
	@Test
	public final void testSubtractIRingElement1()
	{
		assertTrue(f.zero().equals(f.one().subtract(f.one())));
	}

	/**
	 * Test method for {@link org.jlinalg.RingElement#isOne()}.
	 */
	@Test
	public final void testIsOne()
	{
		assertFalse(f.get(0).isOne());
		assertFalse(f.get(0, 1).isOne());
		assertTrue(f.one().isOne());
		assertTrue(f.get(1, 0).isOne());
		for (Complex c : new RandomNumberList<Complex>(f, 20)) {
			if (!c.equals(f.one())) assertFalse(c.equals(f.one()));
		}
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.RingElement#apply(org.jlinalg.MonadicOperator)}.
	 */
	@Test
	@Ignore
	public final void testApply()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.jlinalg.RingElement#abs()}.
	 */
	@Test
	@Ignore
	public final void testAbs1()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.RingElement#lt(org.jlinalg.IRingElement)}.
	 */
	@Test
	public final void testLt()
	{
		assertFalse(f.one().lt(f.one()));
		assertFalse(f.one().lt(f.zero()));
		assertFalse(f.m_one().lt(f.zero()));
		assertTrue(f.zero().lt(f.one()));
		assertTrue(f.zero().lt(f.m_one()));
		for (Complex c : new RandomNumberList<Complex>(f, 20)) {
			if (!c.equals(f.zero())) assertTrue(f.zero().lt(c));
		}
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.RingElement#gt(org.jlinalg.IRingElement)}.
	 */
	@Test
	public final void testGt()
	{
		assertFalse(f.one().gt(f.one()));
		assertTrue(f.one().gt(f.zero()));
		assertTrue(f.m_one().gt(f.zero()));
		assertFalse(f.zero().gt(f.one()));
		assertFalse(f.zero().gt(f.m_one()));
		for (Complex c : new RandomNumberList<Complex>(f, 20)) {
			if (!c.equals(f.zero())) assertTrue(c.gt(f.zero()));
		}
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.RingElement#le(org.jlinalg.IRingElement)}.
	 */
	@Test
	public final void testLe()
	{
		assertTrue(f.one().le(f.one()));
		assertFalse(f.one().le(f.zero()));
		assertFalse(f.m_one().le(f.zero()));
		assertTrue(f.zero().le(f.one()));
		assertTrue(f.zero().le(f.m_one()));
		for (Complex c : new RandomNumberList<Complex>(f, 20)) {
			assertTrue(f.zero().le(c));
		}
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.RingElement#ge(org.jlinalg.IRingElement)}.
	 */
	@Test
	public final void testGe()
	{
		assertTrue(f.one().ge(f.one()));
		assertTrue(f.one().ge(f.zero()));
		assertTrue(f.m_one().ge(f.zero()));
		assertFalse(f.zero().ge(f.one()));
		assertFalse(f.zero().ge(f.m_one()));
		for (Complex c : new RandomNumberList<Complex>(f, 20)) {
			assertTrue(c.ge(f.zero()));
		}
	}

	/**
	 * Test method for {@link java.lang.Object#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject1()
	{
		assertTrue(f.zero().equals(f.zero()));
		assertTrue(f.zero().equals(f.get(0, 0)));
		assertTrue(f.one().equals(f.get(1, 0)));
		assertFalse(f.zero().equals(f.get(1, 0)));
		assertFalse(f.zero().equals(f.get(0, 1)));
	}

}
