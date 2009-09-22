package org.jlinalg.fieldp;

import java.util.Collection;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.field_p.FieldP;
import org.jlinalg.field_p.FieldPFactoryMap;
import org.jlinalg.testutil.MatrixTestBase;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests the method mean() for matrices
 * 
 * @author Georg Thimm
 */
@RunWith(value = Parameterized.class)
public class FieldPMatrixTest<RE extends FieldP<RE>>
		extends MatrixTestBase<RE>
{

	/**
	 * @see FieldPTest#data1()
	 */
	@Parameters
	public static Collection<Object[]> data()
	{
		return FieldPTest.data1();
	}

	private IRingElementFactory<RE> factory;

	/**
	 * @see org.jlinalg.testutil.TestBaseInterface#getFactory()
	 */
	@Override
	public IRingElementFactory<RE> getFactory()
	{
		return factory;
	}

	@SuppressWarnings("unchecked")
	public FieldPMatrixTest(String o)
	{
		factory = (IRingElementFactory<RE>) FieldPFactoryMap.getFactory(o);
	}

	/**
	 *test {@link Matrix#mean()} for data type DoubleWrapper
	 */
	// @Test
	// public void testMean()
	// {
	// Matrix<DoubleWrapper> m = new Matrix<DoubleWrapper>(3, 4,
	// DoubleWrapper.FACTORY);
	// for (int r = 1; r <= m.getRows(); r++) {
	// for (int c = 1; c <= m.getCols(); c++) {
	// m.set(r, c, new DoubleWrapper((double) r / (double) c));
	// }
	// }
	// System.err.println(m);
	// assertTrue("wrong mean = " + m.mean(), m.mean().subtract(
	// new DoubleWrapper((25.0 / 24.0)).abs()).le(
	// new DoubleWrapper(1e-10)));
	// }

}
