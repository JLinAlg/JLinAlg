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