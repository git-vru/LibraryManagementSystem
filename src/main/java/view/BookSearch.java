package view;

import controller.Controller;
import exceptions.BorrowingNotNullException;
import model.Book;
import model.BookCopy;

import java.time.format.DateTimeParseException;
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
                    "Delete a copy of this book",
                    "Modify the title of book: "+ book.getClassificationNumber(),
                    "Modify the author of book: "+ book.getClassificationNumber(),
                    "Modify the publication date of book: "+ book.getClassificationNumber(),
                    "Modify the classification number of book: "+ book.getClassificationNumber());

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
            } else if (input.charAt(0) == '2') {
                System.out.println("Please enter a new title: ");
                controller.getScanner().nextLine();

                controller.modifyBook(book, controller.getScanner().nextLine(), book.getAuthor(), book.getPublicationDate().toString(), book.getClassificationNumber());
                super.promptAndExit("Title was successfully changed!");

                this.show();
            } else if (input.charAt(0) == '3') {
                System.out.println("Please enter a new author: ");
                controller.getScanner().nextLine();
                controller.modifyBook(book, book.getTitle(), controller.getScanner().nextLine(), book.getPublicationDate().toString(), book.getClassificationNumber());
                super.promptAndExit("Title was successfully changed!");

                this.show();
            } else if (input.charAt(0) == '4') {
                System.out.println("Please enter a new publication date <YYYY-MM-DD>: ");
                try {
                    controller.modifyBook(book, book.getTitle(), book.getAuthor(), controller.getScanner().next(), book.getClassificationNumber());
                    super.promptAndExit("Publication date was successfully changed!");
                } catch (DateTimeParseException dateTimeParseException) {
                    super.promptAndExit("Please pass a valid value!");
                }


                this.show();
            } else if (input.charAt(0) == '5') {
                System.out.println("Please enter a new classification number: ");

                controller.modifyBook(book, book.getTitle(), book.getAuthor(), book.getPublicationDate().toString(), controller.getScanner().next());
                super.promptAndExit("Classification number was successfully changed!");

                this.show();
            }


            else if (input.charAt(0) == 'q') {
                break;
            }
        }
        prev.show();
    }
}
