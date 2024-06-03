package view;

import controller.Controller;
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
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the path to the CSV file: ");
        String filePath = scanner.nextLine().trim();

        try {
            List<Customer> importedCustomers = CSVreader.makeCustomer(filePath);
            if (!importedCustomers.isEmpty()) System.out.println("Imported Customers:");
            for (Customer customer : importedCustomers) {
                System.out.println(customer);
            }
            System.out.printf("Total %d Customers imported.%n", importedCustomers.size());
        } catch (IOException e) {
            System.err.println("An error occurred while reading the CSV file: " + e.getMessage());
        }
    }

    private void addNewCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please type : 'create <First Name>,<Last Name>,<Date Of Birth(YYYY-MM-DD)>'");

        String input = scanner.nextLine().trim();
        if (input.startsWith("create ")) {
            String CustomerDetails = input.substring(7).trim();
            String[] parts = CustomerDetails.split(",", 3);
            if (parts.length == 3) {
                String FName = parts[0].trim();
                String LName = parts[1].trim();
                LocalDate dob = LocalDate.parse(parts[2].trim());

                Customer customer = new Customer(FName,LName,dob);
                System.out.println("Customer added successfully:");
                System.out.println(customer);
            } else {
                System.out.println("Invalid input format. Please try again.");
            }
        } else {
            System.out.println("Invalid command. Please use the 'create' command.");
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
            } else if (inputChar == '1') {
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
