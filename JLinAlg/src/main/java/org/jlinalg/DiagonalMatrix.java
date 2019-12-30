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
 * This class represents a DiagonalMatrix.
 * 
 * @author Lei Chen, Veronika Ortner, Safak Oekmen, Andreas Keilhauer
 * @param <RE>
 *            the type of the domain for this matrix.
 */

class DiagonalMatrix<RE extends IRingElement<RE>>
		extends Matrix<RE>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor1: DiagonalMatrix arising from array containing diagonal
	 * Elements.
	 * 
	 * @param diagElements
	 *            array containing diagonal Elements.
	 * @throws InvalidOperationException
	 *             see {@link Matrix#Matrix(int, int, IRingElementFactory)}
	 */
	public DiagonalMatrix(RE[] diagElements) throws InvalidOperationException
	{
		super(diagElements.length, diagElements.length, diagElements[0]
				.getFactory());

		int arraysize = diagElements.length;

		for (int i = 0; i < arraysize; i++)
			for (int j = 0; j < arraysize; j++) {
				if (i != j)
					// zero Element of RingElement
					entries[i][j] = FACTORY.zero();
				else
					entries[i][j] = diagElements[i];
			}
	}

	/**
	 * Constructor2: DiagonalMatrix in given size. Diagonal Elements are set
	 * diagElement.
	 * 
	 * @param size
	 *            size of DiagonalMatrix to be constructed
	 * @param diagElement
	 *            value of diagonal Elements
	 */

	public DiagonalMatrix(int size, RE diagElement)
	{
		super(size, size, diagElement.getFactory());

		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				if (i == j)
					entries[i][j] = diagElement;
				else
					entries[i][j] = FACTORY.zero();
			}
	}

	/*
	 * Methods:
	 * 
	 * ADOPTED METHODS: getRows,getCols,getRow,getCol,withoutRow,
	 * withoutCol,insertRow,insertCol,toString, isZeroRow,isZeroCol,hermitian
	 * 
	 * TRANSCRIBED METHODS: getEntry,setEntry,setRow,setCol,add,
	 * subtract,multiply,copy,det,swapRows,
	 * swapCols,rank,inverse,transpose,equals, gausselim,gaussjord
	 * 
	 * NEW METHODS:
	 * getDiagElement,setDiagElement,setRC,contZeroRow,getDiagonalElements
	 * toMatrix
	 */

	/**
	 * getEntry: Getting particular Element at a given position in a
	 * DiagonalMatrix
	 * 
	 * @param rowIndex
	 *            index of row in DiagonalMatrix
	 * @param colIndex
	 *            index of col in DiagonalMatrix
	 * @return RingElement
	 */

	@Override
	public RE get(int rowIndex, int colIndex)
	{
		if (rowIndex != colIndex) return FACTORY.zero();
		// else
		return getDiagElement(rowIndex);
	}

	/**
	 * setEntry: Setting particular Element at given position in DiagonalMatrix
	 * 
	 * @param rowIndex
	 *            index of row in DiagonalMatrix
	 * @param colIndex
	 *            index of col in DiagonalMatrix
	 * @param newEntry
	 *            RingElement to be set
	 */
	@Override
	public void set(int rowIndex, int colIndex, RE newEntry)
			throws InvalidOperationException
	{
		if (rowIndex != colIndex)
			throw new InvalidOperationException(
					"Tried to set non-diagonal entry.");
		setDiagElement(rowIndex, newEntry);
	}

	/**
	 * @param position
	 *            row or column Index of RingElement
	 * @return RingElement
	 * @throws InvalidOperationException
	 */
	public RE getDiagElement(int position) throws InvalidOperationException
	{
		int size = this.numOfRows;

		if ((position > size) || (position < 0))
			throw new InvalidOperationException(
					"Tried to get Element at Position " + position
							+ ". Size of Matrix:\n " + size);
		return this.entries[position - 1][position - 1];
	}

	/**
	 * @param position
	 *            row or column index of DiagonalMatrix
	 * @param elem
	 *            RingElement to be set
	 * @throws InvalidOperationException
	 *             if the position does not exist.
	 */
	public void setDiagElement(int position, RE elem)
			throws InvalidOperationException
	{
		int size = this.numOfRows;

		if ((position > size) || (position < 0))
			throw new InvalidOperationException(
					"Tried to set Element at Position\n" + position
							+ ". Size of Matrix:\n " + size);
		// else
		this.entries[position - 1][position - 1] = elem;
	}

	/**
	 * setRow/setCol replacing row or column with given vector
	 * 
	 * @param rowIndex
	 *            index of row to be replaced
	 * @param vector
	 *            vector to be set
	 */

	@Override
	public void setRow(int rowIndex, Vector<RE> vector)
	{
		setRC(rowIndex, vector);
	}

	/**
	 * @param colIndex
	 *            index of col to be replaced
	 * @param vector
	 *            vector to be set
	 */

	@Override
	public void setCol(int colIndex, Vector<RE> vector)
	{
		setRC(colIndex, vector);
	}

	/**
	 * @param rcIndex
	 *            row or column index of DiagonalMatrix
	 * @param vector
	 *            vector to be set
	 * @throws InvalidOperationException
	 */
	private void setRC(int rcIndex, Vector<RE> vector)
			throws InvalidOperationException
	{
		int vectorsize = vector.length();

		int size = this.numOfRows;

		if (size != vectorsize)
			throw new InvalidOperationException(
					"Tried to set a row with a voctor of invalid size.");

		for (int i = 1; i <= vectorsize; i++) {
			if (i != rcIndex) {
				if (!(vector.getEntry(i).equals(FACTORY.zero())))
					throw new InvalidOperationException(
							"Tried to set a non-diagonal entry to a value different from zero.");
			}
		}
		setDiagElement(rcIndex, vector.getEntry(rcIndex));
	}

	/**
	 * add matrices
	 * 
	 * @param diagMatrix
	 *            diagonal Matrix to be added
	 * @return sum of diagonal matrices
	 * @throws InvalidOperationException
	 */
	public DiagonalMatrix<RE> add(DiagonalMatrix<RE> diagMatrix)
			throws InvalidOperationException
	{

		if (this.numOfCols != diagMatrix.numOfRows)
			throw new InvalidOperationException("Tried to sum up \n" + this
					+ "and \n" + diagMatrix + "No correct format!");

		DiagonalMatrix<RE> tmp = copy();

		for (int i = 1; i <= tmp.getRows(); i++)
			tmp.set(i, i, this.get(i, i).add(diagMatrix.get(i, i)));

		return tmp;
	}

	/**
	 * add square matrix not being DiagonalMatrix
	 * 
	 * @param matrix
	 *            matrix to be added to this DiagonalMatrix
	 * @return addition of this DiagonalMatrix plus matrix
	 */
	@Override
	public Matrix<RE> add(Matrix<RE> matrix) throws InvalidOperationException
	{

		// size of this: axa; size of matrix: cxd or axd
		if ((this.numOfCols != matrix.numOfRows)
				|| (this.numOfCols != matrix.numOfCols))
			throw new InvalidOperationException("Tried to sum up \n" + this
					+ "and \n" + matrix + "No correct format!");

		Matrix<RE> tmp = matrix.copy();

		for (int i = 1; i <= tmp.numOfRows; i++)
			tmp.set(i, i, this.get(i, i).add(matrix.get(i, i)));

		return tmp;
	}

	/**
	 * Subtract a matrix
	 * 
	 * @param diagMatrix
	 *            matrix to be subtracted from this DiagonalMatrix
	 * @return Difference of this DiagonalMatrix and diagMatrix
	 * @throws InvalidOperationException
	 *             if the sizes of the matrices disagree.
	 */
	public DiagonalMatrix<RE> subtract(DiagonalMatrix<RE> diagMatrix)
			throws InvalidOperationException
	{
		// size of each DiagonalMatrix is different
		if (this.numOfCols != diagMatrix.numOfRows)
			throw new InvalidOperationException("Tried to subtract \n"
					+ diagMatrix + "from \n" + this + "No correct format!");

		DiagonalMatrix<RE> tmp = new DiagonalMatrix<>(this.numOfRows, FACTORY
				.zero());

		for (int i = 1; i <= tmp.numOfRows; i++)
			tmp.set(i, i, this.get(i, i).subtract(diagMatrix.get(i, i)));
		return tmp;

	}

	/**
	 * Subtract a conventional matrix
	 * 
	 * @param matrix
	 *            matrix to be subtracted from this DiagonalMatrix
	 * @return Difference between this DiagonalMatrix and Matrix
	 */
	@Override
	public Matrix<RE> subtract(Matrix<RE> matrix)
			throws InvalidOperationException
	{

		if (this.numOfCols != matrix.numOfRows)
			throw new InvalidOperationException("Tried to subtract \n" + matrix
					+ "from \n" + this + "No correct format!");

		Matrix<RE> tmp = new Matrix<>(this.numOfRows, matrix.numOfCols,
				FACTORY);

		for (int i = 1; i <= tmp.numOfRows; i++)
			for (int j = 1; j <= tmp.numOfCols; j++)
				tmp.set(i, j, this.get(i, j).subtract(matrix.get(i, j)));

		return tmp;
	}

	/**
	 * Returns a DiagonalMatrix that is this DiagonalMatrix multiplied with a
	 * scalar
	 * 
	 * @param scalar
	 *            scalar RingElement that is multiplied to this DiagonalMatrix
	 *            scalar * DiagonalMatrix
	 * @return DiagonalMatrix
	 */
	@Override
	public DiagonalMatrix<RE> multiply(RE scalar)
	{
		int size = this.getRows();
		DiagonalMatrix<RE> diagMatrix = new DiagonalMatrix<>(size, FACTORY
				.zero());

		for (int i = 0; i < size; i++)
			diagMatrix.entries[i][i] = this.entries[i][i].multiply(scalar);
		return diagMatrix;
	}

	/**
	 * Returns vector that is the product of this DiagonalMatrix and given
	 * vector. Multiplication from right.
	 * 
	 * @param vector
	 *            vector that is multiplied to this DiagonalMatrix this * vector
	 * @return the result.
	 */
	@Override
	public Vector<RE> multiply(Vector<RE> vector)
			throws InvalidOperationException
	{
		if (this.numOfCols != vector.length()) {
			throw new InvalidOperationException("Tried to multiply \n" + this
					+ "and \n" + vector + "No correct format!");
		}

		RE[] result = vector.getEntry(1).getFactory().getArray(numOfRows);

		for (int i = 1; i <= this.getRows(); i++)
			result[i - 1] = entries[i - 1][i - 1].multiply(vector.getEntry(i));

		Vector<RE> resultVector = new Vector<>(result);

		return resultVector;
	}

	/**
	 * Calculate the product of this DiagonalMatrix and another Matrix.
	 * Multiplication from right
	 * 
	 * @param matrix
	 *            matrix that is multiplied to this DiagonalMatrix "this *
	 *            matrix"
	 * @return Matrix
	 */
	@Override
	public Matrix<RE> multiply(Matrix<RE> matrix)
			throws InvalidOperationException
	{

		if (this.numOfCols != matrix.getRows()) {
			throw new InvalidOperationException("Tried to multiply \n" + this
					+ "and \n" + matrix + "No correct format!");

		}

		Matrix<RE> resultMatrix = new Matrix<>(this.getRows(), matrix
				.getCols(), FACTORY);

		for (int i = 1; i <= matrix.numOfRows; i++)
			for (int j = 1; j <= matrix.numOfCols; j++)
				resultMatrix.set(i, j, this.entries[i - 1][i - 1]
						.multiply(matrix.get(i, j)));

		return resultMatrix;

	}

	/**
	 * Calculate the product of this DiagonalMatrix and another DiagonalMatrix.
	 * Multiplication from right
	 * 
	 * @param diagMatrix
	 *            DiagonalMatrix that is multiplied to this DiagonalMatrix "this
	 *            * diagMatrix"
	 * @return DiagonalMatrix
	 * @throws InvalidOperationException
	 *             if the matrix sizes differ.
	 */
	public DiagonalMatrix<RE> multiply(DiagonalMatrix<RE> diagMatrix)
			throws InvalidOperationException
	{

		if (this.numOfCols != diagMatrix.getRows())
			throw new InvalidOperationException("Tried to multiply \n" + this
					+ "and \n" + diagMatrix + "No correct format!");

		DiagonalMatrix<RE> result = new DiagonalMatrix<>(this.getRows(),
				FACTORY.zero());

		for (int i = 1; i <= diagMatrix.numOfRows; i++)
			result.setDiagElement(i, this.entries[i - 1][i - 1]
					.multiply(diagMatrix.get(i, i)));

		return result;
	}

	/**
	 * Create a copy of this matrix
	 * 
	 * @return DiagonalMatrix
	 */
	@Override
	public DiagonalMatrix<RE> copy()
	{
		DiagonalMatrix<RE> result = new DiagonalMatrix<>(this.getRows(),
				FACTORY.zero());

		for (int i = 0; i < getRows(); i++)
			result.entries[i][i] = this.entries[i][i];

		return result;
	}

	/**
	 * calculate the determinant of this matrix (that is the product over the
	 * trace).
	 * 
	 * @return RingElement
	 */
	@Override
	public RE det()
	{
		RE result = FACTORY.one();

		RE[] diagonalElements = getDiagonalElements();

		for (int i = 0; i < getRows(); i++)
			result = result.multiply(diagonalElements[i]);

		return result;
	}

	/**
	 * @exception InvalidOperationException
	 *                always as swapping of rows would destroy the property of
	 *                being diagonal.
	 */
	@Override
	public void swapRows(@SuppressWarnings("unused") int rowIndex1,
			@SuppressWarnings("unused") int rowIndex2)
			throws InvalidOperationException
	{
		throw new InvalidOperationException(
				"swapRows is not allowed in diagonal matrices.");
	}

	/**
	 * @exception InvalidOperationException
	 *                always as swapping of rows would destroy the property of
	 *                being diagonal.
	 */
	@Override
	public void swapCols(@SuppressWarnings("unused") int colIndex1,
			@SuppressWarnings("unused") int colIndex2)
			throws InvalidOperationException
	{

		throw new InvalidOperationException(
				"swapCols is not allowed in diagonal matrices.");
	}

	/**
	 * calculate the rank of this matrix
	 */
	@Override
	public int rank()
	{

		int nonZeroRows = 0;

		for (int i = 0; i < this.numOfRows; i++) {
			if (!(isZeroRow(i + 1))) nonZeroRows++;
		}

		return nonZeroRows;
	}

	/**
	 * @return true if some row contains zeros only
	 */

	public boolean contZeroRow()
	{

		for (int i = 1; i <= numOfRows; i++) {
			if (isZeroRow(i)) return true;
		}
		return false;
	}

	/**
	 * @return an array containing the diagonal.
	 */
	public RE[] getDiagonalElements()
	{
		RE[] diagElements = FACTORY.getArray(numOfRows);

		for (int i = 0; i < numOfRows; i++)
			diagElements[i] = entries[i][i];

		return diagElements;
	}

	/**
	 * @return the inverse of this matrix
	 *         N.B.: In General, this operation will fail, if not all entries
	 *         are FieldElements.
	 */
	@Override
	public Matrix<RE> inverse() throws InvalidOperationException
	{

		if (this.contZeroRow())
			throw new InvalidOperationException("Not invertible.");

		DiagonalMatrix<RE> reverse = new DiagonalMatrix<>(this.numOfRows,
				FACTORY.zero());
		for (int i = 1; i <= numOfRows; i++) {
			RE re = this.get(i, i).invert();
			reverse.set(i, i, re);
		}
		return reverse;
	}

	/*
	 * The transposed matrix is the matrix itself.
	 * 
	 * @return a copy of this matrix
	 */
	@Override
	public Matrix<RE> transpose()
	{
		return this.copy();
	}

	/**
	 * The eigenvalues of a diagonal matrix are all the elements on its
	 * diagonal.
	 */
	@Override
	public Vector<RE> eig()
	{
		Vector<RE> result = new Vector<>(this.getRows(), this.getFactory());
		for (int i = 1; i <= this.getRows(); i++) {
			result.set(i, this.get(i, i));
		}
		return result;
	}

	/**
	 * @param anotherDiagMatrix
	 *            Matrix that is compared with this DiagonalMatrix
	 * @return true if the matrices are identical
	 */

	public boolean equals(DiagonalMatrix<RE> anotherDiagMatrix)
	{
		if (anotherDiagMatrix.getRows() != this.getRows()) return false;

		for (int i = 0; i < numOfRows; i++) {
			if (entries[i][i] != anotherDiagMatrix.entries[i][i]) return false;
		}
		return true;
	}

	/**
	 * @return a copy of this matrix
	 */

	@Override
	public Matrix<RE> gaussjord()
	{
		return this.copy();
	}

	/**
	 * @return DiagonalMatrix
	 */

	@Override
	public Matrix<RE> gausselim()
	{
		return this.copy();
	}

	/**
	 * @return Matrix
	 */

	public Matrix<RE> toMatrix()
	{

		Matrix<RE> result = new Matrix<>(this.numOfRows, this.numOfCols,
				FACTORY);

		for (int i = 1; i <= this.numOfRows; i++)
			for (int j = 1; j <= this.numOfCols; j++)
				result.set(i, j, this.get(i, j));

		return result;

	}

}
