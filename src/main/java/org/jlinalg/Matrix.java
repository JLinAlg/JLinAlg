package org.jlinalg;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.jlinalg.polynomial.Polynomial;
import org.jlinalg.polynomial.PolynomialFactory;

/**
 * This class represents a matrix.
 * 
 * @author Andreas Keilhauer, Simon D. Levy, Georg Thimm
 * @param <RE>
 *            the type of the elements in the matrix.
 */
public class Matrix<RE extends IRingElement>
		implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * the number of rows in this matrix.
	 */
	final protected int numOfRows;

	/**
	 * the number of columns in this matrix.
	 */
	final protected int numOfCols;

	protected final IRingElementFactory<RE> FACTORY;

	/**
	 * the elements in the matrix
	 */
	protected RE[][] entries;

	/**
	 * Constructs an empty Matrix with a certain number of rows and columns.
	 * 
	 * @param numberOfRows
	 * @param numberOfCols
	 * @param factory
	 *            the factory to be used to create elements for this matrix.
	 */
	public Matrix(int numberOfRows, int numberOfCols,
			IRingElementFactory<RE> factory)
	{
		this.numOfRows = numberOfRows;
		this.numOfCols = numberOfCols;
		FACTORY = factory;
		entries = factory.getArray(numberOfRows, numberOfCols);
	}

	/**
	 * Constructs a Matrix with a certain number of rows and columns and and
	 * fills it with RingElements from one RingElement array.
	 * 
	 * @param numberOfRows
	 * @param theEntries
	 *            in a RingElement array.
	 * @throws InvalidOperationException
	 *             if theEntries is null
	 * @throws InvalidOperationException
	 *             if numberOfRows is not valid
	 */
	public Matrix(RE[] theEntries, int numberOfRows)
			throws InvalidOperationException
	{
		if (theEntries == null) {
			throw new InvalidOperationException(
					"Tried to construct matrix with a null entry array");
		}
		if (theEntries.length % numberOfRows != 0) {
			throw new InvalidOperationException(
					"Tried to construct matrix with " + theEntries.length
							+ " entries and " + numberOfRows + " rows");

		}
		this.numOfRows = numberOfRows;
		this.numOfCols = theEntries.length / numberOfRows;
		FACTORY = (IRingElementFactory<RE>) theEntries[0].getFactory();
		this.entries = FACTORY.getArray(this.numOfRows, this.numOfCols);
		for (int i = 0; i < this.numOfRows; i++) {
			for (int j = 0; j < this.numOfCols; j++) {
				entries[i][j] = theEntries[i * this.numOfCols + j];
			}
		}
	}

	/**
	 * Constructs a Matrix out of an array of row vectors.
	 * 
	 * @param rowVectors
	 *            as an array of Vectors
	 * @throws InvalidOperationException
	 *             if rowVectors is null
	 * @throws InvalidOperationException
	 *             if rowVectors contains a null Vector
	 * @throws InvalidOperationException
	 *             if rowVectors contains Vectors of unequal lengths
	 */
	public Matrix(Vector<RE>[] rowVectors) throws InvalidOperationException
	{
		if (rowVectors == null) {
			throw new InvalidOperationException(
					"Tried to construct matrix but array of row vectors was null");
		}
		for (int i = 0; i < rowVectors.length; i++) {
			if (rowVectors[i] == null) {
				throw new InvalidOperationException(
						"Tried to construct matrix and found null-vector");
			}
		}
		int vectorLength = rowVectors[0].length();
		for (int i = 0; i < rowVectors.length; i++) {
			if (rowVectors[i].length() != vectorLength) {
				throw new InvalidOperationException(
						"Tried to construct matrix but not all vectors"
								+ " had the same length");
			}
		}

		numOfRows = rowVectors.length;
		numOfCols = vectorLength;
		FACTORY = (IRingElementFactory<RE>) rowVectors[0].entries[0]
				.getFactory();
		entries = FACTORY.getArray(numOfRows, numOfCols);

		for (int k = 0; k < numOfRows; k++) {
			for (int l = 0; l < numOfCols; l++) {
				entries[k][l] = rowVectors[k].getEntry(l + 1);
			}
		}
	}

	/**
	 * Constructs a Matrix out of a two dimensional array of RingElements.
	 * 
	 * @param theEntries
	 *            as a two dimensional RingElement array.
	 * @throws InvalidOperationException
	 *             if theEntries is null
	 */
	public Matrix(RE[][] theEntries) throws InvalidOperationException
	{
		if (theEntries == null) {
			throw new InvalidOperationException(
					"Tried to construct matrix but entry array was null");
		}

		this.numOfRows = theEntries.length;
		if (this.numOfRows != 0)
			this.numOfCols = theEntries[0].length;
		else
			this.numOfCols = 0;
		this.entries = theEntries;
		FACTORY = (IRingElementFactory<RE>) entries[0][0].getFactory();
	}

	/**
	 * Constructs a Matrix out of a two dimensional array of RingElements. But
	 * with the dimensions given it is faster than the constructor above because
	 * it doesnt check any integrity of the entries-array.
	 * 
	 * @param theEntries
	 * @param rows
	 * @param cols
	 */
	public Matrix(RE[][] theEntries, int rows, int cols)
	{
		this.numOfRows = rows;
		this.numOfCols = cols;
		this.entries = theEntries;
		FACTORY = (IRingElementFactory<RE>) entries[0][0].getFactory();
	}

	/**
	 * Gets the number of rows of this Matrix.
	 * 
	 * @return number of rows
	 */

	public int getRows()
	{
		return numOfRows;
	}

	/**
	 * Gets the number of columns of this Matrix.
	 * 
	 * @return number of columns
	 */

	public int getCols()
	{
		return numOfCols;
	}

	public RE[][] getEntries()
	{
		return this.entries;
	}

	/**
	 * Gets the entry of this Matrix at a certain row - and col index.
	 * 
	 * @param rowIndex
	 * @param colIndex
	 * @return the RingElement at this row - and column index
	 * @throws InvalidOperationException
	 *             if rowIndex is not between 1 and numberOfRows or colIndex is
	 *             not between 1 and numberOfCols
	 */

	public RE get(int rowIndex, int colIndex) throws InvalidOperationException
	{
		try {
			return entries[rowIndex - 1][colIndex - 1];
		} catch (ArrayIndexOutOfBoundsException e) {
			if (rowIndex > this.numOfRows || rowIndex < 1) {
				throw new InvalidOperationException(
						"Accessed invalid row index " + rowIndex
								+ ". Only row indices from 1 to "
								+ this.numOfRows + " valid");
			}
			throw new InvalidOperationException(
					"Accessed invalid column index " + colIndex
							+ ". Only column indices " + "from 1 to "
							+ this.numOfCols + " valid");
		}
	}

	/**
	 * Sets the entry of this Matrix at a certain row - and col index.
	 * 
	 * @param rowIndex
	 * @param colIndex
	 * @param iRingElement
	 * @throws InvalidOperationException
	 *             if rowIndex or colIndex is invalid
	 */

	public void set(int rowIndex, int colIndex, IRingElement iRingElement)
			throws InvalidOperationException
	{
		try {
			entries[rowIndex - 1][colIndex - 1] = (RE) iRingElement;
		} catch (ArrayIndexOutOfBoundsException e) {
			if (rowIndex > this.numOfRows || rowIndex < 1) {
				throw new InvalidOperationException(
						"Accessed invalid row index " + rowIndex
								+ ". Only row indices from 1 to "
								+ this.numOfRows + " valid");
			}
			throw new InvalidOperationException(
					"Accessed invalid column index " + colIndex
							+ ". Only column indices " + "from 1 to "
							+ this.numOfCols + " valid");
		}
	}

	/**
	 * Gets the row vector at a certain row index.
	 * 
	 * @param rowIndex
	 * @return a vector holding the selected row
	 */
	public Vector<RE> getRow(int rowIndex)
	{
		RE[] rowEntries = FACTORY.getArray(numOfCols);
		for (int i = 0; i < numOfCols; i++) {
			try {
				rowEntries[i] = entries[rowIndex - 1][i];
			} catch (ArrayIndexOutOfBoundsException a) {
				throw new InvalidOperationException("Tried row index "
						+ rowIndex + ". Only row indices from 1 to "
						+ this.numOfRows + " valid");
			}
		}
		return new Vector<RE>(rowEntries);
	}

	/**
	 * Gets the column vector at a certain column index.
	 * 
	 * @param colIndex
	 * @return a vector holding the selected column.
	 */
	public Vector<RE> getCol(int colIndex)
	{
		RE[] colEntries = FACTORY.getArray(numOfRows);
		for (int i = 0; i < numOfRows; i++) {
			try {
				colEntries[i] = entries[i][colIndex - 1];
			} catch (ArrayIndexOutOfBoundsException a) {
				throw new InvalidOperationException("Tried row index "
						+ colIndex + ". Only row indices from 1 to "
						+ this.numOfCols + " valid");
			}

		}
		return new Vector<RE>(colEntries);
	}

	/**
	 * Sets the row vector at a certain index of the matrix.
	 * 
	 * @param rowIndex
	 * @param vector
	 * @throws InvalidOperationException
	 *             if index out of bounds
	 */

	public void setRow(int rowIndex, Vector<RE> vector)
			throws InvalidOperationException
	{

		if (this.numOfCols != vector.length()) {
			throw new InvalidOperationException("Tried to set row number "
					+ rowIndex + "of a matrix with " + this.numOfCols
					+ " columns with a vector having length " + vector.length()
					+ " ");
		}
		for (int i = 1; i <= vector.length(); i++) {
			set(rowIndex, i, vector.getEntry(i));
		}
	}

	/**
	 * Sets the row the matrix to the RingElement value.
	 * 
	 * @param rowIndex
	 * @param value
	 * @throws InvalidOperationException
	 *             if index out of bounds
	 */
	public void setRow(int rowIndex, RE value) throws InvalidOperationException
	{
		rowIndex--;
		for (int i = 0; i < entries[0].length; i++) {
			entries[rowIndex][i] = value;
		}
	}

	/**
	 * Sets the column vector at a certain column index of the matrix.
	 * 
	 * @param colIndex
	 * @param vector
	 */

	public void setCol(int colIndex, Vector<RE> vector)
	{

		if (this.numOfRows != vector.length()) {
			throw new InvalidOperationException("Tried to set column number "
					+ colIndex + "of a matrix with " + this.numOfRows
					+ " rows with a vector having length " + vector.length()
					+ " ");
		}

		for (int i = 1; i <= vector.length(); i++) {
			set(i, colIndex, vector.getEntry(i));
		}
	}

	/**
	 * Returns this Matrix without the row at a certain row index.
	 * 
	 * @param rowIndex
	 * @return the matrix without the row at the row index.
	 */

	public Matrix<RE> withoutRow(int rowIndex)
	{
		// Exception still missing here
		Matrix<RE> tmp = new Matrix<RE>(this.getRows() - 1, this.getCols(),
				FACTORY);
		int counter = 0;
		for (int i = 1; i <= this.getRows(); i++) {
			counter++;
			if (i == rowIndex) {
				counter--;
				continue;
			}
			tmp.setRow(counter, this.getRow(i));
		}
		return tmp;
	}

	/**
	 * Returns this Matrix without the column at a certain column index.
	 * 
	 * @param colIndex
	 * @return the matrix without the column at the column index.
	 */

	public Matrix<RE> withoutCol(int colIndex)
	{
		// Exception still missing here
		Matrix<RE> tmp = new Matrix<RE>(this.getRows(), this.getCols() - 1,
				FACTORY);
		int counter = 0;
		for (int i = 1; i <= this.getCols(); i++) {
			counter++;
			if (i == colIndex) {
				counter--;
				continue;
			}
			tmp.setCol(counter, this.getCol(i));
		}
		return tmp;
	}

	/**
	 * Returns a Matrix with a Vector inserted at specified index as a column.
	 * Index 1 will it put at the beginning and index numOfCols+1 will attach it
	 * to the end.
	 * 
	 * @param vector
	 * @param colIndex
	 * @return matrix with vector inserted.
	 * @throws InvalidOperationException
	 *             if index out of bounds
	 */

	public Matrix<RE> insertCol(int colIndex, Vector<RE> vector)
			throws InvalidOperationException
	{

		if (this.getRows() != vector.length()) {
			throw new InvalidOperationException("This vector\n" + vector
					+ "\n cannot be attached to " + "the Matrix\n" + this
					+ " as a column vector. The length does " + "not match");
		}

		if (colIndex < 1 || colIndex > this.getCols() + 1) {
			throw new InvalidOperationException(colIndex
					+ " is not a valid column index for inserting " + vector
					+ " into\n" + this);
		}

		Matrix<RE> tmp = new Matrix<RE>(this.getRows(), this.getCols() + 1,
				FACTORY);
		int colOffset = 0;
		for (int col = 1; col <= tmp.getCols(); col++) {
			if (col == colIndex) {
				tmp.setCol(col, vector);
				colOffset = 1;
			}
			else {
				tmp.setCol(col, this.getCol(col - colOffset));
			}
		}

		return tmp;
	}

	/**
	 * Returns a Matrix with a Vector inserted at specified index as a row.
	 * Index 1 will put at the beginning and index numOfRows+1 will attach it to
	 * the end.
	 * 
	 * @param vector
	 * @param rowIndex
	 * @return matrix with vector inserted.
	 * @throws InvalidOperationException
	 *             if index out of bounds
	 */

	public Matrix<RE> insertRow(int rowIndex, Vector<RE> vector)
			throws InvalidOperationException
	{
		if (this.getCols() != vector.length()) {
			throw new InvalidOperationException("This vector\n" + vector
					+ "\n cannot be inserted into the " + "Matrix\n" + this
					+ " as a row vector");
		}

		if (rowIndex < 1 || rowIndex > this.getRows() + 1) {
			throw new InvalidOperationException(rowIndex
					+ " is not a valid row index for inserting " + vector
					+ " into\n" + this);
		}

		Matrix<RE> tmp = new Matrix<RE>(this.getRows() + 1, this.getCols(),
				FACTORY);
		int rowOffset = 0;
		for (int row = 1; row <= tmp.getRows(); row++) {
			if (row == rowIndex) {
				tmp.setRow(row, vector);
				rowOffset = 1;
			}
			else {
				tmp.setRow(row, this.getRow(row - rowOffset));
			}
		}
		return tmp;
	}

	/**
	 * Get a submatrix.
	 * 
	 * @param i0
	 *            Initial row index
	 * @param i1
	 *            Final row index
	 * @param j0
	 *            Initial column index
	 * @param j1
	 *            Final column index
	 * @return A(i0:i1,j0:j1)
	 * @exception ArrayIndexOutOfBoundsException
	 *                Submatrix indices
	 */

	public Matrix<RE> getMatrix(int i0, int i1, int j0, int j1)
	{
		Matrix<RE> X = new Matrix<RE>(i1 - i0 + 1, j1 - j0 + 1, FACTORY);
		RE[][] B = X.getEntries();
		try {
			for (int i = i0; i <= i1; i++) {
				for (int j = j0; j <= j1; j++) {
					B[i - i0][j - j0] = entries[i][j];
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix exceeds matrix");
		}
		return X;
	}

	/**
	 * Get a submatrix.
	 * 
	 * @param r
	 *            Array of row indices.
	 * @param j0
	 *            Initial column index
	 * @param j1
	 *            Final column index
	 * @return A(r(:),j0:j1)
	 * @exception ArrayIndexOutOfBoundsException
	 *                Submatrix indices
	 */

	public Matrix<RE> getMatrix(int[] r, int j0, int j1)
	{
		Matrix<RE> X = new Matrix<RE>(r.length, j1 - j0 + 1, FACTORY);
		IRingElement[][] B = X.getEntries();
		try {
			for (int i = 0; i < r.length; i++) {
				for (int j = j0; j <= j1; j++) {
					B[i][j - j0] = entries[r[i]][j];
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix exceeds matrix");
		}
		return X;
	}

	/**
	 * Returns a String representation of this Matrix.
	 * 
	 * @return String representation
	 */

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		for (int r = 1; r <= numOfRows; r++) {
			if (r == 1)
				sb.append("[");
			else
				sb.append(" ");
			for (int c = 1; c <= numOfCols; c++) {
				if (c > 1) sb.append(",");
				sb.append(get(r, c));
			}
			if (r == numOfRows)
				sb.append("]");
			else
				sb.append("\n");
		}
		// System.err.println(sb.toString());
		return sb.toString();
	}

	/**
	 * Returns the matrix that is the sum of this Matrix and another matrix.
	 * 
	 * @param anotherMatrix
	 *            the second matrix
	 * @return this + anotherMatrix
	 * @throws InvalidOperationException
	 *             if matrices differ in size
	 */
	public Matrix<RE> add(Matrix<RE> anotherMatrix)
			throws InvalidOperationException
	{
		RE[][] anotherMatrixEntries = anotherMatrix.getEntries();
		RE[][] resultMatrixEntries = FACTORY.getArray(this.numOfRows,
				this.numOfCols);
		for (int row = 0; row < this.numOfRows; row++) {
			for (int col = 0; col < this.numOfCols; col++) {
				resultMatrixEntries[row][col] = (RE) entries[row][col]
						.add(anotherMatrixEntries[row][col]);
			}
		}
		return new Matrix<RE>(resultMatrixEntries);
	}

	/**
	 * Returns the matrix that is the sum of this Matrix and a scalar.
	 * 
	 * @param scalar
	 * @return this + scalar
	 */
	public Matrix<RE> add(RE scalar)
	{
		return operate(scalar, AddOperator.getInstance());
	}

	/**
	 * Returns the matrix that is this this Matrix minus another one.
	 * 
	 * @param anotherMatrix
	 * @return this - anotherMatrix
	 * @throws InvalidOperationException
	 *             if matrices differ in size
	 */

	public Matrix<RE> subtract(Matrix<RE> anotherMatrix)
			throws InvalidOperationException
	{
		return operate(anotherMatrix, SubtractOperator.getInstance(), "diff");
	}

	/**
	 * Returns the matrix that is this this Matrix minus a scalar.
	 * 
	 * @param scalar
	 * @return this - scalar
	 */

	public Matrix<RE> subtract(RE scalar)
	{
		return operate(scalar, SubtractOperator.getInstance());
	}

	/**
	 * Returns a Matrix that is this Matrix multiplied with a scalar.
	 * 
	 * @param scalar
	 * @return multiplied Matrix
	 */

	public Matrix<RE> multiply(RE scalar)
	{

		return operate(scalar, MultiplyOperator.getInstance());
	}

	/**
	 * Returns a Matrix that is this Matrix divided by a scalar.
	 * 
	 * @param scalar
	 * @return divided Matrix
	 */
	public Matrix<RE> divide(RE scalar)
	{
		return operate(scalar, DivideOperator.getInstance());
	}

	/**
	 * Returns the vector that is the product of this Matrix and a given vector.
	 * 
	 * @param vector
	 * @return product vector
	 * @throws InvalidOperationException
	 *             if number of columns of this matrix does not equal number of
	 *             elements of vector
	 */

	public Vector<RE> multiply(Vector<RE> vector)
			throws InvalidOperationException
	{
		if (this.numOfCols != vector.length()) {
			String err = "Tried to multiply \n" + this + " and \n" + vector
					+ "Not correct format!";
			throw new InvalidOperationException(err);
		}

		Vector<RE> resultVector = new Vector<RE>(numOfRows, FACTORY);
		for (int i = 1; i <= numOfRows; i++) {
			resultVector.set(i, this.getRow(i).multiply(vector));
		}

		return resultVector;
	}

	/**
	 * Returns the matrix that is the product of this Matrix and another matrix.
	 * 
	 * @param anotherMatrix
	 * @return product matrix
	 * @throws InvalidOperationException
	 *             if number of columns of this matrix does not equal number of
	 *             rows of the other matrix
	 */

	public Matrix<RE> multiply(Matrix<RE> anotherMatrix)
			throws InvalidOperationException
	{
		if (numOfCols != anotherMatrix.getRows()) {
			throw new InvalidOperationException("Tried to multiply \n" + this
					+ " and \n" + anotherMatrix + "Not correct format!");
		}

		int resultRows = this.numOfRows;
		int resultCols = anotherMatrix.getCols();

		Matrix<RE> resultMatrix = new Matrix<RE>(resultRows, resultCols,
				anotherMatrix.FACTORY);

		for (int i = 1; i <= resultRows; i++) {
			for (int j = 1; j <= resultCols; j++) {
				resultMatrix.set(i, j, this.getRow(i).multiply(
						anotherMatrix.getCol(j)));
			}
		}
		return resultMatrix;
	}

	/**
	 * Returns a deep copy of this Matrix.
	 * 
	 * @return matrix copy
	 */

	public Matrix<RE> copy()
	{
		Matrix<RE> tmp = new Matrix<RE>(this.getRows(), this.getCols(), FACTORY);
		for (int row = 1; row <= this.getRows(); row++) {
			for (int col = 1; col <= this.getCols(); col++) {
				tmp.set(row, col, this.get(row, col));
			}
		}
		return tmp;
	}

	/**
	 * Returns the determinant of this Matrix. The Leibniz method, if not all
	 * element are FieldElements and the gaussian nethod fails
	 * 
	 * @return determinant
	 * @throws InvalidOperationException
	 *             if matrix is not square
	 */

	public RE det() throws InvalidOperationException
	{
		try {
			return MatrixDeterminant.gaussianMethod(this);
		} catch (org.jlinalg.InvalidOperationException e) {
			return MatrixDeterminant.leibnizMethod(this);
		} catch (ClassCastException c) {
			return MatrixDeterminant.leibnizMethod(this);
		}
	}

	/**
	 * Swaps two rows of this Matrix.
	 * 
	 * @param rowIndex1
	 *            index of first swap partner.
	 * @param rowIndex2
	 *            index of second swap partner.
	 */

	public void swapRows(int rowIndex1, int rowIndex2)
	{
		Vector<RE> tmp = getRow(rowIndex1);
		this.setRow(rowIndex1, getRow(rowIndex2));
		this.setRow(rowIndex2, tmp);
	}

	/**
	 * Swaps two columns of this Matrix.
	 * 
	 * @param colIndex1
	 *            index of first swap partner.
	 * @param colIndex2
	 *            index of second swap partner.
	 */

	public void swapCols(int colIndex1, int colIndex2)
	{
		Vector<RE> tmp = getCol(colIndex1);
		this.setCol(colIndex1, getCol(colIndex2));
		this.setCol(colIndex2, tmp);
	}

	/**
	 * Returns a matrix that is this Matrix with the Gauss-Jordan algorithm
	 * executed on. In other words: It returns the reduced row echelon form of
	 * this Matrix. N.B.: In General, this operation will fail, if not all
	 * entries are FieldElements.
	 * 
	 * @return matrix in reduced row-echelon form
	 */
	public Matrix<RE> gaussjord()
	{
		Matrix<RE> tmp = this.copy();

		int minOfRowsCols = Math.min(tmp.getRows(), tmp.getCols());
		int colCounter = 0;

		int row = 0;
		while (row < minOfRowsCols && colCounter < tmp.getCols()) {
			row++;
			colCounter++;
			RE diagonalEntry = tmp.get(row, colCounter);

			if (diagonalEntry.isZero()) {
				// search for non zero entry
				boolean found = false;
				for (int candidate = row + 1; candidate <= tmp.getRows(); candidate++)
				{
					if (!tmp.get(candidate, colCounter).isZero()) {
						tmp.swapRows(row, candidate);
						found = true;
						break;
					}
				}

				if (!found) {
					if (colCounter == tmp.getCols()) {
						return tmp;
					}

					row--;
					continue;
				} // else {
				diagonalEntry = tmp.get(row, colCounter);
				// }
			}

			for (int j = colCounter; j <= tmp.getCols(); j++) {
				RE oldEntry = tmp.get(row, j);
				tmp.set(row, j, oldEntry.multiply(diagonalEntry.invert()));
			}

			for (int j = 1; j <= tmp.getRows(); j++) {

				RE factor = tmp.get(j, colCounter);
				if (row == j || factor.isZero()) {
					continue;
				}

				for (int k = colCounter; k <= tmp.getCols(); k++) {
					RE oldEntry = tmp.get(j, k);
					tmp.set(j, k, oldEntry.subtract(tmp.get(row, k).multiply(
							factor)));
				}
			}
		}
		return tmp;
	}

	/**
	 * Returns a matrix that is this Matrix with Gauss-elimination executed on.
	 * In other words: It returns a row echelon form of this Matrix. N.B.: In
	 * General, this operation will fail, if not all entries are FieldElements.
	 * 
	 * @return matrix in row-echelon form
	 */
	public Matrix<RE> gausselim()
	{
		Matrix<RE> tmp = this.copy();

		int minOfRowsCols = Math.min(tmp.getRows(), tmp.getCols());
		int colCounter = 0;

		int row = 0;
		while (row < minOfRowsCols && colCounter < tmp.getCols()) {
			row++;
			colCounter++;
			RE diagonalEntry = tmp.get(row, colCounter);

			if (diagonalEntry.isZero()) {
				// search for non zero entry
				boolean found = false;
				for (int candidate = row + 1; candidate <= tmp.getRows(); candidate++)
				{
					if (!tmp.get(candidate, colCounter).isZero()) {
						tmp.swapRows(row, candidate);
						found = true;
						break;
					}
				}

				if (!found) {
					if (colCounter == tmp.getCols()) {
						return tmp;
					}

					row--;
					continue;
				} // else {
				diagonalEntry = tmp.get(row, colCounter);
				// }
			}

			for (int j = row; j <= tmp.getRows(); j++) {

				RE factor = (RE) tmp.get(j, colCounter).multiply(
						diagonalEntry.invert());
				if (row == j || factor.isZero()) {
					continue;
				}

				for (int k = colCounter; k <= tmp.getCols(); k++) {
					RE oldEntry = tmp.get(j, k);
					tmp.set(j, k, oldEntry.subtract(factor.multiply(tmp.get(
							row, k))));
				}
			}
		}
		return tmp;
	}

	/**
	 * Calculates the eigenvalues of a matrix.
	 * 
	 * @return All eigenvalues as a Vector with Complex entries.
	 * @throws InvalidOperationException
	 *             if this matrix is no square matrix or the entries are not all
	 *             DoubleWrappers.
	 */

	public Vector<? extends IRingElement> eig()
			throws InvalidOperationException
	{
		return Handbook.eig((Matrix<IRingElement>) this);
	}

	/**
	 * Returns whether the row at the specified row index is a zero row or not.
	 * 
	 * @param rowIndex
	 *            index of the row to be tested
	 * @return true if there are only zero elements in the row.
	 */

	public boolean isZeroRow(int rowIndex)
	{
		for (int col = 1; col <= this.getCols(); col++) {
			if (!this.get(rowIndex, col).isZero()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns whether the column at the specified column index is a zero column
	 * or not.
	 * 
	 * @param colIndex
	 *            of the column to be tested
	 * @return true if there are only zero elements in the column.
	 */

	public boolean isZeroCol(int colIndex)
	{
		for (int row = 1; row <= this.getRows(); row++) {
			if (!this.get(row, colIndex).isZero()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns the rank of this Matrix.
	 * 
	 * @return rank
	 */

	public int rank()
	{
		Matrix<RE> tmp = this.gausselim();
		int numberOfZeroRows = 0;
		int row = tmp.getRows();

		while (row > 0 && tmp.isZeroRow(row)) {
			numberOfZeroRows++;
			row--;
		}

		return tmp.getRows() - numberOfZeroRows;
	}

	/**
	 * Returns a matrix that is this Matrix transposed.
	 * 
	 * @return transposed matrix
	 */

	public Matrix<RE> transpose()
	{
		Matrix<RE> tmp = new Matrix<RE>(this.getCols(), this.getRows(), FACTORY);
		for (int row = 1; row <= this.getRows(); row++) {
			for (int col = 1; col <= this.getCols(); col++) {
				tmp.set(col, row, this.get(row, col));
			}
		}
		return tmp;
	}

	/**
	 * Calculate the trace of the matrix
	 * 
	 * @return the sum over the diagonal elements.
	 */
	public RE trace()
	{
		if (numOfCols != numOfRows)
			throw new InvalidOperationException(
					"The trace is only defined for square matrices");
		if (numOfCols == 0)
			throw new InvalidOperationException(
					"The trace is only be calculated for matrices with with a minimal size 1X1.");
		RE t = entries[0][0];
		for (int i = 1; i < numOfCols; i++) {
			t = extracted(t, i);
		}
		return t;

	}

	private RE extracted(RE t, int i)
	{
		return (RE) t.add(entries[i][i]);
	}

	/**
	 * Returns a matrix that is this Matrix hermitianly transposed. For almost
	 * all matrices this method is equivalent to transpose. For matrices with
	 * complex elements, use {@link ComplexMatrix}
	 * 
	 * @return hermitianly transposed matrix
	 * @exception InvalidOperationException
	 *                if the elemnts are of type Complex
	 */

	public Matrix<RE> hermitian()
	{
		if (this.get(0, 0) instanceof Complex)
			throw new InvalidOperationException(
					"This can not be used for complex matrices.");
		Matrix<RE> tmp = new Matrix<RE>(this.getCols(), this.getRows(), FACTORY);
		for (int row = 1; row <= this.getRows(); row++) {
			for (int col = 1; col <= this.getCols(); col++) {
				RE test = this.get(row, col);
				tmp.set(col, row, test);
			}
		}
		return tmp;
	}

	/**
	 * Tests two matrices for equality.
	 * 
	 * @return true if and only if the two matrices equal in all entries.
	 * @param anotherMatrix
	 */

	public boolean equals(Matrix<RE> anotherMatrix)
	{
		if (this.getRows() == anotherMatrix.getRows()
				&& this.getCols() == anotherMatrix.getCols())
		{
			for (int row = 1; row <= this.getRows(); row++) {
				for (int col = 1; col <= this.getCols(); col++) {
					if (!this.get(row, col).equals(anotherMatrix.get(row, col)))
					{
						return false;
					}
				}
			}
		}
		else {
			return false;
		}
		return true;
	}

	/**
	 * Returns the inverse of this Matrix. N.B.: In General, this operation will
	 * fail, if not all entries are FieldElements.
	 * 
	 * @return inverse Matrix
	 * @exception InvalidOperationException
	 *                if the matrix is not square.
	 */

	public Matrix<RE> inverse()
	{
		if (this.getRows() != this.getCols()) {
			throw new InvalidOperationException("Can not inverse " + getRows()
					+ "x" + getCols() + " matrices");
		}

		Matrix<RE> tmp = this.copy();

		RE zero = FACTORY.zero();
		RE one = FACTORY.one();

		RE[][] entries2 = FACTORY.getArray(tmp.getRows(), tmp.getCols());

		for (int i = 0; i < tmp.getRows(); i++) {
			for (int j = 0; j < tmp.getCols(); j++) {
				if (i == j) {
					entries2[i][j] = one;
				}
				else {
					entries2[i][j] = zero;
				}
			}
		}

		Matrix<RE> tmp2 = new Matrix<RE>(entries2);

		int colCounter = 0;

		int row = 0;
		while (row < tmp.getRows() && colCounter < tmp.getCols()) {
			row++;
			colCounter++;
			IRingElement diagonalEntry = tmp.get(row, colCounter);

			if (diagonalEntry.isZero()) {
				// search for non zero entry
				boolean found = false;
				for (int candidate = row + 1; candidate <= tmp.getRows(); candidate++)
				{
					if (!tmp.get(candidate, colCounter).isZero()) {
						tmp.swapRows(row, candidate);
						tmp2.swapRows(row, candidate);
						found = true;
						break;
					}
				}

				if (!found) {
					return null; // Because there is no inverse of this.
				}
				diagonalEntry = tmp.get(row, colCounter);
			}

			for (int j = 1; j <= tmp.getCols(); j++) {
				RE oldEntry = tmp.get(row, j);
				RE oldEntry2 = tmp2.get(row, j);
				tmp.set(row, j, oldEntry.multiply(diagonalEntry.invert()));
				tmp2.set(row, j, oldEntry2.multiply(diagonalEntry.invert()));
			}

			for (int j = 1; j <= tmp.getRows(); j++) {

				RE factor = tmp.get(j, colCounter);
				if (row == j || factor.isZero()) {
					continue;
				}

				for (int k = 1; k <= tmp.getCols(); k++) {
					RE oldEntry = tmp.get(j, k);
					RE oldEntry2 = tmp2.get(j, k);
					tmp.set(j, k, oldEntry.subtract(tmp.get(row, k).multiply(
							factor)));
					tmp2.set(j, k, oldEntry2.subtract(tmp2.get(row, k)
							.multiply(factor)));
				}
			}
		}

		return tmp2;
	}

	/**
	 * Divides this Matrix by a scalar.
	 * 
	 * @param scalar
	 */

	public void divideReplace(RE scalar)
	{
		operateReplace(scalar, DivideOperator.getInstance());
	}

	/**
	 * Divides this Matrix by another.
	 * 
	 * @param anotherMatrix
	 * @throws InvalidOperationException
	 *             if the matrices have different sizes
	 */

	public void divideReplace(Matrix<RE> anotherMatrix)
			throws InvalidOperationException
	{
		operateReplace(anotherMatrix, DivideOperator.getInstance(), "divide");
	}

	/**
	 * Multiplies this Matrix by a scalar.
	 * 
	 * @param scalar
	 */
	public void multiplyReplace(RE scalar)
	{
		operateReplace(scalar, MultiplyOperator.getInstance());
	}

	/**
	 * Multiplies this Matrix element-wise by another.
	 * 
	 * @param anotherMatrix
	 * @throws InvalidOperationException
	 *             if the matrices have different sizes
	 */

	public void multiplyReplace(Matrix<RE> anotherMatrix)
			throws InvalidOperationException
	{
		operateReplace(anotherMatrix, MultiplyOperator.getInstance(),
				"multiply");
	}

	/**
	 * Adds a scalar to this Matrix.
	 * 
	 * @param scalar
	 */

	public void addReplace(RE scalar)
	{
		operateReplace(scalar, AddOperator.getInstance());
	}

	/**
	 * Adds another matrix to this Matrix.
	 * 
	 * @param anotherMatrix
	 * @throws InvalidOperationException
	 *             if the matrices have different sizes
	 */

	public void addReplace(Matrix<RE> anotherMatrix)
	{
		operateReplace(anotherMatrix, AddOperator.getInstance(), "add");
	}

	/**
	 * Subtracts a scalar from this Matrix.
	 * 
	 * @param scalar
	 */

	public void subtractReplace(RE scalar)
	{
		operate(scalar, SubtractOperator.getInstance());
	}

	/**
	 * Subtracts another Matrix from this.
	 * 
	 * @param anotherMatrix
	 * @throws InvalidOperationException
	 *             if the matrices have different sizes
	 */
	public void subtractReplace(Matrix<RE> anotherMatrix)
	{
		operateReplace(anotherMatrix, SubtractOperator.getInstance(),
				"subtract");
	}

	/**
	 * Returns the logical AND of this Matrix with another. Elements of the
	 * result are 1 where both matrices are non-zero, and zero elsewhere.
	 * 
	 * @param anotherMatrix
	 * @return Matrix of 1's and 0's
	 * @throws InvalidOperationException
	 *             if the matrices have different sizes
	 */
	public Matrix<RE> and(Matrix<RE> anotherMatrix)
	{
		return operate(anotherMatrix, AndOperator.getInstance(), "AND");
	}

	/**
	 * Returns the logical OR of this Matrix with another. Elements of the
	 * result are 1 where both matrices are non-zero, and zero elsewhere.
	 * 
	 * @param anotherMatrix
	 * @return Matrix of 1's and 0's
	 * @throws InvalidOperationException
	 *             if the matrices have different sizes
	 */

	public Matrix<RE> or(Matrix<RE> anotherMatrix)
	{
		return operate(anotherMatrix, OrOperator.getInstance(), "OR");
	}

	/**
	 * Returns the logical negation of this Matrix. Elements of the result are 1
	 * where the matrix is zero, and one elsewhere.
	 * 
	 * @return Matrix of 1's and 0's
	 */

	public Matrix<RE> not()
	{
		return this.apply(NotOperator.getInstance());
	}

	/**
	 * Returns a Matrix containing ones where this Matrix's elements are less
	 * than those of another Matrices, and zeros elsewhere.
	 * 
	 * @param anotherMatrix
	 * @return Matrix of ones and zeros
	 * @throws InvalidOperationException
	 *             if the matrices have different sizes
	 */
	public Matrix<RE> lt(Matrix<RE> anotherMatrix)
	{
		return comparison(anotherMatrix, LessThanComparator.getInstance(), "LT");
	}

	/**
	 * Returns a Matrix containing ones where this Matrix's elements are less
	 * than a scalar, and zeros elsewhere.
	 * 
	 * @param scalar
	 * @return Matrix of ones and zeros
	 */
	public Matrix<RE> lt(RE scalar)
	{
		return comparison(scalar, LessThanComparator.getInstance());
	}

	/**
	 * Returns a Matrix containing ones where this Matrix's elements are less
	 * than or equal to those of another Matrices, and zeros elsewhere.
	 * 
	 * @param anotherMatrix
	 * @return Matrix of ones and zeros
	 * @throws InvalidOperationException
	 *             if the matrices have different sizes
	 */
	public Matrix<RE> le(Matrix<RE> anotherMatrix)
	{
		return comparison(anotherMatrix, LessThanOrEqualToComparator
				.getInstance(), "LE");
	}

	/**
	 * Returns a Matrix containing ones where this Matrix's elements are less
	 * than or equal to a scalar, and zeros elsewhere.
	 * 
	 * @param scalar
	 * @return Matrix of ones and zeros
	 */
	public Matrix<RE> le(RE scalar)
	{

		return comparison(scalar, LessThanOrEqualToComparator.getInstance());
	}

	/**
	 * Returns a Matrix containing ones where this Matrix's elements are greater
	 * than those of another Matrices, and zeros elsewhere.
	 * 
	 * @param anotherMatrix
	 * @return Matrix of ones and zeros
	 * @throws InvalidOperationException
	 *             if the matrices have different sizes
	 */
	public Matrix<RE> gt(Matrix<RE> anotherMatrix)
	{

		return comparison(anotherMatrix, GreaterThanComparator.getInstance(),
				"GT");
	}

	/**
	 * Returns a Matrix containing ones where this Matrix's elements are greater
	 * than a scalar, and zeros elsewhere.
	 * 
	 * @param scalar
	 * @return Matrix of ones and zeros
	 */
	public Matrix<RE> gt(RE scalar)
	{

		return comparison(scalar, GreaterThanComparator.getInstance());
	}

	/**
	 * Returns a Matrix containing ones where this Matrix's elements are greater
	 * than or equal to those of another Matrices, and zeros elsewhere.
	 * 
	 * @param anotherMatrix
	 * @return Matrix of ones and zeros
	 * @throws InvalidOperationException
	 *             if the matrices have different sizes
	 */
	public Matrix<RE> ge(Matrix<RE> anotherMatrix)
	{
		return comparison(anotherMatrix, GreaterThanOrEqualToComparator
				.getInstance(), "GE");
	}

	/**
	 * Returns a Matrix containing ones where this Matrix's elements are greater
	 * than or equal to a scalar, and zeros elsewhere.
	 * 
	 * @param scalar
	 * @return Matrix of ones and zeros
	 */
	public Matrix<RE> ge(RE scalar)
	{

		return comparison(scalar, GreaterThanOrEqualToComparator.getInstance());
	}

	/**
	 * Returns a Matrix containing ones where this Matrix's elements are equal
	 * to those of another Matrices, and zeros elsewhere.
	 * 
	 * @param anotherMatrix
	 * @return Matrix of ones and zeros
	 * @throws InvalidOperationException
	 *             if the matrices have different sizes
	 */
	public Matrix<RE> eq(Matrix<RE> anotherMatrix)
	{

		return comparison(anotherMatrix, EqualToComparator.getInstance(), "EQ");
	}

	/**
	 * Returns a Matrix containing ones where this Matrix's elements are equal
	 * to a scalar, and zeros elsewhere.
	 * 
	 * @param scalar
	 * @return Matrix of ones and zeros
	 */
	public Matrix<RE> eq(RE scalar)
	{

		return comparison(scalar, EqualToComparator.getInstance());
	}

	/**
	 * Returns a Matrix containing ones where this Matrix's elements are not
	 * equal to those of another Matrices, and zeros elsewhere.
	 * 
	 * @param anotherMatrix
	 * @return Matrix of ones and zeros
	 * @throws InvalidOperationException
	 *             if the matrices have different sizes
	 */
	public Matrix<RE> ne(Matrix<RE> anotherMatrix)
	{

		return comparison(anotherMatrix, NotEqualToComparator.getInstance(),
				"NE");
	}

	/**
	 * Returns a Matrix containing ones where this Matrix's elements are not
	 * equal to a scalar, and zeros elsewhere.
	 * 
	 * @param scalar
	 * @return Matrix of ones and zeros
	 */
	public Matrix<RE> ne(RE scalar)
	{

		return comparison(scalar, NotEqualToComparator.getInstance());
	}

	/**
	 * Sets this Matrix to the result of applying a specified function to every
	 * element of this Matrix. New functions can be applied to a Matrix by
	 * sub-classing the abstract <tt>MonadicOperator</tt> class.
	 * 
	 * @param fun
	 *            the function to apply
	 */
	public void applyReplace(MonadicOperator<RE> fun)
	{
		for (int i = 1; i <= this.getRows(); i++) {
			for (int j = 1; j <= this.getCols(); j++) {
				this.set(i, j, fun.apply(this.get(i, j)));
			}
		}
	}

	/**
	 * Returns the result of applying a specified function to every element of
	 * this Matrix. New functions can be applied to a Matrix by subclassing the
	 * abstract <tt>MonadicOperator</tt> class.
	 * 
	 * @param monadicOperator
	 *            the function to apply
	 * @return result of applying <tt>fun</tt> to this Matrix
	 */
	public Matrix<RE> apply(
			MonadicOperator<? extends IRingElement> monadicOperator)
	{

		Matrix<RE> matrix = new Matrix<RE>(this.getRows(), this.getCols(),
				FACTORY);

		for (int i = 1; i <= matrix.getRows(); i++) {
			for (int j = 1; j <= this.getCols(); j++) {
				matrix.set(i, j, monadicOperator.apply(this.get(i, j)));
			}
		}

		return matrix;
	}

	/**
	 * Sets this Matrix to the result of applying a specified function to
	 * elements of this Matrix and another's. New functions can be applied to a
	 * Matrix by sub-classing the abstract <tt>DyadicOperator</tt> class.
	 * 
	 * @param anotherMatrix
	 * @param fun
	 *            the function to apply
	 */
	public void applyReplace(Matrix<RE> anotherMatrix, DyadicOperator<RE> fun)
	{

		check_sizes(anotherMatrix, fun.getClass().getName());

		for (int i = 1; i <= this.getRows(); i++) {
			for (int j = 1; j <= this.getCols(); j++) {
				this.set(i, j, fun.apply(this.get(i, j), anotherMatrix
						.get(i, j)));
			}
		}
	}

	/**
	 * Returns the result of applying a specified function to the elements of
	 * this Matrix and another. New functions can be applied to a Matrix by
	 * subclassing the abstract <tt>DyadicOperator</tt> class.
	 * 
	 * @param anotherMatrix
	 * @param fun
	 *            the function to apply
	 * @return result of applying <tt>fun</tt> to the two Matrices
	 */
	public Matrix<RE> apply(Matrix<RE> anotherMatrix, DyadicOperator<RE> fun)
	{

		check_sizes(anotherMatrix, fun.getClass().getName());

		Matrix<RE> matrix = new Matrix<RE>(this.getRows(), this.getCols(),
				FACTORY);

		for (int i = 1; i <= matrix.getRows(); i++) {
			for (int j = 1; j <= this.getCols(); j++) {
				matrix.set(i, j, fun.apply(this.get(i, j), anotherMatrix.get(i,
						j)));
			}
		}
		return matrix;
	}

	/**
	 * Sets this Matrix to the result of applying a specified function to
	 * elements of this Matrix and a scalar. New functions can be applied to a
	 * Matrix by subclassing the abstract <tt>DyadicOperator</tt> class.
	 * 
	 * @param scalar
	 * @param fun
	 *            the function to apply
	 */
	public void applyReplace(RE scalar, DyadicOperator<RE> fun)
	{
		for (int i = 1; i <= this.getRows(); i++) {
			for (int j = 1; j <= this.getCols(); j++) {
				this.set(i, j, fun.apply(this.get(i, j), scalar));
			}
		}
	}

	/**
	 * Returns the result of applying a specified function to the elements of a
	 * this Matrix a scalar. New functions can be applied to a Matrix by
	 * subclassing the abstract <tt>DyadicOperator</tt> class.
	 * 
	 * @param scalar
	 * @param fun
	 *            the function to apply
	 * @return result of applying <tt>fun</tt> to the Matrix and scalar
	 */
	public Matrix<RE> apply(RE scalar, DyadicOperator<RE> fun)
	{
		Matrix<RE> matrix = new Matrix<RE>(this.getRows(), this.getCols(),
				FACTORY);

		for (int i = 1; i <= matrix.getRows(); i++) {
			for (int j = 1; j <= this.getCols(); j++) {
				matrix.set(i, j, fun.apply(this.get(i, j), scalar));
			}
		}

		return matrix;
	}

	/**
	 * Returns the element-wise product of this Matrix and another.
	 * 
	 * @param anotherMatrix
	 * @return this .* anotherMatrix
	 * @throws InvalidOperationException
	 *             if the matrices have different sizes
	 */
	public Matrix<RE> arrayMultiply(Matrix<RE> anotherMatrix)
			throws InvalidOperationException
	{
		return operate(anotherMatrix, MultiplyOperator.getInstance(),
				"arrayMultiply");
	}

	/**
	 * Sets all entries to a RingElement.
	 * 
	 * @param newEntry
	 *            the RingElement
	 */

	public void setAll(RE newEntry)
	{
		for (int i = 1; i <= this.getRows(); ++i) {
			for (int j = 1; j <= this.getCols(); ++j) {
				this.set(i, j, newEntry);
			}
		}
	}

	/**
	 * Computes the sum over all elements of this Matrix.
	 * 
	 * @return the sum
	 */
	public RE sum()
	{
		return reduce(new SumReduction<RE>());
	}

	/**
	 * Computes the mean over all elements of this Matrix. N.B.: In General,
	 * this operation will fail, if not all entries are FieldElements.
	 * 
	 * @return the mean
	 */
	public RE mean()
	{
		return (RE) sum().multiply(FACTORY.get(getRows() * getCols()).invert());
	}

	/**
	 * Computes the smallest value of any element in this Matrix.
	 * 
	 * @return the smallest value
	 */
	public RE min()
	{
		return reduce(new MinReduction<RE>());
	}

	/**
	 * Computes the largest value of any element in this Matrix.
	 * 
	 * @return the largest value
	 */
	public RE max()
	{
		return reduce(new MaxReduction<RE>());
	}

	/**
	 * Computes the sum over the rows of this matrix.
	 * 
	 * @return the sum
	 */
	public Vector<RE> sumRows()
	{
		Vector<RE> sum = getRow(1);
		for (int i = 2; i <= getRows(); ++i) {
			sum.addReplace(getRow(i));
		}
		return sum;
	}

	/**
	 * Computes the sum over the columns of this matrix.
	 * 
	 * @return the sum
	 */
	public Vector<RE> sumCols()
	{
		return this.transpose().sumRows();
	}

	/**
	 * Computes the mean over the rows of this Matrix.
	 * 
	 * @return the mean
	 */
	public Vector<RE> meanRows()
	{
		return this.sumRows().divide(FACTORY.get(this.getRows()));
	}

	/**
	 * Computes the mean over the columns of this Matrix.
	 * 
	 * @return the mean
	 */
	public Vector<RE> meanCols()
	{
		return this.sumCols().divide(FACTORY.get(this.getCols()));
	}

	/**
	 * Returns a new 1xN Vector made from the N elements of this Matrix. Matrix
	 * should have 1 row.
	 * 
	 * @return the Matrix
	 * @throws InvalidOperationException
	 *             if number of rows not equal to one
	 */
	public Vector<RE> toVector() throws InvalidOperationException
	{
		if (getRows() != 1) {
			String err = "Cannot convert multi-row Matrix to Vector";
			throw new InvalidOperationException(err);
		}
		return getRow(1);
	}

	/**
	 * apply an operator on this and a second matrix
	 * 
	 * @param matrix2
	 *            the second matrix
	 * @param dyadicOperator
	 *            an operator
	 * @param funName
	 *            an informal name for the operator.
	 * @return Matrix resulting from operation on two others
	 */
	private Matrix<RE> operate(Matrix<RE> matrix2,
			DyadicOperator<IRingElement> dyadicOperator, String funName)
	{
		check_sizes(matrix2, funName);

		Matrix<RE> matrix3 = new Matrix<RE>(numOfRows, numOfCols, FACTORY);
		for (int i = 1; i <= matrix3.getRows(); i++) {
			for (int j = 1; j <= matrix3.getCols(); j++) {
				matrix3.set(i, j, dyadicOperator.apply(get(i, j), matrix2.get(
						i, j)));
			}
		}
		return matrix3;
	}

	/**
	 * apply an operator on this matrix and a scalar
	 * 
	 * @param scalar
	 * @param dyadicOperator
	 *            an operator
	 * @return Matrix resulting from operation on Matrix and scalar
	 */
	private Matrix<RE> operate(RE scalar,
			DyadicOperator<IRingElement> dyadicOperator)
	{
		Matrix<RE> matrix2 = copy();

		for (int i = 1; i <= getRows(); ++i) {
			for (int j = 1; j <= getCols(); ++j) {
				matrix2.set(i, j, dyadicOperator.apply(get(i, j), scalar));
			}
		}

		return matrix2;
	}

	// set elements of this Matrix to result of operation on them and
	// another's
	private void operateReplace(Matrix<RE> matrix,
			DyadicOperator<IRingElement> dyadicOperator, String funName)
	{
		check_sizes(matrix, funName);
		for (int i = 1; i <= getRows(); ++i) {
			for (int j = 1; j <= getCols(); ++j) {
				this.set(i, j, dyadicOperator
						.apply(get(i, j), matrix.get(i, j)));
			}
		}
	}

	// set elements of this Matrix to result of operation on them and a
	// scalar
	private void operateReplace(RE scalar,
			DyadicOperator<IRingElement> dyadicOperator)
	{
		for (int i = 1; i <= this.getRows(); ++i) {
			for (int j = 1; j <= this.getCols(); ++j) {
				this.set(i, j, dyadicOperator.apply(this.get(i, j), scalar));
			}
		}
	}

	// return the result of comparing this Matrix with another (ones where
	// comparison succeeds, zeros where it fails)
	private Matrix<RE> comparison(Matrix<RE> anotherMatrix,
			FEComparator<IRingElement> feComparator, String compName)
	{
		check_sizes(anotherMatrix, compName);

		Matrix<RE> a = new Matrix<RE>(this.getRows(), this.getCols(), FACTORY);

		for (int i = 1; i <= this.getRows(); ++i) {
			for (int j = 1; j <= this.getCols(); ++j) {
				RE entry = this.get(i, j);
				boolean success = feComparator.compare(entry, anotherMatrix
						.get(i, j));
				RE result = success ? FACTORY.one() : FACTORY.zero();
				a.set(i, j, result);
			}
		}

		return a;
	}

	// return the result of comparing this Matrix with a scalar (ones where
	// comparison succeeds, zeros where it fails)
	private Matrix<RE> comparison(RE scalar,
			FEComparator<IRingElement> feComparator)
	{
		Matrix<RE> a = new Matrix<RE>(this.getRows(), this.getCols(), FACTORY);

		for (int i = 1; i <= this.getRows(); ++i) {
			for (int j = 1; j <= this.getCols(); ++j) {
				RE entry = this.get(i, j);
				boolean success = feComparator.compare(entry, scalar);
				RE result = success ? FACTORY.one() : FACTORY.zero();
				a.set(i, j, result);
			}
		}

		return a;
	}

	// general size checking for two-matrix operations
	private void check_sizes(Matrix<RE> b, String op)
			throws InvalidOperationException
	{

		if (numOfRows != b.getRows() || numOfCols != b.getCols()) {
			throw new InvalidOperationException("Tried " + op + "on \n" + this
					+ "\n and \n" + b + "Not correct format!");
		}
	}

	// generic method for sum, min, max
	private RE reduce(Reduction<RE> r)
	{
		r.init(this.get(1, 1));
		for (int i = 1; i <= this.getRows(); i++) {
			for (int j = 1; j <= this.getCols(); j++) {
				if (i != 1 || j != 1) {
					r.track(this.get(i, j));
				}
			}
		}
		return r.reducedValue;
	}

	/**
	 * Calculate the order o of this matrix: the integer number o for which m^o
	 * is the identity matrix.
	 * 
	 * @param max
	 *            The maximal order to test for.
	 * @return <UL>
	 *         <LI>the order or
	 *         <LI>-1 if the rank is too low or the determinant is not +-1, or
	 *         <li>-2 if the order exceeds max. This is likely to occur if the
	 *         elements in this matrix are some sort of floating point.
	 * @exception InvalidOperationException
	 *                if the matrix is not square.
	 */
	public int order(final int max)
	{
		if (this.numOfCols != this.numOfRows)
			throw new InvalidOperationException("matrix is not square");

		if (this.rank() != this.getCols()) return -1;
		RE d = this.det();
		if (!(d.equals(FACTORY.one()) || d.equals(FACTORY.m_one()))) return -1;

		Matrix<RE> m = this.copy();

		int i = 1;
		while (!m.isIdentity() && i <= max) {
			i++;
			m = m.multiply(this);
		}
		if (i >= max) return -2;
		return i;
	}

	/**
	 * @return the order of a matrix up to order Integer.MAX_VALUE
	 * @see #order
	 */
	public int order()
	{
		return order(Integer.MAX_VALUE);
	}

	/**
	 * Determine whether this matrix is a identity matrix
	 * 
	 * @return true if it is.
	 * @exception InvalidOperationException
	 *                is matrix is not square.
	 */
	public boolean isIdentity()
	{
		if (numOfCols != numOfRows)
			throw new InvalidOperationException("this is not a square matrix");

		RE zero = FACTORY.zero();
		RE one = FACTORY.one();
		for (int c = 1; c <= numOfCols; c++) {
			for (int r = 1; r <= numOfRows; r++) {
				if (c == r) {
					if (!get(r, c).equals(one)) return false;
				}
				else if (!get(r, c).equals(zero)) return false;
			}
		}
		return true;
	}

	/**
	 * Calculate the hash code from the top-left corner, 3x3 matrix (or smaller)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		int hc = 0;
		for (int x = 0; x < entries.length && x < 3; x++) {
			for (int y = 0; y < entries[0].length && y <= 3; y++) {
				hc ^= entries[x][y].hashCode();
				hc <<= 1;
			}
		}
		return hc;
	}

	/**
	 * Set a given column in the matrix to "value"
	 * 
	 * @param matrixCol
	 *            the row to be changed.
	 * @param value
	 */
	public void setCol(final int matrixCol, final RE value)
	{
		for (int row = 0; row < entries.length; row++)
			entries[row][matrixCol - 1] = value;
	}

	/**
	 * copy a given row from another matrix into this matrix
	 * 
	 * @param toRow
	 * @param matrix
	 * @param fromRow
	 */
	public void setRowFromMatrix(final int toRow, final Matrix<RE> matrix,
			final int fromRow)
	{
		if (numOfCols != matrix.numOfCols)
			throw new InvalidOperationException(
					"the matrices have different number of columns.");
		for (int col = 0; col < entries[0].length; col++) {
			entries[toRow - 1][col] = matrix.entries[fromRow - 1][col];
		}
	}

	/**
	 * copy a given column from another matrix into this matrix
	 * 
	 * @param toCol
	 * @param matrix
	 * @param fromCol
	 */
	public void setColFromMatrix(final int toCol, final Matrix<RE> matrix,
			final int fromCol)
	{
		if (numOfCols != matrix.numOfCols)
			throw new InvalidOperationException(
					"the matrices have different number of rows.");
		for (int row = 0; row < entries.length; row++) {
			entries[row][toCol - 1] = matrix.entries[row][fromCol - 1];
		}
	}

	/**
	 * give accesss to the factory
	 * 
	 * @return the factory given during the creation of this matrix
	 */
	public IRingElementFactory<RE> getFactory()
	{
		return FACTORY;
	}

	/**
	 * @return the characteristic polynomial for this matrix.
	 * @throws InvalidOperationException
	 *             if this is not a square matrix
	 */
	public Polynomial<RE> characteristicPolynomial()
	{
		if (this.getRows() != this.getCols()) {
			throw new InvalidOperationException(
					"Only sqare-matrices have a characteristic polynomial!");
		}
		PolynomialFactory<RE> rationalPolyFactory = PolynomialFactory
				.getFactory(this.getFactory());

		Map<Integer, IRingElement> coeffs = new HashMap<Integer, IRingElement>();
		coeffs.put(1, this.getFactory().one());

		Polynomial<RE> x = rationalPolyFactory.get(coeffs);

		LinAlgFactory<Polynomial<RE>> factory = new LinAlgFactory<Polynomial<RE>>(
				rationalPolyFactory);
		Matrix<Polynomial<RE>> e = factory.identity(this.getRows());
		Polynomial<RE>[][] convertedEntries = new Polynomial[this.getRows()][this
				.getRows()];
		for (int i = 1; i <= this.getRows(); i++) {
			for (int j = 1; j <= this.getCols(); j++) {
				convertedEntries[i - 1][j - 1] = rationalPolyFactory.get(this
						.get(i, j));
			}
		}
		Matrix<Polynomial<RE>> a = new Matrix<Polynomial<RE>>(convertedEntries);

		return e.multiply(x).subtract(a).det();
	}

	/**
	 * @return the minimal polynomial
	 */
	public Polynomial<RE> minimalPolynomial()
	{
		return this.characteristicPolynomial().minimalPolynomial();
	}
}
