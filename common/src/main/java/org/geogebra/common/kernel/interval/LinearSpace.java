package org.geogebra.common.kernel.interval;

import java.util.ArrayList;
import java.util.List;

public class LinearSpace {
	public List<Double> values;
	private double scale;

	public LinearSpace() {
		values = new ArrayList<>();
	}

	public void update(Interval interval, int count) {
		values.clear();
		fill(interval.getLow(), interval.getHigh(), interval.getWidth() / count);
		scale = values.size() > 2 ? values.get(1) - values.get(0) :0;
	}

	private void fill(double start, double end, double step) {
		double current = start;
		while (current < end + step) {
			values.add(current);
			current += step;
		}
	}

	public List<Double> values() {
		return values;
	}

	public double getScale() {
		return scale;
	}
}
