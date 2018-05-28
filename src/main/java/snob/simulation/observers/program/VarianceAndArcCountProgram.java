package snob.simulation.observers.program;

import snob.simulation.observers.DictGraph;
import snob.simulation.observers.ObserverProgram;

/**
 * Created by julian on 4/29/15.
 */
public class VarianceAndArcCountProgram implements ObserverProgram {

	int lastSize = -1;
	int lastCount = 0;
	int lastCountTemp = 0;
	double firstVar = -1;

	public void tick(long currentTick, DictGraph observer) {

		if (this.lastSize != observer.size()) {
			this.lastSize = observer.size();
			firstVar = observer.variancePartialView();
			lastCount = lastCountTemp;
			lastCountTemp = observer.countArcs();
		}

		System.out.println(observer.countArcs() + " "
				+ observer.variancePartialView() + " "
				+ observer.meanPartialViewSize() + " " + observer.size() + " "
				+ firstVar + " " + lastCount);

	}

	public void onLastTick(DictGraph observer) {
		System.out.println("LAST");
	}
}
