package cz.upol.fapapp.feda.automata;

import java.io.PrintStream;
import java.util.Set;

import cz.upol.fapapp.core.automata.BaseAutomaton;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.sets.FuzzySet;
import cz.upol.fapapp.core.lingvar.BaseLingVarLabel;
import cz.upol.fapapp.core.lingvar.LingvisticVariable;
import cz.upol.fapapp.core.misc.Printable;
import cz.upol.fapapp.core.sets.TernaryRelation;
import cz.upol.fapapp.feda.event.FuzzyEventsSequence;

/**
 * Base specification of event driven fuzzy automaton.
 * 
 * @author martin
 *
 */
public abstract class BaseEventDrivenFA implements BaseAutomaton {

	protected final Set<State> states;
	protected final Set<LingvisticVariable> eventsAlphabet;
	protected final TernaryRelation<State, BaseLingVarLabel, State> transitionFunction;
	protected final FuzzySet<State> initialStates;

	public BaseEventDrivenFA(Set<State> states, Set<LingvisticVariable> eventsAlphabet,
			TernaryRelation<State, BaseLingVarLabel, State> transitionFunction, FuzzySet<State> initialStates) {
		super();
		this.states = states;
		this.eventsAlphabet = eventsAlphabet;
		this.transitionFunction = transitionFunction;
		this.initialStates = initialStates;
	}

	// /////////////////////////////////////////////////////////////////////////

	public Set<State> getStates() {
		return states;
	}

	public Set<LingvisticVariable> getEventsAlphabet() {
		return eventsAlphabet;
	}

	public TernaryRelation<State, BaseLingVarLabel, State> getTransitionFunction() {
		return transitionFunction;
	}

	public FuzzySet<State> getInitialStates() {
		return initialStates;
	}

	// /////////////////////////////////////////////////////////////////////////

	public abstract State run(FuzzyEventsSequence events);

	// /////////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventsAlphabet == null) ? 0 : eventsAlphabet.hashCode());
		result = prime * result + ((initialStates == null) ? 0 : initialStates.hashCode());
		result = prime * result + ((states == null) ? 0 : states.hashCode());
		result = prime * result + ((transitionFunction == null) ? 0 : transitionFunction.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEventDrivenFA other = (BaseEventDrivenFA) obj;
		if (eventsAlphabet == null) {
			if (other.eventsAlphabet != null)
				return false;
		} else if (!eventsAlphabet.equals(other.eventsAlphabet))
			return false;
		if (initialStates == null) {
			if (other.initialStates != null)
				return false;
		} else if (!initialStates.equals(other.initialStates))
			return false;
		if (states == null) {
			if (other.states != null)
				return false;
		} else if (!states.equals(other.states))
			return false;
		if (transitionFunction == null) {
			if (other.transitionFunction != null)
				return false;
		} else if (!transitionFunction.equals(other.transitionFunction))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BaseEventDrivenFA [states=" + states + ", eventsAlphabet=" + eventsAlphabet + ", transitionFunction="
				+ transitionFunction + ", initialStates=" + initialStates + "]";
	}

	// /////////////////////////////////////////////////////////////////////////

	@Override
	public void print(PrintStream to) {
		Printable.print(to, new EDFATIMComposer(), (EventDrivenFuzzyAutomaton) this);
	}

}
