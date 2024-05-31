package model;

import view.View;

import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.max;

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

    /**
     * STYLES
     ┌─────┬─────┐      +-----+-----+      ═════════════      ╒═════╤═════╕      ╔═════╦═════╗
     │rc 11│rc 12│      |rc 11|rc 12|       rc 11 rc 12       │rc 11│rc 12│      ║rc 11║rc 12║
     ├─────┼─────┤      +-----+-----+      ═════════════      ╞═════╪═════╡      ╠═════╬═════╣
     │rc 21│rc 22│      |rc 21|rc 22|       rc 21 rc 22       │rc 21│rc 22│      ║rc 21║rc 22║
     └─────┴─────┘      +-----+-----+      ═════════════      ╘═════╧═════╛      ╚═════╩═════╝
     */
    //TODO: Title, Authors, ISBN, !ID, !Shelf Location, !Borrowing Status, !Borrow Date
    public String toString() {
        List<String> data = List.of(title, author, isbn, classificationNumber, publicationDate.toString());
        int maxLength = max(data.stream().map(String::length).toList());

        String header = View.addPadding2Text(title, 24 + maxLength);

        return String.format(
                "┌────────────────────────" + "─".repeat(maxLength+2) + "┐\n" +
                "│ %s│\n" +
                "├────────────────────────" + "─".repeat(maxLength+2) + "┤\n" +
                "│ Author                │ %-" + maxLength + "s │\n" +
                "│ ISBN                  │ %-" + maxLength + "s │\n" +
                "│ Classification Number │ %-" + maxLength + "s │\n" +
                "│ Publication Date:     │ %-" + maxLength + "s │\n" +
                "│ # of physical copies  │ %-" + maxLength + "d │\n" +
                "└───────────────────────┴" + "─".repeat(maxLength+2) + "┘",
                header, author, isbn, classificationNumber, publicationDate, copyCount
        );
    }
}
