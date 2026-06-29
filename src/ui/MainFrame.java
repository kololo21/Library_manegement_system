package ui;

import ui.panel.*;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import service.BookService;
import service.LoanService;
import service.MemberService;

public class MainFrame extends JFrame{
    public MainFrame(BookService bs,MemberService ms,LoanService ls){
        setTitle("Library Management System");
        setSize(800,600);
        JTabbedPane tabs = new JTabbedPane();
        DashboardPanel dashboardPanel = new DashboardPanel(bs, ms, ls);
        tabs.addTab("Dashboard",dashboardPanel);
        BookPanel bookPanel = new BookPanel(bs, ls, ms);
        tabs.addTab("Books",bookPanel);
        tabs.addTab("Members",new MemberPanel(ms));
        LoanHistoryPanel loanHistoryPanel = new LoanHistoryPanel(ls,ms);
        tabs.addTab("Loan History",loanHistoryPanel);
        tabs.addChangeListener(e->{
            int index = tabs.getSelectedIndex();
            if(index==0)dashboardPanel.refreshTable();
            if (index == 1) { // Books タブ
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