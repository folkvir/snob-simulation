package snob.simulation.observers.program;

import snob.simulation.observers.DictGraph;
import snob.simulation.observers.ObserverProgram;

public class SnobProgram implements ObserverProgram {
    @Override
    public void tick(long currentTick, DictGraph observer) {
        System.out.println(observer.size() + " "
                + observer.countPartialViewsWithDuplicates() + " "
                + observer.meanPartialViewSize());
    }

    @Override
    public void onLastTick(DictGraph observer) {

    }
}
