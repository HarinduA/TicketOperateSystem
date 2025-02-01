Here’s a **complete `README.md` file** for your backend and frontend ticketing system. This document provides an introduction, setup instructions, and usage instructions, including details for configuring and running both the Spring Boot backend and React frontend.

---

# 🎟️ Real-Time Ticketing System

## Introduction

The **Real-Time Ticketing System** is a full-stack application designed to simulate real-time ticket sales. The system consists of a **Spring Boot backend** and a **React frontend**. Vendors add tickets to a pool, and customers attempt to purchase tickets concurrently. The system allows you to configure various parameters such as the number of tickets, the rate at which tickets are released, and the number of customers. Real-time updates are displayed via the frontend.

---

## Setup Instructions

### Prerequisites

Ensure the following software is installed on your system:

1. **Java Development Kit (JDK) 17 or higher**
    - [Download JDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
2. **Apache Maven**
    - [Download Maven](https://maven.apache.org/download.cgi)
3. **Node.js and npm (Node Package Manager)**
    - [Download Node.js](https://nodejs.org/en/download/)
4. **IDE or Code Editor**
    - Recommended: **IntelliJ IDEA**, **VSCode**, or **Eclipse**

### Backend Setup (Spring Boot)

1. **Clone the repository:**

   ```bash
   git clone https://github.com/your-username/real-time-ticketing-system.git
   cd real-time-ticketing-system/backend
   ```

2. **Build the project using Maven:**

   ```bash
   mvn clean install
   ```

3. **Run the Spring Boot application:**

   ```bash
   mvn spring-boot:run
   ```

   The backend should now be running at `http://localhost:8080`.

4. **API Endpoints:**

    - `POST /api/start`: Starts the system with the given configuration.
    - `GET /api/status`: Retrieves the number of available tickets.
    - `POST /api/stop`: Stops the system.
    - `POST /api/reset`: Resets the system.
    - `GET /api/savedConfig`: Retrieves the saved configuration from a file.

### Frontend Setup (React)

1. **Navigate to the frontend directory:**

   ```bash
   cd ../frontend
   ```

2. **Install dependencies:**

   ```bash
   npm install
   ```

3. **Run the React application:**

   ```bash
   npm start
   ```

   The frontend should now be running at `http://localhost:3000`.

---

## Usage Instructions

### Configuring and Starting the System

1. **Access the UI** by navigating to `http://localhost:3000` in your web browser.

2. **Configure the parameters** in the form:
    - **Total Tickets**: The total number of tickets to be released.
    - **Maximum Ticket Capacity**: The maximum number of tickets that can be in the pool at one time.
    - **Total Customers**: The number of customers attempting to buy tickets.
    - **Ticket Release Rate (sec)**: The interval at which vendors release tickets (in seconds).
    - **Customer Retrieval Rate (sec)**: The interval at which customers attempt to buy tickets (in seconds).
    - **Number of Vendors**: The number of vendors adding tickets.

3. **Start the system** by clicking the **"Start"** button.

### UI Controls

- **Start**: Begins the ticketing process based on the configured parameters.
- **Stop**: Stops all ongoing vendor and customer operations.
- **Reset**: Resets the system to its initial state.
- **Load Saved Config**: Loads the last saved configuration from the backend.

### Real-Time Updates

- The **"Available Tickets"** display shows the current number of tickets in the pool.
- As customers buy tickets, the number of available tickets decreases dynamically.
- Once all tickets are sold and all vendors finish adding tickets, the message **" Tickets are finished. TicketPool closed. 🎉🎉"** is displayed.

---

## Example Workflow

1. **Set Configuration**:
    - Total Tickets: `100`
    - Maximum Ticket Capacity: `10`
    - Total Customers: `5`
    - Ticket Release Rate: `2` seconds
    - Customer Retrieval Rate: `1` second
    - Number of Vendors: `2`

2. **Start the System**:
    - Click **"Start"**.
    - Vendors add tickets every 2 seconds.
    - Customers attempt to buy tickets every 1 second.

3. **Monitor the System**:
    - Watch the available tickets decrease in real-time.
    - Once all tickets are sold, the system displays the closing message.

---

## Troubleshooting

1. **Backend Not Starting**:
    - Ensure no other application is running on port `8080`.
    - Check that the Maven build is successful.

2. **Frontend Not Starting**:
    - Ensure no other application is running on port `3000`.
    - Run `npm install` to ensure all dependencies are installed.

3. **CORS Errors**:
    - Ensure the backend allows requests from `http://localhost:3000` (handled in `WebConfig`).

4. **Configuration File Not Found**:
    - Ensure the path to `configurations.txt` in `TicketController.java` is correct.

---

## Project Structure

```
real-time-ticketing-system/
│
├── backend/
│   ├── src/main/java/com/example/ticketing_system/
│   │   ├── TicketingSystemApplication.java
│   │   ├── TicketController.java
│   │   ├── TicketPool.java
│   │   ├── Customer.java
│   │   ├── Vendor.java
│   │   └── Configuration.java
│   └── pom.xml
│
└── frontend/
    ├── src/
    │   ├── components/
    │   │   ├── Configuration.js
    │   │   ├── ControlPanel.js
    │   │   └── TicketStatus.js
    │   │
    │   ├── App.js
    │   ├── index.js
    │   └── App.css
    └── package.json

```

---

## License

This project is licensed under the **MIT License**.

---

## Contact

For issues or contributions, please contact:  
**Your Name**  
**Email**: `Harinduadhikari@gmail.com`  
**GitHub**: [Your GitHub Profile](https://github.com/harinduA)

---

This `README.md` covers the introduction, setup, and usage of your system, ensuring clarity for users and developers.