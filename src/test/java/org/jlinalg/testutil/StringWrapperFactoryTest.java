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
package org.jlinalg.testutil;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.InvalidOperationException;
import org.jlinalg.doublewrapper.DoubleWrapper;
import org.jlinalg.rational.Rational;
import org.junit.Test;

public class StringWrapperFactoryTest
		extends FactoryTestBase<StringWrapper>
{

	@Test(expected = InvalidOperationException.class)
	public void testGaussianRandomValueRandom()
	{
		getFactory().gaussianRandomValue();
	}

	@Test
	public void testToString()
	{
		assertEquals("123", getFactory().get("123").toString());
	}

	@Test
	public void testGetObject()
	{
		assertEquals(getFactory().one().toString() + ".0", getFactory().get(
				new Double(1)).toString());
		assertEquals(getFactory().m_one(), getFactory().get(
				Rational.FACTORY.get(-1, 1)));
		assertEquals(getFactory().get("1.23"), getFactory().get(
				DoubleWrapper.FACTORY.get(1.23)));
	}

	@Test
	public void testGetInt()
	{
		assertEquals("123", getFactory().get(123).toString());
	}

	@Test
	public void testGetDouble()
	{
		assertEquals("123.45", getFactory().get(123.45).toString());
	}

	@Override
	@SuppressWarnings("deprecation")
	@Test(expected = InvalidOperationException.class)
	public void testRandomValueRandom_base()
	{
		getFactory().randomValue(new Random());
	}

	@Override
	@SuppressWarnings("deprecation")
	@Test(expected = InvalidOperationException.class)
	public void testRandomValueRandomIRingElementIRingElement_base()
	{
		getFactory().randomValue(new Random(), getFactory().m_one(),
				getFactory().one());
	}

	@Override
	@Test(expected = InvalidOperationException.class)
	public void testGaussianRandomValue_base()
	{
		getFactory().gaussianRandomValue();
	}

	@Test
	public void testGetLong()
	{
		assertEquals("123", getFactory().get(123L).toString());
		assertEquals(Long.toString(Long.MAX_VALUE), getFactory().get(
				Long.MAX_VALUE).toString());
	}

	@SuppressWarnings("deprecation")
	@Test(expected = InvalidOperationException.class)
	public void testRandomValueIRingElementIRingElement()
	{
		getFactory().randomValue(new Random(), getFactory().m_one(),
				getFactory().one());
	}

	@Test(expected = InvalidOperationException.class)
	public void testRandomValue()
	{
		getFactory().randomValue();
	}

	@Override
	public IRingElementFactory<StringWrapper> getFactory()
	{
		return StringWrapper.FACTORY;
	}

}
