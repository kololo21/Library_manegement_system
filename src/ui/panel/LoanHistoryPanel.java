package ui.panel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.Book;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import model.LoanRecord;
import service.BookService;
import service.LoanService;
import service.MemberService;
import ui.dialog.BookFormDialog;


public class LoanHistoryPanel extends JPanel {
    private LoanService ls;
    private MemberService ms;
    private JTable table;
    private DefaultTableModel model;//data manegement tool


    public LoanHistoryPanel(LoanService ls,MemberService ms) {
        this.ls=ls;
        this.ms=ms;
        String[] columns = {"LoanID","BookD","MemberName","MemberID","Loan Date","Due Date","Return Date","Status"};
        this.model = new DefaultTableModel(columns,0);
        table = new JTable(model);
        setLayout(new BorderLayout());
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);  // move table to CENTER
        refreshTable();

        JButton returnButton = new JButton("Return Book");
        returnButton.addActionListener(e->{
            //event process when click on
            int row = table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(this, "Please select a book to return");
                return;
            }
            String loanID=(String)model.getValueAt(row, 0);//get value at the particular point
            ls.returnBook(loanID);
            refreshTable();
        });
        add(returnButton,BorderLayout.SOUTH);
        
    }
    public void refreshTable(){
        model.setRowCount(0); //all lines clear
        for (LoanRecord loan:ls.getLoans()){

            model.addRow(new Object[]{
                loan.getLoanID(),
                loan.getBookID(),
                ms.findByID(loan.getMemberID()).getName(),
                loan.getMemberID(),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnDate() == null ? "-" : loan.getReturnDate().toString(),
                loan.getStatus()
            }); 
        }

    }

}
