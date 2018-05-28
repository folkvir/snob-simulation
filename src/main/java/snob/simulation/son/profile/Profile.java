package snob.simulation.son.profile;


import org.apache.jena.graph.Triple;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.sparql.core.TriplePath;
import org.apache.jena.sparql.syntax.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Profile {
    public List<Triple> tpqs;
    public Profile() {
        this.tpqs = new ArrayList<>();
    }

    private void extract(Query query) {

    }

    public void update(String query) {
        System.out.println("Updating the profile with: " + query);
        Query q = QueryFactory.create(query);
        ElementWalker.walk(q.getQueryPattern(), new ElementVisitorBase() {
            @Override
            public void visit(ElementPathBlock elementTriplesBlock) {
                Iterator<TriplePath> it = elementTriplesBlock.patternElts();
                while(it.hasNext()) {
                    tpqs.add(it.next().asTriple());
                }
            }
        });
    }
}

