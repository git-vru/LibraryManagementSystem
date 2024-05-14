package controller;

import model.*;
import view.*;

import java.time.LocalDate;
import java.util.*;

public class Controller {
    private View menu;
    private Scanner sc;
    private final List<Customer> customers = new ArrayList<>();
    private final List<Book> books = new ArrayList<>();
    private final List<Borrowing> borrowing = new ArrayList<>();

    public Controller() {
        sc = new Scanner(System.in);
        this.menu = new MainMenu(this);

        this.customers.add(new Customer("Max", "Musterman", LocalDate.of(2024, 11, 1)));
        this.books.add(new Book("Les Fleurs du Mal", "Charles Baudelaire", LocalDate.of(1857, 6, 21), "BAU01", 5));
        this.menu.show();
    }

    public <T> T search(List<T> list, String query)  {
        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    public void createBorrowing(String bookId, String customerId) {
        Customer customer = getCustomers().get(0);
        PhysicalBook book = getBooks().get(0).getBookList().get(0);
        Borrowing borrowing = new Borrowing(book, customer, LocalDate.now(), (LocalDate.now()).plusDays(20));

        getBorrowing().add(borrowing);
        customer.getBorrowedList().add(borrowing);
        book.setBorrower(customer);
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

    public List<Book> getBooks() {
        return books;
    }

    public List<Borrowing> getBorrowing() {
        return borrowing;
    }
}
