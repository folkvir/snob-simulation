package snob.simulation.observers.program;

import snob.simulation.observers.DictGraph;
import snob.simulation.observers.ObserverProgram;
import peersim.core.CommonState;

/**
 *
 */
public class ArcCountProgram implements ObserverProgram {

    final int step = 1000;
    boolean isFirst = true;

    /**
     *
     * @param currentTick {}
     * @param observer {}
     */
    public void tick(long currentTick, DictGraph observer) {
        /*if (CommonState.getTime() > 100 && CommonState.getTime() % step == 0) {
            boolean imp = this.isFirst;
            this.isFirst =false;
            System.out.println("#=================START=================== step:" + CommonState.getTime());
            System.out.println(observer.networkxDigraph(DictGraph.NetworkX.Connectedness, "g" + (CommonState.getTime()), imp));
            System.out.println("#=================END===================");
        }*/
        System.out.println(observer.countArcs() + " " + observer.size());
    }

    /**
     *
     * @param observer {}
     */
    public void onLastTick(DictGraph observer) {
        /*
        System.out.println("#=================START=================== step:" + CommonState.getTime());
        System.out.println(observer.networkxDigraph(DictGraph.NetworkX.Connectedness, "g" + (CommonState.getTime()), true));
        System.out.println("#=================END===================");
        */
    }
}
