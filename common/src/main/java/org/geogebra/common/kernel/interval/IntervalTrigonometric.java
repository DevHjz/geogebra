package org.geogebra.common.kernel.interval;

import static org.geogebra.common.kernel.interval.IntervalConstants.EMPTY;
import static org.geogebra.common.kernel.interval.IntervalConstants.PI;
import static org.geogebra.common.kernel.interval.IntervalConstants.PI_HALF;
import static org.geogebra.common.kernel.interval.IntervalConstants.PI_HALF_HIGH;
import static org.geogebra.common.kernel.interval.IntervalConstants.PI_HALF_LOW;
import static org.geogebra.common.kernel.interval.IntervalConstants.PI_HIGH;
import static org.geogebra.common.kernel.interval.IntervalConstants.PI_LOW;
import static org.geogebra.common.kernel.interval.IntervalConstants.PI_TWICE;
import static org.geogebra.common.kernel.interval.IntervalConstants.PI_TWICE_LOW;

class IntervalTrigonometric {
	private Interval interval;

	IntervalTrigonometric(Interval interval) {
		this.interval = interval;
	}

	Interval cos() {
		if (interval.isEmpty() || interval.isOnlyInfinity()) {
			return EMPTY;
		}

		Interval cache = new Interval(interval);
		cache.handleNegative();

		Interval pi = new Interval(PI);
		Interval pi2 = new Interval(PI_TWICE);
		cache.fmod(pi2);
		if (cache.getWidth() >= PI_TWICE_LOW) {
			interval.set(-1, 1);
			return interval;
		}

		if (cache.getLow() >= PI_HIGH) {
			cache.subtract(pi).cos();
			cache.negative();
			interval.set(cache);
			return interval;
		}

		double low = cache.getLow();
		double high = cache.getHigh();
  		double rlo = RMath.cosLow(high);
  		double rhi = RMath.cosHigh(low);
		// it's ensured that t.lo < pi and that t.lo >= 0
		if (high <= PI_LOW) {
			// when t.hi < pi
			// [cos(t.lo), cos(t.hi)]
			interval.set(rlo, rhi);
		} else if (high <= PI_TWICE_LOW) {
			// when t.hi < 2pi
			// [-1, max(cos(t.lo), cos(t.hi))]
			interval.set(-1, Math.max(rlo, rhi));
		} else {
			// t.lo < pi and t.hi > 2pi
			interval.set(-1, 1);
		}

		return interval;
	}

	/**
	 *
	 * @return sine of the interval
	 */
	public Interval sin() {
		if (interval.isEmpty() || interval.isOnlyInfinity()) {
			interval.setEmpty();
		} else {
			interval.subtract(PI_HALF).cos();
		}
		return interval;
	}

	/**
	 *
	 * @return tangent of the interval.
	 */
	public Interval tan() {
		if (interval.isEmpty() || interval.isOnlyInfinity()) {
			interval.setEmpty();
			return interval;
		}

		Interval cache = new Interval(interval);
		cache.handleNegative();
		Interval pi = PI;
		cache.fmod(pi);

		if (cache.getLow() >= PI_HALF_LOW) {
			cache.subtract(pi);
		}

		if (cache.getLow() <= -PI_HALF_LOW || cache.getHigh() >= PI_HALF_LOW) {
			interval.setWhole();
		} else {
			interval.set(RMath.tanLow(cache.getLow()), RMath.tanHigh(cache.getHigh()));
		}
		return interval;
	}


	/**
	 *
	 * @return arc sine of the interval
	 */
	public Interval asin() {
		if (interval.isEmpty() || interval.getHigh() < -1 || interval.getLow() > 1) {
			interval.setEmpty();
		} else {
			double low = interval.getLow() <= -1 ? -PI_HALF_HIGH : RMath.asinLow(interval.getLow());
			double high = interval.getHigh() >= 1 ? PI_HALF_HIGH : RMath.asinHigh(interval.getHigh());
			interval.set(low, high);
		}

		return interval;
	}

	/**
	 *
	 * @return arc cosine of the interval
	 */
	public Interval acos() {
		if (interval.isEmpty() || interval.getHigh() < -1 || interval.getLow() > 1) {
			interval.setEmpty();
		} else {
			double low = interval.getHigh() >= 1 ? 0 : RMath.acosLow(interval.getHigh());
			double high = interval.getLow() <= -1 ? PI_HIGH : RMath.acosHigh(interval.getLow());
			interval.set(low, high);
		}
		return interval;
	}


	/**
	 *
	 * @return arc tangent of the interval
	 */
	public Interval atan() {
		if (!interval.isEmpty()) {
			interval.set(RMath.atanLow(interval.getLow()), RMath.atanHigh(interval.getHigh()));
		}
		return interval;
	}
}
