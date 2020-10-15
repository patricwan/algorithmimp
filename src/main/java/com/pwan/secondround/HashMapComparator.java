package com.pwan.secondround;

import java.util.*;


public class HashMapComparator implements Comparator {

    private Map map = null;

    public HashMapComparator(Map hashMap) {
        this.map = hashMap;
    }

    @Override
    public int compare(Object int1, Object int2) {

        return (Integer)this.map.get(int1) - (Integer) this.map.get(int2);
    }
}
