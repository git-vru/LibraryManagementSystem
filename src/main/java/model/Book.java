package model;

import view.View;

import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.max;

// Book Data: Title, Authors, ISBN, Year
public class Book {
    public final static String[] FORMAT = new String[]{"%-30s", "%-30s", "%-20s", "%4s", "%17s"};
    public final static String[] COLUMN_NAMES = new String[]{"TITLE", "AUTHOR", "PUBLISHER", "YEAR", "ISBN"};

    private String classificationNumber;
    private final String isbn;
    private String title;
    private String author;
    private LocalDate publicationDate;
    private String publisher;

    private int copyCount;

    public Book(String title, String author, String isbn, LocalDate dateOfFirstPublication, String classificationNumber, String publisher) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationDate = dateOfFirstPublication;
        this.classificationNumber = classificationNumber;
        this.publisher = publisher;

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

    public String getPublisher() {
        return publisher;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setClassificationNumber(String classificationNumber) {
        this.classificationNumber = classificationNumber;
    }

    public int getCopyCount() {
        return copyCount;
    }

    public void increaseCopyCount() {
        this.copyCount++;
    }

    public void decreaseCopyCount() {
        this.copyCount--;
    }

    public String toString() {
        List<String> data = List.of(title, author, isbn, classificationNumber, publisher, publicationDate.toString());
        int maxLength = max(data.stream().map(String::length).toList());

        String header = View.addPadding2Text(title, 24 + maxLength);

        return String.format(
                "-------------------------" + "-".repeat(maxLength+2) + "-\n" +
                "| %s|\n" +
                "|------------------------" + "-".repeat(maxLength+2) + "|\n" +
                "| Author                | %-" + maxLength + "s |\n" +
                "| ISBN                  | %-" + maxLength + "s |\n" +
                "| Classification Number | %-" + maxLength + "s |\n" +
                "| Publisher             | %-" + maxLength + "s |\n" +
                "| Publication Date:     | %-" + maxLength + "s |\n" +
                "| # of physical copies  | %-" + maxLength + "d |\n" +
                "-------------------------" + "-".repeat(maxLength+2) + "-",
                header, author, isbn, classificationNumber, publisher, publicationDate, copyCount
        );
    }

    public String toCsv() {
        return String.format("%s;%s;%s;%tY;%s", title, author, publisher, publicationDate, isbn);
    }
}
