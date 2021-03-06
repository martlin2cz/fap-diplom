package cz.upol.fapapp.fa.automata;

import java.io.PrintStream;
import java.util.Set;

import cz.upol.fapapp.core.automata.BaseAutomaton;
import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.sets.FuzzySet;
import cz.upol.fapapp.core.fuzzy.sets.FuzzyTernaryRelation;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.misc.Printable;

/**
 * Base fuzzy automata specified exactly by definition.
 * 
 * @author martin
 *
 */
public abstract class BaseFuzzyAutomaton implements BaseAutomaton {
	
	protected final Alphabet alphabet;
	protected final Set<State> states;
	protected final FuzzyTernaryRelation<State, Symbol, State> transitionFunction;
	protected final FuzzySet<State> initialStates;
	protected final FuzzySet<State> finalStates;

	public BaseFuzzyAutomaton(Alphabet alphabet, Set<State> states,
			FuzzyTernaryRelation<State, Symbol, State> transitionFunction, FuzzySet<State> initialStates,
			FuzzySet<State> finalStates) {
		super();
		this.alphabet = alphabet;
		this.states = states;
		this.transitionFunction = transitionFunction;
		this.initialStates = initialStates;
		this.finalStates = finalStates;
	}

	public Alphabet getAlphabet() {
		return alphabet;
	}

	public Set<State> getStates() {
		return states;
	}

	public FuzzyTernaryRelation<State, Symbol, State> getTransitionFunction() {
		return transitionFunction;
	}

	public FuzzySet<State> getInitialStates() {
		return initialStates;
	}

	public FuzzySet<State> getFinalStates() {
		return finalStates;
	}
	
////////////////////////////////////////////////////////////////////////////

	/**
	 * Runs computation on given word. Returns the final fuzzy state (w.r. to
	 * the inital and final states).
	 * 
	 * @param word
	 * @return
	 */
	public abstract FuzzyState computeWord(Word word);

	/**
	 * Computes degree of acceptance of given word.
	 * 
	 * @param word
	 * @return
	 */
	public abstract Degree degreeOfWord(Word word);

	/**
	 * Determinises this automaton.
	 * 
	 * @return
	 */
	public abstract BaseFuzzyAutomaton determinise();

	/**
	 * Minimises this automaton with no tolerance.
	 * 
	 * @return
	 */
	public abstract BaseFuzzyAutomaton minimise();

	/**
	 * Minimises this automaton.
	 * 
	 * @param delta
	 *            tollerance of nonsimilarity.
	 * @return
	 */
	public abstract BaseFuzzyAutomaton minimise(Degree delta);

	////////////////////////////////////////////////////////////////////////////
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alphabet == null) ? 0 : alphabet.hashCode());
		result = prime * result + ((finalStates == null) ? 0 : finalStates.hashCode());
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
		BaseFuzzyAutomaton other = (BaseFuzzyAutomaton) obj;
		if (alphabet == null) {
			if (other.alphabet != null)
				return false;
		} else if (!alphabet.equals(other.alphabet))
			return false;
		if (finalStates == null) {
			if (other.finalStates != null)
				return false;
		} else if (!finalStates.equals(other.finalStates))
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
		return "BaseFuzzyAutomaton [alphabet=" + alphabet + ", states=" + states + ", transitionFunction="
				+ transitionFunction + ", initialStates=" + initialStates + ", finalStates=" + finalStates + "]";
	}

	@Override
	public void print(PrintStream to) {
		Printable.print(to, new FATIMComposer(), this);
	}

}
