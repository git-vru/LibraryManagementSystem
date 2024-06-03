package utilities;

import controller.Controller;
import model.Book;
import model.BookCopy;
import model.Customer;

import java.io.*;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
//Please dont move this out of utilities
//It messes up other class
public class CSVreader {
    public static List<Book> makeBooks(String path) throws FileNotFoundException {
        List<Book> importedBooks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String isbn = parts[0].trim();
                String title = parts[1].trim();
                String author = parts[2].trim();
                LocalDate publicationDate = LocalDate.of(Year.parse(parts[3].trim()).getValue(), 1,1);
                String classificationNumber = parts[4].trim();
                Book book = new Book(title, author, isbn, publicationDate, classificationNumber);
                importedBooks.add(book);
            }
        } catch (IOException ignored) {}
        return importedBooks;
    }
    public static List<BookCopy> makeBookCopies(String path) throws FileNotFoundException {
        List<BookCopy> importedBookCopies = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            Controller controller = new Controller();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String isbn = parts[0].trim();
                Book book = controller.searchBookViaIsbn(isbn);
                boolean status = parts[1].equals("1");
                if (parts.length == 4){
                    LocalDate borrowedDate = LocalDate.parse(parts[2].trim());
                    LocalDate returnDate = LocalDate.parse(parts[3].trim());
                    BookCopy bookCopy = new BookCopy(book,status,borrowedDate,returnDate);
                    importedBookCopies.add(bookCopy);
                }
                BookCopy bookCopy = new BookCopy(book,status);
                importedBookCopies.add(bookCopy);
            }
        } catch (IOException ignored) {}
        return importedBookCopies;
    }
    public static List<Customer> makeCustomer(String path) throws FileNotFoundException {
        List<Customer> importedCustomers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String id = parts[0].trim();
                String firstName = parts[1].trim();
                String lastName = parts[2].trim();
                LocalDate dateOfBirth = LocalDate.parse(parts[3].trim());
                LocalDate subscriptionDate = LocalDate.parse(parts[4].trim());
                Customer customer = new Customer(id, firstName, lastName, dateOfBirth, subscriptionDate);
                importedCustomers.add(customer);
            }
        } catch (IOException ignored) {}
        return importedCustomers;
    }
}
