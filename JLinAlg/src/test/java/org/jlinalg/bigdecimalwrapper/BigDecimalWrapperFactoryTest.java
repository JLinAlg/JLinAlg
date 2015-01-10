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

import static org.junit.Assert.assertTrue;

import org.jlinalg.InvalidOperationException;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Georg Thimm
 */
public class BigDecimalWrapperFactoryTest
{

	private static BigDecimalWrapperFactory fac;

	private final static int PRECISION = 30;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		fac = new BigDecimalWrapperFactory(PRECISION);
	}

	private void assertPrecision(BigDecimalWrapper d)
	{
		assertTrue("precision=" + d.getValue().precision(), PRECISION >= d
				.getValue().precision());
	}

	/**
	 * Test
	 * {@link org.jlinalg.BigDecimalWrapper.BigDecimalWrapperFactory#get(Object)}
	 */
	@Test
	public void get() throws Exception
	{
		double d = 1.234;
		assertTrue(d == fac.get(d).doubleValue());
		assertTrue(d == fac.get(Double.toString(d)).doubleValue());
		assertTrue(-0.375 == fac.get("-3/8").doubleValue());
		assertPrecision(fac.get("1.123456789012345678901234567890123456"));
		assertPrecision(fac.get(1.1));
		assertPrecision(fac.zero());
		assertPrecision(fac.one());
		assertPrecision(fac.m_one());
	}

	/**
	 * Test
	 */
	@Test(expected = InvalidOperationException.class)
	public void get1() throws Exception
	{
		fac.get("a");
	}

	/**
	 * Test
	 */
	@Test(expected = InvalidOperationException.class)
	public void get2() throws Exception
	{
		fac.get("1/2b");
	}
}
