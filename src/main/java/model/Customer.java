package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Customer {
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

    public ArrayList<BookCopy> getBorrowedList() {
        return borrowedList;
    }

    public String toString() {
        return String.format("First Name: %s\nLast Name: %s\nDOB: %s\nMember since: %s\n---\nBorrowed Books:\n...\n...", firstName, lastName, dob.toString(), subscriptionDate.toString());
    }
}
