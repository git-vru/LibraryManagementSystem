package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class BookCopy {
    private final String id;
    private final Book book;
    private Customer borrower;

    private LocalDate borrowedDate;
    private LocalDate returnedDate;
    private float fee;

    public BookCopy(Book book) {
        this.book = book;
        this.borrower = null;
        book.increaseCopyCount();
        this.id = book.getClassificationNumber() + "_" + book.getCopyCount();
    }

    public boolean isBorrowed() {
        return this.borrower != null;
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

    public String toCsv() {
        return String.format("%s;%s;%s;%s;%.2f", book.toCsv(), borrower == null ? "---" : borrower.getId(), borrowedDate == null ? "--/--/----" : borrowedDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)),  returnedDate == null ? "--/--/----" : returnedDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)), fee);
    }
}
