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

package org.jlinalg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import org.jlinalg.rational.Rational;
import org.jlinalg.rational.RationalFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MatrixTests
{
	final private static RationalFactory factory = RationalFactory.getFactory();
	private static Matrix<Rational> matrix;

	@BeforeAll
	static void setup()
	{
		matrix = factory.convert(new String[][] {
				{
						"-1", "-2"
				}, {
						"1", "2"
				}, {
						"1/2", "3/2"
				}
		});
	}

	@Test
	void matrixConversion() throws Exception
	{
		assertEquals(3, matrix.numOfRows, "num rows not ok");
		assertEquals(2, matrix.numOfCols, "num cols not ok");
		assertEquals(factory.get("-1"), matrix.get(1, 1));
		assertEquals(factory.get("-2"), matrix.get(1, 2));
		assertEquals(factory.get("1"), matrix.get(2, 1));
		assertEquals(factory.get("2"), matrix.get(2, 2));
		assertEquals(factory.get("1/2"), matrix.get(3, 1));
		assertEquals(factory.get("3/2"), matrix.get(3, 2));
	}

	@Test
	void transposedMatrixConversion() throws Exception
	{
		Matrix<Rational> transposed = matrix.transpose();
		assertEquals(2, transposed.numOfRows, "num rows not ok");
		assertEquals(3, transposed.numOfCols, "num cols not ok");
		assertEquals(factory.get("-1"), transposed.get(1, 1));
		assertEquals(factory.get("-2"), transposed.get(2, 1));
		assertEquals(factory.get("1"), transposed.get(1, 2));
		assertEquals(factory.get("2"), transposed.get(2, 2));
		assertEquals(factory.get("1/2"), transposed.get(1, 3));
		assertEquals(factory.get("3/2"), transposed.get(2, 3));
	}

	@Test
	void matrixIterator()
	{
		Iterator<Rational> iterator = matrix.iterator();
		assertTrue(iterator.hasNext(), "iterator should not be at end.");
		assertEquals(factory.get("-1"), iterator.next());
		assertTrue(iterator.hasNext(), "iterator should not be at end.");
		assertEquals(factory.get("-2"), iterator.next());
		assertTrue(iterator.hasNext(), "iterator should not be at end.");
		assertEquals(factory.get("1"), iterator.next());
		assertTrue(iterator.hasNext(), "iterator should not be at end.");
		assertEquals(factory.get("2"), iterator.next());
		assertTrue(iterator.hasNext(), "iterator should not be at end.");
		assertEquals(factory.get("1/2"), iterator.next());
		assertTrue(iterator.hasNext(), "iterator should not be at end.");
		assertEquals(factory.get("3/2"), iterator.next());
		assertFalse(iterator.hasNext(), "iterator should be at end.");
	}

	@Test
	void transposedMatrixIterator()
	{
		Iterator<Rational> iterator = matrix.transpose().iterator();
		assertTrue(iterator.hasNext(), "iterator should not be at end.");
		assertEquals(factory.get("-1"), iterator.next());
		assertTrue(iterator.hasNext(), "iterator should not be at end.");
		assertEquals(factory.get("1"), iterator.next());
		assertTrue(iterator.hasNext(), "iterator should not be at end.");
		assertEquals(factory.get("1/2"), iterator.next());
		assertTrue(iterator.hasNext(), "iterator should not be at end.");
		assertEquals(factory.get("-2"), iterator.next());
		assertTrue(iterator.hasNext(), "iterator should not be at end.");
		assertEquals(factory.get("2"), iterator.next());
		assertTrue(iterator.hasNext(), "iterator should not be at end.");
		assertEquals(factory.get("3/2"), iterator.next());
		assertFalse(iterator.hasNext(), "iterator should be at end.");
	}
}
