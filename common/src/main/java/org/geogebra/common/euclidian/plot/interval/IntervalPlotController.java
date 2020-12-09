package org.geogebra.common.euclidian.plot.interval;

import org.geogebra.common.euclidian.CoordSystemAnimationListener;
import org.geogebra.common.euclidian.CoordSystemInfo;
import org.geogebra.common.euclidian.EuclidianController;

/**
 * Controller for Interval Plotter to handle zoom and moving the view.
 *
 * @author laszlo
 */
public class IntervalPlotController implements CoordSystemAnimationListener {

	private final IntervalPlotModel model;

	/**
	 * Constructor.
	 * @param model {@link IntervalPlotModel}
	 */
	public IntervalPlotController(IntervalPlotModel model) {
		this.model = model;
	}

	/**
	 * @param controller {@link EuclidianController}
	 */
	public void attachEuclidianController(EuclidianController controller) {
		controller.addZoomerAnimationListener(this);
	}

	@Override
	public void onZoomStop(CoordSystemInfo info) {
		info.setAxisZoom(false);
		model.updateAll();
	}

	@Override
	public void onMoveStop() {
		model.updateAll();
	}

	@Override
	public void onMove(CoordSystemInfo info) {
		if (info.isAxisZoom() || info.isCenterVew()) {
			return;
		}
		model.updateDomain();
	}
}