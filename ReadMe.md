# ClockIt – Faculty Office Hours Manager

A desktop application built with Java and JavaFX designed to help faculty members manage their office hours efficiently. ClockIt allows users to define semester schedules, create time slots, track student appointments, and generate reports — all in a simple and user-friendly interface. The app is designed to be lightweight, portable, and accessible to faculty with minimal technical experience.

**Collaborators:** Izabella Doser, Tabassum Zahir, Mariam Jamil, Yadanar Lin  
**CS151 San José State University Term Project**

---

## 🚀 Features

### Functional Features

#### Semester Setup
- Define semester and year (e.g., Spring 2025).  
- Prevents duplicate semester–year combinations for cleaner data.

#### Time Slot Management
- Create customizable time slots with defined start and end times.  
- View, edit, and reuse time slots across schedules.

#### Course Management
- Add new courses with code, name, and section.  
- Prevents duplicate course entries to maintain consistency.

#### Office Hours Scheduling
- Schedule appointments by entering student name, date, selected time slot, and course.  
- Include optional reason and comments fields.  
- Supports search, edit, and delete functionality.

#### Reports
- View all appointments for a given course–section.  
- Get notified of scheduled appointments for the current day upon app launch.

---

## 💾 Data Storage

- All user and application data is stored locally using structured CSV files.  
- Ensures data persists across sessions and allows users to edit and manage information as needed.  
- CSV file structure simplifies portability and transparency.
