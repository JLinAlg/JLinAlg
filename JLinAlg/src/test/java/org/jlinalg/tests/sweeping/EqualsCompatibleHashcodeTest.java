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
package org.jlinalg.tests.sweeping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.testutil.TestBaseFixture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test whether the equals method fulfils of {@link IRingElement}s fulfil the
 * contract with {@link #hashCode()}
 * 
 * @author Georg Thimm
 */
@RunWith(Parameterized.class)
public class EqualsCompatibleHashcodeTest<RE extends IRingElement<RE>>
		extends TestBaseFixture<RE>
{
	/**
	 * @return the factories representing the fixtures
	 */
	@Parameters
	public static List<Object[]> data()
	{
		return AllFactories.factories();
	}

	/**
	 * The fixture: the factory producing elements.
	 */
	final IRingElementFactory<RE> factory;
	/**
	 * Fixture: element 3
	 */
	final RE re1;
	/**
	 * Fixture: element 4
	 */
	final RE re2;

	/**
	 * @param _factory
	 *            the factory to be used as fixture.
	 */
	public EqualsCompatibleHashcodeTest(IRingElementFactory<RE> _factory)
	{
		this.factory = _factory;
		re1 = getFactory().get("3");
		re2 = getFactory().get("4");
	}

	/**
	 * Test that two elements created from the same value have the same hashcode
	 */
	@Test
	public void testHashcodeIsConsistent() throws Exception
	{
		int h1 = re1.hashCode();
		int h2 = re1.hashCode();
		assertEquals("Factory: " + getFactory().toString(), h1, h2);
	}

	/**
	 * Test whether two elements created from the same value are equal and have
	 * the same
	 * Hashcode
	 */
	@Test
	public void sameValueSameHashcode() throws Exception
	{
		RE re3 = getFactory().get("3");
		assertTrue("Factory: " + getFactory().toString(), re1.equals(re3));
		assertEquals("Factory: " + getFactory().toString(), re1.hashCode(), re3
				.hashCode());
	}

	/**
	 * Test that elements created from different values are not equal.
	 */
	@Test
	public void differentValuesNotEqual() throws Exception
	{
		RE re1 = getFactory().get("3");
		RE re2 = getFactory().get("4");
		assertFalse("Factory: " + getFactory().toString(), re1.equals(re2));
	}

	/**
	 * Test whether {@code re.equals(null)} returns false
	 */
	@Test
	public void notEqualNull() throws Exception
	{
		RE re1 = getFactory().get("3");
		assertFalse("Factory: " + getFactory().toString(), re1.equals(null));
	}

	@Override
	public IRingElementFactory<RE> getFactory()
	{
		return factory;
	}
}
