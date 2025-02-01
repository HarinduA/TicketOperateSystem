package com.example.ticketing_system;

import java.io.Serializable;

public class Configuration implements Serializable {
    private int totalTickets;
    private int maxTicketCapacity;
    private int totalCustomers;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int totalVendors;

    // Constructor
    public Configuration(int totalTickets, int maxTicketCapacity, int totalCustomers,
                         int ticketReleaseRate, int customerRetrievalRate, int totalVendors) {
        this.totalTickets = totalTickets;
        this.maxTicketCapacity = maxTicketCapacity;
        this.totalCustomers = totalCustomers;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.totalVendors = totalVendors;
    }

    // Getters and setters
    public int getTotalTickets() { return totalTickets; }
    public void setTotalTickets(int totalTickets) { this.totalTickets = totalTickets; }

    public int getMaxTicketCapacity() { return maxTicketCapacity; }
    public void setMaxTicketCapacity(int maxTicketCapacity) { this.maxTicketCapacity = maxTicketCapacity; }

    public int getTotalCustomers() { return totalCustomers; }
    public void setTotalCustomers(int totalCustomers) { this.totalCustomers = totalCustomers; }

    public int getTicketReleaseRate() { return ticketReleaseRate; }
    public void setTicketReleaseRate(int ticketReleaseRate) { this.ticketReleaseRate = ticketReleaseRate; }

    public int getCustomerRetrievalRate() { return customerRetrievalRate; }
    public void setCustomerRetrievalRate(int customerRetrievalRate) { this.customerRetrievalRate = customerRetrievalRate; }

    public int getTotalVendors() { return totalVendors; }
    public void setTotalVendors(int totalVendors) { this.totalVendors = totalVendors; }

    @Override
    public String toString() {
        return "Configuration{" +
                "totalTickets=" + totalTickets +
                ", maxTicketCapacity=" + maxTicketCapacity +
                ", totalCustomers=" + totalCustomers +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", totalVendors=" + totalVendors +
                '}';
    }
}