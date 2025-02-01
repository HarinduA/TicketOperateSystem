package com.example.ticketing_system;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final Queue<String> tickets = new LinkedList<>();
    private final int maxCapacity;
    private final int globalPurchaseLimit;
    private boolean allTicketsAdded = false;
    private boolean messageDisplayed = false;
    private int totalTicketsBought = 0;
    private int vendorsFinished = 0; // Track finished vendors
    private int totalVendors; // Total vendors count
    private int unsuccessfulPurchaseAttempts = 0; // Count unsuccessful purchase attempts

    // Generate the log file name with current date and time
    private static final String LOG_FILE = generateLogFileName();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Constructor to initialize the Ticket Pool with maximum capacity and purchase limit.
     * @param maxCapacity       - Maximum number of tickets that can be stored in the pool at a time.
     * @param globalPurchaseLimit - Total number of tickets that can be purchased.
     */
    public TicketPool(int maxCapacity, int globalPurchaseLimit) {
        this.maxCapacity = maxCapacity;
        this.globalPurchaseLimit = globalPurchaseLimit;
        logMessage("System initialized with max capacity: " + maxCapacity + " and global purchase limit: " + globalPurchaseLimit);
    }

    // Set the total number of vendors
    public synchronized void setTotalVendors(int totalVendors) {
        this.totalVendors = totalVendors;
    }

    /**
     * Vendor adds a ticket to the pool.
     * @param ticket - The name of the ticket being added.
     */
    public synchronized void addTicket(String ticket) {
        while (tickets.size() >= maxCapacity) {
            try {
                wait(); // Wait until there is space to add the ticket
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        tickets.add(ticket);
        String message = "Ticket added: " + ticket + ". Available tickets: " + tickets.size();
        System.out.println(message);
        logMessage(message);
        notifyAll(); // Notify all waiting customers that a new ticket is available
    }

    /**
     * Marks that a vendor has finished adding tickets.
     */
    public synchronized void vendorFinished() {
        vendorsFinished++;
        String message = "Vendor-" + vendorsFinished + " has finished adding tickets.";
        System.out.println(message);
        logMessage(message);

        if (vendorsFinished == totalVendors) {
            allTicketsAdded = true;
            notifyAll();
        }
    }

    /**
     * Customer tries to buy a ticket from the pool.
     * @param customerName - The name of the customer trying to buy the ticket.
     * @return - The name of the ticket that was bought, or null if no ticket is available.
     */
    public synchronized String removeTicket(String customerName) {
        while (tickets.isEmpty() && !allTicketsAdded) {
            try {
                wait(); // Wait until a ticket is available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }

        if (tickets.isEmpty() || totalTicketsBought >= globalPurchaseLimit) {
            unsuccessfulPurchaseAttempts++; // Increment the counter
            checkAndDisplayFinalMessage(); // Ensure final message is displayed when tickets are finished
            return null;
        }

        String ticket = tickets.poll();
        totalTicketsBought++;
        String message = customerName + " bought " + ticket + ". Available tickets: " + tickets.size();
        System.out.println(message);
        logMessage(message);
        notifyAll();
        return ticket;
    }

    /**
     * Displays a message when all tickets are sold out and the pool is closed.
     */
    private synchronized void checkAndDisplayFinalMessage() {
        if (allTicketsAdded && tickets.isEmpty() && !messageDisplayed) {
            messageDisplayed = true;
            String message = "Tickets are finished. TicketPool closed. 🎉🎉";
            System.out.println(message);
            logMessage(message);

            if (unsuccessfulPurchaseAttempts > 0) {
                String attemptMessage = unsuccessfulPurchaseAttempts + " customers tried to purchase tickets but none were available.";
                System.out.println(attemptMessage);
                logMessage(attemptMessage);
            }
        }
    }

    /**
     * Logs messages to a file with the current date and time.
     * @param message - The message to be logged.
     */
    private void logMessage(String message) {
        String timestampedMessage = LocalDateTime.now().format(formatter) + " - " + message;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(timestampedMessage);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    /**
     * Generates a log file name based on the current date and time.
     * @return - A string representing the log file name.
     */
    private static String generateLogFileName() {
        DateTimeFormatter fileNameFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String timestamp = LocalDateTime.now().format(fileNameFormatter);
        return timestamp + "_ticket_logs.txt";
    }

    /**
     * Resets the TicketPool, clearing all tickets and related state.
     */
    public synchronized void reset() {
        tickets.clear();
        allTicketsAdded = false;
        messageDisplayed = false;
        totalTicketsBought = 0;
        vendorsFinished = 0;
        unsuccessfulPurchaseAttempts = 0; // Reset the unsuccessful purchase attempts counter
        logMessage("System reset.");
        notifyAll();
    }

    /**
     * Gets the number of available tickets in the pool.
     * @return - The number of tickets currently in the pool.
     */
    public synchronized int getAvailableTickets() {
        return tickets.size();
    }

    /**
     * Checks if the global purchase limit has been reached.
     * @return - True if the total number of tickets bought reaches the purchase limit, otherwise false.
     */
    public synchronized boolean isGlobalLimitReached() {
        return totalTicketsBought >= globalPurchaseLimit;
    }

    /**
     * Checks if all tickets have been added and all tickets have been bought.
     * @return - True if all vendors have finished adding tickets and all tickets are sold out.
     */
    public synchronized boolean isFinished() {
        return allTicketsAdded && tickets.isEmpty() && vendorsFinished == totalVendors;
    }

    /**
     * Gets the total tickets added (bought + available in the pool).
     * @return - Total tickets added to the system.
     */
    public synchronized int getTotalTicketsAdded() {
        return totalTicketsBought + tickets.size();
    }
}
