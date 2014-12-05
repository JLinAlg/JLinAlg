/**
 * Test of F2.
 */
package org.jlinalg.f2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.f2.F2.F2Factory;
import org.jlinalg.rational.Rational;
import org.jlinalg.testutil.RingElementTestBase;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Georg Thimm
 */
public class F2Test
		extends RingElementTestBase<F2>
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
	 * Check presence of singleton factory
	 */
	@Test
	public void testFactory() throws Exception
	{
		assertNotNull("Cannot access factory \'F2\'", F2.FACTORY);

	}

	/**
	 * test whether the factory creates arrays,
	 */
	@Test
	public void testCreateArrays()
	{
		assertNotNull("Cannot create F2-array", F2.FACTORY.getArray(5));
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

	@Test
	public void testAbs()
	{
		assertSame(F2.ONE, F2.ONE.abs());
		assertSame(F2.ZERO, F2.ZERO.abs());
	}

	@Override
	public IRingElementFactory<F2> getFactory()
	{
		return F2.FACTORY;
	}

	@Override
	@Ignore
	@Test
	public void testDivide_base()
	{
	}

	@Test
	public void testNorm()
	{
		assertSame(F2.ONE, F2.ONE.norm());
		assertSame(F2.ZERO, F2.ZERO.norm());
	}

	@Override
	@Test
	public void testInvert_base()
	{
		assertEquals(getFactory().one(), getFactory().one().invert());
	}

	@Override
	@Test(expected = org.jlinalg.DivisionByZeroException.class)
	public void invertZero_base()
	{
		getFactory().zero().invert();
	}

	/**
	 * test the annotation of the type.
	 */
	@Test
	public void testAnnotation()
	{
		assertFalse(dataTypeHasNegativeValues());
		assertTrue(dataTypeIsDiscreet());
		assertTrue(dataTypeIsExact());
	}

	/**
	 * test {@link Rational#add(org.jlinalg.IRingElement)}
	 */
	@Override
	@Test
	public void testAdd_base()
	{
		assertEquals(getFactory().one(),
				getFactory().zero().add(getFactory().one()));
		assertEquals(getFactory().one(),
				getFactory().one().add(getFactory().zero()));
		assertEquals(getFactory().zero(),
				getFactory().one().add(getFactory().m_one()));
		assertEquals(getFactory().get("11"),
				getFactory().one().add(getFactory().get("10")));
	}
}
