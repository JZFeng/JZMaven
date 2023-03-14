package com.jz.algo.datastructureDesign;

import java.util.*;

/*[380. Insert Delete GetRandom O(1)](https://leetcode.com/problems/insert-delete-getrandom-o1/)
要getrandom，必须要得是有连续index；
现有的hashset并不提供按index访问，于是自然想到list或者array；
List插入删除的复杂度都为O(N),所以，得需要一个Map存 key 和 index的对应关系；*/
class RandomizedSet {
    List<Integer> randomList;
    Map<Integer, Integer> map; //key 和 index的对应关系
    Random random;

    public RandomizedSet() {
        this.randomList = new ArrayList<>();
        this.map = new HashMap<>();
        random = new Random();
    }

    public boolean insert(int val) {
        if( map.containsKey(val) ) {
            return false;
        }
        randomList.add(val);
        map.put( randomList.get(randomList.size() - 1), randomList.size() -1 );
        return true;
    }

    public boolean remove(int val) {
        if( ! map.containsKey(val) ) {
            return false;
        }
        Integer index = map.get(val);
        randomList.set(index, randomList.get(randomList.size() - 1));
        map.remove(val);
        map.put(  randomList.get(randomList.size() - 1),  index);

        return randomList.remove(randomList.get(randomList.size() - 1));
    }

    public int getRandom() {
        int index = random.nextInt(randomList.size());
        return randomList.get(index);
    }
}