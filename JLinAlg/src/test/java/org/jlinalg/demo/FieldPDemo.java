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

import org.jlinalg.Matrix;
import org.jlinalg.Vector;
import org.jlinalg.field_p.FieldP;
import org.jlinalg.field_p.FieldPAbstractFactory;
import org.jlinalg.field_p.FieldPFactoryMap;

/**
 * This demonstration of the class FieldP shows that in vector spaces over
 * finite fields there can be linear dependent vectors which are all orthogonal
 * to each other.<br>
 * This also demonstrates the use of a dynamic factory
 * 
 * @author Andreas Lochbihler, Georg Thimm
 */
public class FieldPDemo
{
	/**
	 * Start the demo
	 * 
	 * @param args
	 *            are ignored.
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		// The PField require the creation of a factory. Here, a factory for
		// G(7) is used.
		FieldPAbstractFactory<?> factory7 = FieldPFactoryMap
				.getFactory(Long.valueOf(7));

		// By the means of the factory, instances are created.
		FieldP<?> p1, p2, p3, p4, p5, p6;
		p1 = factory7.get(1);
		p2 = factory7.get(2);
		p3 = factory7.get(3);
		p4 = factory7.get(4);
		p5 = factory7.get(5);
		p6 = factory7.get(6);

		// The vectors will be used to define a matrix

		Vector<?> u = new Vector<>(new FieldP[] {
				p1, p1, p5
		});
		Vector<?> v = new Vector<>(new FieldP[] {
				p1, p3, p2
		});
		Vector<?> w = new Vector<>(new FieldP[] {
				p6, p4, p5,
		});

		// the matrix is created and printed.
		Matrix<?> matrix = new Matrix<>(new Vector[] {
				u, v, w
		});

		System.out.println(matrix);

		/*
		 * Now we will see that the vectors are linear dependent, because the
		 * rank of the their matrix is smaller than 3.
		 */
		System.out.println("Rank: " + matrix.rank());

		/*
		 * Let us check now, if they are all orthogonal to each other. Two
		 * vectors a, b are orthogonal on each other if <a, b> = 0, where < , >
		 * is the scalar product.
		 */

		// <u, v> =
		System.out.println(
				" < " + u + ", " + v + " > = " + ((Vector) u).multiply(v));
		// <u, w> =
		System.out.println(
				" < " + u + ", " + w + " > = " + ((Vector) u).multiply(w));
		// <v, w> =
		System.out.println(
				" < " + v + ", " + w + " > = " + ((Vector) v).multiply(w));
	}
}
