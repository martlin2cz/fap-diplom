package cz.upol.fapapp.cfa.gui.task;

import java.util.concurrent.TimeUnit;

import cz.upol.fapapp.cfa.automata.CellularFuzzyAutomata;
import cz.upol.fapapp.cfa.config.CFAConfiguration;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;

public class CFAComputationTask extends Task<Void> {

	private static final int DEFAULT_SPEED = 10;

	private final CellularFuzzyAutomata automata;
	private final ObjectProperty<CFAConfiguration> configProperty;
	private final IntegerProperty generationProperty;
	private final IntegerProperty speedProperty;

	public CFAComputationTask(CellularFuzzyAutomata automata) {
		super();
		this.automata = automata;
		this.configProperty = new SimpleObjectProperty<CFAConfiguration>(automata.getCurrentConfig());
		this.generationProperty = new SimpleIntegerProperty();
		this.speedProperty = new SimpleIntegerProperty(DEFAULT_SPEED);

		reportAutomataChanged();
	}

	public ObjectProperty<CFAConfiguration> getConfig() {
		return configProperty;
	}

	public IntegerProperty getGeneration() {
		return generationProperty;
	}

	public IntegerProperty getSpeed() {
		return speedProperty;
	}

	/**************************************************************************/

	@Override
	protected Void call() throws Exception {

		while (!this.isCancelled()) {
			doOneStep();

			sleepItself();
		}

		return null;
	}

	private void sleepItself() {
		int speed = speedProperty.get();
		int timeout = 1000 / speed;

		try {
			TimeUnit.MILLISECONDS.sleep(timeout);
		} catch (InterruptedException eIgnore) {
		}
	}

	public void doOneStep() {
		automata.toNextGeneration();

		reportAutomataChanged();

	}

	private void reportAutomataChanged() {

		Platform.runLater(() -> { //
			configProperty.set(automata.getCurrentConfig());
			generationProperty.set(automata.getCurrentGeneration());
		});
	}

}