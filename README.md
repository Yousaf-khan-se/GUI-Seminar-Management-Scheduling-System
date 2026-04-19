# Seminar Management & Scheduling System

A robust Java-based desktop application designed to manage seminars, speakers, and attendee schedules. This project serves as a showcase of clean architectural patterns, GUI development, and algorithmic conflict detection.

## 🚀 Key Concepts Demonstrated
This project isn't just about scheduling; it's about software engineering principles. By exploring this codebase, you can see the implementation of:

* **MVP (Model-View-Presenter) Pattern:** A clear separation between the data (Model), the user interface (View), and the logic that connects them (Presenter).
* **DAO (Data Access Object) Pattern:** Abstracting and encapsulating all access to the data source (files/database) to keep the business logic clean.
* **Interface-Driven Design:** Using interfaces like `IModel` and `IView` to allow for easy testing and future-proofing the code.
* **Conflict Resolution Algorithms:** Logic to detect overlapping schedules where the same room is booked or the same attendee is required in two places at once.



[[Image of Model-View-Presenter Architecture Diagram]](https://encrypted-tbn3.gstatic.com/licensed-image?q=tbn:ANd9GcTpyq4XLmYAOJ8TW2--tw0taMSp9kBpJHa_CHSJLpJOG4W4WLJBZmfQcNgsWfbb-rCy4R5DRQzS1_xQFwHctcrepwWJUftj-LlwOjBgZjW8rAlzzuQ)


---

## ✨ Features

* **Seminar Management:** Create, update, and delete seminars including details for speakers and attendees.
* **Automated Scheduling:** Assign rooms and time slots to seminars.
* **Intelligent Conflict Detection:** * **Room Conflicts:** Identifies if two seminars are booked in the same room at the same time.
    * **Attendee Conflicts:** Identifies if an attendee is scheduled for two different seminars in the same time slot.
* **Data Persistence:** Import seminar and schedule data via `.txt` or `.csv` files.
* **Swing GUI:** A user-friendly desktop interface built with Java Swing.

---

## 🛠️ Project Structure

The project is organized into clear packages to follow the Principle of Least Knowledge:

* `BusinessLogic.Model`: Contains the core data structures (`Seminar`, `Attendee`, `Schedule`) and the scheduling logic.
* `UI.View`: Handles the Java Swing components and user interactions.
* `BusinessLogic.Presenter`: Acts as the "middle-man," reacting to UI events and updating the Model.
* `DataAccessLayer`: Manages file I/O and data persistence logic.

---

## ⚙️ Configuration & Setup

### Prerequisites
* **Java Development Kit (JDK) 17 or higher**
* An IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Installation
1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/seminar-management-system.git
    ```
2.  **Navigate to the project directory:**
    ```bash
    cd seminar-management-system
    ```
3.  **Data Files:** Ensure your input files (e.g., `seminarList.txt`) follow the comma-separated format:
    `SeminarName, SpeakerName, AttendeeID1, AttendeeID2...`

---

## 🏃 How to Run

1.  Open the project in your preferred IDE.
2.  Locate the `src/Main.java` file.
3.  Run the `main` method. 
    * The application uses `SwingUtilities.invokeLater` to ensure the GUI is thread-safe and launches smoothly.

---

## 📊 Algorithmic Insight: Conflict Detection

The system employs a specific logic to ensure event integrity. For instance, when checking for attendee conflicts:
1. It iterates through all scheduled seminars.
2. It identifies seminars sharing the same **TimeSlot**.
3. It performs a set intersection on the attendee IDs to find overlaps.

> **Note:** The `ScheduleList` class uses `HashSet` operations for these checks to ensure the detection remains performant even as the list of attendees grows.

---

### Future Improvements
* [ ] Integration with a SQL database.
* [ ] Exporting conflict reports to PDF.
* [ ] Drag-and-drop scheduling UI.

---

**Developed as an academic project to explore Software Design and Architecture (SCD).**

---
