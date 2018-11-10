package main.java.com.jz.collection;

import java.util.PriorityQueue;
import java.util.Queue;

public class PriorityQueueDemo {
    public static void main(String[] args) {
        Queue<Person> queue = new PriorityQueue<Person>();
        queue.offer(new Person("Zhu", 20));
        queue.offer(new Person("Xia", 21));
        queue.offer(new Person("Lin", 19));

        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());

    }
}


