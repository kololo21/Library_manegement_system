package ui.panel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


import java.awt.*;
import model.Member;
import model.LoanRecord;
import service.LoanService;

import service.MemberService;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class LoanHistoryPanel extends JPanel {
    private LoanService ls;
    private MemberService ms;
    private JTable table;
    private DefaultTableModel model;//data manegement tool
    private JTextField searchField;


    public LoanHistoryPanel(LoanService ls,MemberService ms) {
        this.ls=ls;
        this.ms=ms;
        String[] columns = {"LoanID","BookID","MemberName","MemberID","Loan Date","Due Date","Return Date","Status"};
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

        //Searching Bar

        searchField=new JTextField(20);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            //when word is append
            public void insertUpdate(DocumentEvent e){filterTable();}
            //when word is removed
            public void removeUpdate(DocumentEvent e){filterTable();}
            //when style is changed
            public void changedUpdate(DocumentEvent e){filterTable();}

        });

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        add(searchPanel,BorderLayout.NORTH);
        
    }
    public void refreshTable(){
        ls.updateOverdueStatus();
        model.setRowCount(0); //all lines clear
        for (LoanRecord loan:ls.getLoans()){
            Member m = ms.findByID(loan.getMemberID());
            String name = (m==null)?"(deleted)":m.getName();
            model.addRow(new Object[]{
                loan.getLoanID(),
                loan.getBookID(),
                name,
                loan.getMemberID(),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnDate() == null ? "-" : loan.getReturnDate().toString(),
                loan.getStatus()
            }); 
        }

    }
    public void filterTable(){
        String keyword = searchField.getText().toLowerCase(); //convert all input to lower case
        model.setRowCount(0);//Table clear

        for(LoanRecord loan:ls.getLoans()){
            Member m = ms.findByID(loan.getMemberID());
            String name = (m==null)?"(deleted)":m.getName();
            if(loan.getLoanID().toLowerCase().contains(keyword)||
            loan.getBookID().toLowerCase().contains(keyword)||
            loan.getMemberID().toLowerCase().contains(keyword)||
            name.toLowerCase().contains(keyword)||
            loan.getStatus().toString().toLowerCase().contains(keyword)){
                model.addRow(new Object[]{
                    loan.getLoanID(),
                    loan.getBookID(),
                    name,
                    loan.getMemberID(),
                    loan.getLoanDate(),
                    loan.getDueDate(),
                    loan.getReturnDate(),
                    loan.getStatus()
                });
            }

        }

    }

}
