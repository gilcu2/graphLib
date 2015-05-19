/**
 * Created by gil on 19/05/15.
 */

import core.HistoBin;
import core.Histogram;
import org.junit.Test;

import bds.twitter.model.Edge;
import bds.twitter.model.UserVertex;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

import twitter.javaInterface.GraphFollowers;
import bds.twitter.model.*;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class HistogramString {
    @Test
    public void test() {
        List<Edge> following = new ArrayList();
        List<UserVertex> vertexs = new ArrayList();

        UserVertex u = new UserVertex(1);
        u.setScreenName(String.valueOf("p1"));
        u.setLang("es");
        u.setFollowersCount(2 );
        vertexs.add(u);

        u = new UserVertex(2);
        u.setScreenName(String.valueOf("p2"));
        u.setLang("en");
        u.setFollowersCount(2 );
        vertexs.add(u);

        u = new UserVertex(3);
        u.setScreenName(String.valueOf("p3"));
        u.setLang("es");
        u.setFollowersCount(3 );
        vertexs.add(u);


        Edge e=new Edge(1,2);
        following.add(e);

        GraphFollowers g=new GraphFollowers(vertexs,following);

        List<HistoBin> bins=new ArrayList();
        HistoBin bin= new HistoBin("es",2);
        bins.add(bin);
        bin=new HistoBin("en",1);
        bins.add(bin);

        assertEquals(bins,g.langHistogram());


    }
}