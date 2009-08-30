/**
 * @author Andreas Keilhauer, Georg Thimm
 */

package org.jlinalg.demo;

// import the required classes from JLinAlg
import org.jlinalg.DoubleWrapper;
import org.jlinalg.Matrix;

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
