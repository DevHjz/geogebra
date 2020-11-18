package org.geogebra.common.euclidian.plot.interval;

import static org.junit.Assert.assertEquals;

import org.geogebra.common.BaseUnitTest;
import org.geogebra.common.euclidian.EuclidianView;
import org.geogebra.common.jre.headless.AppCommon;
import org.geogebra.common.kernel.geos.GeoFunction;
import org.geogebra.common.kernel.interval.IntervalFunctionSampler;
import org.geogebra.common.kernel.interval.IntervalTuple;
import org.junit.Before;
import org.junit.Test;

public class IntervalPlotterTest extends BaseUnitTest {
	public static final int NUMBER_OF_SAMPLES = 20;
	private IntervalPlotController controller;
	private AppCommon app;
	private EuclidianView view;
	private IntervalPathPlotterMock gp;

	@Before
	public void setUp() {
		app = getApp();
		view = getApp().getActiveEuclidianView();
		gp = new IntervalPathPlotterMock();
	}

	@Test
	public void testEqualsSin() {
		IntervalTuple range = PlotterUtils.createRange(-4.0, 4.0, -3.0, 3.0);
		IntervalPlotModel model = PlotterUtils.createModel(range, createSampler("sin(x)",
				range), view);
		IntervalPath path = new IntervalPath(gp, view, model);
		model.setPath(path);
		model.updateAll();
		assertEquals("", gp.getLog());
	}

	private IntervalFunctionSampler createSampler(String functionString,
			IntervalTuple range) {
		GeoFunction function = add(functionString);
		return new IntervalFunctionSampler(function, range, NUMBER_OF_SAMPLES);
	}
}
