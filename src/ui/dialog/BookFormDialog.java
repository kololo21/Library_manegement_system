package ui.dialog;

import javax.swing.*;
import java.awt.*;
import model.Book;
import service.BookService;


public class BookFormDialog extends JDialog{
    private BookService bs;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField genreField;
    private JTextField totalCopiesField;

    public BookFormDialog(JFrame parent,BookService bs){
        super(parent,"Add Book",true);//true=We cannnot control parent until this dialog will close
        setSize(400,300);
        setLocationRelativeTo(parent);//display parent window at the center

        //oneline text input
        JPanel form = new JPanel(new GridLayout(5,2));//5x2 matrix
        titleField = new JTextField(20);//length for 20 words
        form.add(new JLabel("Title"));
        form.add(titleField);

        authorField = new JTextField(20);//length for 20 words
        form.add(new JLabel("Author"));
        form.add(authorField);

        genreField = new JTextField(20);//length for 20 words
        form.add(new JLabel("Genre"));
        form.add(genreField);
        
        totalCopiesField = new JTextField(20);//length for 20 words
        form.add(new JLabel("TotalCopies"));
        form.add(totalCopiesField);

        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e->{
            //event process when click on
           String input_title=titleField.getText();
           String input_author=authorField.getText();
           String input_genre=genreField.getText();
           int input_totalCopies=Integer.valueOf(totalCopiesField.getText());
           //totalLentCopies is 0 because new book is not borrowed
           Book book = new Book("B"+(bs.getBooks().size()+1),input_title,input_author,input_genre,input_totalCopies,0);
            bs.addBook(book);
            dispose();
        });
        form.add(saveButton);
        add(form,BorderLayout.CENTER);
        setVisible(true);

    
        
    }
}