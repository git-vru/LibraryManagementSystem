package model;

import java.time.LocalDate;

public class PhysicalBook {
    private final String id;
    private final Book book;
    private Customer borrower;

    private LocalDate borrowedDate;
    private LocalDate returnedDate;
    private float fee;

    public PhysicalBook(Book book) {
        this.book = book;
        this.borrower = null;
        this.id = "Generated consecutively";
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(LocalDate borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }
    public String getId() {
        return id;
    }

    public Customer getBorrower() {
        return borrower;
    }

    public Book getBook() {
        return book;
    }

    public void setBorrower(Customer borrower) {
        this.borrower = borrower;
    }

    public String toString() {
        return String.format("%s", book);
    }
}
