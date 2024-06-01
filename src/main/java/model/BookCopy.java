package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class BookCopy {
    public final static String[] FORMAT = new String[] {"%-30s", "%-30s", "%4s", "%17s", "%12s", "%14s", "%9s", "%10s", "%11s"};
    public final static String[] COLUMN_NAMES = new String[]{"TITLE", "AUTHOR", "YEAR", "ISBN", "BOOK COPY ID", "SHELF LOCATION", "AVAILABLE", "START DATE", "RETURN DATE"};

    private final String id;
    private final Book book;
    private boolean isBorrowed;

    private LocalDate borrowedDate;
    private LocalDate returnedDate;
    private float fee;

    public BookCopy(Book book) {
        this.book = book;
        this.isBorrowed = false;
        book.increaseCopyCount();
        this.id = book.getClassificationNumber() + "_" + book.getCopyCount();
    }

    public boolean isBorrowed() {
        return this.isBorrowed;
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

    public Book getBook() {
        return book;
    }

    public void setIsBorrowed(boolean isBorrowed) {
        this.isBorrowed = isBorrowed;
    }

    public String toString() {
        return String.format("%s", book);
    }

    public String toCsv() {
        return String.format("%s;%s;%s;%s;%s;%s", book.toCsv(), id, book.getClassificationNumber(), isBorrowed ? "no" : "yes", borrowedDate == null ? "--/--/----" : borrowedDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)),  returnedDate == null ? "--/--/----" : returnedDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
    }
}
