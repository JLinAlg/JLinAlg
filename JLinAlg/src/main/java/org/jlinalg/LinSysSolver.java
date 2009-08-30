package org.jlinalg;

import java.io.Serializable;
import java.util.Stack;

/**
 * This class is capable of solving linear equations.
 * 
 * @author Andreas Keilhauer, Georg Thimm
 * @param <RE>
 *            the type for the domain for which equations are to be solved.
 */

public class LinSysSolver<RE extends IRingElement>
		implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Calculates the solution space of a given linear equation system of the
	 * form A*x=b.
	 * N.B.: In General, this operation will fail, if not all entries are
	 * FieldElements.
	 * 
	 * @param <RE>
	 *            the type of the dimensions
	 * @param a
	 *            coefficient matrix
	 * @param b
	 *            result vector
	 * @return solution space as an AffinLinearSubSpace.
	 * @throws InvalidOperationException
	 *             if the matrix and vector sizes mismatch.
	 */
	public static <RE extends IRingElement> AffineLinearSubspace<RE> solutionSpace(
			Matrix<RE> a, Vector<RE> b) throws InvalidOperationException
	{
		IRingElementFactory<RE> factory = a.getFactory();
		if (a.getRows() != b.length()) {
			throw new InvalidOperationException(
					"Tried to solve an equation system with a coefficient matrix"
							+ " with " + a.getRows() + " rows and a"
							+ " vector with length " + b.length()
							+ ". Not correct format!");
		}

		Matrix<RE> extCoeff = LinSysSolver.isSolvableHelper(a, b);

		if (extCoeff == null) {
			return new LinearSubspace<RE>(new Vector[] {});
		}

		Stack<Integer> swaps = new Stack<Integer>();
		for (int row = 1; row <= extCoeff.getRows(); row++) {
			if (extCoeff.get(row, row).isZero()) {
				int col = row;
				while (extCoeff.get(row, col).isZero()) {
					col++;
				}
				swaps.push(row);
				swaps.push(col);
				extCoeff.swapCols(row, col);
			}
		}

		int dimension = a.getCols();

		RE zero = factory.zero();
		RE minusOne = (RE) zero.subtract(factory.one());

		Vector<RE>[] generatingSystem = new Vector[extCoeff.getCols()
				- extCoeff.getRows() - 1];

		for (int col = extCoeff.getRows() + 1; col <= extCoeff.getCols() - 1; col++)
		{
			Vector<RE> tmp = new Vector<RE>(dimension, factory);
			for (int row = 1; row <= dimension; row++) {
				if (row <= extCoeff.getRows()) {
					tmp.set(row, extCoeff.get(row, col));
				}
				else if (row == col) {
					tmp.set(row, minusOne);
				}
				else {
					tmp.set(row, zero);
				}
			}

			generatingSystem[col - (extCoeff.getRows() + 1)] = tmp;
		}

		Vector<RE> inhomogenousPart = new Vector<RE>(dimension, factory);

		for (int row = 1; row <= dimension; row++) {
			if (row <= extCoeff.getRows()) {
				inhomogenousPart
						.set(row, extCoeff.get(row, extCoeff.getCols()));
			}
			else {
				inhomogenousPart.set(row, zero);
			}
		}

		while (!swaps.isEmpty()) {
			int index1 = swaps.pop();
			int index2 = swaps.pop();
			inhomogenousPart.swapEntries(index1, index2);
			for (int i = 0; i < generatingSystem.length; i++) {
				generatingSystem[i].swapEntries(index1, index2);
			}
		}

		if (inhomogenousPart.isZero()) {
			if (generatingSystem.length == 0) {
				return new LinearSubspace<RE>(new Vector[]
				{
					inhomogenousPart
				}, true);
			}
			else {
				return new LinearSubspace<RE>(generatingSystem, true);
			}
		}
		return new AffineLinearSubspace<RE>(inhomogenousPart, generatingSystem,
				true);
	}

	/**
	 * Calculates a solution of a given linear equation system of the form
	 * A*x=b.
	 * N.B.: In General, this operation will fail, if not all entries are
	 * FieldElements.
	 * 
	 * @param <RE>
	 *            the type of the elements in the matices and vectors.
	 * @param a
	 *            coefficient matrix
	 * @param b
	 *            result vector
	 * @return solution as a Vector.
	 * @throws InvalidOperationException
	 *             if the matrix and vector sizes mismatch.
	 */
	public static <RE extends IRingElement> Vector<RE> solve(Matrix<RE> a,
			Vector<RE> b)
	{
		IRingElementFactory<RE> factory = a.getFactory();
		if (a.getRows() != b.length()) {
			throw new InvalidOperationException(
					"Tried to solve an equation system with a coefficient matrix"
							+ " with " + a.getRows() + " rows and a"
							+ " vector with length " + b.length()
							+ ". Not correct format!");
		}

		Matrix<RE> extCoeff = LinSysSolver.isSolvableHelper(a, b);

		if (extCoeff == null) {
			return null;
		}

		Stack<Integer> swaps = new Stack<Integer>();
		for (int row = 1; row <= extCoeff.getRows(); row++) {
			if (extCoeff.get(row, row).isZero()) {
				int col = row;
				while (extCoeff.get(row, col).isZero()) {
					col++;
				}
				swaps.push(row);
				swaps.push(col);
				extCoeff.swapCols(row, col);
			}
		}

		int dimension = a.getCols();

		RE zero = factory.zero();
		Vector<RE> inhomogenousPart = new Vector<RE>(dimension, factory);

		for (int row = 1; row <= dimension; row++) {
			if (row <= extCoeff.getRows()) {
				inhomogenousPart
						.set(row, extCoeff.get(row, extCoeff.getCols()));
			}
			else {
				inhomogenousPart.set(row, zero);
			}
		}

		while (!swaps.isEmpty()) {
			int index1 = swaps.pop();
			int index2 = swaps.pop();
			inhomogenousPart.swapEntries(index1, index2);
		}

		return inhomogenousPart;

	}

	/**
	 * Does something quite similar to isSolvable but is used by solve and
	 * solutionSpace
	 * N.B.: In General, this operation will fail, if not all entries are
	 * FieldElements.
	 * 
	 * @param <RE>
	 *            the type of the coeffcients.
	 * @param a
	 *            coefficient matrix
	 * @param b
	 *            result vector
	 * @return a.gaussjord() without zero rows or null if there is no solution
	 */
	private static <RE extends IRingElement> Matrix<RE> isSolvableHelper(
			Matrix<RE> a, Vector<RE> b)
	{
		if (a.getRows() != b.length()) {
			throw new InvalidOperationException(
					"Tried to solve an equation system with a coefficient matrix"
							+ " with " + a.getRows() + " rows and a"
							+ " vector with length " + b.length()
							+ ". Not correct format!");
		}

		Matrix<RE> tmp = a.insertCol(a.getCols() + 1, b);
		/*
		 * The Following is equivalent to: return tmp.rank() == a.rank(); But it
		 * is more efficient.
		 */

		tmp = tmp.gaussjord();
		int row = tmp.getRows();

		// Find the index of the first not zero row.
		while (row > 0 && tmp.isZeroRow(row)) {
			tmp = tmp.withoutRow(row);
			row--;
		}

		if (b.isZero()) {
			return tmp;
		}
		else {
			/*
			 * Check whether there is any non zero entry in the first not zero
			 * row of tmp in a.gaussjord() (i.e. tmp without last column)
			 */
			for (int col = 1; col < tmp.getCols(); col++) {
				if (!tmp.get(row, col).isZero()) {
					return tmp;
				}
			}
			return null;
		}

	}

	/**
	 * Tests whether a linear equation system (A*x=b) is solvable or not.
	 * 
	 * @param <RE>
	 *            the type of the coefficients.
	 * @param a
	 *            coefficient matrix
	 * @param b
	 *            result vector
	 * @return return true if and only if there is a solution for this linear
	 *         equation system.
	 */
	public static <RE extends IRingElement> boolean isSolvable(Matrix<RE> a,
			Vector<RE> b)
	{

		if (a.getRows() != b.length()) {
			throw new InvalidOperationException(
					"Tried to solve an equation system with a coefficient matrix"
							+ " with " + a.getRows() + " rows and a"
							+ " vector with length " + b.length()
							+ ". Not correct format!");
		}

		Matrix<RE> tmp = a.insertCol(a.getCols() + 1, b);
		// The Following is equivalent to: return tmp.rank() == a.rank();
		// But it is more efficient.

		tmp = tmp.gausselim();
		int row = tmp.getRows();

		// Find the index of the first not zero row.
		while (row > 0 && tmp.isZeroRow(row)) {
			row--;
		}

		// Check whether there is any non zero entry in the first not zero row
		// of tmp
		// in a.gaussjord() (i.e. tmp without last column)
		for (int col = 1; col < tmp.getCols(); col++) {
			if (!tmp.get(row, col).isZero()) {
				return true;
			}
		}

		return false;

	}
}
