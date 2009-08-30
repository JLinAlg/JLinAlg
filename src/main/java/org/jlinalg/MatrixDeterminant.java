package org.jlinalg;

/**
 * @author ???, Georg Thimm
 */
public class MatrixDeterminant
{

	/**
	 * check whether <code>matrix</code> square
	 * 
	 * @param matrix
	 * @throws InvalidOperationException
	 *             if it is not.
	 */
	private static void checkSquare(Matrix<? extends IRingElement> matrix)
			throws InvalidOperationException
	{
		if (matrix.getRows() != matrix.getCols()) {
			throw new InvalidOperationException(
					"Square matrix needed for determinant");
		}
	}

	/**
	 * Uses the Gaussian method to calculate the determinant of the given
	 * matrix. Asymptotic runtime: O(n^3)
	 * N.B.: In General, this operation will fail, if not all entries are
	 * RingElements.
	 * 
	 * @param <RE>
	 *            the type of the elements in the matrix
	 * @param matrix
	 * @return the determinant
	 * @throws InvalidOperationException
	 */
	public static <RE extends IRingElement> RE gaussianMethod(Matrix<RE> matrix)
			throws InvalidOperationException
	{
		IRingElementFactory<? extends RE> factory = (IRingElementFactory<RE>) matrix
				.get(1, 1).getFactory();

		checkSquare(matrix);

		// do the calculations.
		Matrix<RE> m = matrix.copy();
		RE determinant = factory.one();
		RE zero = factory.zero();
		// while m is not a single field element
		while (m.numOfCols != 1) {
			// Search element with maximal norm value in row=1.
			int mrow = 1;
			IRingElement max = m.get(mrow, 1).norm();
			for (int r = 1; r <= m.getRows(); r++) {
				if (m.get(r, 1).norm().gt(max)) {
					max = m.get(r, 1).norm();
					mrow = r;
				}
			}
			// if max=0, determinant is zero
			if (max.equals(zero)) return factory.zero();
			// reduce other rows
			for (int r = 1; r <= m.numOfRows; r++) {
				if (r == mrow || m.get(r, 1).equals(zero)) continue;
				RE div = (RE) m.get(mrow, 1).multiply(m.get(r, 1).invert());
				m.set(r, 1, factory.zero());
				for (int c = 2; c <= m.numOfCols; c++) {
					m.set(r, c, m.get(r, c).subtract(
							m.get(mrow, c).multiply(div.invert())));
				}
			}
			// System.err.println("Reduced w/ mrow=" + mrow + "\n" + m + "
			// max="+ max);
			determinant = (RE) determinant.multiply(m.get(mrow, 1));
			if (mrow % 2 == 0) determinant = (RE) determinant.negate();
			m = m.withoutRow(mrow).withoutCol(1);
			// System.err.println("partial det=" + determinant + "\nnew m=\n" +
			// m);
		}
		determinant = (RE) determinant.multiply(m.get(1, 1));
		return determinant;
	}

	/**
	 * Uses the Leibniz method to calculate the determinant of the given matrix.
	 * Asymptotic runtime: O(n!) => Extremely inefficient!
	 * BUT: It provides a way to calculate the determinant of Matrices
	 * containing RingElements (which are not FieldElements, e.g. Polynomials)
	 * 
	 * @param <RE>
	 *            the type of the elements in the matrix
	 * @param squareMatrix
	 * @return the determinant
	 */
	public static <RE extends IRingElement> RE leibnizMethod(
			Matrix<RE> squareMatrix)
	{
		IRingElementFactory<RE> factory = (IRingElementFactory<RE>) squareMatrix
				.get(1, 1).getFactory();

		checkSquare(squareMatrix);

		if (squareMatrix.numOfRows == 1) {
			return squareMatrix.get(1, 1);
		}
		else if (squareMatrix.numOfRows == 2) {
			/*
			 * This case is unnecessary, but it is nice to have it there (maybe
			 * even a bit of a runtime optimization ;-)...
			 */
			return (RE) squareMatrix.get(1, 1).multiply(squareMatrix.get(2, 2))
					.subtract(
							squareMatrix.get(1, 2).multiply(
									squareMatrix.get(2, 1)));
		}
		else {
			RE determinant = factory.zero();
			RE m_one = factory.m_one();
			RE one = factory.one();

			for (int i = 1; i <= squareMatrix.numOfRows; i++) {
				RE f;
				if (i % 2 == 0)
					f = m_one;
				else
					f = one;
				determinant = (RE) determinant
						.add(f.multiply(squareMatrix.get(i, 1)).multiply(
								leibnizMethod(withoutRowAndColumn(squareMatrix,
										i, 1))));
			}
			return determinant;
		}

	}

	/**
	 * Create a new matrix without a certain row and column
	 * 
	 * @param <RE>
	 *            The type of the elements in the matrix to be modified
	 * @param squareMatrix
	 * @param withoutRow
	 *            the row to be removed
	 * @param withoutColumn
	 *            the column to be removed
	 * @return the reduced matrix
	 */
	public static <RE extends IRingElement> Matrix<RE> withoutRowAndColumn(
			Matrix<RE> squareMatrix, int withoutRow, int withoutColumn)
	{
		Matrix<RE> result = new Matrix<RE>(squareMatrix.numOfRows - 1,
				squareMatrix.numOfCols - 1, squareMatrix.getFactory());
		int resultRow = 1;
		int resultCol = 1;

		for (int row = 1; row <= squareMatrix.numOfRows; row++) {
			if (row == withoutRow) {
				continue;
			}

			resultCol = 1;
			for (int col = 1; col <= squareMatrix.numOfCols; col++) {
				if (col == withoutColumn) {
					continue;
				}
				result.set(resultRow, resultCol, squareMatrix.get(row, col));
				resultCol++;
			}
			resultRow++;
		}
		return result;
	}
}
