package model;

public class PhysicalBook {
    private final String id;
    private final Book book;
    private Customer borrower;

    public PhysicalBook(Book book) {
        this.book = book;
        this.borrower = null;
        this.id = "Generated consecutively";
    }

    public PhysicalBook(Book book, Customer borrower) {
        this(book);
        this.borrower = borrower;
    }

    public String getId() {
        return id;
    }

    public Customer getBorrower() {
        return borrower;
    }

    public void setBorrower(Customer borrower) {
        this.borrower = borrower;
    }

    public String toString() {
        return String.format("%s", book);
    }
}
