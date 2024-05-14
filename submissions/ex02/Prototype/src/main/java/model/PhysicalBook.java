package model;

import model.Customer;

import java.time.LocalDate;

public class PhysicalBook {
    private final String id;
    private final Book book;
    private final LocalDate publicationDate;
    private Customer borrower;

    public PhysicalBook(Book book, LocalDate publicationDate) {
        this.book = book;
        this.publicationDate = publicationDate;

        this.borrower = null;
        this.id = "Generated consecutively";
    }

    public PhysicalBook(Book book, LocalDate publicationDate, Customer borrower) {
        this(book, publicationDate);
        this.borrower = borrower;
    }

    public String getId() {
        return id;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public Customer getBorrower() {
        return borrower;
    }

    public void setBorrower(Customer borrower) {
        this.borrower = borrower;
    }

    public Book getBook() {
        return book;
    }

    public String toString() {
        return String.format("%sDate of publication:%s\n---", book, publicationDate);
    }
}
