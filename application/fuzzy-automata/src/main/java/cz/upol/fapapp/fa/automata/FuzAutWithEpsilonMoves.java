package cz.upol.fapapp.fa.automata;

import java.util.Set;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.sets.FuzzySet;
import cz.upol.fapapp.core.fuzzy.sets.FuzzyTernaryRelation;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;

/**
 * Fuzzy automaton with epsilon moves is extension of {@link FuzzyAutomaton}.
 * Allows transtition function to operate with {@link Symbol#EMPTY} and perform
 * so-called epsilon closures. Since the closure can cause infinite
 * approximation, requires as well precision (max. number of closure computation steps).
 * 
 * @author martin
 *
 */
public class FuzAutWithEpsilonMoves extends FuzzyAutomaton {

	private final int precision;

	public FuzAutWithEpsilonMoves(Alphabet alphabet, Set<State> states,
			FuzzyTernaryRelation<State, Symbol, State> transitionFunction, FuzzySet<State> initialStates,
			FuzzySet<State> finalStates, int precision) {
		super(alphabet, states, transitionFunction, initialStates, finalStates);

		this.precision = precision;
	}

	@Override
	protected FuzzyState stepOver(FuzzyState fromFuzzyState, Symbol over) {

		FuzzyState toConvience = computeRegularStepTo(fromFuzzyState, over);
		FuzzyState toEpsilons = computeEpsilonClosure(toConvience);

		FuzzyState toFuzzyState = new FuzzyState(FuzzySet.union(toConvience, toEpsilons));

		return toFuzzyState;
	}

	private FuzzyState computeRegularStepTo(FuzzyState fromFuzzyState, Symbol over) {
		return super.stepOver(fromFuzzyState, over);
	}

	protected FuzzyState computeEpsilonClosure(FuzzyState convienced) {
		FuzzyState currentState = convienced; /*computeRegularStepTo(convienced, Symbol.EMPTY);*/
		FuzzySet<State> nextState = null; /*convienced;*/

		for (int i = 0; i < precision; i++) {
			nextState = computeRegularStepTo(currentState, Symbol.EMPTY);

			currentState = new FuzzyState(FuzzySet.union(currentState, nextState));

			// System.out.println(currentState);
			if (currentState.equals(nextState)) {
				break;
			}
		}

		FuzzyState closed = currentState;
		return closed;
	}

	// do {
	// previousState = currentState;
	// nextState = computeRegularStepTo(currentState, Symbol.EMPTY);
	//
	// currentState = new FuzzyState(FuzzySet.union(currentState, nextState));
	//
	// // TODO does not still work perfecty, but what to do with infinite loop
	// ...
	// } while (previousState.isSubsetOf(currentState, false));

}
