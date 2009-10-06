/**
 * 
 */
package org.jlinalg.complex;

import static org.junit.Assert.assertEquals;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.Vector;
import org.jlinalg.testutil.VectorTestBase;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Georg Thimm
 */
public class ComplexVectorTest
		extends VectorTestBase<Complex>
{
	/**
	 * @see org.jlinalg.testutil.TestBaseInterface#getFactory()
	 */
	@Override
	public IRingElementFactory<Complex> getFactory()
	{
		return Complex.FACTORY;
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#max()}.
	 */
	@Override
	@Test
	public void testMax_base()
	{
		Vector<Complex> v = new Vector<Complex>(vec_m2_3_6, getFactory());
		assertEquals(getFactory().get("6"), v.max());
		v = new Vector<Complex>(new String[] {
				"0", "-3", "2", "0"
		}, getFactory());
		assertEquals(getFactory().get("-3"), v.max());
	}

	@Ignore
	@Override
	public void testCompareTo_base()
	{
		// TODO: implement
	}

	@Override
	@Ignore
	@Test
	public void testLtVectorOfRE_base()
	{
		// TODO: implement
	}

	@Ignore
	@Override
	public void testL1Norm_base()
	{
	}

	@Ignore
	@Override
	public void testNycDist_base()
	{
	}
}