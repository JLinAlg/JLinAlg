package org.jlinalg.demo;

import org.jlinalg.FieldElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.LinAlgFactory;
import org.jlinalg.LinSysSolver;
import org.jlinalg.Matrix;
import org.jlinalg.Vector;
import org.jlinalg.doublewrapper.DoubleWrapper;
import org.jlinalg.rational.Rational;

/**
 * This demo shows how the squared distance between the real solution of an
 * equation system to the one that is found when using floating point
 * arithmetic, grows exponentially when the dimension of the Hilbert matrix
 * increases.
 * 
 * @author Andreas Keilhauer
 */
public class HilbertMatrixDemo
{

	public static void main(String[] argv)
	{
		for (int dimension = 1; dimension <= 50; dimension++) {
			Matrix<DoubleWrapper> m = createHilbertMatrix(dimension,
					DoubleWrapper.FACTORY);
			LinAlgFactory<DoubleWrapper> factory = new LinAlgFactory<>(
					m.getFactory());
			Vector<DoubleWrapper> solution = LinSysSolver.solve(m,
					factory.ones(dimension));

			Matrix<Rational> mExact = createHilbertMatrix(dimension,
					Rational.FACTORY);
			LinAlgFactory<Rational> factoryRational = new LinAlgFactory<>(
					mExact.getFactory());
			Vector<Rational> solutionExact = LinSysSolver.solve(mExact,
					factoryRational.ones(dimension));

			System.out.println("dimension = "
					+ dimension
					+ " -> error =  "
					+ solutionExact.squaredDistance(convert(solution))
							.doubleValue());
		}

	}

	private static <RE extends FieldElement<RE>> Matrix<RE> createHilbertMatrix(
			int dimension, IRingElementFactory<RE> factory)
	{
		Matrix<RE> hilbertMatrix = new Matrix<RE>(dimension, dimension, factory);
		RE one = factory.one();
		for (int row = 1; row <= hilbertMatrix.getRows(); row++) {
			RE rowNumber = factory.get(row);
			for (int col = 1; col <= hilbertMatrix.getCols(); col++) {
				RE colNumber = factory.get(col);
				hilbertMatrix.set(row, col,
						one.divide(rowNumber.add(colNumber).subtract(one)));
			}
		}
		return hilbertMatrix;
	}

	private static Vector<Rational> convert(Vector<DoubleWrapper> vector)
	{
		Vector<Rational> converted = new Vector<Rational>(vector.length(),
				Rational.FACTORY);
		for (int i = 1; i <= vector.length(); i++) {
			converted.set(i, Rational.FACTORY.get(vector.getEntry(i)));
		}
		return converted;

	}
}
