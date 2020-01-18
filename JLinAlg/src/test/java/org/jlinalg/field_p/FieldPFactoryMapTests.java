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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class FieldPFactoryMapTests
{

	@DisplayName("FieldPFactoryMap from strings")
	@ParameterizedTest(name = "Field size: \"{0}\" s ")
	@ValueSource(strings = {
			"3", "301", "68720001023"
	})
	void getFactories(String fieldSize)
	{
		FieldPAbstractFactory factory = FieldPFactoryMap.getFactory(fieldSize);
		assertNotNull(factory, "factory was not created.");
	}

	@DisplayName("FieldPFactoryMap from longs")
	@ParameterizedTest(name = "Field size: \"{0}\" s ")
	@ValueSource(longs = {
			3L, 301L, 32212254719L, 68720001023L
	})
	void getFactories(long fieldSize)
	{
		FieldPAbstractFactory factory = FieldPFactoryMap.getFactory(fieldSize);
		assertNotNull(factory, "factory was not created.");
	}

	@DisplayName("FieldPFactoryMap for invalid field sizes")
	@ParameterizedTest(name = "Field size: \"{0}\" s ")
	@ValueSource(longs = {
			6L, 300L, 3037000500l
	})
	void getInvalidFactory(long fieldSize)
	{
		assertThrows(IllegalArgumentException.class,
				() -> FieldPFactoryMap.getFactory(fieldSize));
	}

	@DisplayName("FieldPFactoryMap from strings and long are the same")
	@ParameterizedTest(name = "Field size: \"{0}\" s ")
	@ValueSource(strings = {
			"3", "301", "32212254719", "68720001023"
	})
	void factoriesFromStringandLongAreIdentical(String fieldSize)
	{
		FieldPAbstractFactory factory1 = FieldPFactoryMap.getFactory(fieldSize);
		FieldPAbstractFactory factory2 = FieldPFactoryMap
				.getFactory(Long.parseLong(fieldSize));
		assertSame(factory1, factory2, "factories are not identical");
	}

	@DisplayName("FieldPFactoryMap from strings and strings are the same")
	@ParameterizedTest(name = "Field size: \"{0}\" s ")
	@ValueSource(strings = {
			"5", "307", "32212254719", "68720001023"
	})
	void factoriesFromStringAndStringAreIdentical(String fieldSize)
	{
		FieldPAbstractFactory factory1 = FieldPFactoryMap.getFactory(fieldSize);
		FieldPAbstractFactory factory2 = FieldPFactoryMap.getFactory(fieldSize);
		assertSame(factory1, factory2, "factories are not identical");
		assertTrue(factory1.equals(factory1), "factories should be equal");
	}
}
