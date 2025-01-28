package com.jobrunr;

import jakarta.annotation.PostConstruct;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@SpringBootApplication
public class JobrunrApplication {

    private final JobScheduler jobScheduler;

    public JobrunrApplication(JobScheduler jobScheduler) {
        this.jobScheduler = jobScheduler;
    }

    @PostConstruct
    public void startJob() {
        jobScheduler.scheduleRecurrently(
                Duration.of(5, ChronoUnit.SECONDS),
                () -> System.out.println("hello job for every 5 seconds")
        );
    }

    public static void main(String[] args) {
        SpringApplication.run(JobrunrApplication.class, args);
    }

}
