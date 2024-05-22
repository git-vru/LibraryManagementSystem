package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Book {
    private String classificationNumber;
    private final String isbn;
    private final String title;
    private final String author;
    private final LocalDate publicationDate;

    public Book(String title, String author, String isbn, LocalDate dateOfFirstPublication, String classificationNumber) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationDate = dateOfFirstPublication;
        this.classificationNumber = classificationNumber;
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

    public String toString() {
        return String.format("Classification Number:%s\nTitle:%s\nAuthor:%s\nDate of first publication:%s\n---\n", classificationNumber, title, author, publicationDate.toString());
    }
}
