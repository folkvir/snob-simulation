package snob.simulation.snob;

import peersim.core.Network;
import snob.simulation.observers.DictGraph;
import snob.simulation.observers.ObserverProgram;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;
import java.util.stream.Stream;


public class SnobInit implements ObserverProgram {
    @Override
    public void tick(long currentTick, DictGraph observer) {
        if(currentTick == 1) {
            // hack to get the proper pid.... fix it for a proper version
            int networksize = Network.size();
            System.err.println("[INIT:SNOB] Initialized data for: " + networksize + " peers..." + observer.nodes.size());
            String diseasome = System.getProperty("user.dir") + "/datasets/data/diseasome/fragments/";
            String linkedmdb = System.getProperty("user.dir") + "/datasets/data/linkedmdb/fragments/";
            Vector filenames = new Vector();
            try (Stream<Path> paths = Files.walk(Paths.get(diseasome))) {
                paths.filter(Files::isRegularFile).forEach((fileName)->filenames.add(fileName));
            } catch(IOException e) {
                System.err.println(e.toString());
            }
            try (Stream<Path> paths = Files.walk(Paths.get(linkedmdb))) {
                paths.filter(Files::isRegularFile).forEach((fileName)->filenames.add(fileName));
            } catch(IOException e) {
                System.err.println(e.toString());
            }
            System.err.println("[INIT:SNOB] Number of fragments to load: " + filenames.size());

            Vector<Snob> peers = new Vector();
            for(int i = 0; i < networksize; ++i) {
                System.err.println("Loading data into peer:" + i);
                Snob snob = (Snob) observer.nodes.get(Network.get(i).getID()).pss;
                peers.add(snob);
            }
            int pickedElement = 0;
            int peersPicked = 0;
            while(pickedElement < filenames.size()) {
                System.err.println("Inserting triples into peer: " + peersPicked);
                peers.get(peersPicked).profile.datastore.update(filenames.get(pickedElement).toString());
                peersPicked++;
                if(peersPicked > peers.size() - 1) peersPicked = 0;
                pickedElement++;
            }
        }
    }



    @Override
    public void onLastTick(DictGraph observer) {

    }
}
