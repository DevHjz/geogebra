package org.geogebra.common.kernel.interval;

public interface IntervalMiscOperands {
	/**
	 * Gives e^x where e is the euclidean constant
	 *
	 * @return e^x
	 */
	Interval exp();

	/**
	 *
	 * @return the natural logarithm of the interval.
	 */
	Interval log();

	/**
	 *
	 * @return base 10 logarithm of the interval
	 */
	Interval log10();

	/**
	 *
	 * @return base 2 logarithm of the interval
	 */
	Interval log2();

	/**
	 * From interval-arithmetic.js:
	 *
	 * Computes an interval that has all the values of this and other, note that it may be
	 * possible that values that don't belong to either this or other are included in the
	 * interval that represents the hull
	 *
	 * @param other to compute the hull with
	 * @return this as result.
	 */
	Interval hull(Interval other);
}
