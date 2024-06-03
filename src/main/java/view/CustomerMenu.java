package view;

import controller.Controller;
import model.Book;
import utilities.CSVreader;
import model.Customer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerMenu extends View {
    //private utilities.CSVreader reader;
    private char inputChar;

    private final List<String> options;
    private final List<String> options2;

    public CustomerMenu(Controller controller, View prev) {
        super(controller, prev);

        this.name = "Customer Menu";
        this.options = new ArrayList<>();
        this.options.add("Search for a customer");
        this.options.add("Add a new customer");
        this.options2 = new ArrayList<>();
        this.options2.add("Add Single Customer");
        this.options2.add("Import Customers from CSV file");
    }

    private void importCustomersFromCSV() {
        int importedCustomerCount = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the path to the CSV file: ");
        String filePath = scanner.nextLine().trim();

        List<String[]> importedCustomer = CSVreader.parseFile(filePath);

        if (!importedCustomer.isEmpty()) System.out.println("Imported Customer:");

        for (String[] data : importedCustomer) {
            Customer customer = controller.addCustomer(data[0].trim(), data[1].trim(), data[2].trim());
            if (customer != null) {
                System.out.println(customer);
                importedCustomerCount++;
            }
        }

        if (importedCustomerCount == 0) {
            System.out.printf("Total %d customer imported.%n", importedCustomerCount);
        }
        else {
            System.out.println("No customer was imported !");
        }
    }

    private void addNewCustomer() {
        Customer customer = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please type : <First Name>,<Last Name>,<Date Of Birth(YYYY-MM-DD)>'");

        String input = scanner.nextLine().trim();
        String[] parts = input.split(",", 3);

        if (parts.length == 3) {
            customer = controller.addCustomer(parts[0].trim(), parts[1].trim(), parts[2].trim());
        }
        else {
            System.out.println("Invalid input format. Please try again.");
        }

        if (customer != null) {
            System.out.println("Customer added successfully!");
            System.out.println(customer);
        }
    }

    public void show() {
        inputChar = super.promptMenu(options);

        if (inputChar == '0') {
            controller.setMenu(new CustomerInfo(controller, this));

        }
        else if (inputChar == '1') {
            inputChar = super.promptOptions(options2);
            if (inputChar == '0'){
                //System.out.println("Ex: Please type : 'create <FirstName>,<LastName>,<DOB>'");
                addNewCustomer();
            }
            else if (inputChar == '1') {
                importCustomersFromCSV();
            }
            else {
                prev.show();
            }

            controller.getCustomers().add(new Customer("Add", "Add", LocalDate.now()));
            super.promptAndExit("Customer with new id XYZ was successfully added!");
            this.show();
        }
        else {
            prev.show();
        }
    }

}
