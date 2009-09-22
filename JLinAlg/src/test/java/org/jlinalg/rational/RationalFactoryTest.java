/**
 * 
 */
package org.jlinalg.rational;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.rational.Rational.RationalFactory;
import org.jlinalg.testutil.LinAlgFactoryTestBase;
import org.junit.Test;

/**
 * @author Georg Thimm
 */
public class RationalFactoryTest
		extends LinAlgFactoryTestBase<Rational>
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

		assertTrue(11.0 == rFac.get(new Double(11)).doubleValue());
		assertEquals(rFac.one(), rFac.get(new Double(1)));
		assertEquals(rFac.m_one(), rFac.get(new Double(-1)));
		assertTrue(11.0 == rFac.get(new Double(11)).doubleValue());
		assertSame(rFac.one(), rFac.get(new Integer(1)));
		assertSame(rFac.m_one(), rFac.get(new Integer(-1)));
		assertSame(rFac.zero(), rFac.get(new Integer(0)));
		assertTrue(rFac.get(new Integer(0)) == rFac.zero());
		assertEquals(rFac.get(1, 11), rFac.get(3024, 33264));
	}

}
