package com.example.ticketing_system;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int retrievalRate;
    private final String customerName;
    private final int maxTickets;
    private int ticketsBought = 0;

    public Customer(TicketPool ticketPool, String customerName, int retrievalRate, int maxTickets) {
        this.ticketPool = ticketPool;
        this.customerName = customerName;
        this.retrievalRate = retrievalRate;
        this.maxTickets = maxTickets;
    }

    @Override
    public void run() {
        try {
            while (ticketsBought < maxTickets) {
                Thread.sleep(retrievalRate * 1000L); // Delay between purchases

                synchronized (ticketPool) {
                    String ticket = ticketPool.removeTicket(customerName);
                    if (ticket != null) {
                        ticketsBought++; // Increment customer ticket count
                        System.out.println(customerName + " bought " + ticket + " (Total: " + ticketsBought + "/" + maxTickets + ")");
                    }

                    if (ticketsBought == maxTickets) {
                        System.out.println(customerName + " has reached their purchase limit of " + maxTickets + " tickets.");
                        break;
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(customerName + " process stopped.");
        }
    }
}
