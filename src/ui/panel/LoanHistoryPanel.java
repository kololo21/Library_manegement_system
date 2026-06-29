package ui.panel;
import javax.swing.*;

import model.LoanRecord;
import service.LoanService;


public class LoanHistoryPanel extends JPanel {
    private LoanService ls;
    public LoanHistoryPanel(LoanService ls) {
        this.ls=ls;
    }
}
