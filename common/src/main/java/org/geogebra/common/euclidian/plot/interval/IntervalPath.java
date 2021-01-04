package org.geogebra.common.euclidian.plot.interval;

import org.geogebra.common.euclidian.EuclidianView;
import org.geogebra.common.kernel.interval.Interval;
import org.geogebra.common.kernel.interval.IntervalTuple;

public class IntervalPath {
	private final IntervalPathPlotter gp;
	private final EuclidianView view;
	private final IntervalPlotModel model;
	private boolean moveTo;

	/**
	 * Constructor.
	 * @param gp {@link IntervalPathPlotter}
	 * @param view {@link EuclidianView}
	 * @param model {@link IntervalPlotModel}
	 */
	public IntervalPath(IntervalPathPlotter gp, EuclidianView view, IntervalPlotModel model) {
		this.gp = gp;
		this.view = view;
		this.model = model;
	}

	/**
	 * Update the path based on the model.
	 */
	public void update() {
		if (model.isEmpty()) {
			return;
		}

		reset();

		Interval lastY = new Interval();

		int pointCount = model.getPoints().count();
		if (pointCount == 1) {
			return;
		}
		IntervalTuple last = model.getPoints().get(0);
		moveTo(last);
		for (int i = 1; i < pointCount; i++) {
			IntervalTuple tuple =  model.getPoints().get(i);
			if (last != null && tuple != null && !tuple.y().isEmpty()
				&& last.x().getHigh() == tuple.x().getLow()) {
				plotInterval(lastY, tuple);
			} else {
				moveTo = true;
			}
			last = tuple;
		}
	}

	private void moveTo(IntervalTuple point) {
		Interval x = view.toScreenIntervalX(point.x());
		Interval y = view.toScreenIntervalY(point.y());
		gp.moveTo(x.getLow(), y.getLow());
		if (point.isValueIncreasing()) {
			plotHigh(x, y);
		} else {
			gp.lineTo(x.getHigh(), y.getHigh());
		}
	}

	/**
	 * Resets path
	 */
	void reset() {
		gp.reset();
	}

	private void plotInterval(Interval lastY, IntervalTuple point) {
		Interval x = view.toScreenIntervalX(point.x());
		Interval y = view.toScreenIntervalY(point.y());
		if (y.isGreaterThan(lastY)) {
			plotHigh(x, y);
		} else {
			plotLow(x, y);
		}

		lastY.set(y);
	}

	private void plotHigh(Interval x, Interval y) {
		if (moveTo) {
			gp.moveTo(x.getLow(), y.getLow());
		} else {
			lineTo(x.getLow(), y.getLow());
		}

		lineTo(x.getHigh(), y.getHigh());
	}

	private void plotLow(Interval x, Interval y) {
		if (moveTo) {
			gp.moveTo(x.getLow(), y.getHigh());
		} else {
			lineTo(x.getLow(), y.getHigh());
		}

		lineTo(x.getHigh(), y.getLow());
	}

	private void lineTo(double low, double high) {
		gp.lineTo(low, high);
	}
}
