package ui.panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import model.Book;
import service.BookService;
import service.LoanService;
import ui.dialog.BookFormDialog;

public class BookPanel extends JPanel {
    private BookService bs;
    private LoanService ls;
    private JTable table;
    private DefaultTableModel model;//data manegement tool

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

            //get JFrame(Main Frame) which is a base of BookPanel
            JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
            new BookFormDialog(parent, bs,null);
            refreshTable();
        });
        JButton editButton = new JButton("Edit Book");
        editButton.addActionListener(e->{
            //event process when click on
            int row = table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(this, "Please select a book to edit");
                return;
            }
            String bookID=(String)model.getValueAt(row, 0);//get value at the particular point
            Book book = bs.findByID(bookID);
            JFrame parent =(JFrame)SwingUtilities.getWindowAncestor(this);
            new BookFormDialog(parent,bs, book);//give book (editor mode)
            refreshTable();
        });
        JButton deleteButton = new JButton("Delete Book");
        deleteButton.addActionListener(e->{
            //event process when click on
            int row = table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(this, "Please select a book to delete");
                return;
            }
            int result = JOptionPane.showConfirmDialog(this, "Delete this","Confirm",JOptionPane.YES_NO_OPTION);
            if(result == JOptionPane.YES_OPTION){
                //Process for Yes
                String bookID=(String)model.getValueAt(row, 0);//get value at the particular point
                bs.deleteBook(bookID);
                refreshTable();
            }
            
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
