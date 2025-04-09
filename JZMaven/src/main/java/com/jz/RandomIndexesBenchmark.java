package com.jz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RandomIndexesBenchmark {
    public static List<Integer> getRandomIndexes(int rightBound, int count) {
        if (count >= 0 && count <= rightBound) {
            Random random = new Random();
            Set<Integer> result = new HashSet();

            for (int i = 0; i < count; ++i) {
                result.add(random.nextInt(rightBound));
            }

            return new ArrayList(result);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static double performTask(int rightBound, int count) {
        long startTime = System.nanoTime();
        getRandomIndexes(rightBound, count);
        long endTime = System.nanoTime();
        return (double) (endTime - startTime) / (double) 1000000.0F;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int totalExecutions = 1000000;
        int numThreads = 512;
        int tasksPerThread = 1953;
        int rightBound = 10;
        ExecutorService executor = Executors.newFixedThreadPool(512);
        List<Callable<Double>> tasks = getCallables(512, 1953, 10);
        List<Future<Double>> results = executor.invokeAll(tasks);
        double totalAverageTime = (double) 0.0F;

        for (Future<Double> result : results) {
            totalAverageTime += (Double) result.get();
        }

        double overallAverageTime = totalAverageTime / (double) 512.0F;
        System.out.println("Overall Average Execution Time for 1000000 executions: " + overallAverageTime + " ms");
        executor.shutdown();
    }

    private static List<Callable<Double>> getCallables(int numThreads, int tasksPerThread, int rightBound) {
        List<Callable<Double>> tasks = new ArrayList();

        for (int i = 0; i < numThreads; ++i) {
            tasks.add((Callable) () -> {
                double totalTime = (double) 0.0F;
                for (int j = 0; j < tasksPerThread; ++j) {
                    totalTime += performTask(rightBound, 3);
                }
                return totalTime / (double) numThreads;
            });
        }

        return tasks;
    }
}
