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

import org.jlinalg.IRingElement;
import org.jlinalg.Matrix;
import org.jlinalg.MatrixMultiplication;
import org.jlinalg.complex.Complex;

/**
 * This computes the determinant and the inverse of a complex matrix.
 * 
 * @author Andreas Keilhauer, Georg Thimm
 */
public class MatrixOperationsDemo
{

	private static Matrix<Complex> matrix1;
	private static Matrix<Complex> matrix2;

	/**
	 * Run the demo: create a 2x2 matrix with complex numbers, calculate its
	 * determinant and inverse.
	 * 
	 * @param argv
	 *            is ignored.
	 */
	public static void main(String[] argv)
	{
		createMatrices();

		// print the determinant
		IRingElement<?> determinant = matrix1.det();
		System.out.println("\nDeteterminant of matrix1: " + determinant);

		// print the inverse
		Matrix<Complex> inverse = matrix1.inverse();
		System.out.println("\nInverse of matrix1: \n" + inverse);

		Matrix<Complex> product = MatrixMultiplication.simple(matrix1, matrix2);
		System.out.println("\nmatrix1*matrix2: \n" + product);
	}

	private static void createMatrices()
	{
		// Create two complex numbers
		Complex c1 = Complex.FACTORY.get(1.0, 0.0);
		Complex c2 = Complex.FACTORY.get(0.0, 1.0);
		Complex c3 = Complex.FACTORY.get(0.5, 0.5);
		Complex c4 = Complex.FACTORY.get(0.5, -0.5);

		matrix1 = new Matrix<>(new Complex[][] {
				{
						c1, c2
				}, {
						c2, c1.add(c2)
				}
		});

		matrix2 = new Matrix<>(new Complex[][] {
				{
						c3, c3
				}, {
						c3, c4
				}
		});
		System.out.println("Matrix matrix1: \n" + matrix1);
		System.out.println("Matrix matrix2: \n" + matrix2);

	}

}
