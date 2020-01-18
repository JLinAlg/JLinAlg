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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.testutil.RingElementTestBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author Georg Thimm
 */
@RunWith(value = Parameterized.class)
public class FieldPTest<RE extends IRingElement<RE>>
		extends
		RingElementTestBase<RE>
{
	/**
	 * Two values from which the fixture is created: a small to test
	 * {@link FieldPBig} and {@link FieldPLong} .
	 **/
	@Parameters
	public static Collection<Object[]> data1()
	{
		Object[][] data = {
				{
						"113"
				}, {
						"2932031007403"
				}
		};

		return Arrays.asList(data);
	}

	private final IRingElementFactory<RE> factory;

	@SuppressWarnings("unchecked")
	public FieldPTest(String o)
	{
		factory = (IRingElementFactory<RE>) FieldPFactoryMap.getFactory(o);
	}

	@Override
	public IRingElementFactory<RE> getFactory()
	{
		return factory;
	}

	/**
	 * test the annotation of the type.
	 */
	@Test
	public void testAnnotation()
	{
		assertFalse(getFactory().getClass().getName(),
				dataTypeHasNegativeValues());
		assertTrue(getFactory().getClass().getName(), dataTypeIsDiscreet());
		assertTrue(getFactory().getClass().getName(), dataTypeIsExact());
	}

	/**
	 * test {@link IRingElement#lt(IRingElement)}
	 */
	@Test
	public void testLt()
	{
		RE v = getFactory().get("44");
		RE w = getFactory().get("77");
		assertTrue(v.lt(w));
	}

	/**
	 * test whether {@link IRingElement#norm()} can be executed and returns the
	 * same element.
	 */
	@Override
	@Test
	public void testNorm_base()
	{
		assertSame(getFactory().zero(), getFactory().zero().norm());
		RE e = getFactory().get("7");
		assertSame(e, e.norm());
	}
}
