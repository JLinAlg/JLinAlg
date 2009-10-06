package org.jlinalg.fastrational;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.jlinalg.LinSysSolver;
import org.jlinalg.LinearSubspace;
import org.jlinalg.Matrix;
import org.jlinalg.Vector;
import org.jlinalg.rational.Rational;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Andreas Keilhauer, Georg Thimm
 */
public class FastRationalLinSysSolverTest
{

	Rational r0, r1, r2, r3, r4, r5, r6, r7, r8, r9 = null;

	/**
	 * set up r0 to r9
	 */
	@Before
	public void setUp()
	{
		r0 = Rational.FACTORY.get(0);
		r1 = Rational.FACTORY.get(1);
		r2 = Rational.FACTORY.get(2);
		r3 = Rational.FACTORY.get(3);
		r4 = Rational.FACTORY.get(4);
		r5 = Rational.FACTORY.get(5);
		r6 = Rational.FACTORY.get(6);
		r7 = Rational.FACTORY.get(7);
		r8 = Rational.FACTORY.get(8);
		r9 = Rational.FACTORY.get(9);
	}

	/**
	 * test {@link LinSysSolver#solve(Matrix, Vector)},
	 * {@link LinSysSolver#solutionSpace(Matrix, Vector)}
	 */
	@Test
	public void test1()
	{
		Matrix<Rational> a = new Matrix<Rational>(new Rational[][]
		{
				{
						r1, r0
				},
				{
						r0, r1
				}
		});
		Vector<Rational> b = new Vector<Rational>(new Rational[]
		{
				r0, r0
		});
		assertTrue(new Vector<Rational>(new Rational[]
		{
				r0, r0
		}).equals(LinSysSolver.solve(a, b)));
		assertTrue(LinSysSolver.solutionSpace(a, b) instanceof LinearSubspace<?>);
		assertEquals(0, LinSysSolver.solutionSpace(a, b).getDimension());
	}

	/**
	 * test {@link LinSysSolver#solve(Matrix, Vector)},
	 * {@link LinSysSolver#solutionSpace(Matrix, Vector)}
	 */
	@Test
	public void test2()
	{
		Matrix<Rational> a = new Matrix<Rational>(new Rational[][]
		{
				{
						r1, r1
				},
				{
						r1, r1
				}
		});
		Vector<Rational> b = new Vector<Rational>(new Rational[]
		{
				r1, r1
		});

		assertEquals(LinSysSolver.solutionSpace(a, b), LinSysSolver
				.solutionSpace(a, b).normalize());
		assertEquals(new Vector<Rational>(new Rational[]
		{
				r1, r0
		}), LinSysSolver.solve(a, b));
		assertEquals(1, LinSysSolver.solutionSpace(a, b).getDimension());
		assertEquals(new Vector<Rational>(new Rational[]
		{
				r1, r1.negate()
		}), LinSysSolver.solutionSpace(a, b).getGeneratingSystem()[0]);
	}

	/**
	 * test {@link LinSysSolver#solve(Matrix, Vector)},
	 * {@link LinSysSolver#solutionSpace(Matrix, Vector)}
	 */
	@Test
	public void test3()
	{
		Matrix<Rational> a = new Matrix<Rational>(new Rational[][]
		{
				{
						r0, r1, r2
				},
				{
						r3, r4, r5
				},
				{
						r6, r7, r8
				}
		});
		Vector<Rational> b = new Vector<Rational>(new Rational[]
		{
				r1, r2, r3
		});
		assertEquals(new Vector<Rational>(new Rational[]
		{
				r2.divide(r3).negate(), r1, r0
		}), LinSysSolver.solve(a, b));
		assertEquals(new Vector<Rational>(new Rational[]
		{
				r2.divide(r3).negate(), r1, r0
		}), LinSysSolver.solutionSpace(a, b).getInhomogenousPart());
		assertEquals(1, LinSysSolver.solutionSpace(a, b).getDimension());
		assertEquals(new Vector<Rational>(new Rational[]
		{
				r1.negate(), r2, r1.negate()
		}), LinSysSolver.solutionSpace(a, b).getGeneratingSystem()[0]);
		assertTrue(LinSysSolver.solutionSpace(a, b).isNormalized());
	}
}
