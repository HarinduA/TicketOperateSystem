package com.example.ticketing_system;

import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TicketController {

    private TicketPool ticketPool;
    private List<Thread> vendorThreads = new ArrayList<>();
    private List<Thread> customerThreads = new ArrayList<>();
    private boolean isRunning = false;

    @PostMapping("/start")
    public synchronized String startSystem(
            @RequestParam int totalTickets,
            @RequestParam int ticketReleaseRate,
            @RequestParam int totalVendors,
            @RequestParam int totalCustomers,
            @RequestParam int customerRetrievalRate,
            @RequestParam int maxTicketCapacity
    ) {
        if (isRunning) {
            return "System is already running.";
        }

        // Save configuration to file
        Configuration config = new Configuration(totalTickets, maxTicketCapacity, totalCustomers, ticketReleaseRate, customerRetrievalRate, totalVendors);
        saveConfigurationToFile(config);

        // Initialize the Ticket Pool
        ticketPool = new TicketPool(maxTicketCapacity, totalTickets);
        ticketPool.setTotalVendors(totalVendors);

        // Notify customers that vendors will start
        System.out.println("Vendors are about to start adding tickets. Customers can purchase tickets after this.");

        // Start vendor threads
        int ticketsPerVendor = totalTickets / totalVendors;
        int leftoverTickets = totalTickets % totalVendors;

        for (int i = 1; i <= totalVendors; i++) {
            int ticketsToRelease = (i == totalVendors) ? ticketsPerVendor + leftoverTickets : ticketsPerVendor;
            String vendorName = "Vendor-" + i;

            Thread vendorThread = new Thread(new Vendor(ticketPool, ticketsToRelease, ticketReleaseRate, vendorName));
            vendorThreads.add(vendorThread);
            vendorThread.start();
        }

        // Start customer threads
        for (int i = 1; i <= totalCustomers; i++) {
            String customerName = "Customer-" + i;

            Thread customerThread = new Thread(new Customer(ticketPool, customerName, customerRetrievalRate, maxTicketCapacity));
            customerThreads.add(customerThread);
            customerThread.start();
        }

        isRunning = true;
        return "System started with " + totalVendors + " vendors, releasing " + totalTickets + " tickets for " + totalCustomers + " customers.";
    }

    @GetMapping("/status")
    public synchronized Map<String, Integer> getAvailableTickets() {
        Map<String, Integer> status = new HashMap<>();
        if (ticketPool != null) {
            status.put("availableTickets", ticketPool.getAvailableTickets());
            status.put("totalTicketsAdded", ticketPool.getTotalTicketsAdded());
        } else {
            status.put("availableTickets", 0);
            status.put("totalTicketsAdded", 0);
        }
        return status;
    }

    @PostMapping("/stop")
    public synchronized String stopSystem() {
        for (Thread thread : vendorThreads) {
            thread.interrupt();
        }
        for (Thread thread : customerThreads) {
            thread.interrupt();
        }
        vendorThreads.clear();
        customerThreads.clear();
        isRunning = false;
        return "System stopped!";
    }

    @PostMapping("/reset")
    public synchronized String resetSystem() {
        stopSystem();
        if (ticketPool != null) {
            ticketPool.reset();
        }
        return "System reset!";
    }

    private void saveConfigurationToFile(Configuration config) {
        try (FileWriter writer = new FileWriter("configurations.txt")) {
            writer.write("Configuration Details:\n");
            writer.write("Total Tickets: " + config.getTotalTickets() + "\n");
            writer.write("Maximum Ticket Capacity: " + config.getMaxTicketCapacity() + "\n");
            writer.write("Total Customers: " + config.getTotalCustomers() + "\n");
            writer.write("Ticket Release Rate (sec): " + config.getTicketReleaseRate() + "\n");
            writer.write("Customer Retrieval Rate (sec): " + config.getCustomerRetrievalRate() + "\n");
            writer.write("Number of Vendors: " + config.getTotalVendors() + "\n");
            System.out.println("Configuration saved to configurations.txt: " + config);
        } catch (Exception e) {
            System.err.println("Error saving configuration: " + e.getMessage());
        }
    }

    @GetMapping("/savedConfig")
    public Map<String, String> getSavedConfig() {
        Map<String, String> configMap = new HashMap<>();
        try {
            String filePath = "configurations.txt";
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    configMap.put(parts[0].trim(), parts[1].trim());
                }
            }
            System.out.println("Successfully read the configuration file: " + filePath);
        } catch (IOException e) {
            System.err.println("Error reading configuration file: " + e.getMessage());
        }
        return configMap;
    }
}
