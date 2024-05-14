package model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Customer {
    private final String id;
    private String firstName;
    private String lastName;
    private final LocalDate dob;
    private final LocalDate subscriptionDate;
    private final Set<Borrowing> borrowedList;

    public Customer(String firstName, String lastName, LocalDate dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;

        this.id = "03781849"; //Will be generated consecutively
        this.subscriptionDate = java.time.LocalDate.now();
        this.borrowedList = new HashSet<>();
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

    public LocalDate getSubscriptionDate() {
        return subscriptionDate;
    }

    public Set<Borrowing> getBorrowedList() {
        return borrowedList;
    }

    public String toString() {
        return String.format("First Name:%s\nLast Name:%s\nDOB:%s\nMember since:%s\n---\nBorrowed Book.s:\n...\n...", firstName, lastName, dob.toString(), subscriptionDate.toString());
    }
}
