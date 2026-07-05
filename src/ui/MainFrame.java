package ui;

import ui.panel.*;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import service.BookService;
import service.LoanService;
import service.MemberService;

import javax.swing.Timer;

public class MainFrame extends JFrame{
    public MainFrame(BookService bs,MemberService ms,LoanService ls){
        setTitle("Library Management System");
        setSize(800,600);
        JTabbedPane tabs = new JTabbedPane();
        DashboardPanel dashboardPanel = new DashboardPanel(bs, ms, ls);
        tabs.addTab("Dashboard",dashboardPanel);
        BookPanel bookPanel = new BookPanel(bs, ls, ms);
        tabs.addTab("Books",bookPanel);
        tabs.addTab("Members",new MemberPanel(ms,ls));
        LoanHistoryPanel loanHistoryPanel = new LoanHistoryPanel(ls,ms);
        tabs.addTab("Loan History",loanHistoryPanel);
        tabs.addChangeListener(e->{
            int index = tabs.getSelectedIndex();
            if(index==0)dashboardPanel.refreshTable();
            if (index == 1) { // Books tab
                bookPanel.refreshTable();
            }
            if(index ==3){ //(start from 0) Loan history tab
                loanHistoryPanel.refreshTable();
            }
        });
        add(tabs);
        
            

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
    }
}