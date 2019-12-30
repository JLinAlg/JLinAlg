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

/**
 * This includes some different methods of multiplying two matrices.
 * 
 * @author Andreas Keilhauer
 * @author Marco Bodrato
 */
public class MatrixMultiplication
{

	/**
	 * a value for the Strassen algorithm
	 */
	protected static int STRASSEN_ORIGINAL_TRUNCATION_POINT = 48;

	/**
	 * a value for the Strassen-Winograd algorithm
	 */
	protected static int STRASSEN_WINOGRAD_TRUNCATION_POINT = 48;

	protected static int STRASSEN_BODRATO_TRUNCATION_POINT = 48;

	/**
	 * @param <RE>
	 *            the type of the elements in the matrices to be compared.
	 * @param m1
	 * @param m2
	 * @throws InvalidOperationException
	 *             if the two matrices can not be multiplied as the number
	 *             columns in m1 is different to that of the rows in m2
	 */
	private static <RE extends IRingElement<RE>> void checkDimensions(
			Matrix<RE> m1, Matrix<RE> m2) throws InvalidOperationException
	{
		if (m1.getCols() != m2.getRows()) {
			throw new InvalidOperationException(
					"Tried to multiply a matrix with " + m1.getCols()
							+ " columns and a matrix with " + m2.getRows()
							+ " rows");
		}
	}

	/**
	 * Uses the standard method for multiplication of Matrix-objects. Asymptotic
	 * runtime: 0(n^3)
	 * 
	 * @param <RE>
	 *            the type of the elements in the matrices.
	 * @param m1
	 * @param m2
	 * @return m1 multiplied by m2
	 * @throws InvalidOperationException
	 */
	public static <RE extends IRingElement<RE>> Matrix<RE> simple(
			Matrix<RE> m1, Matrix<RE> m2) throws InvalidOperationException
	{
		checkDimensions(m1, m2);

		int resultRows = m1.getRows();
		int resultCols = m2.getCols();

		Matrix<RE> resultMatrix = new Matrix<>(resultRows, resultCols, m1
				.getFactory());

		for (int i = 1; i <= resultRows; i++) {
			for (int j = 1; j <= resultCols; j++) {
				RE e = m1.getRow(i).multiply(m2.getCol(j));
				resultMatrix.set(i, j, e);
			}
		}
		return resultMatrix;
	}

	/**
	 * This could be considered the school-method of multiplying matrices.
	 * Asymptotic runtime: 0(n^3)
	 * 
	 * @param <RE>
	 *            The type of the returned value
	 * @param m1
	 * @param m2
	 * @return m1 multiplied by m2
	 * @throws InvalidOperationException
	 */
	public static <RE extends IRingElement<RE>> Matrix<RE> school(
			Matrix<RE> m1, Matrix<RE> m2) throws InvalidOperationException
	{
		checkDimensions(m1, m2);

		int resultRows = m1.getRows();
		int resultCols = m2.getCols();

		RE[][] m1Entries = m1.getEntries();
		RE[][] m2Entries = m2.getEntries();

		RE[][] resultEntries = m1.FACTORY.getArray(resultRows, resultCols);

		for (int i = 0; i < resultRows; i++) {
			for (int j = 0; j < resultCols; j++) {
				resultEntries[i][j] = m1.FACTORY.zero();
				for (int k = 0; k < m2Entries.length; k++) {
					resultEntries[i][j] = resultEntries[i][j]
							.add(m1Entries[i][k].multiply(m2Entries[k][j]));
				}
			}
		}
		return new Matrix<>(resultEntries);
	}

	/**
	 * The original Strassen-Algorithm for matrix-multiplication using 7
	 * multiplications and 18 additions (or subtraction) in one recursion.
	 * Runtime: O(n^(log_2 7)) (appromximately: O(n^(2.807) )
	 * 
	 * @param <RE>
	 *            the type of the elements in the matrix
	 * @param m1
	 * @param m2
	 * @return m1 multiplied by m2
	 * @throws InvalidOperationException
	 */
	public static <RE extends IRingElement<RE>> Matrix<RE> strassenOriginal(
			Matrix<RE> m1, Matrix<RE> m2)
	{

		checkDimensions(m1, m2);

		int resultRows = m1.getRows();
		int resultCols = m2.getCols();

		if (m1.getRows() <= STRASSEN_ORIGINAL_TRUNCATION_POINT) {
			return MatrixMultiplication.simple(m1, m2);
		}

		m1 = MatrixMultiplication.fillUpPow2(m1);
		m2 = MatrixMultiplication.fillUpPow2(m2);

		return strassenOriginalHelper(m1, m2).getMatrix(0, resultRows - 1, 0,
				resultCols - 1);
	}

	private static <RE extends IRingElement<RE>> Matrix<RE> strassenOriginalHelper(
			Matrix<RE> m1, Matrix<RE> m2)
	{

		if (m1.getRows() <= STRASSEN_ORIGINAL_TRUNCATION_POINT) {
			return MatrixMultiplication.simple(m1, m2);
		}

		int endIndex = m1.getRows();
		int splitIndex = endIndex / 2;

		Matrix<RE> a11 = m1.getMatrix(0, splitIndex - 1, 0, splitIndex - 1);
		Matrix<RE> a12 = m1.getMatrix(0, splitIndex - 1, splitIndex,
				endIndex - 1);
		Matrix<RE> a21 = m1.getMatrix(splitIndex, endIndex - 1, 0,
				splitIndex - 1);
		Matrix<RE> a22 = m1.getMatrix(splitIndex, endIndex - 1, splitIndex,
				endIndex - 1);

		Matrix<RE> b11 = m2.getMatrix(0, splitIndex - 1, 0, splitIndex - 1);
		Matrix<RE> b12 = m2.getMatrix(0, splitIndex - 1, splitIndex,
				endIndex - 1);
		Matrix<RE> b21 = m2.getMatrix(splitIndex, endIndex - 1, 0,
				splitIndex - 1);
		Matrix<RE> b22 = m2.getMatrix(splitIndex, endIndex - 1, splitIndex,
				endIndex - 1);

		Matrix<RE> p1 = strassenOriginalHelper(a11.add(a22), b11.add(b22));
		Matrix<RE> p2 = strassenOriginalHelper(a21.add(a22), b11);
		Matrix<RE> p3 = strassenOriginalHelper(a11, b12.subtract(b22));
		Matrix<RE> p4 = strassenOriginalHelper(a22, b21.subtract(b11));
		Matrix<RE> p5 = strassenOriginalHelper(a11.add(a12), b22);
		Matrix<RE> p6 = strassenOriginalHelper(a21.subtract(a11), b11.add(b12));
		Matrix<RE> p7 = strassenOriginalHelper(a12.subtract(a22), b21.add(b22));

		Matrix<RE> c11 = p1.add(p4).subtract(p5).add(p7);
		Matrix<RE> c12 = p3.add(p5);
		Matrix<RE> c21 = p2.add(p4);
		Matrix<RE> c22 = p1.add(p3).subtract(p2).add(p6);

		RE[][] c11Entries = c11.getEntries();
		RE[][] c12Entries = c12.getEntries();
		RE[][] c21Entries = c21.getEntries();
		RE[][] c22Entries = c22.getEntries();

		RE[][] cEntries = m1.getFactory().getArray(m1.getRows(), m2.getRows());

		for (int i = 0; i < c11.getRows(); i++) {
			for (int j = 0; j < c11.getCols(); j++) {
				cEntries[i][j] = c11Entries[i][j];
			}
		}

		for (int i = 0; i < c12.getRows(); i++) {
			// int offset = splitIndex;
			for (int j = 0; j < c12.getCols(); j++) {
				cEntries[i][j + splitIndex] = c12Entries[i][j];
			}
		}

		for (int i = 0; i < c21.getRows(); i++) {
			// int offset = splitIndex;
			for (int j = 0; j < c21.getCols(); j++) {
				cEntries[i + splitIndex][j] = c21Entries[i][j];
			}
		}

		for (int i = 0; i < c22.getRows(); i++) {
			// int offset = splitIndex;
			for (int j = 0; j < c22.getCols(); j++) {
				cEntries[i + splitIndex][j + splitIndex] = c22Entries[i][j];
			}
		}
		return new Matrix<>(cEntries);
	}

	/**
	 * The Algorithm of Strassen-Winograd for matrix-multiplication using 7
	 * multiplications and 15 additions (or subtractions) in one recursion.
	 * Runtime: O(n^(log_2 7)) (appromximately: O(n^(2.807) )
	 * 
	 * @param <RE>
	 *            the type of the elements in the matrix
	 * @param m1
	 * @param m2
	 * @return m1 multiplied by m2
	 * @throws InvalidOperationException
	 */
	public static <RE extends IRingElement<RE>> Matrix<RE> strassenWinograd(
			Matrix<RE> m1, Matrix<RE> m2)
	{
		checkDimensions(m1, m2);

		int resultRows = m1.getRows();
		int resultCols = m2.getCols();

		if (m1.getRows() <= STRASSEN_WINOGRAD_TRUNCATION_POINT) {
			return MatrixMultiplication.simple(m1, m2);
		}

		m1 = MatrixMultiplication.fillUpPow2(m1);
		m2 = MatrixMultiplication.fillUpPow2(m2);

		return strassenWinogradHelper(m1, m2).getMatrix(0, resultRows - 1, 0,
				resultCols - 1);
	}

	private static <RE extends IRingElement<RE>> Matrix<RE> strassenWinogradHelper(
			Matrix<RE> m1, Matrix<RE> m2)
	{
		if (m1.getRows() <= STRASSEN_WINOGRAD_TRUNCATION_POINT) {
			return MatrixMultiplication.simple(m1, m2);
		}

		int endIndex = m1.getRows();
		int splitIndex = endIndex / 2;

		Matrix<RE> a11 = m1.getMatrix(0, splitIndex - 1, 0, splitIndex - 1);
		Matrix<RE> a12 = m1.getMatrix(0, splitIndex - 1, splitIndex,
				endIndex - 1);
		Matrix<RE> a21 = m1.getMatrix(splitIndex, endIndex - 1, 0,
				splitIndex - 1);
		Matrix<RE> a22 = m1.getMatrix(splitIndex, endIndex - 1, splitIndex,
				endIndex - 1);

		Matrix<RE> b11 = m2.getMatrix(0, splitIndex - 1, 0, splitIndex - 1);
		Matrix<RE> b12 = m2.getMatrix(0, splitIndex - 1, splitIndex,
				endIndex - 1);
		Matrix<RE> b21 = m2.getMatrix(splitIndex, endIndex - 1, 0,
				splitIndex - 1);
		Matrix<RE> b22 = m2.getMatrix(splitIndex, endIndex - 1, splitIndex,
				endIndex - 1);

		Matrix<RE> s1 = a21.add(a22);
		Matrix<RE> s2 = s1.subtract(a11);
		Matrix<RE> s3 = a11.subtract(a21);
		Matrix<RE> s4 = a12.subtract(s2);

		Matrix<RE> t1 = b12.subtract(b11);
		Matrix<RE> t2 = b22.subtract(t1);
		Matrix<RE> t3 = b22.subtract(b12);
		Matrix<RE> t4 = b21.subtract(t2);

		Matrix<RE> p1 = strassenWinogradHelper(a11, b11);
		Matrix<RE> p2 = strassenWinogradHelper(a12, b21);
		Matrix<RE> p3 = strassenWinogradHelper(s1, t1);
		Matrix<RE> p4 = strassenWinogradHelper(s2, t2);
		Matrix<RE> p5 = strassenWinogradHelper(s3, t3);
		Matrix<RE> p6 = strassenWinogradHelper(s4, b22);
		Matrix<RE> p7 = strassenWinogradHelper(a22, t4);

		Matrix<RE> u1 = p1.add(p2);
		Matrix<RE> u2 = p1.add(p4);
		Matrix<RE> u3 = u2.add(p5);
		Matrix<RE> u4 = u3.add(p7);
		Matrix<RE> u5 = u3.add(p3);
		Matrix<RE> u6 = u2.add(p3);
		Matrix<RE> u7 = u6.add(p6);

		RE[][] c11Entries = u1.getEntries();
		RE[][] c12Entries = u7.getEntries();
		RE[][] c21Entries = u4.getEntries();
		RE[][] c22Entries = u5.getEntries();
		IRingElementFactory<RE> factory = m1.getFactory();

		RE[][] cEntries = factory.getArray(m1.getRows(), m2.getRows());

		for (int i = 0; i < u1.getRows(); i++) {
			for (int j = 0; j < u1.getCols(); j++) {
				cEntries[i][j] = c11Entries[i][j];
			}
		}

		for (int i = 0; i < u7.getRows(); i++) {
			// int offset = splitIndex;
			for (int j = 0; j < u7.getCols(); j++) {
				cEntries[i][j + splitIndex] = c12Entries[i][j];
			}
		}

		for (int i = 0; i < u4.getRows(); i++) {
			// int offset = splitIndex;
			for (int j = 0; j < u4.getCols(); j++) {
				cEntries[i + splitIndex][j] = c21Entries[i][j];
			}
		}

		for (int i = 0; i < u5.getRows(); i++) {
			// int offset = splitIndex;
			for (int j = 0; j < u5.getCols(); j++) {
				cEntries[i + splitIndex][j + splitIndex] = c22Entries[i][j];
			}
		}

		return new Matrix<>(cEntries);

	}

	/**
	 * The Algorithm of Strassen-Bodrato for matrix-multiplication using 7
	 * multiplications and 15 additions (or subtractions) in one recursion.
	 * Runtime: O(n^(log_2 7)) (appromximately: O(n^(2.807) ) If the two
	 * operands coincide (squaring), 4 of 7 multiplications are themselves
	 * squarings, and 4 additions (subtractions) are saved.
	 * <P>
	 * See <a href="http://marco.bodrato.it/papers/Bodrato2008-StrassenLikeMatrixMultiplicationForSquares.pdf"
	 * >A Strassen-like matrix multiplication suited for squaring and highest
	 * power computation</a> (written 12/24/08 by Marco Bodrato)
	 * 
	 * @param <RE>
	 *            The type of the returned value
	 * @param m1
	 *            First operand
	 * @param m2
	 *            Second operand
	 * @return m1 multiplied by m2
	 * @throws InvalidOperationException
	 */
	public static <RE extends IRingElement<RE>> Matrix<RE> strassenBodrato(
			Matrix<RE> m1, Matrix<RE> m2)
	{

		checkDimensions(m1, m2);

		int resultRows = m1.getRows();
		int resultCols = m2.getCols();

		if (m1.getRows() <= STRASSEN_BODRATO_TRUNCATION_POINT) {
			return MatrixMultiplication.simple(m1, m2);
		}

		boolean squaring = (m1 == m2);
		m1 = MatrixMultiplication.fillUpPow2(m1);
		if (!squaring) {
			m2 = MatrixMultiplication.fillUpPow2(m2);
			return strassenBodratoHelper(m1, m2).getMatrix(0, resultRows - 1,
					0, resultCols - 1);
		}
		return strassenBodratoHelper(m1, m1).getMatrix(0, resultRows - 1, 0,
				resultCols - 1);
	}

	private static <RE extends IRingElement<RE>> Matrix<RE> strassenBodratoHelper(
			Matrix<RE> m1, Matrix<RE> m2)
	{

		if (m1.getRows() <= STRASSEN_BODRATO_TRUNCATION_POINT) {
			return MatrixMultiplication.simple(m1, m2);
		}

		int endIndex = m1.getRows();
		int splitIndex = endIndex / 2;

		Matrix<RE> a11 = m1.getMatrix(0, splitIndex - 1, 0, splitIndex - 1);
		Matrix<RE> a12 = m1.getMatrix(0, splitIndex - 1, splitIndex,
				endIndex - 1);
		Matrix<RE> a21 = m1.getMatrix(splitIndex, endIndex - 1, 0,
				splitIndex - 1);
		Matrix<RE> a22 = m1.getMatrix(splitIndex, endIndex - 1, splitIndex,
				endIndex - 1);

		Matrix<RE> s1 = a22.add(a12);
		a22.subtractReplace(a21); /* s2 */
		Matrix<RE> s3 = a22.add(a12);
		Matrix<RE> s4 = s3.subtract(a11);

		Matrix<RE> p1, p2, p3, p4, p5, p6, p7;
		if (m1 != m2) {
			Matrix<RE> b11 = m2.getMatrix(0, splitIndex - 1, 0, splitIndex - 1);
			Matrix<RE> b12 = m2.getMatrix(0, splitIndex - 1, splitIndex,
					endIndex - 1);
			Matrix<RE> b21 = m2.getMatrix(splitIndex, endIndex - 1, 0,
					splitIndex - 1);
			Matrix<RE> b22 = m2.getMatrix(splitIndex, endIndex - 1, splitIndex,
					endIndex - 1);

			Matrix<RE> t1 = b22.add(b12);
			b22.subtractReplace(b21); /* t2 */
			Matrix<RE> t3 = b22.add(b12);
			Matrix<RE> t4 = t3.subtract(b11);

			p1 = strassenBodratoHelper(s1, t1);
			p2 = strassenBodratoHelper(a22, b22);
			p3 = strassenBodratoHelper(s3, t3);
			p4 = strassenBodratoHelper(a11, b11);
			p5 = strassenBodratoHelper(a12, b21);
			p6 = strassenBodratoHelper(s4, b12);
			p7 = strassenBodratoHelper(a21, t4);
		}
		else {
			p1 = strassenBodratoHelper(s1, s1);
			p2 = strassenBodratoHelper(a22, a22);
			p3 = strassenBodratoHelper(s3, s3);
			p4 = strassenBodratoHelper(a11, a11);
			p5 = strassenBodratoHelper(a12, a21);
			p6 = strassenBodratoHelper(s4, a12);
			p7 = strassenBodratoHelper(a21, s4);
		}

		p3.addReplace(p5); /* u1 */
		p1.subtractReplace(p3); /* u2 */
		p3.subtractReplace(p2); /* u3 */
		p4.addReplace(p5); /* u4 */
		p3.subtractReplace(p6); /* u5 */
		p2.addReplace(p1); /* u7 */
		p1.subtractReplace(p7); /* u6 */

		RE[][] c11Entries = p4.getEntries();
		RE[][] c12Entries = p3.getEntries();
		RE[][] c21Entries = p1.getEntries();
		RE[][] c22Entries = p2.getEntries();

		IRingElementFactory<RE> factory = m1.getFactory();

		RE[][] cEntries = factory.getArray(m1.getRows(), m2.getRows());

		for (int i = 0; i < p4.getRows(); i++) {
			for (int j = 0; j < p4.getCols(); j++) {
				cEntries[i][j] = c11Entries[i][j];
			}
		}

		for (int i = 0; i < p3.getRows(); i++) {
			// int offset = splitIndex;
			for (int j = 0; j < p3.getCols(); j++) {
				cEntries[i][j + splitIndex] = c12Entries[i][j];
			}
		}

		for (int i = 0; i < p1.getRows(); i++) {
			// int offset = splitIndex;
			for (int j = 0; j < p1.getCols(); j++) {
				cEntries[i + splitIndex][j] = c21Entries[i][j];
			}
		}

		for (int i = 0; i < p2.getRows(); i++) {
			// int offset = splitIndex;
			for (int j = 0; j < p2.getCols(); j++) {
				cEntries[i + splitIndex][j + splitIndex] = c22Entries[i][j];
			}
		}

		return new Matrix<>(cEntries);
	}

	private static <RE extends IRingElement<RE>> Matrix<RE> fillUpPow2(
			Matrix<RE> m)
	{
		double mLog2 = Math.log(Math.max(m.getRows(), m.getCols()))
				/ Math.log(2);

		if (m.getRows() != m.getCols() || (mLog2 != Math.floor(mLog2))) {
			int targetDimension = (int) Math.pow(2, Math.ceil(mLog2));
			LinAlgFactory<RE> factory = new LinAlgFactory<>(m.getFactory());
			Matrix<RE> filledUp = factory.identity(targetDimension);
			for (int row = 1; row <= m.getRows(); row++) {
				for (int col = 1; col <= m.getCols(); col++) {
					filledUp.set(row, col, m.get(row, col));
				}
			}
			return filledUp;
		}
		return m;
	}
}
