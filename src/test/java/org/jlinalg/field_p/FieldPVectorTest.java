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
package org.jlinalg.field_p;

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