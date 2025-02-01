package com.example.ticketing_system;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int ticketsToRelease;
    private final int releaseRate;
    private final String vendorName;

    public Vendor(TicketPool ticketPool, int ticketsToRelease, int releaseRate, String vendorName) {
        this.ticketPool = ticketPool;
        this.ticketsToRelease = ticketsToRelease;
        this.releaseRate = releaseRate;
        this.vendorName = vendorName;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= ticketsToRelease; i++) {
                String ticket = vendorName + "-Ticket-" + i;
                ticketPool.addTicket(ticket);
                System.out.println(vendorName + " added " + ticket);
                Thread.sleep(releaseRate * 1000L); // Delay between adding tickets
            }
            System.out.println(vendorName + " has finished adding tickets.");
            ticketPool.vendorFinished(); // Notify the TicketPool that this vendor is done
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(vendorName + " was interrupted.");
        }
    }
}
