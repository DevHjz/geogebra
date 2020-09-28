package org.geogebra.common.kernel.statistics;

import org.geogebra.common.kernel.CircularDefinitionException;
import org.geogebra.common.kernel.Kernel;
import org.geogebra.common.kernel.arithmetic.Command;
import org.geogebra.common.kernel.commands.CommandProcessor;
import org.geogebra.common.kernel.commands.EvalInfo;
import org.geogebra.common.kernel.geos.GeoElement;
import org.geogebra.common.kernel.geos.GeoList;
import org.geogebra.common.main.MyError;

public class CmdLineGraph extends CommandProcessor {

	/**
	 * Creates new command processor
	 * @param kernel kernel
	 */
	public CmdLineGraph(Kernel kernel) {
		super(kernel);
	}

	@Override
	public GeoElement[] process(Command c, EvalInfo info)
			throws MyError, CircularDefinitionException {
		if (c.getArgumentNumber() != 2) {
			throw argNumErr(c);
		}
		GeoElement[] args = resArgs(c);
		for (int i = 0; i < 2; i++) {
			if (!args[i].isGeoList()) {
				throw argErr(args[i], c);
			}
		}
		AlgoLineGraph algo = new AlgoLineGraph(cons, (GeoList) args[0], (GeoList) args[1]);
		algo.getOutput(0).setLabel(c.getLabel());
		return algo.getOutput(0).asArray();
	}
}
