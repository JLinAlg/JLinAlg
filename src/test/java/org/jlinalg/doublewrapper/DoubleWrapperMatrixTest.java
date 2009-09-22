package org.jlinalg.doublewrapper;

import static org.junit.Assert.assertTrue;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.Matrix;
import org.jlinalg.testutil.MatrixTestBase;
import org.junit.Test;

/**
 * Tests the method mean() for matrices
 * 
 * @author Georg Thimm
 */
public class DoubleWrapperMatrixTest
		extends MatrixTestBase<DoubleWrapper>
{
	/**
	 *test {@link Matrix#mean()} for data type DoubleWrapper
	 */
	@Test
	public void testMean()
	{
		Matrix<DoubleWrapper> m = new Matrix<DoubleWrapper>(3, 4,
				DoubleWrapper.FACTORY);
		for (int r = 1; r <= m.getRows(); r++) {
			for (int c = 1; c <= m.getCols(); c++) {
				m.set(r, c, new DoubleWrapper((double) r / (double) c));
			}
		}
		System.err.println(m);
		assertTrue("wrong mean = " + m.mean(), m.mean().subtract(
				new DoubleWrapper((25.0 / 24.0)).abs()).le(
				new DoubleWrapper(1e-10)));
	}

	@Override
	public IRingElementFactory<DoubleWrapper> getFactory()
	{
		return DoubleWrapper.FACTORY;
	}

}
