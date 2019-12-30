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

import org.jlinalg.LinAlgFactory;
import org.jlinalg.Matrix;
import org.jlinalg.Vector;
import org.jlinalg.rational.Rational;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Georg Thimm
 */
public class LatexStreamTest
{

	/**
	 * rational matrix used in the tests
	 */
	static Matrix<Rational> m;

	/**
	 * rational vector used in the tests
	 */
	static Vector<Rational> v;

	/**
	 * setup {@link #m} and {@link #v}
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		LinAlgFactory<Rational> linalgFactory = new LinAlgFactory<>(
				Rational.FACTORY);
		m = linalgFactory.identity(4);
		m.multiplyReplace(Rational.FACTORY.get(3, 67));
		v = new Vector<>(4, Rational.FACTORY);
		for (int i = 1; i <= 4; i++)
			v.set(i, Rational.FACTORY.get(4 - i, i));
	}

	/**
	 * print m and n to stdout.
	 */
	@Test
	public void print()
	{
		LatexStream ls = new LatexStream(System.out);

		ls.startEquation(LatexStream.EQUATION);
		ls.print("m=");
		ls.print(m);
		ls.print('=');
		ls.print(v);
		ls.printVectorsAsColumns();
		ls.print(v);
		ls.endEquation();
		ls.close();
	}

	/**
	 * test whether a matrix environment is automatically closed if the stream
	 * to which it is written is closed
	 */
	@Test
	public void testFinalise()
	{
		LatexStream ls = new LatexStream(System.out);
		ls.print("This should be a closed LaTeX math environment:");
		ls.startEquation(LatexStream.IN_TEXT);
		ls.print("a=1");
		ls.close();
		System.gc();
	}
}
