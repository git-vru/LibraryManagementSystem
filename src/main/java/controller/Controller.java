package controller;

import exceptions.BorrowingNotNullException;
import model.Book;
import model.BookCopy;
import model.Customer;
import view.MainMenu;
import view.View;

import java.time.LocalDate;
import java.util.*;

public class Controller {
    private View menu;
    private Scanner sc;
    private final List<Customer> customers = new ArrayList<>();
    private final Map<Book, List<BookCopy>> bookDatabase = new HashMap<>();

    public Controller() {
        sc = new Scanner(System.in);
        this.menu = new MainMenu(this);
    }

    /**
     * Returns book on specified type of search and key.
     * @param by Style of the search.
     *           0 - ISBN
     *           1 - Book Name
     *           2 - Author Name
     * @param token Token for search.
     * @return Returns a book list of found books.
     */
    public List<Book> searchBook(int by, String token) {
        List<Book> foundBooks = new ArrayList<>();

        if (0 > by || by > 2) return foundBooks;

        String value = null;

        List<Book> books = bookDatabase.keySet().stream().toList();

        for (Book book : books) {
            if (by == 0) value = book.getIsbn();
            else if (by == 1) value = book.getTitle();
            else if (by == 2) value = book.getAuthor();
            if (value.equals(token)) foundBooks.add(book);
        }
        return foundBooks;
    }

    public boolean borrowBook(String customerId, String bookId) {
        return false;
    }

    public boolean returnBook(String customerId, String bookId) {
        return false;
    }

    public void deleteBook(String isbn) throws BorrowingNotNullException {
        Optional<Book> optionalBook = this.bookDatabase.keySet().stream().filter(book -> book.getIsbn().equals(isbn)).findFirst();

        if (optionalBook.isEmpty()) {
            throw new NoSuchElementException();
        }

        if (this.bookDatabase.get(optionalBook.get()).stream()
                .anyMatch(bookCopy -> bookCopy.getBorrower() != null)) {
            throw new BorrowingNotNullException("A physical copy of this book is still borrowed by someone.");
        }

        this.bookDatabase.remove(optionalBook.get());
    }

    public Customer searchCustomer(String id) {
        Optional<Customer> optionalCustomer = this.customers.stream().filter(customer -> customer.getId().equals(id)).findFirst();
        return optionalCustomer.map(customer -> this.customers.get(this.customers.indexOf(customer))).orElse(null);
    }

    public boolean addCustomer(String firstName, String lastName, String date) {
        LocalDate dob = LocalDate.parse(date);
        Customer customer = new Customer(firstName, lastName, dob);

        return this.customers.add(customer);
    }

    public void deleteCustomer(String id) throws BorrowingNotNullException {
        Optional<Customer> optionalCustomer = this.customers.stream().filter(customer -> customer.getId().equals(id)).findFirst();

        if (optionalCustomer.isEmpty()) {
            throw new NoSuchElementException();
        }

        if (!optionalCustomer.get().getBorrowedList().isEmpty()) {
            throw new BorrowingNotNullException("This customer is still borrowing a book.");
        }

        this.customers.remove(optionalCustomer.get());
    }

    public BookCopy searchbookCopy(Book book, String id){
        for (BookCopy bookCopy : bookDatabase.get(book)) {
            if (bookCopy.getId().equals(id)) {
                return  bookCopy;
            }
        }
        return null;
    }

    public void deleteBookCopy(String id) throws BorrowingNotNullException {
        Optional<BookCopy> optionalBook = this.bookDatabase.values().stream().flatMap(Collection::stream).filter(bookCopy -> bookCopy.getId().equals(id)).findFirst();

        if (optionalBook.isEmpty()) {
            throw new NoSuchElementException();
        }

        if (optionalBook.get().getBorrower() != null) {
            throw new BorrowingNotNullException("This copy is still borrowed by someone.");
        }

        optionalBook.get().getBook().decreaseCopyCount();
        this.bookDatabase.get(optionalBook.get().getBook()).remove(optionalBook.get());
    }

    public boolean addBook(String title, String author, String isbn, String dateOfFirstPublication, String classificationNumber){
        return true;
    }

    public boolean modifyBook(Book book, String title, String author, String dateOfFirstPublication, String classificationNumber){
        return true;
    }
    public boolean modifyCustomer(Customer customer, String Fname, String Lname, String dob){
        return true;
    }

    public boolean addBookCopy(String isbn){
        return true;
    }

    public void setMenu(View menu) {
        this.menu = menu;
        this.menu.show();
    }

    public Scanner getScanner() {
        return this.sc;
    }

    public List<Customer> getCustomers() {
        return this.customers;
    }

    public View getMenu() {
        return menu;
    }

    public Scanner getSc() {
        return sc;
    }

    public void setSc(Scanner sc) {
        this.sc = sc;
    }

    public Map<Book, List<BookCopy>> getBookDatabase() {
        return bookDatabase;
    }

    public List<BookCopy> getBookCopys(Book book) {
        return bookDatabase.get(book);
    }
}
