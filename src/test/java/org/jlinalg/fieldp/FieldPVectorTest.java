/**
 * 
 */
package org.jlinalg.fieldp;

import java.util.Collection;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.field_p.FieldP;
import org.jlinalg.field_p.FieldPFactoryMap;
import org.jlinalg.testutil.VectorTestBase;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author Georg Thimm
 */
@RunWith(value = Parameterized.class)
public class FieldPVectorTest<RE extends FieldP<RE>>
		extends VectorTestBase<RE>
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
	public FieldPVectorTest(String o)
	{
		factory = (IRingElementFactory<RE>) FieldPFactoryMap.getFactory(o);
	}
}