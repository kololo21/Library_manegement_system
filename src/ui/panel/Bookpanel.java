package ui.panel;

import javax.swing.*;
import service.BookService;
import service.LoanService;


public class BookPanel extends JPanel {
    private BookService bs;
    private LoanService ls;
    public BookPanel(BookService bs,LoanService ls) {
        this.bs = bs;
        this.ls = ls;
    }
}
