package model;

import java.time.LocalDate;

public class Borrowing {
    private final String id;
    private final PhysicalBook book;
    private final Customer borrower;
    private final LocalDate startDate;
    private LocalDate endDate;
    private boolean extended;
    private double lateFee;

    public Borrowing(PhysicalBook book, Customer borrower, LocalDate startDate, LocalDate endDate) {
        this.book = book;
        this.borrower = borrower;
        this.startDate = startDate;
        this.endDate = endDate;

        this.id = "Generated consecutively";
        this.extended = false;
        this.lateFee = 0.0;
    }

    public String getId() {
        return id;
    }

    public PhysicalBook getBook() {
        return book;
    }

    public Customer getBorrower() {
        return borrower;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public double getLateFee() {
        return lateFee;
    }

    public void setLateFee(double lateFee) {
        this.lateFee = lateFee;
    }

    public String toString() {
        return String.format("%s\nBorrower:\n%s\n---\nStart:%s\nEnd:%s\nExtended:%b\nFees:%f\n", book, borrower, startDate.toString(), endDate.toString(), extended, lateFee);
    }
}
