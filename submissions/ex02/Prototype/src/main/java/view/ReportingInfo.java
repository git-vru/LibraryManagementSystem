package view;

import controller.Controller;
import model.Customer;
import model.PhysicalBook;

import java.util.List;

public class ReportingInfo extends View {
    public ReportingInfo(Controller controller, View prev) {
        super(controller, prev);
        this.name = "Borrowing Info";
    }

    public void show() {

    }
}
