package view;

import controller.Controller;
import model.Book;
import model.BookCopy;
import model.Customer;

import java.io.*;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class CSVreader {

    private List<Book> importedBooks;
    private List<BookCopy> importedBookCopies;
    private List<Customer> importedCustomers;
    private Controller controller;

    public CSVreader() {
        this.importedBooks = new ArrayList<>();
        this.importedBookCopies = new ArrayList<>();
        this.importedCustomers = new ArrayList<>();
        this.controller = new Controller();
    }
    public List<Book> makeBooks(String path) throws FileNotFoundException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            //reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String isbn = parts[0].trim();
                    String title = parts[1].trim();
                    String author = parts[2].trim();
                    Year year = Year.parse(parts[3].trim());
                    LocalDate publicationDate = LocalDate.of(year.getValue(), 1,1);
                    Book book = new Book(title, author, isbn, publicationDate, "SUPA_DIGGA");
                    importedBooks.add(book);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return importedBooks;
    }
    public List<BookCopy> makeBookCopies(String path) throws FileNotFoundException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 1) {
                    String isbn = parts[0].trim();
                    BookCopy bookCopy = new BookCopy(controller.searchBook(1, isbn).get(0));
                    importedBookCopies.add(bookCopy);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return importedBookCopies;
    }
    public List<Customer> makeCustomer(String path) throws FileNotFoundException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String fName = parts[0].trim();
                    String lName = parts[1].trim();
                    LocalDate DOB = LocalDate.parse(parts[2].trim());

                    Customer customer = new Customer(fName,lName,DOB);
                    importedCustomers.add(customer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return importedCustomers;
    }

}
