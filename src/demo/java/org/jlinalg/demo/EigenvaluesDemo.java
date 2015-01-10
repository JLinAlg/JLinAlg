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
package org.jlinalg.demo;

import org.jlinalg.Matrix;
import org.jlinalg.doublewrapper.DoubleWrapper;

/**
 * @author Andreas Keilhauer, Georg Thimm
 */
public class EigenvaluesDemo
{
	/**
	 * Start the demo
	 * 
	 * @param argv
	 *            is ignored.
	 */
	public static void main(String[] argv)
	{
		// create double values for the matrix
		DoubleWrapper d0, d1, d2, d3;
		d0 = new DoubleWrapper(0.0);
		d1 = new DoubleWrapper(1.0);
		d2 = new DoubleWrapper(2.0);
		d3 = new DoubleWrapper(3.0);

		// create a diagonal matrix
		Matrix<DoubleWrapper> diagonalMatrix = new Matrix<DoubleWrapper>(
				new DoubleWrapper[][]
				{
						{
								d1, d0, d0
						},
						{
								d0, d2, d0
						},
						{
								d0, d0, d3
						}
				});
		System.out.println("Diagonal Matrix: \n" + diagonalMatrix);
		// The eigenvalues of a diagonal matrix are always the diagonal entries:
		System.out.println("All eigenvalues: " + diagonalMatrix.eig());

		// In general, the eigenvalues of a real matrix can be complex:
		Matrix<DoubleWrapper> m = new Matrix<DoubleWrapper>(
				new DoubleWrapper[][]
				{
						{
								d1, d1.negate()
						},
						{
								d1, d1
						}
				});
		System.out.println("New matrix: \n" + m);
		System.out.println("All eigenvalues: " + m.eig());

	}
}
