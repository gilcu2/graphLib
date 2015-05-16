/**
 * Created by gil on 15/05/15.
 */

import twitter.javaInterface.GraphFollowers;
import bds.twitter.model.*;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class cComponents {
    @Test
    public void test() {
        List<Edge> following = new ArrayList<>();
        List<UserVertex> users = new ArrayList<>();

        for (long i = 1; i < 4; i++) {
            UserVertex u = new UserVertex(i);
            u.setScreenName(String.valueOf(i));
            u.setFollowersCount(2 * i);
            users.add(u);

        }

        Edge e=new Edge(1,2);
        following.add(e);

        GraphFollowers g=new GraphFollowers(users,following);

        assertEquals(2,g.connectedComponentsCount());

    }
}
