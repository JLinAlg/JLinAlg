/**
 * 
 */
package org.jlinalg.latex;

import org.jlinalg.Matrix;
import org.jlinalg.Rational;
import org.jlinalg.RationalMatrixFactory;
import org.jlinalg.Vector;
import org.jlinalg.latex.LatexStream;
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
		m = RationalMatrixFactory.unit(4);
		m.multiplyReplace(Rational.FACTORY.get(3, 67));
		v = new Vector<Rational>(4, Rational.FACTORY);
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
		ls = null;
		System.gc();
	}
}
