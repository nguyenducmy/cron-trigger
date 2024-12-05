package com.example.demo.ApiController;

import com.example.demo.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class Api {
    @Autowired
    private SchedulerService schedulerService;

    @GetMapping("test")
    public String test() {
        return "test";
    }


    @GetMapping("/start")
    public String scheduleTask() {
        Runnable task = () -> System.out.println("Task executed at: " + Instant.now());

        // Schedule task to run 3 times with a 15-minute interval
        return schedulerService.scheduleTask(task, 8, 1 * 60 * 1000); // 1 minutes in milliseconds
    }

    @GetMapping("/cancel")
    public String cancelTask() {
        return schedulerService.cancelTask();
    }
}
