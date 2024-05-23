package view;

import controller.Controller;
import exceptions.BorrowingNotNullException;
import model.Book;
import model.PhysicalBook;

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
            if (bookISBN.equals("q")) break;

            book = controller.searchBook(bookISBN);

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

            String input = super.prompt(options);

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
                PhysicalBook physicalBook = null;
                while (physicalBook == null) {
                    System.out.print("Please enter a physical book ID: ");
                    String physicalBookID = controller.getScanner().next();
                    if (physicalBookID.equals("q")) break;

                    physicalBook = controller.searchPhysicalBook(book, physicalBookID);
                    if (physicalBook == null) {
                        System.out.println("---\nPlease enter a valid ID!\n");
                    }
                    else {
                        try {
                            controller.deletePhysicalBook(physicalBookID);
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
