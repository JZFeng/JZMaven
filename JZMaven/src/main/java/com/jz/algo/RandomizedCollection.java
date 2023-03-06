package com.jz.algo;

import java.util.*;

//之前做过RandomizedSet，用的是ArrayList和Map (val, index)键值对；
//只是这题把它换成了Map  （val, Set）键值对；
public class RandomizedCollection {

    List<Integer> rc;
    Map<Integer, LinkedHashSet<Integer>> map;
    Random random;

    public RandomizedCollection() {
        rc = new ArrayList<Integer>();
        map = new HashMap<Integer, LinkedHashSet<Integer>>();
        random = new Random();
    }

    public boolean insert(int val) {
        boolean alreadyExists = map.containsKey(val);
        if(!alreadyExists) {
            map.put(val, new LinkedHashSet<Integer>());
        }
        map.get(val).add(rc.size());
        rc.add(val);
        return !alreadyExists;
    }

    public boolean remove(int val) {
        if(!map.containsKey(val)) {
            return false;
        }
        Set<Integer> valSet = map.get(val);
        int indexToReplace = valSet.iterator().next();

        int numAtLastPlace = rc.get(rc.size() - 1);
        Set<Integer> replaceWith = map.get(numAtLastPlace);

        rc.set(indexToReplace, numAtLastPlace);

        valSet.remove(indexToReplace);

        if(indexToReplace != rc.size() - 1) {
            replaceWith.remove(rc.size() - 1);
            replaceWith.add(indexToReplace);
        }
        rc.remove(rc.size() - 1);

        if(valSet.isEmpty()) {
            map.remove(val);
        }
        return true;
    }
    public int getRandom() {
        return rc.get(random.nextInt(rc.size()));
    }
}