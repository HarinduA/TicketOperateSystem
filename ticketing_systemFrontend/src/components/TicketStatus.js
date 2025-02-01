import React from "react";

const TicketStatus = ({ availableTickets, totalTicketsAdded }) => {
  return (
    <div className="status-display">
      <p>{availableTickets} tickets available</p>
      <p>{totalTicketsAdded} tickets added </p>
    </div>
  );
};

export default TicketStatus;
