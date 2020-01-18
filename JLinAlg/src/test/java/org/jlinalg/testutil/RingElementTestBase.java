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
package org.jlinalg.testutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import org.jlinalg.DivisionByZeroException;
import org.jlinalg.IRingElement;
import org.jlinalg.InvalidOperationException;
import org.junit.Assume;
import org.junit.Test;

/**
 * @author georg
 */
public abstract class RingElementTestBase<RE extends IRingElement<RE>>
		extends
		TestBaseFixture<RE>
{
	/**
	 * Test method for {@link org.jlinalg.RingElement#isZero()}.
	 */
	@Test
	public void testIsZero_base()
	{
		assertTrue(getFactory().zero().isZero());
		assertTrue(getFactory().get("0").isZero());
		assertFalse(getFactory().one().isZero());
		assertEquals(getFactory().get("0"), getFactory().zero());
		Assume.assumeTrue(dataTypeHasNegativeValues());
		assertSimilar(getFactory().zero(), getFactory().get("-0.0"),
				"0.0000001");
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.RingElement#add(org.jlinalg.IRingElement)}.
	 */
	@Test
	public void testAdd_base()
	{
		assertEquals(getFactory().get("2"),
				getFactory().one().add(getFactory().one()));
		assertEquals(getFactory().one(),
				getFactory().zero().add(getFactory().one()));
		assertEquals(getFactory().get("75"),
				getFactory().get("43").add(getFactory().get("32")));
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.RingElement#subtract(org.jlinalg.IRingElement)}.
	 */
	@Test
	public void testSubtract_base()
	{
		assertEquals(getFactory().zero(),
				getFactory().one().subtract(getFactory().one()));
		assertEquals(getFactory().m_one(),
				getFactory().zero().subtract(getFactory().one()));
		assertEquals(getFactory().get("11"),
				getFactory().get("43").subtract(getFactory().get("32")));
	}

	/**
	 * Test method for {@link org.jlinalg.RingElement#isOne()}.
	 */
	@Test
	public void testIsOne_base()
	{
		assertTrue(getFactory().one().isOne());
		assertTrue(getFactory().get("1").isOne());
		assertFalse(getFactory().zero() + " is one()?",
				getFactory().zero().isOne());
		assertEquals(getFactory().get("1"), getFactory().one());
	}

	/**
	 * Test method for {@link org.jlinalg.RingElement#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject_base()
	{
		assertEquals(getFactory().m_one(), getFactory().get("-1"));
		assertEquals(getFactory().one(), getFactory().get("1"));
		assertEquals(getFactory().get("13"), getFactory().get("13"));
	}

	/**
	 * Test method for {@link org.jlinalg.RingElement#abs()}.
	 */
	@Test
	public void testAbs_base()
	{
		Assume.assumeTrue(dataTypeHasNegativeValues()
				&& !methodIsDepreciated(getFactory().one(), "abs", null));
		assertEquals(getFactory().toString(), getFactory().zero(),
				getFactory().zero().abs());
		assertEquals(getFactory().one(), getFactory().one().abs());
		assertEquals(getFactory().one(), getFactory().m_one().abs());
		assertEquals(getFactory().get("7"), getFactory().get("-7").abs());
	}

	/**
	 * Test method for {@link org.jlinalg.RingElement#norm()} ()except if the
	 * method is deprecated).
	 */
	@Test
	public void testNorm_base()
	{
		assumeTrue(!methodIsDepreciated(getFactory().one(), "norm", null));
		assertEquals(getFactory().zero(), getFactory().zero().norm());
		assertEquals(getFactory().one(), getFactory().one().norm());
		assertEquals(getFactory().one(), getFactory().m_one().norm());
		assertEquals(getFactory().get("7"), getFactory().get("-7").norm());
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.RingElement#lt(org.jlinalg.IRingElement)}. "-1" is
	 * exclude for instances of {@link org.jlinalg.demo.FieldP}
	 */
	@Test
	public void testLt_base()
	{
		assertFalse(getFactory().one().lt(getFactory().one()));
		assertFalse(getFactory().one().lt(getFactory().zero()));
		assertTrue(getFactory().zero().lt(getFactory().one()));
		assertFalse(getFactory().zero().lt(getFactory().zero()));
		if (dataTypeHasNegativeValues()) {
			assertTrue(getFactory().toString(),
					getFactory().m_one().lt(getFactory().zero()));
			assertFalse(getFactory().toString(),
					getFactory().one().lt(getFactory().m_one()));
			assertFalse(getFactory().toString(),
					getFactory().zero().lt(getFactory().m_one()));
		}
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.RingElement#gt(org.jlinalg.IRingElement)}. "-1" is
	 * exclude for instances of {@link org.jlinalg.demo.FieldP}
	 */
	@Test
	public void testGt_base()
	{
		assertTrue(getFactory().one().gt(getFactory().zero()));
		assertFalse(getFactory().one().gt(getFactory().one()));
		assertFalse(getFactory().zero().gt(getFactory().zero()));
		assertFalse(getFactory().zero().gt(getFactory().one()));
		if (dataTypeHasNegativeValues()) {
			assertFalse("Factory: " + getFactory().getClass().getName(),
					getFactory().m_one().gt(getFactory().zero()));
			assertTrue("Factory: " + getFactory().getClass().getName(),
					getFactory().zero().gt(getFactory().m_one()));
		}
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.RingElement#le(org.jlinalg.IRingElement)}.
	 */
	@Test
	public void testLe_base()
	{
		assertTrue(getFactory().zero().le(getFactory().one()));
		assertTrue(getFactory().zero().le(getFactory().zero()));
		assertTrue(getFactory().one().le(getFactory().one()));
		assertFalse(getFactory().one().le(getFactory().zero()));
		if (dataTypeHasNegativeValues()) {
			assertTrue(getFactory().toString(),
					getFactory().m_one().le(getFactory().zero()));
			assertFalse(getFactory().toString(),
					getFactory().zero().le(getFactory().m_one()));
		}
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.RingElement#ge(org.jlinalg.IRingElement)}.
	 */
	@Test
	public void testGe_base()
	{
		assertFalse(getFactory().zero().ge(getFactory().one()));
		assertTrue(getFactory().zero().ge(getFactory().zero()));
		assertTrue(getFactory().one().ge(getFactory().one()));
		assertTrue(getFactory().one().ge(getFactory().zero()));
		if (dataTypeHasNegativeValues()) {
			assertFalse(getFactory().toString(),
					getFactory().m_one().ge(getFactory().zero()));
			assertTrue(getFactory().toString(),
					getFactory().zero().ge(getFactory().m_one()));
		}
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.RingElement#divide(org.jlinalg.IRingElement)}. Only
	 * divisions with 1,0,-1 are tested.
	 */
	@Test
	public void testDivide_base()
	{
		assertEquals(getFactory().zero(),
				getFactory().zero().divide(getFactory().one()));
		assertEquals(getFactory().one(),
				getFactory().one().divide(getFactory().one()));
		assertEquals(getFactory().m_one(),
				getFactory().m_one().divide(getFactory().one()));
		assertEquals(getFactory().m_one(),
				getFactory().one().divide(getFactory().m_one()));
		assertEquals(getFactory().one(),
				getFactory().m_one().divide(getFactory().m_one()));
	}

	/**
	 * Test method for {@link org.jlinalg.RingElement#invert_()}. Only
	 * inversions with 1 and -1 are tested.
	 */
	@Test
	public void testInvert_base()
	{
		assertEquals(getFactory().one(), getFactory().one().invert());
		assertEquals(getFactory().m_one(), getFactory().m_one().invert());
	}

	/**
	 * Test method for {@link java.lang.Object#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEquals_base()
	{
		assertTrue(getFactory().zero().equals(getFactory().zero()));
		assertTrue(getFactory().zero().equals(getFactory().get("0")));
		assertTrue(getFactory().one().equals(getFactory().get("1")));
		assertFalse(getFactory().zero().equals(getFactory().get(1)));
		assertTrue(getFactory().get("8").equals(getFactory().get("8")));
	}

	/**
	 * test whether the inversion of zero results in an exception
	 */
	public void invertZero_base()
	{
		try {
			getFactory().zero().invert();
		} catch (DivisionByZeroException e) {
			return; // this is expected
		} catch (InvalidOperationException e) {
			// In certain cases other things can not be inverted,
			// check the exception's message for the keyword "inverted"
			if (e.getMessage().contains("invert")) return;
			throw e; // This invalid operation was not expected...
		}
		fail(getFactory().zero().toString() + " from factory "
				+ getFactory().toString() + " is apparently invertible");
	}

	/**
	 * test {@link IRingElement#compareTo(org.jlinalg.IRingElement)} for some
	 * values
	 */
	@Test
	public void testCompare_base()
	{
		assertEquals(0, getFactory().one().compareTo(getFactory().one()));
		assertEquals(0, getFactory().zero().compareTo(getFactory().get("0")));
		assertEquals(1, getFactory().one().compareTo(getFactory().zero()));
		if (dataTypeHasNegativeValues())
			assertEquals(-1, getFactory().zero().compareTo(getFactory().one()));
	}

	/**
	 * test {@link IRingElement#multiply(org.jlinalg.IRingElement)}
	 */
	@Test
	public void testMultiply_base()
	{
		assertEquals(getFactory().one(),
				getFactory().one().multiply(getFactory().one()));
		assertEquals(getFactory().zero(),
				getFactory().one().multiply(getFactory().zero()));
		assertEquals(getFactory().get("6"),
				getFactory().get("2").multiply(getFactory().get("3")));
	}

	/**
	 * test {@link org.jlinalg.RingElementFactory#negate()}
	 */
	@Test
	public void testNegate_base()
	{
		// Assume.assumeTrue(!methodIsDepreciated(getFactory().one(), "negate",
		// null));
		assertEquals(getFactory().one(), getFactory().m_one().negate());
		assertEquals(getFactory().zero(), getFactory().zero().negate());
		assertEquals(getFactory().get(5), getFactory().get(-5).negate());
	}
}
