package view;

import controller.Controller;
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
        System.out.print("Please enter a book ISBN: ");
        String bookISBN = controller.getScanner().next();

        Book book = controller.searchBook(bookISBN);

        this.name = "Book Id: " + book.getClassificationNumber();

        System.out.printf("\t** %s **\n", book.getClassificationNumber());
        System.out.println(book);

        List<String> options = List.of("Delete the book : " + book.getClassificationNumber());

        String input = super.prompt(options);

        if (input.charAt(0) == '0') {
            controller.deleteBook(bookISBN);
        }

        prev.show();
    }
}
