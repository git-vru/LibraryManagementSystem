package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class BookCopy {
    public final static String[] FORMAT = new String[] {"%-30s", "%-30s", "%-4s", "%17s", "%-12s", "%-14s", "%11s", "%-10s", "%11s"};
    public final static String[] COLUMN_NAMES = new String[]{"TITLE", "AUTHOR", "YEAR", "ISBN", "BOOK COPY ID", "SHELF LOCATION", "BORROWER ID", "START DATE", "RETURN DATE"};
    public final static int LINE_SIZE = Book.LINE_SIZE + 75;
    public final static int MAX_CELL_SIZE = 30;

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
        return String.format("%s;%s;%s;%s;%s;%s", book.toCsv(), id, book.getClassificationNumber(), borrower == null ? "---" : borrower.getId(), borrowedDate == null ? "--/--/----" : borrowedDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)),  returnedDate == null ? "--/--/----" : returnedDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
    }
}
