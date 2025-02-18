package com.jobrunr.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.YearMonth;
import java.util.UUID;

@Service
public class OrderFulfillmentService {

    private static final Logger logger = LoggerFactory.getLogger(OrderFulfillmentService.class);

    public void sendOrderConfirmation(UUID orderId) throws InterruptedException {
        logger.info("Order {}: sending order confirmation%n", orderId);
        // TODO retrieve order from database, generate an invoice and send the confirmation to the customer
        Thread.sleep(2000);
    }

    public void notifyWarehouse(UUID orderId) throws InterruptedException {
        // TODO notify the warehouse of the arrival of a new order after retrieving/computing the necessary data using the orderId
        logger.info("Order {}: notifying the warehouse", orderId);
        Thread.sleep(1000);
    }

    public void initiateShipment(UUID orderId) throws InterruptedException {
        // TODO call the carrier's shipment initiation endpoint after retrieving/computing the necessary data using the orderId
        logger.info("Order {}: initiating shipment", orderId);
        Thread.sleep(5000);
    }

    public void generateMonthlySalesReport(YearMonth month) throws InterruptedException {
        // TODO aggregate monthly sales, generate PDF and send it to managers
        logger.info("Sales report: generating monthly sales report on {} for month {}", Instant.now(), month);
        Thread.sleep(10000);
    }

    public void resupply(String stockLocation) throws InterruptedException {
        // TODO check the stock and contact supplier if needed
        logger.info("Inventory resupply: resupplying the inventory on {} for stock {}", Instant.now(), stockLocation);
        Thread.sleep(5000);
    }
}
