package com.jz.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CollectionsDemo {
    public static void main(String[] args) {
        List<String> unmodifiableList = Collections.unmodifiableList(new ArrayList<>(Arrays.asList("A","B","C")));
        System.out.println(unmodifiableList);

        List<Integer> nums = Arrays.asList(2,16,23,89,11);
        System.out.println(nums);
        Collections.sort(nums);
        System.out.println(nums);
        Collections.shuffle(nums);
        System.out.println(nums);

        List<Integer> singletonList = Collections.singletonList(5);
        System.out.println(singletonList);

        Collections.fill(nums,22);
        System.out.println(nums);


        int[] array = new int[] {13,5,8,21,17,55};
        Arrays.sort(array);
        System.out.println(Arrays.toString(array));

    }
}
