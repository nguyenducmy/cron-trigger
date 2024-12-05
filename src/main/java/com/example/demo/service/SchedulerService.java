package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SchedulerService {

    @Autowired
    private TaskScheduler taskScheduler;

    private ScheduledFuture<?> futureTask;
    private final AtomicInteger executionCount = new AtomicInteger(0);

    public String scheduleTask(Runnable task, int totalExecutions, long intervalInMillis) {
        futureTask = taskScheduler.scheduleAtFixedRate(() -> {
            int currentExecution = executionCount.incrementAndGet();
            System.out.printf("TASK IS RUNNING !!" +  currentExecution);
            if (currentExecution >= totalExecutions) {
                futureTask.cancel(false);
                System.out.printf("task cancelled");
            }
        }, Instant.now(), Duration.ofMillis(intervalInMillis));

        return "Task scheduled to run " + totalExecutions + " times with an interval of " + (intervalInMillis / 60000) + " minutes.";
    }

    public String cancelTask() {
        if (futureTask != null && !futureTask.isCancelled()) {
            futureTask.cancel(true);
            return "Task cancelled.";
        }
        return "No task to cancel.";
    }
}