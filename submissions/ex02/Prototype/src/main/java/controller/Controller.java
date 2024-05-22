package controller;

import model.Book;
import model.Customer;
import model.PhysicalBook;
import view.MainMenu;
import view.View;
import exceptions.*;

import java.time.LocalDate;
import java.util.*;

public class Controller {
    private View menu;
    private Scanner sc;
    private final List<Customer> customers = new ArrayList<>();
    private final Map<Book, List<PhysicalBook>> books = new HashMap<>();

    public Controller() {
        sc = new Scanner(System.in);
        this.menu = new MainMenu(this);

        this.customers.add(new Customer("Max", "Musterman", LocalDate.of(2024, 11, 1)));

        Book book = new Book("Les Fleurs du Mal", "Charles Baudelaire", "978-2-290-11507-7", LocalDate.of(1857, 6, 21), "BAU01");
        this.books.put(book, new ArrayList<>());

        for (int i = 0; i < 3; i++) {
            this.books.get(book).add(new PhysicalBook(book));
        }
    }

    public Book searchBook(String isbn) {
        Optional<Book> optionalBook = this.books.keySet().stream().filter(book -> book.getIsbn().equals(isbn)).findFirst();
        return optionalBook.orElse(null);
    }

    public boolean borrowBook(String customerId, String bookId) {
        /*Customer customer = searchCustomer(customerId);
        PhysicalBook physicalBook = getBooks().get(0).getBookList().get(0);

        if (customer == null || physicalBook == null) return false;

        customer.getBorrowedList().add(physicalBook);
        physicalBook.setBorrower(customer);

        return true;*/
        return false;
    }

    public boolean returnBook(String customerId, String bookId) {
        /*Customer customer = searchCustomer(customerId);
        PhysicalBook physicalBook = getBooks().get(0).getBookList().get(0);

        if (physicalBook.getFee() == 0) {
            customer.getBorrowedList().remove(physicalBook);
            // physicalBook.setReturnedDate(some date);
            physicalBook.setBorrower(null);
            return true;
        }
        else {
            return false;
        }*/
        return false;
    }

    public void deleteBook(String isbn) throws BorrowingNotNullException {
        Optional<Book> optionalBook = this.books.keySet().stream().filter(book -> book.getIsbn().equals(isbn)).findFirst();

        if (optionalBook.isEmpty()) {
            throw new NoSuchElementException();
        }

        if (this.books.get(optionalBook.get()).stream().anyMatch(physicalBook -> physicalBook.getBorrower() != null)) {
            throw new BorrowingNotNullException("A physical copy of this book is still borrowed by someone.");
        }

        this.books.remove(optionalBook.get());
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

    public PhysicalBook searchPhysicalBook(String id){
        PhysicalBook book = null;
        // Optional<PhysicalBook> optionalPhysicalBook = this.books.values().tostream().filter(physicalBook -> physicalBook.getId().equals(id)).findFirst();
        for (List<PhysicalBook> list : books.values()) {
            for (PhysicalBook physicalBook : list) {
                if (physicalBook.getId().equals(id)) {
                    book = physicalBook;
                    break;
                }
            }
        }
        return book;
    }

    public void deletePhysicalBook(String id) throws BorrowingNotNullException {
        Optional<PhysicalBook> optionalBook = this.books.values().stream().flatMap(Collection::stream).filter(physicalBook -> physicalBook.getId().equals(id)).findFirst();

        if (optionalBook.isEmpty()) {
            throw new NoSuchElementException();
        }

        if (optionalBook.get().getBorrower() != null) {
            throw new BorrowingNotNullException("This copy is still borrowed by someone.");
        }

        this.books.get(optionalBook.get().getBook()).remove(optionalBook.get());
    }

    public boolean addBook(String title, String author, String isbn, LocalDate dateOfFirstPublication, String classificationNumber){
        return true;
    }

    public boolean modifyBook(String title, String author, LocalDate dateOfFirstPublication, String classificationNumber){
        return true;
    }
    public boolean modifyCustomer(String id, String Fname, String Lname, String dob, String subscription){
        return true;
    }

    public boolean addPhysicalBook(String id, String book, String borrower, String borrowedDate, String returnedDate, String fee){

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

    public Map<Book, List<PhysicalBook>> getBooks() {
        return books;
    }

    public List<PhysicalBook> getPhysicalBooks(Book book) {
        return books.get(book);
    }
}
