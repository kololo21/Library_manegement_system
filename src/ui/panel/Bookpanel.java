package ui.panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import model.Book;
import service.BookService;
import service.LoanService;

public class BookPanel extends JPanel {
    private BookService bs;
    private LoanService ls;
    private JTable table;
    private DefaultTableModel model;

    public BookPanel(BookService bs,LoanService ls) {
        this.bs = bs;
        this.ls = ls;
        //data is gotten from bs.getBooks()
        //DefaultTableModel will manage the data from the table
        String[] columns = {"ID", "Title", "Author", "Genre", "Total", "Lent", "Available"};

        this.model = new DefaultTableModel(columns,0);
        table = new JTable(model);
        setLayout(new BorderLayout());
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);  // move table to CENTER
        refreshTable();
        
         //Button Event
        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(e->{
            //event process when click on
            System.out.println("Add clicked");
        });
        JButton editButton = new JButton("Edit Book");
        addButton.addActionListener(e->{
            //event process when click on
            System.out.println("Edit clicked");

        });
        JButton deleteButton = new JButton("Delete Book");
        addButton.addActionListener(e->{
            //event process when click on
            System.out.println("Delete clicked");

        });
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel,BorderLayout.SOUTH);
    }
    public void refreshTable(){
        model.setRowCount(0); //all lines clear
        for (Book book:bs.getBooks()){
            model.addRow(new Object[]{
                book.getBookID(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.getTotalCopies(),
                book.getLentCopies(),
                book.getAvailableCopies()
            });
        }

    }
    
}
