/**
 * 
 */
package org.jlinalg.rational;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.jlinalg.AffineLinearSubspace;
import org.jlinalg.LinSysSolver;
import org.jlinalg.Matrix;
import org.jlinalg.Vector;
import org.jlinalg.rational.Rational;
import org.jlinalg.rational.Rational.RationalFactory;
import org.junit.Test;

/**
 * @author Georg Thimm
 */
public class RationalAffineLinearSubspaceTest
{
	@Test
	public void testEmpty()
	{
		// create a unit matrix
		Matrix<Rational> unit = new Matrix<Rational>(3, 3, Rational.FACTORY);
		unit = new Matrix<Rational>(3, 3, Rational.FACTORY);
		unit.setAll(Rational.FACTORY.zero());
		for (int i = 1; i <= 3; i++)
			unit.set(i, i, Rational.FACTORY.one());
		// a zero Vector
		Vector<Rational> v = new Vector<Rational>(3, Rational.FACTORY);
		v.setAll(Rational.FACTORY.zero());
		// now do some solving...
		AffineLinearSubspace<Rational> sol = LinSysSolver
				.solutionSpace(unit, v);
		assertTrue("dim(sol) is not 1: " + sol,
				sol.getGeneratingSystem().length == 1);
	}

	@Test
	public void testNormalize()
	{
		// create a unit matrix
		RationalFactory f = Rational.FACTORY;
		Rational z = f.zero();
		Rational[][] entries = new Rational[2][2];
		entries[0] = new Rational[]
		{
				f.one(), z
		};
		entries[1] = new Rational[]
		{
				z, f.one()
		};
		Matrix<Rational> m = new Matrix<Rational>(entries);
		Vector<Rational> v = new Vector<Rational>(new Rational[]
		{
				z, z
		});

		AffineLinearSubspace<Rational> sol = LinSysSolver.solutionSpace(m, v);
		assertEquals(0, sol.getDimension());
		assertTrue(sol.getGeneratingSystem()[0].isZero());
		sol = sol.normalize();
		assertEquals(0, sol.getDimension());
		assertTrue(sol.getGeneratingSystem()[0].isZero());
	}
}
