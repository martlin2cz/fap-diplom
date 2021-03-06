
package cz.upol.fapapp.core.misc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.sets.FuzzySet;
import cz.upol.fapapp.core.fuzzy.sets.FuzzySet.FuzzyTuple;
import cz.upol.fapapp.core.fuzzy.tnorm.TNorms;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Language;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.sets.BinaryRelation;
import cz.upol.fapapp.core.sets.BinaryRelation.Couple;

/**
 * Various utilities working with collections.
 * 
 * @author martin
 *
 */
// TODO javadoc and little refactoring
// TODO something move to TestUtils !
public class CollectionsUtils {

	@SafeVarargs
	public static <E> Set<E> toSet(E... elements) {
		return new HashSet<>(Arrays.stream(elements).collect(Collectors.toSet()));
	}

	@SafeVarargs
	public static <DT, TT> BinaryRelation<DT, TT> toBinary(Couple<DT, TT>... elements) {
		return new BinaryRelation<>(Arrays.stream(elements).collect(Collectors.toSet()));
	}

	public static Alphabet toAlphabet(Symbol... symbols) {
		return new Alphabet(Arrays.stream(symbols).collect(Collectors.toSet()));
	}

	public static Word toWord(String... symbolsLbls) {
		return new Word(Arrays.stream(symbolsLbls).map((l) -> new Symbol(l)).collect(Collectors.toList()));
	}

	/////////////////////////////////////////////////////////////////

	public static <E> void checkNotNull(String key, E object) {
		if (object == null) {
			throw new IllegalArgumentException("Missing " + key);
		}
	}

	public static <E> void checkInSet(E element, Set<E> set) {
		if (!set.contains(element)) {
			throw new IllegalArgumentException("Not in set, set " + set + " does not contain " + element);
		}
	}

	@SafeVarargs
	public static <E> void checkInSetsJoin(E element, Set<E>... sets) {
		for (Set<E> set : sets) {
			if (set.contains(element)) {
				return;
			}
		}

		throw new IllegalArgumentException("Not in sets, sets " + Arrays.asList(sets) + " does not contain " + element);
	}

	public static <E> void checkDisjoint(Set<E> first, Set<E> second) {
		first.forEach((e) -> {
			if (second.contains(e)) {
				throw new IllegalArgumentException(
						"Not disjoint, both contains " + e + " (" + first + " and " + second + ")");
			}
		});
	}

	public static <E> void checkSubset(Set<E> subset, Set<E> superset) {
		subset.forEach((e) -> {
			if (!superset.contains(e)) {
				throw new IllegalArgumentException(
						"Not subset, subset does't contain " + e + " (" + subset + " and " + superset + ")");
			}
		});
	}

	public static void checkFuzzyState(FuzzyState fuzzyState, Set<State> states) {
		fuzzyState.listStates().forEach((s) -> {
			if (!states.contains(s)) {
				throw new IllegalArgumentException(
						"Not subset, subset does't contain " + s + " (" + fuzzyState + " and " + states + ")");
			}
		});
	}

	public static void checkWord(Word word, Alphabet alphabet) {
		word.getSymbols().forEach((s) -> {
			if (!alphabet.contains(s)) {
				throw new IllegalArgumentException(
						"Word " + word + " is not a word of alphabet " + alphabet + " because of " + s);
			}
		});
	}

	public static void checkIsEmptyWord(Word word) {
		if (word.getLength() > 0) {
			throw new IllegalArgumentException("Word " + word + " must be empty");
		}
	}

	public static void checkIsNotEmptyWord(Word word) {
		if (word.getLength() == 0) {
			throw new IllegalArgumentException("Word " + word + " must not be empty");
		}
	}

	/////////////////////////////////////////////////////////////////

	/**
	 * If possible, use {@link #singletonFuzzySet(Set, Object)} !
	 * 
	 * @param element
	 * @return
	 */
	public static <E> FuzzySet<E> singletonFuzzySet(E element) {
		return singletonFuzzySet(element, Degree.ONE);
	}

	/**
	 * If possible, use {@link #singletonFuzzySet(Set, Object)} !
	 * 
	 * @param element
	 * @param degree
	 * @return
	 */
	public static <E> FuzzySet<E> singletonFuzzySet(E element, Degree degree) {
		Map<E, Degree> map = new HashMap<>();
		map.put(element, degree);

		return new FuzzySet<>(map);
	}

	public static <E> FuzzySet<E> singletonFuzzySet(Set<E> universe, E singleOne) {
		return new FuzzySet<>(universe.stream() //
				.collect(Collectors.toMap( //
						(e) -> e, //
						(e) -> singleOne.equals(e) ? Degree.ONE : Degree.ZERO)));
	}

	/////////////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	public static <E> Set<Couple<E, Degree>> cast(Set<FuzzyTuple<E>> tuples) {
		Set<FuzzyTuple<E>> fuzzys = tuples;
		Object object = fuzzys;

		Set<Couple<E, Degree>> couples = (Set<Couple<E, Degree>>) object;

		return couples;
	}

	public static <E> Map<E, Degree> convert(Set<FuzzyTuple<E>> tuples) {
		return tuples.stream() //
				.collect(Collectors.toMap( //
						(t) -> t.getDomain(), //
						(t) -> t.getTarget()));

	}

	/////////////////////////////////////////////////////////////////

	public static <K, V> Map<K, V> createMap(Set<K> keys, V value) {
		return keys.stream() //
				.collect(Collectors.toMap((e) -> e, //
						(e) -> value));
	}

	public static Alphabet inferAlphabetOfWord(Word word) {
		// TODO move to Alpabet or Word class?
		return new Alphabet(word.getSymbols().stream().collect(Collectors.toSet()));
	}

	public static Alphabet inferAlphabetOfLanguage(Language language) {
		return new Alphabet(language.getWords().stream() //
				.flatMap((w) -> w.getSymbols().stream()) //
				.collect(Collectors.toSet())); //
	}

	public static Alphabet createAlphabet(char from, char to) {
		Set<Symbol> symbols = new HashSet<>();

		for (char ch = from; ch < to; ch++) {
			Symbol symbol = new Symbol(Character.toString(ch));
			symbols.add(symbol);
		}

		return new Alphabet(symbols);
	}

	public static <E> Set<E> join(Set<E> first, Set<E> second) {
		Set<E> result = new HashSet<>(first.size() + second.size());

		result.addAll(first);
		result.addAll(second);

		return result;
	}

	public static <E> Map<E, Degree> joinFuzzy(Map<E, Degree> first, Map<E, Degree> second) {
		Map<E, Degree> result = new HashMap<>(first.size() + second.size());

		addAllFuzzy(first, result);
		addAllFuzzy(second, result);

		return result;
	}

	private static <E> void addAllFuzzy(Map<E, Degree> who, Map<E, Degree> result) {
		who.forEach((e, d) -> {
			Degree degree = result.get(e);
			if (degree != null) {
				degree = TNorms.getTnorm().tconorm(degree, d);
			} else {
				degree = d;
			}
			result.put(e, degree);
		});
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////

	@FunctionalInterface
	public static interface BinaryFunction<TFI, TSI, TO> {
		TO run(TFI first, TSI second);
	}

}
