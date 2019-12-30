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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.jlinalg.IRingElement;
import org.jlinalg.Matrix;
import org.jlinalg.Vector;
import org.junit.Assume;
import org.junit.Test;

/**
 * @author Georg Thimm
 */
public abstract class FactoryTestBase<RE extends IRingElement<RE>>
		extends TestBaseFixture<RE>
{

	/**
	 * Test method for {@link org.jlinalg.RingElementFactory#getArray(int)}.
	 */
	@Test
	public void testGetArrayInt_base()
	{
		RE[] array = getFactory().getArray(7);
		assertEquals(7, array.length);
	}

	/**
	 * Test method for {@link org.jlinalg.RingElementFactory#getArray(int, int)}
	 * .
	 */
	@Test
	public void testGetArrayIntInt_base()
	{

		RE[][] array = getFactory().getArray(7, 8);
		assertEquals(7, array.length);
		assertEquals(8, array[0].length);
	}

	/**
	 * Test method for {@link org.jlinalg.RingElementFactory#one()}.
	 */
	@Test
	public void testOne_base()
	{
		assertNotNull(getFactory().one());
		assertEquals(getFactory().one(), getFactory().get(1));
	}

	/**
	 * Test method for {@link org.jlinalg.RingElementFactory#zero()}.
	 */
	@Test
	public void testZero_base()
	{
		assertNotNull(getFactory().zero());
		assertEquals(getFactory().zero(), getFactory().get(0));
	}

	/**
	 * Test method for {@link org.jlinalg.RingElementFactory#m_one()}.
	 */
	@Test
	public void testM_one_base()
	{
		assertNotNull(getFactory().m_one());
		assertEquals(getFactory().m_one(), getFactory().get(-1));
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.RingElementFactory#get(java.lang.Object)}.
	 */
	@Test
	public void testGetObject_base()
	{
		assertEquals(getFactory().one(), getFactory().get(Integer.valueOf(1)));
		assertEquals(getFactory().one(), getFactory().get("1"));
		assertEquals(getFactory().m_one(), getFactory().get("-1"));
	}

	/**
	 * Test method for {@link org.jlinalg.RingElementFactory#get(int)}.
	 */
	@Test
	public void testGetInt_base()
	{
		assertEquals(getFactory().one(), getFactory().get(1));
		assertEquals(getFactory().m_one(), getFactory().get(-1));
	}

	/**
	 * Test method for {@link org.jlinalg.RingElementFactory#get(double)}. Due
	 * to the various number formats, it is tested whether the string
	 * representation of the number commences with "0", 1", or "-1", as
	 * appropriate.
	 */
	@Test
	public void testGetDouble_base()
	{
		RE r;
		r = getFactory().get(1.0);
		assertTrue(r.toString(), r.toString().startsWith("1"));
		r = getFactory().get(0.0);
		assertTrue(r.toString(), r.toString().startsWith("0"));
		Assume.assumeTrue(dataTypeHasNegativeValues());
		r = getFactory().get(-1.0);
		assertTrue(r.toString(), r.toString().startsWith("-1"));
	}

	/**
	 * check whether an array of numbers contains at least two distinct values.
	 */
	private void assertSetOK(ArrayList<RE> array)
	{
		for (int i = 0; i < array.size() - 1; i++) {
			if (!array.get(i).equals(array.get(i + 1))) return;
		}
		fail("Array " + array + " does not contain random values.");
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.RingElementFactory#gaussianRandomValue(java.util.Random)}
	 * .
	 */
	@Test
	public void testGaussianRandomValue_base()
	{
		Assume.assumeTrue(!methodIsDepreciated(getFactory(),
				"gaussianRandomValue", null));
		ArrayList<RE> array = new ArrayList<>();
		for (int i = 0; i < 20; i++)
			array.add(getFactory().gaussianRandomValue());
		assertSetOK(array);
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.RingElementFactory#randomValue(java.util.Random)}.
	 */
	@Test
	public void testRandomValueRandom_base()
	{
		Assume.assumeTrue(!methodIsDepreciated(getFactory(), "randomValue",
				null));
		ArrayList<RE> array = new ArrayList<>();
		for (int i = 0; i < 20; i++)
			array.add(getFactory().randomValue());
		assertSetOK(array);
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.RingElementFactory#randomValue(java.util.Random, org.jlinalg.IRingElement, org.jlinalg.IRingElement)}
	 * .
	 */
	@Test
	public void testRandomValueRandomIRingElementIRingElement_base()
	{
		Assume.assumeTrue(!methodIsDepreciated(getFactory(), "randomValue",
				new Class<?>[] {
						getFactory().one().getClass(),
						getFactory().one().getClass()
				}));
		RE min = getFactory().zero();
		RE max = getFactory().get("3");
		if (!min.lt(max)) {
			System.err
					.println("Skipping  testRandomValueRandomIRingElementIRingElement_base for "
							+ getFactory().getClass());
			return;
		}
		ArrayList<RE> array = new ArrayList<>();
		for (int i = 0; i < 20; i++)
			array.add(getFactory().randomValue(min, max));
		assertSetOK(array);
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.RingElementFactory#convert(org.jlinalg.Matrix)}. The
	 * comparison of the values is based on the string values: if the string in
	 * the matrix values is the start of the string representation of the
	 * converted value, the test passes.
	 */
	@Test
	public void testConvertMatrixOfARG_base()
	{
		String[][] values = {
				{
						"1", "2", "3"
				}, {
						"4", "5", "6"
				}
		};
		Matrix<StringWrapper> in = new Matrix<>(values,
				StringWrapper.FACTORY);
		Matrix<RE> conv = getFactory().convert(in);
		assertNotNull(conv);
		assertEquals(values[0].length, conv.getCols());
		assertEquals(values.length, conv.getRows());
		for (int col = 1; col <= values[0].length; col++) {
			for (int row = 1; row < values.length; row++) {
				assertTrue(conv.get(row, col).toString()
						.startsWith(values[row - 1][col - 1]));
			}
		}
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.RingElementFactory#convert(org.jlinalg.Vector)}. The
	 * comparison of the values is based on the string values: if the string in
	 * the matrix values is the start of the string representation of the
	 * converted value, the test passes.
	 */
	@Test
	public void testConvertVectorOfARG_base()
	{
		String[] values = {
				"1", "2", "3"
		};

		Vector<StringWrapper> in = new Vector<>(values,
				StringWrapper.FACTORY);
		Vector<RE> conv = getFactory().convert(in);
		assertNotNull(conv);
		assertEquals(values.length, conv.length());
		for (int i = 1; i <= values.length; i++) {
			assertTrue(conv.getEntry(i).toString().startsWith(values[i - 1]));
		}
	}
}
