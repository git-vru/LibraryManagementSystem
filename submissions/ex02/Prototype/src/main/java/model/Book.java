package model;

import java.time.LocalDate;
import java.util.*;

public class Book {
    private String classificationNumber;
    private final String isbn;
    private final String title;
    private final String author;
    private final LocalDate publicationDate;

    private final List<PhysicalBook> bookList;

    public Book(String title, String author, String isbn, LocalDate dateOfFirstPublication, String classificationNumber, int numberOfCopy) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationDate = dateOfFirstPublication;
        this.classificationNumber = classificationNumber;

        this.bookList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            bookList.add(new PhysicalBook(this));
        }
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

    public List<PhysicalBook> getBookList() {
        return bookList;
    }

    public String toString() {
        return String.format("Classification Number:%s\nTitle:%s\nAuthor:%s\nDate of first publication:%s\n---\n", classificationNumber, title, author, publicationDate.toString());
    }
}
