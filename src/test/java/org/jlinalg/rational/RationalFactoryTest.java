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
package org.jlinalg.rational;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.testutil.LinAlgFactoryTestBase;
import org.junit.Test;

/**
 * @author Georg Thimm
 */
public class RationalFactoryTest
		extends
		LinAlgFactoryTestBase<Rational>
{
	private static RationalFactory rFac = Rational.FACTORY;

	@Override
	public IRingElementFactory<Rational> getFactory()
	{
		return Rational.FACTORY;
	}

	/**
	 * test {@link RationalFactory} for creating {@link Rational}s, in
	 * particular 1,0, and -1 and the ability to cancel fractions
	 */
	@Test
	public void testFactory()
	{
		assertNotNull("Cannot access factory for \'Rational\'",
				Rational.FACTORY);
		assertNotNull(Rational.FACTORY.get(0));
		assertNotNull(Rational.FACTORY.one());
		assertNotNull(Rational.FACTORY.zero());
		assertNotNull(Rational.FACTORY.m_one());
		assertFalse(Rational.FACTORY.zero().equals(Rational.FACTORY.get(1e-6)));

		assertSame(rFac.zero(), rFac.get(0));
		assertSame(rFac.zero(), rFac.get("0.0"));
		assertSame(rFac.zero(), rFac.get("-0.0"));
		assertSame(rFac.one(), rFac.get(1));

		assertTrue(11.0 == rFac.get(Double.valueOf(11)).doubleValue());
		assertEquals(rFac.one(), rFac.get(Double.valueOf(1)));
		assertEquals(rFac.m_one(), rFac.get(Double.valueOf(-1)));
		assertTrue(11.0 == rFac.get(Double.valueOf(11)).doubleValue());
		assertSame(rFac.one(), rFac.get(Integer.valueOf(1)));
		assertSame(rFac.m_one(), rFac.get(Integer.valueOf(-1)));
		assertSame(rFac.zero(), rFac.get(Integer.valueOf(0)));
		assertTrue(rFac.get(Integer.valueOf(0)) == rFac.zero());
		assertEquals(rFac.get(1, 11), rFac.get(3024, 33264));
	}

}
