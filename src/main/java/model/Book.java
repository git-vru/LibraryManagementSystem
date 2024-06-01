package model;

import java.time.LocalDate;

public class Book {
    public final static String[] FORMAT = new String[]{"%-50s", "%-50s", "%4s", "%17s"};
    public final static String[] COLUMN_NAMES = new String[]{"TITLE", "AUTHOR", "YEAR", "ISBN"};

    private String classificationNumber;
    private final String isbn;
    private final String title;
    private final String author;
    private final LocalDate publicationDate;

    private int copyCount;

    public Book(String title, String author, String isbn, LocalDate dateOfFirstPublication, String classificationNumber) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationDate = dateOfFirstPublication;
        this.classificationNumber = classificationNumber;

        copyCount = 0;
    }

    public Book getBook() {
        return this;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public String getClassificationNumber() {
        return classificationNumber;
    }

    public void setClassificationNumber(String classificationNumber) {
        this.classificationNumber = classificationNumber;
    }

    public int getCopyCount() {
        return copyCount;
    }

    public void setCopyCount(int copyCount) {
        this.copyCount = copyCount;
    }

    public void increaseCopyCount() {
        this.copyCount++;
    }

    public void decreaseCopyCount() {
        this.copyCount--;
    }

    public String toString() {
        return String.format("Classification Number:%s\nTitle:%s\nAuthor:%s\nDate of first publication:%s\nNb of physical copies:%d\n---\n", classificationNumber, title, author, publicationDate.toString(), copyCount);
    }

    public String toCsv() {
        return String.format("%s;%s;%tY;%s", title, author, publicationDate, isbn);
    }
}
