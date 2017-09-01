package cz.upol.fapapp.cfa.comp;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.misc.TwoDimArrTIMParser;
import cz.upol.fapapp.cfa.misc.TwoDimArray;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectParser;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

public class CFAConfTTIMParser extends TIMObjectParser<CFAConfiguration> {

	public static final String TYPE = "cellular fuzzy automata configuration";

	private final TwoDimArrTIMParser<CellState> arrParser;

	public CFAConfTTIMParser(String... cellsItemsNames) {
		super(TYPE);

		this.arrParser = new TwoDimArrTIMParser<>("whatever", //
				(s) -> new CellState(TIMObjectParserComposerTools.parseDouble(s)), //
				cellsItemsNames);
	}

	@Override
	public CFAConfiguration process(TIMFileData data) {
		int m = processSize(data);
		TwoDimArray<CellState> cells = processCells(data, m);

		return new CommonConfiguration(m, cells);
	}

	/*************************************************************************/

	private int processSize(TIMFileData data) {
		String value = TIMObjectParserComposerTools.findSingleItem(data, "size", "m");
		return TIMObjectParserComposerTools.parseInt(value);
	}

	private TwoDimArray<CellState> processCells(TIMFileData data, int size) {
		return arrParser.processWithKnownSize(data, 0, size);
	}

}
