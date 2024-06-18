package controller;

import exceptions.BorrowingNotNullException;
import model.Book;
import model.BookCopy;
import model.Customer;
import view.MainMenu;
import view.View;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Predicate;

public class Controller {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private View menu;
    private Scanner scanner;
    private final List<Customer> customers = new ArrayList<>();
    private final Map<Book, List<BookCopy>> bookDatabase = new HashMap<>();

    public Controller() {
        scanner = new Scanner(System.in);
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

    public Customer searchCustomerViaId(String id) {
        return searchCustomer(c -> c.getId().equals(id), Comparator.comparing(Customer::getId)).stream().findFirst().orElse(null);
    }

    //Book Methods
    //ToDo: add nb of book copy as argument
    public Book addBook(String isbn, String title, String author , String publisher, String dateOfFirstPublication, String classificationNumber, int copyNb) {

        if (!title.isEmpty() && !author.isEmpty() && !isbn.isEmpty() && !classificationNumber.isEmpty()) {
            if (searchBookViaIsbn(isbn) != null) {
                System.out.println("A book with this id already exists in the database!");
                return null;
            }

            if (!validIsbn(isbn.replace("-", ""))) {
                System.out.println("The given ISBN is not valid.");
                return null;
            }

            try {
                LocalDate dateOfBirth = LocalDate.parse(dateOfFirstPublication, DATE_FORMAT);
                Book book = new Book(title, author, isbn, dateOfBirth, classificationNumber, publisher);
                bookDatabase.put(book, new ArrayList<>());

                for (int i = 0; i < copyNb; i++) {
                    addBookCopy("", isbn);
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
            book.setPublicationDate(LocalDate.parse(dateOfFirstPublication, DATE_FORMAT));
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

        if (this.bookDatabase.get(optionalBook.get()).stream().anyMatch(BookCopy::isBorrowed)) {
            throw new BorrowingNotNullException("A physical copy of this book is still borrowed by someone.");
        }

        this.bookDatabase.remove(optionalBook.get());
    }


    //Customer Methods
    public Customer addCustomer(String firstName, String lastName, String date) {
        if (!firstName.isEmpty() && !lastName.isEmpty()) {
            LocalDate dateOfBirth = LocalDate.parse(date, DATE_FORMAT);
            Customer customer = new Customer(firstName, lastName, dateOfBirth);
            this.getCustomers().add(customer);
            return customer;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public boolean modifyCustomer(Customer customer, String firstName, String lastName, String dateOfBirth){
        if (!firstName.isEmpty() && !lastName.isEmpty()) {
            customer.setFirstName(firstName);
            customer.setDateOfBirth(LocalDate.parse(dateOfBirth, DATE_FORMAT));
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
    public BookCopy addBookCopy(String bookCopyId, String isbn){
        Book book = searchBookViaIsbn(isbn);

        if (book == null) {
            System.out.println("There is no book with such isbn!");
            return null;
        }

        BookCopy bookCopy = new BookCopy(book, bookCopyId, "", null, null, .0f);
        getBookCopies(searchBookViaIsbn(isbn)).add(bookCopy);
        return bookCopy;
    }

    public BookCopy addBorrowedBookCopy(String bookCopyId, String isbn, String customerId, String borrowedDate, String returnDate, String fee) {
        Customer customer = searchCustomerViaId(customerId);

        LocalDate borrowedDateParse;
        LocalDate returnDateParse;
        float feeParse;

        if (customer == null) {
            System.out.println("There is no customer with such id!");
            return null;
        }

        try {
            borrowedDateParse = LocalDate.parse(borrowedDate, DATE_FORMAT);
        }
        catch (DateTimeParseException e) {
            System.out.println("Error at passing the borrowing date: " + e.getMessage());
            return null;
        }

        try {
            returnDateParse = LocalDate.parse(returnDate, DATE_FORMAT);
        }
        catch (DateTimeParseException e) {
            System.out.println("Error at passing the return date: " + e.getMessage());
            return null;
        }

        try {
            feeParse = Float.parseFloat(fee);
        }

        catch (NumberFormatException e) {
            System.out.println("Error at parsing the fees: " + e.getMessage());
            return null;
        }

        BookCopy bookCopy = addBookCopy(bookCopyId, isbn);

        if (bookCopy != null) {
            borrowBookCopy(customer, bookCopy, borrowedDateParse, returnDateParse, feeParse);
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
            bookCopy.setCustomerId(customer.getId());
            bookCopy.setBorrowedDate(LocalDate.now());
            bookCopy.setReturnedDate(LocalDate.now().plusWeeks(2));
            customer.getBorrowedList().add(bookCopy);
        } else {
            throw new IllegalArgumentException("BookCopy is already borrowed!");
        }
    }

    public void borrowBookCopy(Customer customer, BookCopy bookCopy, LocalDate borrowedDate, LocalDate returnDate, float fee) {
        if (!bookCopy.isBorrowed()) {
            bookCopy.setCustomerId(customer.getId());
            bookCopy.setBorrowedDate(borrowedDate);
            bookCopy.setReturnedDate(returnDate);
            bookCopy.setFee(fee);
            customer.getBorrowedList().add(bookCopy);
        } else {
            throw new IllegalArgumentException("BookCopy is already borrowed!");
        }
    }

    public void returnBookCopy(Customer customer, BookCopy bookCopy) {

        if (customer.getBorrowedList().contains(bookCopy)) {
            bookCopy.removeCustomer();
            bookCopy.setBorrowedDate(null);
            bookCopy.setReturnedDate(null);
            customer.getBorrowedList().remove(bookCopy);
        } else {
            throw new IllegalArgumentException("BookCopy is not borrowed!");
        }

    }


    //Getters
    public Scanner getScanner() {
        return this.scanner;
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

    public static boolean validIsbn(String isbn) {
        if (isbn.length() == 10) {
            try {
                int sum = 0;
                for (int i = 0; i < 9; i++) {
                    int digit = Integer.parseInt(isbn.substring(i, i + 1));
                    sum += digit * (10 - i);
                }

                char lastChar = isbn.charAt(9);
                int checksum = (lastChar == 'X') ? 10 : Integer.parseInt(String.valueOf(lastChar));
                sum += checksum;

                return (sum % 11 == 0);
            }
            catch (NumberFormatException e) {
                return false;
            }
        }
        else if (isbn.length() == 13) {
            try {
                int sum = 0;
                for (int i = 0; i < 12; i++) {
                    int digit = Integer.parseInt(isbn.substring(i, i + 1));
                    sum += digit * (i % 2 == 0 ? 1 : 3);
                }

                int checksum = 10 - (sum % 10);
                if (checksum == 10) {
                    checksum = 0;
                }

                int lastDigit = Integer.parseInt(isbn.substring(12));
                return (checksum == lastDigit);
            }
            catch (NumberFormatException e) {
                return false;
            }
        }
        else {
            return false;
        }
    }
}
