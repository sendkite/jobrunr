package com.jobrunr.task;

import com.jobrunr.service.OrderFulfillmentService;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.jobs.annotations.Recurring;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.jobs.context.JobDashboardProgressBar;
import org.jobrunr.scheduling.BackgroundJob;
import org.jobrunr.scheduling.JobBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class OrderFulfillmentTask {

    private static final Logger logger = LoggerFactory.getLogger(OrderFulfillmentTask.class);

    @Value("${stock-locations}")
    private List<String> stockLocations;

    private final OrderFulfillmentService orderFulfillmentService;

    public OrderFulfillmentTask(OrderFulfillmentService orderFulfillmentService) {
        this.orderFulfillmentService = orderFulfillmentService;
    }

    @Job(name = "order-%0")
    public void enqueueConfirmedOrderTasks(UUID orderId) {
        BackgroundJob.create(Stream.of(
                JobBuilder.aJob()
                        .withName(String.format("order-%s-confirmation", orderId))
                        .withDetails(() -> orderFulfillmentService.sendOrderConfirmation(orderId)),
                JobBuilder.aJob()
                        .withName(String.format("order-%s-warehouse-notification", orderId))
                        .withAmountOfRetries(20)
                        .withDetails(() -> orderFulfillmentService.notifyWarehouse(orderId)),
                JobBuilder.aJob()
                        .withName(String.format("order-%s-shipment-initiation", orderId))
                        .withDetails(() -> orderFulfillmentService.initiateShipment(orderId))
        ));

    }

    @Recurring(id = "monthly-sales-report", cron = "0 0 1 * *", zoneId = "Asia/Seoul")
    public void generateMonthlySalesReport() throws InterruptedException {
        YearMonth previousMonth = YearMonth.now().minusMonths(1);
        orderFulfillmentService.generateMonthlySalesReport(previousMonth);
    }

    @Recurring(id = "daily-resupply", cron = "0 * * * * *", zoneId = "Asia/Seoul")
    public void resupply(JobContext jobContext) throws InterruptedException {
        JobDashboardProgressBar jobDashboardProgressBar = jobContext.progressBar(stockLocations.size());

        logger.info("Resupplying stock locations - {}", stockLocations);

        for(String stockLocation : stockLocations) {
            orderFulfillmentService.resupply(stockLocation);
            jobDashboardProgressBar.increaseByOne();
            jobContext.logger().info(String.format("Resupplied stock %s", stockLocation));
        }
    }

}
