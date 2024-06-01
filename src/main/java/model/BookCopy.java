package model;

import java.time.LocalDate;

// Book Copy Data: [Book Data] + ID, Shelf Location, Borrowing Status, Borrow Date
public class BookCopy {
    private final Book book;
    private final String id;
    private final String shelfLocation;
    private boolean isBorrowed;
    private LocalDate borrowedDate;
    private LocalDate returnedDate;

    private Customer borrower;

    // preset static fee
    private static float fee;

    public BookCopy(Book book) {
        this.book = book;
        this.id = book.getClassificationNumber() + "_" + book.getCopyCount();
        this.shelfLocation = book.getClassificationNumber();
        this.borrower = null;
        book.increaseCopyCount();
    }

    public BookCopy(Book book,
                    String id,
                    boolean isBorrowed,
                    LocalDate borrowedDate) {
        this.book = book;
        this.id = id;
        this.shelfLocation = book.getClassificationNumber();
        this.isBorrowed = isBorrowed;
        this.borrowedDate = borrowedDate;
        this.returnedDate = null;
        this.borrower = null;
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
