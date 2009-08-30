/**
 * Test of F2.
 */
package org.jlinalg;

import static org.junit.Assert.assertTrue;

import org.jlinalg.Complex;
import org.jlinalg.F2;
import org.jlinalg.F2.F2Factory;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Georg Thimm
 */
public class F2Test
{
	/**
	 * the zero element
	 */
	F2 z;

	/**
	 * the one element
	 */
	F2 o;

	/**
	 * a reference to the factory for F2 elements.
	 */
	F2Factory fac;

	/**
	 * initialise fac,z,o
	 */
	@Before
	public void setup()
	{
		fac = F2.FACTORY;
		z = fac.zero();
		o = fac.one();
	}

	/**
	 * test the addition in F2
	 */
	@Test
	public void testAdd()
	{
		assertTrue(z.add(z).equals(z));
		assertTrue(o.add(z).equals(o));
		assertTrue(z.add(o).equals(o));
		assertTrue(o.add(o).equals(z));
	}

	/**
	 * test the subtraction in F2
	 */
	@Test
	public void testSub()
	{
		assertTrue(z.subtract(z).equals(z));
		assertTrue(o.subtract(z).equals(o));
		assertTrue(z.subtract(o).equals(o));
		assertTrue(o.subtract(o).equals(z));
	}

	/**
	 * test the multiplication in F2
	 */
	@Test
	public void testMult()
	{
		assertTrue(z.multiply(z).equals(z));
		assertTrue(o.multiply(z).equals(z));
		assertTrue(z.multiply(o).equals(z));
		assertTrue(o.multiply(o).equals(o));
	}

	/**
	 * test whether the inversion of zero results in an exception
	 */
	@Test(expected = org.jlinalg.DivisionByZeroException.class)
	public void invertZero()
	{
		z.invert();
	}

	/**
	 * test whether the addition with non-F2 objects results into an exception
	 */
	@Test(expected = org.jlinalg.InvalidOperationException.class)
	public void argumentTypeAdd()
	{
		z.add(Complex.FACTORY.get(1));
	}

	/**
	 * test {@link #equals(Object)}, {@link #toString()}, {@link F2#negate()}
	 */
	@Test
	public void test()
	{
		assertTrue(z == fac.get(0L));
		assertTrue(z == F2.ZERO);
		assertTrue(o == F2.ONE);
		assertTrue(o.equals(fac.m_one()));

		assertTrue(o.toString().equals("1m2"));
		assertTrue(z.toString().equals("0m2"));

		assertTrue(o.negate().equals(o));
		assertTrue(z.negate().equals(z));
	}

}
