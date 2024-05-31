package model;

import java.time.LocalDate;

public class Book {
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

    //TODO: Title, Authors, ISBN, !ID, !Shelf Location, !Borrowing Status, !Borrow Date
    public String toString() {
        return String.format(
                """
                Title: %s
                Author: %s
                ISBN: %s
                Classification Number: %s
                Publication Date: %s
                # of physical copies: %d
                """,
                title, author, isbn, classificationNumber, publicationDate.toString(), copyCount
        );
    }
}
