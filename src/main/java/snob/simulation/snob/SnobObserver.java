package snob.simulation.snob;

import org.apache.jena.query.ResultSet;
import peersim.core.Network;
import snob.simulation.observers.DictGraph;
import snob.simulation.observers.ObserverProgram;

import java.util.Iterator;

public class SnobObserver implements ObserverProgram {
    public SnobObserver(String prefix) {

    }
    @Override
    public void tick(long currentTick, DictGraph observer) {
        if(currentTick > 0) {
            // hack to get the proper pid.... fix it for a proper version
            int networksize = Network.size();
            Snob snob_default = (Snob) observer.nodes.get(Network.get(0).getID()).pss;

            int completeness = 0;
            int messages = 0;
            for(int i = 0; i < networksize; ++i) {
                Snob snob = (Snob) observer.nodes.get(Network.get(i).getID()).pss;
                messages += snob.messages;
                snob.messages = 0;
                Iterator<ResultSet> it = snob.profile.results.values().iterator();
                // System.err.println("Number of queries for peer-"+Network.get(i).getID() + ": " +snob.profile.results.size());
                if(snob.profile.results.size() == 1) {
                    Iterator<ResultSet> itres = snob.profile.results.values().iterator();
                    while(itres.hasNext()){
                        ResultSet res = itres.next();
                        while(res.hasNext()) {
                            completeness++;
                            res.next();
                        }
                    }
                }
            }
            System.err.println("Number of messages in the network: " + messages);
            if (snob_default.son) {
                System.out.println(currentTick
                        + ", " + observer.size()
                        + ", " + observer.countPartialViewsWithDuplicates()
                        + ", " + observer.meanPartialViewSize()
                        + ", " + snob_default.getPeers(Integer.MAX_VALUE).size()
                        + ", " + snob_default.getSonPeers(Integer.MAX_VALUE).size()
                        + ", " + completeness
                        + ", " + messages);
            } else {
                System.out.println(currentTick
                        + ", " + observer.size()
                        + ", " + observer.countPartialViewsWithDuplicates()
                        + ", " + observer.meanPartialViewSize()
                        + ", " + snob_default.getPeers(Integer.MAX_VALUE).size()
                        + ", " + completeness
                        + ", " + messages);
            }
        }
    }

    @Override
    public void onLastTick(DictGraph observer) {

    }
}
