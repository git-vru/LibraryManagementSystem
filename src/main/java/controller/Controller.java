package controller;

import exceptions.BorrowingNotNullException;
import model.Book;
import model.BookCopy;
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

    //Search methods
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


    //Book Methods
    //ToDo: add nb of book copy as argument
    public Book addBook(String isbn, String title, String author , String publisher, String dateOfFirstPublication, String classificationNumber, int copyNb) {

        if (!title.isEmpty() && !author.isEmpty() && !isbn.isEmpty() && !classificationNumber.isEmpty()) {
            try {
                LocalDate dob = LocalDate.parse(dateOfFirstPublication);
                Book book = new Book(title, author, isbn, dob, classificationNumber, publisher);
                bookDatabase.put(book, new ArrayList<>());

                for (int i = 0; i < copyNb; i++) {
                    addBookCopy(isbn, "0", null, null);
                }
                return book;
            }
            catch (DateTimeParseException a) {
                System.out.println("Error at passing the date!");
            }

        }
        else {
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


    //Customer Methods
    public Customer addCustomer(String firstName, String lastName, String date) {
        if (!firstName.isEmpty() && !lastName.isEmpty()) {
            LocalDate dob = LocalDate.parse(date);
            Customer customer = new Customer(firstName, lastName, dob);
            this.getCustomers().add(customer);
            return customer;
        }
        else {
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


    //BookCopy Methods
    public BookCopy addBookCopy(String isbn, String isBorrowed, String borrowedDate, String returnDate){

        BookCopy bookCopy = null;
        if (!isbn.isEmpty() && (isBorrowed.equals("0") || (!borrowedDate.isEmpty() && !returnDate.isEmpty()))) {
            try {
                if (isBorrowed.equals("0")) {
                    bookCopy = new BookCopy(searchBookViaIsbn(isbn), false);
                }
                else {
                    bookCopy = new BookCopy(searchBookViaIsbn(isbn), true, LocalDate.parse(borrowedDate), LocalDate.parse(returnDate));
                }
                getBookCopies(searchBookViaIsbn(isbn)).add(bookCopy);
                searchBookViaIsbn(isbn).increaseCopyCount();

            }
            catch (DateTimeParseException a) {
                System.out.println("Error at passing the date!");
            }
            catch (NullPointerException a) {
                System.out.println("There is no book with such isbn!");
                return null;
            }
        }
        else {
            throw new IllegalArgumentException();
        }

        return bookCopy;
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


    //Borrowing Methods
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

        if (customer.getBorrowedList().contains(bookCopy)) {
            bookCopy.setIsBorrowed(false);
            bookCopy.setBorrowedDate(null);
            bookCopy.setReturnedDate(null);
            customer.getBorrowedList().remove(bookCopy);
        } else {
            throw new IllegalArgumentException("BookCopy is not borrowed!");
        }

    }


    //Getters
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


    //Setters
    public void setMenu(View menu) {
        this.menu = menu;
        this.menu.show();
    }
}
