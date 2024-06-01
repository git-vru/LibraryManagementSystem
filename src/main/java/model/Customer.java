package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    public final static String[] FORMAT = new String[]{"%8s", "%-30s", "%-30s", "%20s", "%5s"};
    public final static String[] COLUMN_NAMES = new String[]{"ID", "FIRST NAME", "LAST NAME", "# OF BORROWED BOOKS", "TOTAL FEES"};
    public final static int LINE_SIZE = 109;
    public final static int MAX_CELL_SIZE = 50;

    private final String id;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private final LocalDate subscriptionDate;
    private final ArrayList<BookCopy> borrowedList;

    private static int ID_GENERATOR = 0;

    public Customer(String firstName, String lastName, LocalDate dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;

        ID_GENERATOR += 1;
        this.id = String.valueOf(ID_GENERATOR);
        this.subscriptionDate = java.time.LocalDate.now();
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

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public LocalDate getSubscriptionDate() {
        return subscriptionDate;
    }

    public List<BookCopy> getBorrowedList() {
        return borrowedList;
    }

    public String toString() {
        return String.format("First Name: %s\nLast Name: %s\nDOB: %s\nMember since: %s\n---\nBorrowed Books:\n...\n...", firstName, lastName, dob.toString(), subscriptionDate.toString());
    }

    public String toCsv() {
        return String.format("%s;%s;%s;%d;%.2f", id, firstName, lastName, borrowedList.size(), borrowedList.stream().mapToDouble(BookCopy::getFee).sum());
    }
}
