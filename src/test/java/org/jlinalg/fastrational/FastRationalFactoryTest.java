/**
 * 
 */
package org.jlinalg.fastrational;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.jlinalg.doublewrapper.DoubleWrapper;
import org.junit.Test;

/**
 * @author Georg Thimm
 */
public class FastRationalFactoryTest
		extends FastRationalTestBase
{

	/**
	 * test {@link RationalFactory} for creating {@link Rational}s, in
	 * particular 1,0, and -1 and the ability to cancel fractions
	 */
	@Test
	public void testFactory()
	{
		assertNotNull("Cannot access factory for \'Rational\'", getFactory());
		assertNotNull(getFactory().get(0));
		assertNotNull(getFactory().one());
		assertNotNull(getFactory().zero());
		assertNotNull(getFactory().m_one());
		assertFalse(getFactory().zero().equals(getFactory().get(1e-6)));

		assertSame(getFactory().zero(), getFactory().get(0));
		assertSame(getFactory().zero(), getFactory().get("0.0"));
		assertSame(getFactory().zero(), getFactory().get("-0.0"));
		assertSame(getFactory().one(), getFactory().get(1));

		assertTrue(11.0 == getFactory().get(new Double(11)).doubleValue());
		assertEquals(getFactory().one(), getFactory().get(new Double(1)));
		assertEquals(getFactory().m_one(), getFactory().get(new Double(-1)));
		assertTrue(11.0 == getFactory().get(new Double(11)).doubleValue());
		assertSame(getFactory().one(), getFactory().get(new Integer(1)));
		assertSame(getFactory().m_one(), getFactory().get(new Integer(-1)));
		assertSame(getFactory().zero(), getFactory().get(new Integer(0)));
		assertTrue(getFactory().get(new Integer(0)) == getFactory().zero());
		assertEquals(getFactory().get(1, 11), getFactory().get(3024, 33264));
		assertEquals("4/5", getFactory().get("4/5").toString());
		assertEquals("4/5", getFactory().get(getFactory().get("4/5"))
				.toString());
	}

	/**
	 * test {@link FastRationalFactory#long10toPower(int)}
	 */
	@Test
	public void testLong10toPower()
	{
		assertEquals(1, FastRationalFactory.long10toPower(0));
		assertEquals(10000, FastRationalFactory.long10toPower(4));
	}

	@Test
	public void testGetDoubleWrapper()
	{
		DoubleWrapper d = DoubleWrapper.FACTORY.get(0.1115120293307322452);
		FastRational r = FastRational.FACTORY.get(d);
		assertEquals(d.doubleValue(), r.doubleValue(), 0.0000001);
		d = DoubleWrapper.FACTORY.get(-0.00385365);
		r = FastRational.FACTORY.get(d);
		assertEquals(d.doubleValue(), r.doubleValue(), 0.0000001);
	}
}
