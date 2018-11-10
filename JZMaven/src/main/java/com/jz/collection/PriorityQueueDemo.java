package com.jz.collection;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class PriorityQueueDemo {
    public static void main(String[] args) {
        Queue<Person> queue = new PriorityQueue<>(((o1, o2) -> {
            int result;

            result = o1.name.compareTo(o2.name);

            if (result == 0) {
                result = Integer.compare(o1.age, o2.age);
            }


            return result;
        }));
        queue.offer(new Person("Zhu", 20));
        queue.offer(new Person("Zhu", 21));
        queue.offer(new Person("Lin", 19));

        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());

    }
}


