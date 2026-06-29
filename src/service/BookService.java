package service;
//this is a interface layer between Datamanger(FILE Write/Read) and UI(Screen) 
//In this jave file,function of "Adding Book"and "Lending Book" are built
import java.util.*;
import model.Book;
import data.DataManager;

public class BookService{
    private List<Book>books;
    private DataManager dm;

    public BookService(DataManager dm){
        this.dm=dm;
        this.books=dm.loadBooks();
    }
    public List<Book> getBooks(){return books;}//get all books for view screen
    public void addBook(Book Book){//adding new book
        books.add(Book);
        dm.saveBooks(books);
    }
    public void updateBook(Book book){//input: Book after update ,overwrite book information
        String bookID = book.getBookID();
        for(Book target_book:books){
            if(target_book.getBookID().equals(bookID)){
                int index = books.indexOf(target_book);
                books.set(index,book);
            }
        }
        dm.saveBooks(books);
    }
    public void deleteBook(String bookID){
        
        //lamda method,to prevent ConcurrentModificationException
        books.removeIf(b -> b.getBookID().equals(bookID));
        dm.saveBooks(books);
    }
    public Book findByID(String bookID){
        for(Book target_book:books){
            if(target_book.getBookID().equals(bookID)){
                return target_book;
            }
        }
        return null;
    }
}