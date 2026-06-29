package ui.panel;
import javax.swing.*;

import model.LoanRecord;
import service.LoanService;
import service.MemberService;


public class MemberPanel extends JPanel {
    private MemberService ms;
    private LoanService ls;
    public MemberPanel(MemberService ms,LoanService ls) {
        this.ms=ms;
        this.ls=ls;
    }
}
