package ui.dialog;

import javax.swing.*;
import java.awt.*;
import model.Book;
import service.BookService;


public class BookFormDialog extends JDialog{
    private JTextField titleField;
    private JTextField authorField;
    private JTextField genreField;
    private JTextField totalCopiesField;

    public BookFormDialog(JFrame parent,BookService bs,Book book){
        super(parent,"Add Book",true);//true=We cannnot control parent until this dialog will close
        setSize(400,300);
        setLocationRelativeTo(parent);//display parent window at the center

        Book editingBook = book;
        
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

        if(editingBook!=null){
            titleField.setText(editingBook.getTitle());
            authorField.setText(editingBook.getAuthor());
            genreField.setText(editingBook.getGenre());
            totalCopiesField.setText(String.valueOf(editingBook.getTotalCopies()));
        }

        JButton saveButton = new JButton("Save Changes");
        
            saveButton.addActionListener(e->{
            try{
                    //event process when click on
                String input_title=titleField.getText();
                String input_author=authorField.getText();
                String input_genre=genreField.getText();
                int input_totalCopies=Integer.valueOf(totalCopiesField.getText());

            if(editingBook==null){
                //ADD MODE
                //totalLentCopies is 0 because new book is not borrowed
                Book newBook = new Book("B"+(bs.getBooks().size()+1),input_title,input_author,input_genre,input_totalCopies,0);
                bs.addBook(newBook);
            }

            else{
                //EDIT MODE
                editingBook.setTitle(input_title);
                editingBook.setAuthor(input_author);
                editingBook.setGenre(input_genre);
                editingBook.setTotalCopies(input_totalCopies);
                bs.updateBook(editingBook);
            }
            dispose();
            
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(null, "Total Copies must be a number!");
            }
            });
        
        form.add(saveButton);
        add(form,BorderLayout.CENTER);
        setVisible(true);
        
    
        
    }
}