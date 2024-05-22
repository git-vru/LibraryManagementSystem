package view;

import controller.Controller;
import model.Book;

public class BookInfo extends View {

    public BookInfo(Controller controller, View prev) {
        super(controller, prev);
        this.name = "Book Info";
    }
    
    public void show() {
        System.out.print("Please enter a book id:");
        Book b = controller.search(controller.getBooks().keySet().stream().toList(), controller.getScanner().next());

        this.name = "Book Id: " + b.getClassificationNumber();

        super.promptAndExit(b.toString());
        prev.show();
    }
}
