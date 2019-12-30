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
package org.jlinalg.bigdecimalwrapper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jlinalg.testutil.RandomNumberList;
import org.junit.Test;

/**
 * @author Georg Thimm
 */
public class BigDecimalWrapperBlackBoxTest
{

	/**
	 * The factory that will be used to create values used the tests.
	 */
	BigDecimalWrapperFactory factory = new BigDecimalWrapperFactory(77);

	/**
	 * test {@link BigDecimalWrapper#invert()} for random values
	 */
	@Test
	public void invert()
	{
		for (BigDecimalWrapper dw1 : new RandomNumberList<>(factory, 20)) {
			if (!dw1.equals(factory.zero())) {
				BigDecimalWrapper inv = dw1.invert();
				assertFalse(inv.equals(dw1));
				assertTrue(inv.invert().subtract(dw1).abs()
						.le(factory.get(0.00001)));
			}
		}
	}

	/**
	 * test {@link BigDecimalWrapper#abs()}
	 */
	@Test
	public final void testAbs()
	{
		for (BigDecimalWrapper dw1 : new RandomNumberList<>(factory, 20)) {
			double d1 = Math.abs(dw1.doubleValue());
			BigDecimalWrapper abs = dw1.abs();
			assertTrue(abs.ge(factory.zero()));
			assertTrue(d1 == abs.doubleValue());
		}
	}

	/**
	 * test {@link BigDecimalWrapper#add(org.jlinalg.IRingElement)}
	 */
	@Test
	public void testAdd()
	{
		for (BigDecimalWrapper dw1 : new RandomNumberList<>(factory, 20)) {
			assertTrue(dw1.toString(), dw1.equals(dw1));

			for (BigDecimalWrapper dw2 : new RandomNumberList<>(factory, 20)) {
				double d1 = dw1.doubleValue();
				double d2 = dw2.doubleValue();

				assertTrue(dw1 + "+" + dw2 + "!=" + d1 + d2, Math
						.abs(dw1.add(dw2).doubleValue() - (d1 + d2)) < 0.0001);
			}
		}
	}

	/**
	 * test {@link BigDecimalWrapper#compareTo(org.jlinalg.IRingElement)} for
	 * random values
	 */
	@Test
	public void testCompare()
	{
		for (BigDecimalWrapper dw1 : new RandomNumberList<>(factory, 20)) {
			assertTrue(dw1.toString(), dw1.equals(dw1));

			for (BigDecimalWrapper dw2 : new RandomNumberList<>(factory, 20)) {
				Double d1 = Double.valueOf(dw1.doubleValue());
				Double d2 = Double.valueOf(dw2.doubleValue());
				assertTrue(
						dw1 + ".compareTo(" + dw2 + ")==" + dw1.compareTo(dw2)
								+ " but " + d1 + ".compareTo(" + d2 + ")=="
								+ d1.compareTo(d2),
						dw1.compareTo(dw2) == d1.compareTo(d2));
			}
		}
	}

	/**
	 * test {@link BigDecimalWrapper#equals(Object)}
	 */
	@Test
	public final void testEqualsObject()
	{
		BigDecimalWrapper r1 = factory.get(-1);
		assertTrue(r1 + "=-1", r1.equals(factory.m_one()));
		r1 = factory.get(1);
		assertTrue(r1 + "!=1", r1.equals(factory.one()));
		for (BigDecimalWrapper dw1 : new RandomNumberList<>(factory, 20)) {
			assertTrue(dw1.toString(), dw1.equals(dw1));

			for (BigDecimalWrapper dw2 : new RandomNumberList<>(factory, 20)) {
				double d1 = dw1.doubleValue();
				double d2 = dw2.doubleValue();
				if (d1 != d2)
					assertFalse(dw1 + "==" + dw2, dw1.equals(dw2));
				else
					assertTrue(dw1 + "!=" + dw2, dw1.equals(dw2));
			}
		}
	}

	/**
	 * test le(BigDecimalWrapper) and ge(BigDecimalWrapper)
	 */
	@Test
	public void testLeGe()
	{
		for (BigDecimalWrapper r1 : new RandomNumberList<>(factory, 10)) {
			for (BigDecimalWrapper r2 : new RandomNumberList<>(factory, 10)) {
				if (r1.doubleValue() <= r2.doubleValue()) {
					assertTrue(r1.le(r2));
					assertTrue(r2.ge(r1));
				}
				else {
					assertTrue(r2.le(r1));
					assertTrue(r1.ge(r2));
				}
			}
		}
		assertTrue(factory.get(1.4E-12).le(factory.get(1e-6)));
	}

	/**
	 * test {@link BigDecimalWrapper#invert()}
	 */
	@Test
	public final void testInvert()
	{
		for (BigDecimalWrapper d1 : new RandomNumberList<>(factory, 10)) {
			BigDecimalWrapper d2 = d1.invert();
			assertTrue("1/(1/" + d1 + "))!=" + d2.invert(),
					d2.invert().subtract(d1).abs().le(factory.get(1e-6)));
		}
	}

}
