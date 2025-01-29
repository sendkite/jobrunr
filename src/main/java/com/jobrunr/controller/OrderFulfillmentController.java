package com.jobrunr.controller;

import com.jobrunr.task.OrderFulfillmentTask;
import org.jobrunr.scheduling.BackgroundJob;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class OrderFulfillmentController {

    @GetMapping("/confirm-order")
    public String confirmOrder() {
        UUID orderId = UUID.randomUUID();
        BackgroundJob.<OrderFulfillmentTask>enqueue(x -> x.enqueueConfirmedOrderTasks(orderId));

        return "Done";
    }
}
