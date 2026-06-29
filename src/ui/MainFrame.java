package ui;

import javax.swing.JFrame;

import service.BookService;
import service.LoanService;
import service.MemberService;

public class MainFrame extends JFrame{
    public MainFrame(BookService bs,MemberService ms,LoanService ls){
        setTitle("Library Management System");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}