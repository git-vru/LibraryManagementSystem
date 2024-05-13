package controller;

import view.*;

import java.util.Scanner;

public class Controller {
    private View menu;
    private Scanner sc;

    public Controller() {
        sc = new Scanner(System.in);
        this.menu = new MainMenu(this);

        this.menu.show();
    }

    public void setMenu(View menu) {
        this.menu = menu;
        this.menu.show();
    }

    public Scanner getScanner() {
        return this.sc;
    }
}
