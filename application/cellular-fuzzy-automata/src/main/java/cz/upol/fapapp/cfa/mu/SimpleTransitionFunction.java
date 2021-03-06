package cz.upol.fapapp.cfa.mu;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.conf.CFAConfiguration;
import cz.upol.fapapp.cfa.conf.CellNeighborhood;

/**
 * Transition function doing no preparation and needs to compute only knowledge
 * of the cell's neighborhood.
 * 
 * @author martin
 *
 */
public abstract class SimpleTransitionFunction implements CFATransitionFunction {

	public SimpleTransitionFunction() {
	}

	@Override
	public void beforeNextGeneration(CFAConfiguration config) {
		// nothing
	}

	@Override
	public CellState perform(int i, int j, CellState cell, CellNeighborhood neig, CFAConfiguration config) {
		return computeNext(cell, neig);
	}

	public abstract CellState computeNext(CellState cell, CellNeighborhood neig);

}
