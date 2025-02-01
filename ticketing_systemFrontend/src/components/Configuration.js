import React from "react";

const ConfigurationForm = ({
  totalTickets,
  setTotalTickets,
  maxTicketCapacity,
  setMaxTicketCapacity,
  totalCustomers,
  setTotalCustomers,
  ticketReleaseRate,
  setTicketReleaseRate,
  customerRetrievalRate,
  setCustomerRetrievalRate,
  totalVendors,
  setTotalVendors,
}) => {
  // Helper function to validate and set the input value
  const handleInputChange = (setter) => (e) => {
    const value = e.target.value.trim();
    if (/^\d*$/.test(value)) {
      setter(value === "" ? "" : parseInt(value)); // Allow empty string or valid number
    } else {
      alert("Please enter a valid number.");
    }
  };

  return (
    <div className="form-container">
      <div className="form-group">
        <label>Total Tickets:</label>
        <input
          type="text"
          value={totalTickets}
          onChange={handleInputChange(setTotalTickets)}
        />
      </div>
      <div className="form-group">
        <label>Maximum Ticket Capacity:</label>
        <input
          type="text"
          value={maxTicketCapacity}
          onChange={handleInputChange(setMaxTicketCapacity)}
        />
      </div>
      <div className="form-group">
        <label>Total Customers:</label>
        <input
          type="text"
          value={totalCustomers}
          onChange={handleInputChange(setTotalCustomers)}
        />
      </div>
      <div className="form-group">
        <label>Ticket Release Rate (sec):</label>
        <input
          type="text"
          value={ticketReleaseRate}
          onChange={handleInputChange(setTicketReleaseRate)}
        />
      </div>
      <div className="form-group">
        <label>Customer Retrieval Rate (sec):</label>
        <input
          type="text"
          value={customerRetrievalRate}
          onChange={handleInputChange(setCustomerRetrievalRate)}
        />
      </div>
      <div className="form-group">
        <label>Number of Vendors:</label>
        <input
          type="text"
          value={totalVendors}
          onChange={handleInputChange(setTotalVendors)}
        />
      </div>
    </div>
  );
};

export default ConfigurationForm;
