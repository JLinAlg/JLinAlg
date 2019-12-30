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
package org.jlinalg.demo;

import org.jlinalg.LinSysSolver;
import org.jlinalg.Matrix;
import org.jlinalg.Vector;
import org.jlinalg.rational.Rational;

/**
 * Example that computes a solution of a linear equation system for the domain
 * of rational numbers.
 * 
 * @author Andreas Keilhauer, Georg Thimm
 */
public class LinearEquationSystemDemo
{
	/**
	 * start the demonstration
	 * 
	 * @param argv
	 *            is ignored
	 */
	public static void main(String[] argv)
	{
		// Create some rational numbers from the default (singelton) factory.
		Rational r1, r2, r3, r4, r5, r6;
		r1 = Rational.FACTORY.get(0.1);
		r2 = Rational.FACTORY.get(1.2);
		r3 = Rational.FACTORY.get(1, 9);
		r4 = Rational.FACTORY.get(1, 1);
		r5 = r2.add(r3);
		r6 = r1.multiply(r4);

		// create a matrix
		Matrix<Rational> a = new Matrix<>(new Rational[][]
		{
				{
						r1, r2, r3
				},
				{
						r4, r5, r6
				}
		});

		// create a vector
		Vector<Rational> b = new Vector<>(new Rational[]
		{
				r1, r2
		});

		// calculate the solution and print it
		Vector<Rational> solution = LinSysSolver.solve(a, b);

		System.out.println("x = " + solution);
	}
}
