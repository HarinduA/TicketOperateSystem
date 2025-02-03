# Ticket Management System

This project is a simple Ticket Management System built using React. It includes components to configure system parameters, control the system, and display ticket status.

## Components

### 1. ConfigurationForm (Configuration.js)
The `ConfigurationForm` component allows users to configure key system parameters:
- **Total Tickets**: The total number of tickets available.
- **Maximum Ticket Capacity**: The highest number of tickets the system can handle.
- **Total Customers**: The number of customers in the system.
- **Ticket Release Rate**: The rate (in seconds) at which tickets are released.
- **Customer Retrieval Rate**: The rate (in seconds) at which customers retrieve tickets.
- **Number of Vendors**: The total vendors managing ticket distribution.

It includes input validation to ensure only numerical values are entered.

### 2. ControlPanel (ControlPanel.js)
The `ControlPanel` component provides buttons to control the system:
- **Start**: Begins the ticketing system operations.
- **Stop**: Halts the ticketing system.
- **Reset**: Resets the system parameters.
- **Load Saved Config**: Loads a previously saved configuration.

### 3. TicketStatus (TicketStatus.js)
The `TicketStatus` component displays the current ticket status:
- **Available Tickets**: The number of tickets still available.
- **Total Tickets Added**: The cumulative number of tickets added to the system.

## Installation and Setup
1. Clone this repository:
   ```sh
   git clone https://github.com/your-repo/ticket-management-system.git
   cd ticket-management-system
   ```
2. Install dependencies:
   ```sh
   npm install
   ```
3. Run the development server:
   ```sh
   npm start
   ```

## Usage
- Use the **Configuration Form** to set initial parameters.
- Use the **Control Panel** to start, stop, or reset the system.
- Check the **Ticket Status** to monitor ticket availability.

## Technologies Used

## Frontend
- React.js
- JavaScript (ES6+)
- CSS (for styling)

## Backend
- Spring Boot
  

