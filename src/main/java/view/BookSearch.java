package view;

import controller.Controller;
import exceptions.BorrowingNotNullException;
import model.Book;
import model.BookCopy;

import java.util.List;

public class BookSearch extends View {

    public BookSearch(Controller controller, View prev) {
        super(controller, prev);
        this.name = "Book Info";
    }

    // TODO null-check
    public void show() {
        Book book = null;
        while (book == null) {
            System.out.print("Please enter a book ISBN: ");
            String bookISBN = controller.getScanner().next();
            if (bookISBN.equals("q")) break;

            book = controller.searchBookViaIsbn(bookISBN);

            if (book == null) {
                System.out.println("---\nPlease enter a valid ISBN!\n");
                continue;
            }

            this.name = "Book Id: " + book.getClassificationNumber();

            System.out.printf("\t** %s **\n", book.getClassificationNumber());
            System.out.println(book);

            List<String> options = List.of(
                    "Delete the book : " + book.getClassificationNumber(),
                    "Delete a copy of this book");

            String input = super.promptOptions(options);

            if (input.charAt(0) == '0') {
                try {
                    controller.deleteBook(bookISBN);
                    System.out.println("Book with the ISBN : " + bookISBN + " has been successfully deleted!");
                }
                catch (BorrowingNotNullException e) {
                    System.out.println(e.getMessage());
                }
            }
            else if (input.charAt(0) == '1') {
                BookCopy bookCopy = null;
                while (bookCopy == null) {
                    System.out.print("Please enter a book copy ID: ");
                    String bookCopyID = controller.getScanner().next();
                    if (bookCopyID.equals("q")) break;

                    bookCopy = controller.searchbookCopy(book, bookCopyID);
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
            else if (input.charAt(0) == 'q') {
                break;
            }
        }
        prev.show();
    }
}
