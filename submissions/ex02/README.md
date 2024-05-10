# Structure of the software requirements specification (SRS) for library management system

### 1. **Introduction:**
*This section specifies a Library Management System (LMS), including its purpose and scope.*

The Library Management System (LMS) is a program software in Java (using the command line) for managing books and customers of a library. The goal is to keep a reccord of all the books and their physical copies, as well as the customers and allowing them to borrow a book following some conditions.  

### 2. **General Description:** details about the Library Management System, including its implementation, core classes, and how the library staff uses it.

- *Domain Model: explains the system's domain model, including the classes Customer, Book, and BookCopy.*

- *Use Case Diagram: describes the use cases of the system, divided into groups like Data Management, Reporting, and core functions.*
- Data Management :
	- Create an entry (book, bookcopy, customer)
	- Delete an entry (book, bookcopy, custormer)
	- Modify an entry
- Reporting :
	- Borrowing a book
	- Returning a book
- Core functions :
	- Search for an entry

### 3. **Specific Requirements:**

- Functional Requirements: enumerates the system's functional requirements, including importing CSV files, deleting entities, searching, borrowing, and returning book copies, and reporting.

- Non-Functional Requirements: outlines the non-functional requirements such as using Java, efficiency, user-friendliness, and system maintainability.

### 4. **Tables:** includes use case tables for borrowing and returning a copy of a book, detailing the goals, actors, preconditions, postconditions, standard procedure, and handling of particular cases.
