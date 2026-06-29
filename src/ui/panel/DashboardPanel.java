package ui.panel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.table.DefaultTableModel;

import javax.swing.*;

import model.LoanRecord;
import model.LoanStatus;
import service.BookService;
import service.LoanService;
import service.MemberService;


public class DashboardPanel extends JPanel {
    private DefaultTableModel model;
    private BookService bs;
    private MemberService ms;
    private LoanService ls;
    public DashboardPanel(BookService bs,MemberService ms,LoanService ls) {
        this.bs=bs;
        this.ms=ms;
        this.ls=ls;

        setLayout((new BorderLayout()));
        JPanel statsJPanel = new JPanel(new GridLayout(2,2));

        statsJPanel.add(new JLabel("Total Books:"+bs.getBooks().size()));   // statsics at top
        statsJPanel.add(new JLabel("Total Members:"+ms.getMembers().size()));   // statsics at top
        statsJPanel.add(new JLabel("Active Loans:"+ls.getActiveLoans().size()));   // statsics at top
        long overdueCount = ls.getActiveLoans().stream().filter(loan->loan.getStatus() == LoanStatus.OVERDUE).count();
        statsJPanel.add(new JLabel("Overdue"+overdueCount));   // statics at top
        add(statsJPanel,BorderLayout.NORTH);
        //model
        String[] columns = {"LoanID", "BookID", "MemberName", "Due Date"};
        model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll,BorderLayout.CENTER);
        refreshTable();
    }
    public void refreshTable(){
        model.setRowCount(0); //all lines clear
        for (LoanRecord loan:ls.getActiveLoans()){
            if (loan.getStatus() == LoanStatus.OVERDUE) {
                model.addRow(new Object[]{
                    loan.getLoanID(),
                    loan.getBookID(),
                    ms.findByID(loan.getMemberID()).getName(),
                    loan.getDueDate(),
                    
                });
            }
            
        }
    }
}

