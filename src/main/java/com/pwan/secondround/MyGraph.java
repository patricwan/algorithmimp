package com.pwan.secondround;

import java.util.*;

public class MyGraph {

    Map map = new HashMap();

    int vertexCount = 0;

    MyGraph() {
        System.out.println("This is the constructor");

    }

    public String toString() {
        StringBuilder str = new StringBuilder();

        Iterator iterator = map.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry entry = ((Map.Entry)iterator.next());
            str.append( "vertex " +  entry.getKey().toString() + " ");

            HashMap map = ((HashMap)entry.getValue());

            if (map.containsKey("invs")) {
                List<Integer> inList = ((List<Integer>) map.get("invs"));
                for (Integer vertexEach : inList) {
                    str.append(" in " + vertexEach);
                }
            }

            if (map.containsKey("outvs")) {
                List<Integer> outList = ((List<Integer>) map.get("outvs"));
                for (Integer vertexEach : outList) {
                    str.append(" out " + vertexEach);
                }
            }
            str.append("\r\n");
        }

        return str.toString();
    }

    public Map buildGraph(int[][] edges) {
        //Map  Key Vertex Num => HashMap  invs : List (vertex,.)
        //                                outvs: List（vertex,..）
        for (int i =0; i< edges.length;i++) {
            int[] oneEdge = edges[i];
            int startV = oneEdge[0];
            int endV = oneEdge[1];
            System.out.println("add startV "  + startV + " endV " + endV);

            if (!map.containsKey(startV)) {
                Map edgesMap = new HashMap();
                List<Integer> list = new ArrayList<Integer>();
                list.add(new Integer(endV));
                //All in
                edgesMap.put("outvs", list);

                map.put(startV, edgesMap);
            } else {
                Map edgesMap = (Map)(map.get(startV));

                if (!edgesMap.containsKey("outvs")) {
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(new Integer(endV));
                    edgesMap.put("outvs", list);
                } else {
                    ((List<Integer>)edgesMap.get("outvs")).add(new Integer(endV));
                }
            }

            if (!map.containsKey(endV)) {
                Map edgesMap = new HashMap();

                List<Integer> list = new ArrayList<Integer>();
                list.add(new Integer(startV));
                edgesMap.put("invs", list);

                map.put(endV, edgesMap);
            } else {
                Map edgesMap = (Map)(map.get(endV));

                if (!edgesMap.containsKey("invs")) {
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(new Integer(startV));
                    edgesMap.put("invs", list);
                } else {
                    ((List<Integer>)edgesMap.get("invs")).add(new Integer(startV));
                }
            }
        }
        vertexCount = map.size();

        return map;
    }

    public void BFSFindLoop() {
        //Find a Vertex without
        int[] indegrees = new int[vertexCount];
        Map indegreesMap = new HashMap();

        //Calculate all indegrees and stored in a map
        Queue<Integer> queue = new LinkedList<Integer>();
        Iterator iterator =   map.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry entry = ((Map.Entry)iterator.next());
            Integer key = (Integer)entry.getKey();
            HashMap vertexmap = (HashMap) entry.getValue();

            if (vertexmap.containsKey("invs")) {
                indegreesMap.put(key, ((List<Integer>)vertexmap.get("invs")).size());
            } else {
                indegreesMap.put(key, 0);
                queue.add(new Integer(key));
            }
        }

        int visited = 0;
        while(!queue.isEmpty()) {
            visited++;
            Integer oneZeroInDegreeEle = queue.poll();
            System.out.println("current one zero in degree element " + oneZeroInDegreeEle);

            //get all out edges of this visited element.
            // -1 for all its indegrees
            HashMap edgedsMap = (HashMap)map.get(oneZeroInDegreeEle);
            if(edgedsMap.containsKey("outvs")) {
                List<Integer>  outList = ( List<Integer> )edgedsMap.get("outvs");

                for (Integer vertexOut: outList) {
                    Integer originalIn = (Integer) indegreesMap.get(vertexOut);
                    originalIn--;
                    indegreesMap.put(vertexOut,originalIn);

                    if (originalIn ==0) {
                        queue.offer(vertexOut);
                    }
                }

            }
        }
        System.out.println("visited " + visited);
    }
}
