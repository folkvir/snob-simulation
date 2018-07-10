package snob.simulation.snob;

import peersim.core.Network;
import snob.simulation.observers.DictGraph;
import snob.simulation.observers.ObserverProgram;

public class SnobObserver implements ObserverProgram {
    @Override
    public void tick(long currentTick, DictGraph observer) {
        if(currentTick > 0) {

            // hack to get the proper pid.... fix it for a proper version
            int networksize = Network.size();
            Snob snob = (Snob) observer.nodes.get(Network.get(0).getID()).pss;
            if (snob.son) {
                //List<Node> son = snob.getSonPeers(Integer.MAX_VALUE);
                //Iterator<Node> it = son.iterator();
                //while(it.hasNext()) System.err.println(observer.nodes.get(it.next().getID()).neighbors.toString());
                System.out.println(currentTick
                        + ", " + observer.size()
                        + ", " + observer.countPartialViewsWithDuplicates()
                        + ", " + observer.meanPartialViewSize()
                        + ", " + snob.getPeers(Integer.MAX_VALUE).size()
                        + ", " + snob.getSonPeers(Integer.MAX_VALUE).size());
            } else {
                System.out.println(currentTick
                        + ", " + observer.size()
                        + ", " + observer.countPartialViewsWithDuplicates()
                        + ", " + observer.meanPartialViewSize()
                        + ", " + snob.getPeers(Integer.MAX_VALUE).size());
            }
        }
    }

    @Override
    public void onLastTick(DictGraph observer) {

    }
}
