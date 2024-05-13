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

Functional Requirements:

1. Add/delete/modify a book
2. Add/delete/modify a customer
3. Add/delete/modify a borrowing
4. Check return date of a borrowing/check fee if return date is in the past
5. Search for a book/customer/borrowing
6. Import a file (CSV)


Non-Functional Requirements:

1. The used language should be java.
2. The application should run most efficient.
3. The system should be user-friendly and easy to use.
4. The system should be easy to maintain by implementing a proper codebase.
5. The code should be secure so no user can break the program.
6. The code should also be secure from any unauthorized access from the outside.


### 4. **Tables:** includes use case tables for borrowing and returning a copy of a book, detailing the goals, actors, preconditions, postconditions, standard procedure, and handling of particular cases.
