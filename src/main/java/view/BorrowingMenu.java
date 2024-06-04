package view;

import controller.Controller;
import model.Book;
import model.BookCopy;
import utilities.CSVreader;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BorrowingMenu extends View {
    private final List<String> options1;

    public BorrowingMenu(Controller controller, View prev) {
        super(controller, prev);

        this.name = "Borrowing Menu";

        this.options1 = List.of("Create a new borrowing","Return a book");
    }

    public void show() {
        char inputChar = super.promptMenu(name, options1);

        if (inputChar == '0') {

        }
        else if (inputChar == '1') {
        }
            prev.show();
    }
}
