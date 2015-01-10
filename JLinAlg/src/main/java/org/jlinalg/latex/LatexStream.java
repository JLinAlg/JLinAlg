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
package org.jlinalg.latex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import org.jlinalg.IRingElement;
import org.jlinalg.Matrix;
import org.jlinalg.Vector;
import org.jlinalg.rational.Rational;

/**
 * LatexStream is an extension of PrintStream to accomodate the particular needs
 * of LaTeX and accomodates printing of Vectors and Matrix objects. However,
 * before a matrix or a vector can be printed, an equation has to be started.
 * This is done by the means of method {@link #startEquation(byte)}, with an
 * argument determining which type of LaTeX equation should be started.
 * Aletrnatives are {@link #IN_TEXT}, resulting in the equation being enclosed
 * in dollar signs, {@link #EQNARRAY} starts an eqnarray environment,
 * {@link #EQUATION} and equation environment and {@link #EQUATION_STAR} an
 * equation* environment. An equation should be closed by a call to
 * {@link #endEquation()}, though closing or finalising the stream closes an
 * open equation environment automatically. <BR>
 * Printing a Vector or a Matrix object outside an equation, results into a
 * LatexStreamError. <br>
 * After a call to {@link #printVectorsAsRows()} or
 * {@link #printVectorsAsColumns()}, all vectors ar printed as rows or as
 * columns, respectively.
 * 
 * @author Georg Thimm
 */
public class LatexStream
		extends PrintStream
{

	/**
	 * @return the rowSeparator
	 */
	public static String getRowSeparator()
	{
		return rowSeparator;
	}

	/**
	 * @param rowSeparator
	 *            the rowSeparator to set
	 */
	public static void setRowSeparator(String rowSeparator)
	{
		LatexStream.rowSeparator = rowSeparator;
	}

	/**
	 * @param file
	 * @param csn
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public LatexStream(File file, String csn) throws FileNotFoundException,
			UnsupportedEncodingException
	{
		super(file, csn);
		if (!csn.equals("US-ASCII"))
			throw new UnsupportedEncodingException("only US-ASCII is supported");
	}

	/**
	 * @param file
	 * @throws FileNotFoundException
	 */
	public LatexStream(File file) throws FileNotFoundException
	{
		super(file);
	}

	/**
	 * @param out
	 * @param autoFlush
	 * @param encoding
	 * @throws UnsupportedEncodingException
	 */
	public LatexStream(OutputStream out, boolean autoFlush, String encoding)
			throws UnsupportedEncodingException
	{
		super(out, autoFlush, encoding);
		if (!encoding.equals("US-ASCII"))
			throw new UnsupportedEncodingException("only US-ASCII is supported");
	}

	/**
	 * @param out
	 * @param autoFlush
	 */
	public LatexStream(OutputStream out, boolean autoFlush)
	{
		super(out, autoFlush);
	}

	/**
	 * @param out
	 */
	public LatexStream(OutputStream out)
	{
		super(out);
	}

	/**
	 * @param fileName
	 * @param csn
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public LatexStream(String fileName, String csn)
			throws FileNotFoundException, UnsupportedEncodingException
	{
		super(fileName, csn);
		if (!csn.equals("US-ASCII"))
			throw new UnsupportedEncodingException("only US-ASCII is supported");
	}

	/**
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	public LatexStream(String fileName) throws FileNotFoundException
	{
		super(fileName);
	}

	/**
	 * status to keep the equation
	 */
	private byte equationSet = NONE;

	/**
	 * flag indication whether vectors should be printed as columns or rows.
	 * Default: falls/columns
	 */
	protected boolean vectorsAsRows = false;

	/**
	 * No equation is open
	 */
	final private static byte NONE = 100;

	/**
	 * the equation will be enclosed by equation*
	 */
	final public static byte EQUATION_STAR = 1;

	/**
	 * the equation will be enclosed by equation
	 */
	final public static byte EQUATION = 2;

	/**
	 * the equation will be enclosed by eqnarray
	 */
	final public static byte EQNARRAY = 3;

	/**
	 * the equation is enclosed by '$'-signs
	 */
	final public static byte IN_TEXT = 0;

	/**
	 * a string constant representing a unbreakable space in latex.
	 */
	public final static String SEPARATOR_SPACE = "\\ ";

	/**
	 * a comma followed by a space.
	 */
	public final static String SEPARATOR_COMMA = ", ";

	/**
	 * the separator used for separating rows in vectors and matrices. The
	 * following values are permitted:
	 * <UL>
	 * <LI>{@link #SEPARATOR_SPACE}</LI>
	 * <LI>{@link #SEPARATOR_COMMA} (default)</LI>
	 * </UL>
	 */
	private static String rowSeparator = SEPARATOR_COMMA;

	/**
	 * strings for starting an equation
	 */
	private String[] startEquation = {
			"$", "\\begin{equation*}", "\\begin{equation}", "\\begin{eqnarray}"
	};

	/**
	 * strings for ending an equation
	 */
	private String[] endEquation = {
			"$", "\\end{equation*}", "\\end{equation}", "\\end{eqnarray}"
	};

	/**
	 * start an equation
	 * 
	 * @param type
	 *            the type of the equation environment to be use
	 */
	public void startEquation(byte type)
	{
		if (equationSet != NONE) {
			throw new LatexStreamError(
					"Equation environments can not be include others");
		}
		if (type < 0 || type >= startEquation.length)
			throw new LatexStreamError("Passed illegal value " + type + ".");

		print(startEquation[type]);
		if (type != IN_TEXT) print('\n');
		equationSet = type;
	}

	/**
	 * allow to check whether an equation is started.
	 * 
	 * @return true if the output stream is in math mode.
	 */
	public boolean isEqStarted()
	{
		return equationSet != NONE;
	}

	/**
	 * end an equation
	 */
	public void endEquation()
	{
		if (equationSet == NONE) {
			throw new LatexStreamError("No equation started");
		}
		print(endEquation[equationSet]);
		print('\n');
		equationSet = NONE;
	}

	/**
	 * After calling this method, vectors are printed as rows
	 */
	public void printVectorsAsRows()
	{
		vectorsAsRows = true;
	}

	/**
	 * After calling this method, vectors are printed as columns
	 */
	public void printVectorsAsColumns()
	{
		vectorsAsRows = false;
	}

	/**
	 * Print a JLinAlg Vector in a row or an latex array (see
	 * {@link #vectorsAsRows}).
	 * 
	 * @param v
	 *            a vector
	 */
	public void print(Vector<? extends IRingElement<?>> v)
	{
		if (equationSet == NONE)
			throw new LatexStreamError("No equation environment open");
		print("\\left(");
		if (!vectorsAsRows) print("\\begin{array}{r}\n");
		if (v.length() > 0) {
			print(v.getEntry(1));
			for (int i = 2; i <= v.length(); i++) {
				if (vectorsAsRows)
					print(rowSeparator);
				else
					print("\\\\");
				print(v.getEntry(i));
			}
		}
		if (!vectorsAsRows) print("\\end{array}\n");
		print("\\right)");
	}

	/**
	 * Print {@link Rational}s
	 * 
	 * @param f
	 *            a Rational
	 * @throws LatexStreamError
	 *             is f is not a {@link Rational}
	 */
	public void print(IRingElement<?> f)
	{
		if (f instanceof Rational) {
			Rational r = (Rational) f;

			if (r.getDenominator().equals(BigInteger.ONE))
				print(r.getNumerator());
			else {
				print("\\frac{");
				print(r.getNumerator());
				print("}{");
				print(r.getDenominator());
				print("}");
			}
			return;
		}
		throw new LatexStreamError("Can not print FieldElement " + f
				+ " in class " + f.getClass().getCanonicalName());

	}

	/**
	 * print a matrix in a latex array.
	 * 
	 * @param m
	 *            the matrix to be printed
	 */
	public void print(Matrix<? extends IRingElement<?>> m)
	{
		// header for the matrix
		print("\\left(\\begin{array}{");
		for (int i = 1; i <= m.getCols(); i++)
			print("r");
		print("}\n");
		// contents
		for (int r = 1; r <= m.getRows(); r++) {
			if (r > 1) print("\\\\\n");
			for (int c = 1; c <= m.getRows(); c++) {
				if (c > 1) {
					if (rowSeparator != SEPARATOR_SPACE) print(rowSeparator);
					print("&");
				}
				print(m.get(r, c));
			}
		}
		// end of array
		print("\\end{array}\\right)\n");
		return;
	}

	/**
	 * print sets of things (i.e. orbits)
	 * 
	 * @param set
	 *            a set of objects to be printed
	 */
	public void print(Iterable<?> set)
	{
		boolean notfirst = false;
		startEquation(LatexStream.IN_TEXT);
		print("\\left\\{");
		for (Object o : set) {
			if (notfirst)
				print(rowSeparator);
			else
				notfirst = true;
			print(o);
		}
		print("\\right\\}");
		endEquation();
	}

	/**
	 * print arbitrary objects (including JLinAlg objects)
	 */
	@Override
	public void print(Object obj)
	{
		super.print(obj);
	}

	@Override
	public void close()
	{
		if (equationSet != NONE) {
			endEquation();
		}
		super.close();
	}

	@Override
	protected void finalize() throws Throwable
	{
		if (equationSet != NONE) {
			endEquation();
		}
		super.finalize();
	}
}
