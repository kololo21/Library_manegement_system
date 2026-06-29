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
        tabs.addTab("Dashboard",new DashboardPanel(bs,ms,ls));
        tabs.addTab("Books",new BookPanel(bs,ls,ms));
        tabs.addTab("Members",new MemberPanel(ms));
        tabs.addTab("Loan History",new LoanHistoryPanel(ls));
        add(tabs);
       

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
    }
}