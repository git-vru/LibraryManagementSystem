package view;

import controller.Controller;
import model.Borrowing;
import model.Customer;

public class ReportingInfo extends View{
    public ReportingInfo(Controller controller, View prev) {
        super(controller, prev);
        this.name = "Borrowing Info";
    }

    public void show() {
        System.out.print("Please enter a borrowing id:");
        Borrowing b = controller.search(controller.getBorrowing(), controller.getScanner().next());

        if (b == null) {
            super.promptAndExit("No Borrowing record");
        }
        else {
            this.name = "Borrowing Id: " + b.getId();
            super.promptAndExit(b.toString());
        }
        prev.show();
    }
}
