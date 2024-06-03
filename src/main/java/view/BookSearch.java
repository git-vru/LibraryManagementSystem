package view;

import controller.Controller;
import exceptions.BorrowingNotNullException;
import model.Book;
import model.BookCopy;

import java.util.List;
import java.util.Scanner;

public class BookSearch extends View {
    private Book book;

    public BookSearch(Controller controller, View prev) {
        super(controller, prev);
        this.name = "Book Info";
        this.book = null;
    }

    public void show() {
        List<String> searchByMenu = List.of("ISBN", "Book Name", "Author");

        char inputChar = super.promptOptions(searchByMenu);
        switch (inputChar) {
            case 'q' -> prev.show();
            case '0' -> System.out.print("Please enter a book ISBN: ");
            case '1' -> System.out.print("Please enter a book name: ");
            case '2' -> System.out.print("Please enter an author name: ");
        }

        String token = new Scanner(System.in).nextLine();

        List<Book> foundBooks = controller.searchBook(inputChar-'0', token);

        if (foundBooks.isEmpty()) {
            System.out.println("Couldn't find the book you were searching for.");
            prev.show();
        }
        else if (foundBooks.size() == 1) {
            System.out.println("Found only 1 book with the given token: " + token);
            book = foundBooks.get(0);
            System.out.println(book);
        }
        else {
            System.out.println("Select the book you want to operate on.");
            List<String> foundBookSelectionMenu = foundBooks.stream()
                            .map(Book::toString)
                            .toList();
            inputChar = super.promptOptions(foundBookSelectionMenu);
            if (inputChar == 'q') prev.show();
            else book = foundBooks.get(inputChar-'0');
        }

        List<String> options = List.of(
                "Delete the book : " + book.getTitle() + " - " + book.getIsbn(),
                "Delete a copy of this book."
        );

        inputChar = super.promptOptions(options);
        String bookISBN = book.getIsbn();

        if (inputChar == '0') {
            try {
                controller.deleteBook(bookISBN);
                System.out.println("Book with the ISBN : " + bookISBN + " has been successfully deleted!");
            }
            catch (BorrowingNotNullException e) {
                System.out.println(e.getMessage());
            }
        }
        else if (inputChar == '1') {
            BookCopy bookCopy = null;
            while (bookCopy == null) {
                System.out.print("Please enter a book copy ID: ");
                String bookCopyID = controller.getScanner().next();
                if (bookCopyID.equals("q")) break;

                bookCopy = controller.searchBookCopy(book, bookCopyID);
                if (bookCopy == null) {
                    System.out.println("---\nPlease enter a valid ID!\n");
                }
                else {
                    try {
                        controller.deleteBookCopy(bookCopyID);
                        System.out.println("Book copy with the ID : " + bookCopyID + " has been successfully deleted!");
                        break;
                    } catch (BorrowingNotNullException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        prev.show();
    }
}
