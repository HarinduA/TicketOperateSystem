import React, { useState, useEffect } from "react";
import axios from "axios";
import ConfigurationForm from "./components/Configuration";
import TicketStatus from "./components/TicketStatus";
import ControlPanel from "./components/ControlPanel";

import "./App.css";

const App = () => {
  const [totalTickets, setTotalTickets] = useState(0);
  const [maxTicketCapacity, setMaxTicketCapacity] = useState(0);
  const [totalCustomers, setTotalCustomers] = useState(0);
  const [ticketReleaseRate, setTicketReleaseRate] = useState(0);
  const [customerRetrievalRate, setCustomerRetrievalRate] = useState(0);
  const [totalVendors, setTotalVendors] = useState(1);
  const [availableTickets, setAvailableTickets] = useState(0);
  const [totalTicketsAdded, setTotalTicketsAdded] = useState(0); // New state for total tickets added
  const [isSystemStarted, setIsSystemStarted] = useState(false);

  useEffect(() => {
    let interval;
    if (isSystemStarted) {
      interval = setInterval(fetchTicketStatus, 1000);
    }
    return () => clearInterval(interval);
  }, [isSystemStarted]);

  const fetchTicketStatus = async () => {
    try {
      const response = await axios.get("http://localhost:8081/api/status");
      setAvailableTickets(response.data.availableTickets);
      setTotalTicketsAdded(response.data.totalTicketsAdded); // Update total tickets added
    } catch (error) {
      console.error("Error fetching ticket status:", error);
    }
  };

  const startSystem = async () => {
    try {
      await axios.post("http://localhost:8081/api/start", null, {
        params: { totalTickets, maxTicketCapacity, totalCustomers, ticketReleaseRate, customerRetrievalRate, totalVendors },
      });
      setIsSystemStarted(true);
    } catch (error) {
      console.error("Error starting system:", error);
    }
  };

  const stopSystem = async () => {
    try {
      await axios.post("http://localhost:8081/api/stop");
      setIsSystemStarted(false);
    } catch (error) {
      console.error("Error stopping system:", error);
    }
  };

  const resetSystem = async () => {
    try {
      await axios.post("http://localhost:8081/api/reset");
      setAvailableTickets(0);
      setTotalTicketsAdded(0); // Reset total tickets added
      setIsSystemStarted(false);
    } catch (error) {
      console.error("Error resetting system:", error);
    }
  };

  const fetchSavedConfig = async () => {
    try {
      const response = await axios.get("http://localhost:8081/api/savedConfig");
      const config = response.data;
      setTotalTickets(parseInt(config["Total Tickets"] || 0));
      setMaxTicketCapacity(parseInt(config["Maximum Ticket Capacity"] || 0));
      setTotalCustomers(parseInt(config["Total Customers"] || 0));
      setTicketReleaseRate(parseInt(config["Ticket Release Rate (sec)"] || 0));
      setCustomerRetrievalRate(parseInt(config["Customer Retrieval Rate (sec)"] || 0));
      setTotalVendors(parseInt(config["Number of Vendors"] || 0));
    } catch (error) {
      console.error("Error fetching saved configuration:", error);
    }
  };

  return (
    <div className="app-container">
      <h1 className="title">REAL-TIME TICKET CONFIGURATION SYSTEM</h1>
      <ConfigurationForm 
        totalTickets={totalTickets} setTotalTickets={setTotalTickets}
        maxTicketCapacity={maxTicketCapacity} setMaxTicketCapacity={setMaxTicketCapacity}
        totalCustomers={totalCustomers} setTotalCustomers={setTotalCustomers}
        ticketReleaseRate={ticketReleaseRate} setTicketReleaseRate={setTicketReleaseRate}
        customerRetrievalRate={customerRetrievalRate} setCustomerRetrievalRate={setCustomerRetrievalRate}
        totalVendors={totalVendors} setTotalVendors={setTotalVendors}
      />
      <ControlPanel startSystem={startSystem} stopSystem={stopSystem} resetSystem={resetSystem} fetchSavedConfig={fetchSavedConfig} />
      <TicketStatus availableTickets={availableTickets} totalTicketsAdded={totalTicketsAdded} />
    </div>
  );
};

export default App;
