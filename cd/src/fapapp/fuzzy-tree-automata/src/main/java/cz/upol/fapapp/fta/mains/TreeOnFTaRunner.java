package cz.upol.fapapp.fta.mains;

import java.util.List;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.misc.AppsMainsTools;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.fta.automata.FTATIMParser;
import cz.upol.fapapp.fta.automata.FuzzyTreeAutomaton;
import cz.upol.fapapp.fta.tree.BaseTree;
import cz.upol.fapapp.fta.tree.TreeTIMParser;

/**
 * Application performing computation of degree of acceptance of tree by
 * automata, both given by TIM files.
 * 
 * @author martin
 *
 */
public class TreeOnFTaRunner {

	public static void main(String[] args) {
		List<String> argsList = AppsMainsTools.checkArgs(args, 2, 2, () -> printHelp());

		// String automatonFile = "test/data/example-from-MorMal/fta-4.11.2.df";
		// String treeFile = "test/data/example-from-MorMal/tree-4.11.2.df";

		// String automatonFile = "test/data/example-from-thesis/fta.df";
		// String treeFile = "test/data/example-from-thesis/tree-1.df";

		// String automatonFile =
		// "test/data/m-ary-complete-trees/fta-of-3-ary.df";
		// String treeFile =
		// "test/data/m-ary-complete-trees/2-ary-notcomplete-tree.df";

		String automatonFile = argsList.get(0);
		String treeFile = argsList.get(1);

		Degree degree = run(automatonFile, treeFile);
		System.out.println(degree.getValue());
	}

	private static Degree run(String automatonFile, String treeFile) {
		FuzzyTreeAutomaton automaton = AppsMainsTools.runParser(automatonFile, new FTATIMParser());

		BaseTree tree = AppsMainsTools.runParser(treeFile, new TreeTIMParser());

		if (automaton == null || tree == null) {
			return null;
		}

		return run(automaton, tree);
	}

	private static Degree run(FuzzyTreeAutomaton automaton, BaseTree tree) {
		// automaton.print(System.out);
		// System.out.println(automaton);

		Logger.get().info("Runnin tree over automaton");

		return automaton.accept(tree);
	}

	private static void printHelp() {
		System.out.println("TreeOnFTaRunner AUTOMATON.timf TREE.timf");
	}

}
