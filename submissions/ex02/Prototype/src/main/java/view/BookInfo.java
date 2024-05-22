package view;

import controller.Controller;
import exceptions.BorrowingNotNullException;
import model.Book;

import java.util.List;
import java.util.Scanner;

public class BookInfo extends View {

    public BookInfo(Controller controller, View prev) {
        super(controller, prev);
        this.name = "Book Info";
    }

    // TODO null-check
    public void show() {
        Book book = null;
        while (book == null) {
            System.out.print("Please enter a book ISBN: ");
            String bookISBN = controller.getScanner().next();

            book = controller.searchBook(bookISBN);

            if (book == null) {
                System.out.println("---\nPlease enter a valid ISBN!\n");
                continue;
            }

            this.name = "Book Id: " + book.getClassificationNumber();

            System.out.printf("\t** %s **\n", book.getClassificationNumber());
            System.out.println(book);

            List<String> options = List.of("Delete the book : " + book.getClassificationNumber());

            String input = super.prompt(options);

            if (input.charAt(0) == '0') {
                try {
                    controller.deleteBook(bookISBN);
                }
                catch (BorrowingNotNullException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (input.charAt(0) == 'q') {
                break;
            }
        }
        prev.show();
    }
}
