package cz.upol.fapapp.cfa.outers;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.conf.CFAConfiguration;

public class ConstantOuterSupplier implements CFAOuterCellSupplier {

	private final CellState outer;

	public ConstantOuterSupplier(CellState outer) {
		this.outer = outer;
	}

	@Override
	public CellState computeOuterCell(int i, int j, CFAConfiguration config) {
		return outer;
	}

}
