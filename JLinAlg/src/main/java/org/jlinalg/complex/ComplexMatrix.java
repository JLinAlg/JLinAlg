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
package org.jlinalg.complex;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.InvalidOperationException;
import org.jlinalg.Matrix;
import org.jlinalg.Vector;

/**
 * An explicit implementation of <code>Matrix&lt;Complex&gt;</code>in order to
 * cater for the
 * special behaviour of the method {@link #hermitian()}
 * 
 * @author ???, Georg Thimm
 */
public class ComplexMatrix
		extends Matrix<Complex>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ComplexMatrix(Complex[] theEntries, int numberOfRows)
			throws InvalidOperationException
	{
		super(theEntries, numberOfRows);
	}

	public ComplexMatrix(Complex[][] theEntries, int rows, int cols)
	{
		super(theEntries, rows, cols);
	}

	public ComplexMatrix(Complex[][] theEntries)
			throws InvalidOperationException
	{
		super(theEntries);
	}

	public ComplexMatrix(int numberOfRows, int numberOfCols,
			IRingElementFactory<Complex> factory)
	{
		super(numberOfRows, numberOfCols, factory);
	}

	public ComplexMatrix(Vector<Complex>[] rowVectors)
			throws InvalidOperationException
	{
		super(rowVectors);
	}

	/**
	 * Returns a matrix that is this Matrix hermitianly transposed. For almost
	 * all matrices this method is equivalent to transpose. But in case of
	 * complex number entries the matrix will be transposed and all entries will
	 * be conjugated as well.
	 * 
	 * @return hermitianly transposed matrix
	 */
	@Override
	public Matrix<Complex> hermitian()
	{
		Matrix<Complex> tmp = new Matrix<>(this.getCols(), this
				.getRows(), FACTORY);
		for (int row = 1; row <= this.getRows(); row++) {
			for (int col = 1; col <= this.getCols(); col++) {
				Complex el = get(row, col);
				tmp.set(col, row, el.conjugate());
			}
		}
		return tmp;
	}

}
