package org.geogebra.common.kernel.interval;

import static org.apache.commons.math3.util.FastMath.nextAfter;

/**
 * Utility class to determine the previous/next numbers
 * for algebra functions.
 *
 * @author Laszlo
 */
public class RMath {

	/**
	 *
	 * @param v reference number
	 * @return previous number of v
	 */
	public static double prev(double v) {
		if (v == Double.POSITIVE_INFINITY) {
			return v;
		}
		return nextAfter(v, Double.NEGATIVE_INFINITY);
	}

	/**
	 *
	 * @param v reference number
	 * @return next number of v
	 */
	public static double next(double v) {
		if (v == Double.NEGATIVE_INFINITY) {
			return v;
		}
		return nextAfter(v, Double.POSITIVE_INFINITY);
	}

	/**
	 *
	 * @param m nominator
	 * @param n denominator
	 * @return the previous number of m/n
	 */
	public static double divLow(double m, double n) {
		return prev(m / n);
	}

	/**
	 *
	 * @param m nominator
	 * @param n denominator
	 * @return the next number of m/n
	 */
	public static double divHigh(double m, double n) {
		return next(m / n);
	}

	/**
	 *
	 * @param m argument
	 * @param n argument
	 * @return the previous number of m * n
	 0*/
	public static double mulLow(double m, double n) {
		return prev(m * n);
	}


	/**
	 *
	 * @param m argument
	 * @param n argument
	 * @return the next number of m * n
	 */
	public static double mulHigh(double m, double n) {
		return next(m * n);
	}

	/**
	 *
	 * @param n any double.
	 * @param power to raise of.
	 * @return the previous number of n^{power}
	 */
	public static double powLow(double n, double power) {
		if (power % 1 != 0) {
			// power has decimals
			return prev(Math.pow(n, power));
		}

		int m = (int)power;
		double y = (m & 1) == 1 ? n : 1;
		m >>= 1;
		while (m > 0) {
			double x1 = mulLow(n, n);
			if ((m & 1) == 1) {
				y = mulLow(x1, y);
			}
			m >>= 1;
		}
		return y;
	}

	/**
	 *
	 * @param n any double.
	 * @param power to raise of.
	 * @return the next number of n^{power}
	 */
	public static double powHigh(double n, double power) {
		if (power % 1 != 0) {
			// power has decimals
			return next(Math.pow(n, power));
		}

		return powHigh(n, (int)power);
	}

	private static double powHigh(double n, int power) {
		double y = (power & 1) == 1 ? n : 1;
		int p = power;
		p >>= 1;
		while (p > 0) {
			double k = mulHigh(n, n);
			if ((p & 1) == 1) {
				y = mulHigh(k, y);
			}
			p >>= 1;
		}
		return y;
	}

	public static double subHi(double m, double n) {
		return next(m - n);
	}

	public static double cosLow(double x) {
		return prev(Math.cos(x));
	}

	public static double cosHigh(double x) {
		return next(Math.cos(x));
	}
}
