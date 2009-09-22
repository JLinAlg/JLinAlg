package org.jlinalg.demo;

import org.jlinalg.IRingElement;
import org.jlinalg.Matrix;
import org.jlinalg.complex.Complex;

/**
 * This computes the determinant and the inverse of a complex matrix.
 * 
 * @author Andreas Keilhauer, Georg Thimm
 */
public class MatrixOperationsDemo
{

	/**
	 * Run the demo: create a 2x2 matrix with complex numbers, calculate its
	 * determinant and inverse.
	 * 
	 * @param argv
	 *            is ignored.
	 */
	public static void main(String[] argv)
	{
		// Create two complex numbers
		Complex c1 = Complex.FACTORY.get(1.0, 0.0);
		Complex c2 = Complex.FACTORY.get(0.0, 1.0);

		// create the matrix
		Matrix<Complex> m = new Matrix<Complex>(new Complex[][] {
				{
						c1, c2
				}, {
						c2, c1.add(c2)
				}
		});
		System.out.println("Matrix m: \n" + m);

		// print the determinant
		IRingElement<?> determinant = m.det();
		System.out.println("Deteterminant of m: " + determinant);

		// print the inverse
		Matrix<Complex> inverse = m.inverse();
		System.out.println("Inverse of m: \n" + inverse);

	}

}
