package controller;

import exceptions.BorrowingNotNullException;
import model.Book;
import model.BookCopy;
import view.BookMenu;
import model.Customer;
import view.MainMenu;
import view.View;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Predicate;

public class Controller {
    private View menu;
    private Scanner sc;
    private final List<Customer> customers = new ArrayList<>();
    private final Map<Book, List<BookCopy>> bookDatabase = new HashMap<>();

    public Controller() {
        sc = new Scanner(System.in);
        this.menu = new MainMenu(this);
    }

    public Book searchBookViaIsbn(String isbn) {
        return searchBook(book -> book.getIsbn().equals(isbn), Comparator.comparing(Book::getTitle)).stream().findFirst().orElse(null);
    }

    public List<Book> searchBook(Predicate<Book> predicate, Comparator<Book> comparator) {
        return this.bookDatabase.keySet().stream().filter(predicate).sorted(comparator).toList();
    }

    public List<BookCopy> searchBookCopy(Predicate<BookCopy> predicate, Comparator<BookCopy> comparator) {
        List<BookCopy> list = new ArrayList<>();

        this.bookDatabase.values().forEach(bookCopyList -> {
            list.addAll(bookCopyList.stream().filter(predicate).toList());
        });

        return list.stream().sorted(comparator).toList();
    }

    public List<Customer> searchCustomer(Predicate<Customer> predicate, Comparator<Customer> comparator) {
        return this.customers.stream().filter(predicate).sorted(comparator).toList();
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


    public void deleteBook(String isbn) throws BorrowingNotNullException {
        Optional<Book> optionalBook = this.bookDatabase.keySet().stream().filter(book -> book.getIsbn().equals(isbn)).findFirst();

        if (optionalBook.isEmpty()) {
            throw new NoSuchElementException();
        }

        if (this.bookDatabase.get(optionalBook.get()).stream()
                .anyMatch(BookCopy::isBorrowed)) {
            throw new BorrowingNotNullException("A physical copy of this book is still borrowed by someone.");
        }

        this.bookDatabase.remove(optionalBook.get());
    }

    public Customer searchCustomer(String id) {
        Optional<Customer> optionalCustomer = this.customers.stream().filter(customer -> customer.getId().equals(id)).findFirst();
        return optionalCustomer.map(customer -> this.customers.get(this.customers.indexOf(customer))).orElse(null);
    }

    public boolean addCustomer(String firstName, String lastName, String date) {
        if (!firstName.isEmpty() && !lastName.isEmpty()) {
            LocalDate dob = LocalDate.parse(date);
            Customer customer = new Customer(firstName, lastName, dob);

            return this.customers.add(customer);
        } else {
            throw new IllegalArgumentException();
        }
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

    public BookCopy searchBookCopy(Book book, String id){
        for (BookCopy bookCopy : bookDatabase.get(book)) {
            if (bookCopy.getId().equals(id)) {
                return  bookCopy;
            }
        }
        return null;
    }

    public BookCopy searchbookCopy(String id) {

        return null;
    }

    public void deleteBookCopy(String id) throws BorrowingNotNullException {
        Optional<BookCopy> optionalBook = this.bookDatabase.values().stream().flatMap(Collection::stream).filter(bookCopy -> bookCopy.getId().equals(id)).findFirst();

        if (optionalBook.isEmpty()) {
            throw new NoSuchElementException();
        }

        if (optionalBook.get().isBorrowed()) {
            throw new BorrowingNotNullException("This copy is still borrowed by someone.");
        }

        optionalBook.get().getBook().decreaseCopyCount();
        this.bookDatabase.get(optionalBook.get().getBook()).remove(optionalBook.get());
    }

    public Book addBook(String title, String author, String isbn, String dateOfFirstPublication, String classificationNumber){

        if (!title.isEmpty() && !author.isEmpty() && !isbn.isEmpty() && !classificationNumber.isEmpty()) {
            try {
                LocalDate dob = LocalDate.parse(dateOfFirstPublication);
                Book book = new Book(title, author, isbn, dob, classificationNumber);
                bookDatabase.put(book, new ArrayList<>());
                return book;
            } catch (DateTimeParseException a) {
                System.out.println("Error at passing the date!");
            }

        } else {
            throw new IllegalArgumentException();

        }
        return null;

    }

    public boolean modifyBook(Book book, String title, String author, String dateOfFirstPublication, String classificationNumber){
        if (!title.isEmpty() && !author.isEmpty() && !classificationNumber.isEmpty()) {
            book.setTitle(title);
            book.setAuthor(author);
            book.setPublicationDate(LocalDate.parse(dateOfFirstPublication));
            book.setClassificationNumber(classificationNumber);
            return true;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public boolean modifyCustomer(Customer customer, String firstName, String lastName, String dob){
        if (!firstName.isEmpty() && !lastName.isEmpty()) {
            customer.setFirstName(firstName);
            customer.setDateOfBirth(LocalDate.parse(dob));
            customer.setLastName(lastName);
            return true;

        } else {
            throw new NoSuchElementException();

        }
    }

    public BookCopy addBookCopy(String isbn){

        if (!isbn.isEmpty()) {

            BookCopy bookCopy = new BookCopy(searchBookViaIsbn(isbn), false);
            getBookCopies(searchBookViaIsbn(isbn)).add(bookCopy);
            return bookCopy;

        } else {
            throw new IllegalArgumentException();
        }

    }

    public void setMenu(View menu) {
        this.menu = menu;
        this.menu.show();
    }

    public void borrowBookCopy(Customer customer, BookCopy bookCopy) {
        if (!bookCopy.isBorrowed()) {
            bookCopy.setIsBorrowed(true);
            bookCopy.setBorrowedDate(LocalDate.now());
            bookCopy.setReturnedDate(LocalDate.now().plusWeeks(2));
            customer.getBorrowedList().add(bookCopy);
        } else {
            throw new IllegalArgumentException("BookCopy is already borrowed!");
        }

    }

    public void returnBookCopy(Customer customer, BookCopy bookCopy) {
        if (customer.getBorrowedList().contains(bookCopy)){
            bookCopy.setIsBorrowed(false);
            bookCopy.setBorrowedDate(null);
            bookCopy.setReturnedDate(null);
            customer.getBorrowedList().remove(bookCopy);
        }
        else {
            throw new IllegalArgumentException("BookCopy is not borrowed!");
        }
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

    public Map<Book, List<BookCopy>> getBookDatabase() {
        return bookDatabase;
    }

    public List<BookCopy> getBookCopies(Book book) {
        return bookDatabase.get(book);
    }
}
