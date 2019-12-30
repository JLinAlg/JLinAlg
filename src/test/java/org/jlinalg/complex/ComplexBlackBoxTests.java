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
package org.jlinalg.complex;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jlinalg.complex.Complex.ComplexFactory;
import org.jlinalg.testutil.RandomNumberList;
import org.junit.Test;

/**
 * @author Georg Thimm
 */
public class ComplexBlackBoxTests
{
	static ComplexFactory f = Complex.FACTORY;

	/**
	 * Test method for
	 * {@link org.jlinalg.FieldElement#divide(org.jlinalg.IRingElement)}.
	 */
	@Test
	public final void testDivideIRingElement()
	{
		for (Complex c1 : new RandomNumberList<>(f, 10)) {
			for (Complex c2 : new RandomNumberList<>(f, 10)) {
				Complex r = c1.divide(c2);
				assertTrue(r.multiply(c2).equals(c1));
			}
		}
	}

	/**
	 * Test method for {@link org.jlinalg.RingElement#isZero()}.
	 */
	@Test
	public final void testIsZero()
	{
		for (Complex c : new RandomNumberList<>(f, 20)) {
			if (!c.equals(f.zero())) assertFalse(c.isZero());
		}
	}

	/**
	 * Test method for {@link org.jlinalg.RingElement#isOne()}.
	 */
	@Test
	public final void testIsOne()
	{
		for (Complex c : new RandomNumberList<>(f, 20)) {
			if (!c.equals(f.one())) assertFalse(c.equals(f.one()));
		}
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.RingElement#lt(org.jlinalg.IRingElement)}.
	 */
	@Test
	public final void testLt()
	{
		for (Complex c : new RandomNumberList<>(f, 20)) {
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
		for (Complex c : new RandomNumberList<>(f, 20)) {
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
		for (Complex c : new RandomNumberList<>(f, 20)) {
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
		for (Complex c : new RandomNumberList<>(f, 20)) {
			assertTrue(c.ge(f.zero()));
		}
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.FieldElement#subtract(org.jlinalg.IRingElement)}.
	 */
	@Test
	public final void testSubtractIRingElement()
	{
		for (Complex c1 : new RandomNumberList<>(f, 10)) {
			for (Complex c2 : new RandomNumberList<>(f, 10)) {
				Complex r = c1.subtract(c2);
				assertTrue(c2.negate().add(c1).equals(r));
			}
		}
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.complex.Complex#compareTo(org.jlinalg.IRingElement)}.
	 */
	@Test
	public final void testCompareTo()
	{
		for (Complex c1 : new RandomNumberList<>(f, 10)) {
			if (c1.equals(Complex.FACTORY.zero()))
				assertTrue(c1.compareTo(Complex.FACTORY.zero()) == 0);
			else
				assertTrue(c1.compareTo(Complex.FACTORY.zero()) > 0);
			for (Complex c2 : new RandomNumberList<>(f, 10)) {
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
	 * Test method for {@link org.jlinalg.complex.Complex#invert()}.
	 */
	@Test
	public final void testInvert()
	{
		for (Complex c1 : new RandomNumberList<>(f, 10)) {
			assertTrue(c1.multiply(c1.invert()).equals(Complex.FACTORY.one()));
		}
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.complex.Complex#multiply(org.jlinalg.IRingElement)}.
	 */
	@Test
	public final void testMultiply()
	{
		for (Complex c1 : new RandomNumberList<>(f, 10)) {
			for (Complex c2 : new RandomNumberList<>(f, 10)) {
				Complex r = c1.multiply(c2);
				assertTrue(r.norm().equals(c1.norm().multiply(c2.norm())));
			}
		}
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.complex.Complex#add(org.jlinalg.IRingElement)} .
	 */
	@Test
	public final void testAdd()
	{
		for (Complex c1 : new RandomNumberList<>(f, 10)) {
			for (Complex c2 : new RandomNumberList<>(f, 10)) {
				Complex r = c1.add(c2);
				assertTrue(r.getReal().equals(c1.getReal().add(c2.getReal())));
				assertTrue(r.getImaginary()
						.equals(c1.getImaginary().add(c2.getImaginary())));
			}
		}
	}
}
