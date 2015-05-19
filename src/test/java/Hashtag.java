import bds.twitter.model.Edge;
import bds.twitter.model.UserVertex;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by gil on 18/05/15.
 */


import twitter.javaInterface.GraphHashtag;
        import bds.twitter.model.*;
        import java.util.List;
        import java.util.ArrayList;

        import static org.junit.Assert.assertEquals;
        import org.junit.Test;


public class Hashtag {
    @Test
    public void test() {
        List<Edge> following = new ArrayList();
        List<Vertex> vertexs = new ArrayList();

        UserVertex u = new UserVertex(1);
        u.setScreenName(String.valueOf("pep"));
        u.setFollowersCount(2 );
        vertexs.add(u);

        HashtagVertex h=new HashtagVertex(2,"barba");
        vertexs.add(h);

        Edge e=new Edge(1,2);
        following.add(e);

        GraphHashtag g=new GraphHashtag(vertexs,following);

        assertEquals(2,g.numNodes());
        assertEquals(1,g.numEdges());

    }
}