package model;

import java.io.*;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class CSVreader {
    public CSVreader() {
    }

    // CSV Format: ISBN, Title, Author, Year, Classification Number
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

    // CSV Format: [Book Data] + ID, Shelf Location (classification number), Borrowing Status, Borrow Date
    public static List<BookCopy> makeBookCopies(String path) throws FileNotFoundException {
        List<BookCopy> importedBookCopies = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String isbn = parts[0].trim();
                String title = parts[1].trim();
                String author = parts[2].trim();
                LocalDate publicationDate = LocalDate.of(Year.parse(parts[3].trim()).getValue(), 1,1);
                String classificationNumber = parts[4].trim();
                String id = parts[5].trim();
                boolean isBorrowed = parts[6].trim().equals("1");
        /*todo*/LocalDate borrowDate = LocalDate.of(1,1,1);
                Book book = new Book(title, author, isbn, publicationDate, classificationNumber);
                BookCopy bookCopy = new BookCopy(book);
                importedBookCopies.add(bookCopy);
            }
        } catch (IOException ignored) {}
        return importedBookCopies;
    }
    //

    // CSV Format: ID, FirstName, LastName, Date of Birth, Subscription Date
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
