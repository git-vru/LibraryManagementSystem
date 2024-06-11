package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

// Book Copy Data: [Book Data] + ID, Shelf Location, Borrowing Status, Borrow Date

public class BookCopy {
    public final static String[] FORMAT = new String[] {"%-20s", "%-20s", "%-20s", "%4s", "%17s", "%12s", "%14s", "%9s", "%10s", "%11s"};
    public final static String[] COLUMN_NAMES = new String[]{"TITLE", "AUTHOR", "PUBLISHER", "YEAR", "ISBN", "BOOK COPY ID", "SHELF LOCATION", "AVAILABLE", "START DATE", "RETURN DATE"};

    private final String id;
    private final Book book;
    private String customerId;

    private LocalDate borrowedDate;
    private LocalDate returnedDate;
    private float fee;

    public BookCopy(Book book) {
        this.book = book;
        this.customerId = "";
        book.increaseCopyCount();
        this.id = book.getClassificationNumber() + "_" + book.getCopyCount();
    }

    public BookCopy(Book book, String id, String customerId, LocalDate borrowedDate, LocalDate returnedDate, float fee) {
        this.book = book;
        this.customerId = customerId;

        if (id.isEmpty()) {
            book.increaseCopyCount();
            this.id = book.getClassificationNumber() + "_" + book.getCopyCount();
        }
        else {
            this.id = id;
        }

        if (!customerId.isEmpty()) {
            this.returnedDate = returnedDate;
            this.borrowedDate = borrowedDate;
            this.fee = fee;
        }
    }

    public boolean isBorrowed() {
        return !this.customerId.isEmpty();
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

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void removeCustomer() {
        this.customerId = "";
    }

    //ToDo change to string
    public String toString() {
        return String.format("%s", book);
    }

    public String toCsv() {
        return String.format("%s;%s;%s;%s;%s;%s", book.toCsv(), id, book.getClassificationNumber(), customerId.isEmpty() ? "yes" : "no", borrowedDate == null ? "--/--/----" : borrowedDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)),  returnedDate == null ? "--/--/----" : returnedDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
    }
}
