package snob.simulation;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;
import snob.simulation.son.profile.Profile;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void profileShouldUpdateWithQuery()
    {
        Profile p = new Profile();
        p.update("PREFIX foaf:  <http://xmlns.com/foaf/0.1/>"+
                "SELECT DISTINCT ?name ?nick {" +
                "   ?x foaf:mbox <mailt:person@server> ."+
                "   ?x foaf:name ?name" +
                "   OPTIONAL { ?x foaf:nick ?nick }" +
                "}");
        Assert.assertEquals(3, p.tpqs.size());
    }
}
