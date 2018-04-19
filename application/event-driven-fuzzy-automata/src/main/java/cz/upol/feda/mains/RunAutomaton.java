package cz.upol.feda.mains;

import java.io.File;
import java.util.List;

import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.misc.AppsMainsTools;
import cz.upol.feda.automata.EDFATIMParser;
import cz.upol.feda.automata.EventDrivenFuzzyAutomaton;
import cz.upol.feda.event.FESTIMParser;
import cz.upol.feda.event.FuzzyEventsSequence;

public class RunAutomaton {

	public static void main(String[] args) {
//		args = new String[]{
//			"test-data/some/automaton.timf",
//			"test-data/some/sequence.timf",
//		};
		
		List<String> argsList = AppsMainsTools.checkArgs(args, 2, 2, () -> printHelp());

		String automatonFileName = argsList.get(0);
		String sequenceFileName = argsList.get(1);

		State state = run(automatonFileName, sequenceFileName);
		System.out.println(state);

	}

	private static State run(String automatonFileName, String sequenceFileName) {
		File automatonFile = new File(automatonFileName);
		File sequenceFile = new File(sequenceFileName);

		return run(automatonFile, sequenceFile);
	}

	private static State run(File automatonFile, File sequenceFile) {
		EventDrivenFuzzyAutomaton automaton = AppsMainsTools.runParser(automatonFile, new EDFATIMParser());
		FuzzyEventsSequence sequence = AppsMainsTools.runParser(sequenceFile, new FESTIMParser());

		if (automaton == null || sequence == null) {
			return null;
		}

		return run(automaton, sequence);
	}

	private static State run(EventDrivenFuzzyAutomaton automaton, FuzzyEventsSequence sequence) {
		return automaton.run(sequence);
	}

	///////////////////////////////////////////////////////////////////////////

	private static void printHelp() {
		System.out.println("RunAutomaton AUTOMATA.timf SEQUENCE.timf");
	}

}