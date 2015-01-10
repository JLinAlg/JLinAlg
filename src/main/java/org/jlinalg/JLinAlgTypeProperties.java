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
package org.jlinalg;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation is intended to guide algorithms and tests with respect to
 * properties of a given type. The Annotation would typically decorate the
 * factory for a data type.
 * 
 * @author Georg Thimm
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface JLinAlgTypeProperties {
	/**
	 * @return true is the data type is exact. Default {@code true}
	 */
	boolean isExact() default true;

	/**
	 * @return true is the data type is discreet.Default: {@code false}
	 */
	boolean isDiscreet() default true;

	/**
	 * @return true if the data type includes negative numbers (i.e. -1<0 is
	 *         true)
	 */
	boolean hasNegativeValues() default true;

	/**
	 * @return true if the data type is a compound type (e.g. a polynomial).
	 *         Default: false
	 */
	boolean isCompound() default false;
}