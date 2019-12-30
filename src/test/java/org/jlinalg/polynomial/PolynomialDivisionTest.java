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

package org.jlinalg.polynomial;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.jlinalg.rational.Rational;
import org.jlinalg.rational.RationalFactory;
import org.junit.jupiter.api.Test;

class PolynomialDivisionTest
{
	private static RationalFactory factory = RationalFactory.getFactory();
	private static PolynomialFactory<Rational> rationalPolyFactory = PolynomialFactory
			.getFactory(Rational.FACTORY);

	@Test
	void byScalar()
	{
		Map<Integer, Rational> coeff1 = new HashMap<>();
		coeff1.put(0, Rational.FACTORY.get(1));
		coeff1.put(1, Rational.FACTORY.get(2));
		coeff1.put(2, Rational.FACTORY.get(3, 5));
		Polynomial<Rational> poly1 = rationalPolyFactory.get(coeff1,
				Rational.FACTORY);
		System.out.println("poly1: " + poly1);
		Rational aHalf = factory.get(1, 2);
		Polynomial<Rational> result = poly1.divideByScalar(aHalf);
		assertEquals(factory.get(2), result.getCoefficinet(0),
				"coefficient: 0");
		assertEquals(factory.get(4), result.getCoefficinet(1),
				"coefficient: 1");
		assertEquals(factory.get(6, 5), result.getCoefficinet(2),
				"coefficient: 2");
	}

	@Test
	void byPolynomial()
	{
		Map<Integer, Rational> coeff1 = new HashMap<>();
		coeff1.put(0, Rational.FACTORY.get(3));
		coeff1.put(1, Rational.FACTORY.get(21));
		coeff1.put(2, Rational.FACTORY.get(36));
		Polynomial<Rational> poly1 = rationalPolyFactory.get(coeff1,
				Rational.FACTORY);
		System.out.println("poly1: " + poly1);

		Map<Integer, Rational> coeff2 = new HashMap<>();
		coeff2.put(0, Rational.FACTORY.get(1));
		coeff2.put(1, Rational.FACTORY.get(4));
		Polynomial<Rational> poly2 = rationalPolyFactory.get(coeff2,
				Rational.FACTORY);
		System.out.println("poly2: " + poly2);

		Polynomial<Rational> result = poly1.divide(poly2);
		System.out.println("result: " + result);
		assertEquals(factory.get(3), result.getCoefficinet(0),
				"coefficient: 0");
		assertEquals(factory.get(9), result.getCoefficinet(1),
				"coefficient: 1");
		assertEquals(1, poly2.getHighestPower(), "power is wrong");
	}

}
