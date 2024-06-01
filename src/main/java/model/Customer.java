package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    public final static String[] FORMAT = new String[]{"%8s", "%-30s", "%-30s", "%20s", "%10s"};
    public final static String[] COLUMN_NAMES = new String[]{"ID", "FIRST NAME", "LAST NAME", "# OF BORROWED BOOKS", "TOTAL FEES"};

    private final String id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private int paymentStatus;
    private final List<BookCopy> borrowedList;

    private final LocalDate subscriptionDate;

    private static int ID_GENERATOR = 0;

    public Customer(String firstName, String lastName, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;

        ID_GENERATOR += 1;
        this.id = String.valueOf(ID_GENERATOR);
        this.subscriptionDate = java.time.LocalDate.now();
        this.borrowedList = new ArrayList<>();
    }

    // For parsing from CSV
    // Customer CSV Data: ID, FirstName, LastName, Date of Birth, Subscription Date
    public Customer(String id, String firstName, String lastName, LocalDate dateOfBirth, LocalDate subscriptionDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.subscriptionDate = subscriptionDate;

        this.borrowedList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getSubscriptionDate() {
        return subscriptionDate;
    }

    public List<BookCopy> getBorrowedList() {
        return borrowedList;
    }

    public String toString() {
        return String.format("First Name: %s\nLast Name: %s\nDOB: %s\nMember since: %s\n---\nBorrowed Books:\n...\n...", firstName, lastName, dateOfBirth.toString(), subscriptionDate.toString());
    }

    public String toCsv() {
        return String.format("%s;%s;%s;%d;%.2f", id, firstName, lastName, borrowedList.size(), borrowedList.stream().mapToDouble(BookCopy::getFee).sum());
    }
}
