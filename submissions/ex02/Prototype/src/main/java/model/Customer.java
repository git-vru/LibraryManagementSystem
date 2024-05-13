package model;

import java.time.LocalDate;
import java.util.Date;

public class Customer {
    private final String id;
    private String firstName;
    private String lastName;
    private final LocalDate dob;
    private final LocalDate subscriptionDate;

    public Customer(String firstName, String lastName, LocalDate dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;

        this.id = "03781849"; //Will be generated consecutively
        this.subscriptionDate = java.time.LocalDate.now();
    }

    public String toString() {
        return String.format("**Customer Id: %s**\n---\nFirst Name:%s\nLast Name:%s\nDOB:%s\nMember since:%s\n---\nBorrowed Book.s:\n...\n...", id, firstName, lastName, dob.toString(), subscriptionDate.toString());
    }
}
