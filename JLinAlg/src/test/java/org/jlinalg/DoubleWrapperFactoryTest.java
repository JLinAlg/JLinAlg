/**
 * 
 */
package org.jlinalg;

import static org.junit.Assert.assertTrue;

import org.jlinalg.DoubleWrapper;
import org.jlinalg.InvalidOperationException;
import org.jlinalg.DoubleWrapper.DoubleWrapperFactory;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Georg Thimm
 */
public class DoubleWrapperFactoryTest
{

	private static DoubleWrapperFactory fac;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		fac = DoubleWrapper.FACTORY;
	}

	/**
	 * Test {@link org.jlinalg.DoubleWrapper.DoubleWrapperFactory#get(Object)}
	 */
	@Test
	public void get() throws Exception
	{
		double d = 1.234;
		assertTrue(d == fac.get(d).doubleValue());
		assertTrue(d == fac.get(Double.toString(d)).doubleValue());
		assertTrue(-0.375 == fac.get("-3/8").doubleValue());
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
