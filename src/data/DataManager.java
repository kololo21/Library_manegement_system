package data;

//Read and Write Files using Java Serialization

import java.io.*;
import java.util.*;
import model.Book;
import model.Member;
import model.LoanRecord;

public class DataManager{
    private static final String BOOKS_FILE = "data/books.dat";
    private static final String MEMBERS_FILE = "data/members.dat";
    private static final String LOANS_FILE = "data/loans.dat";

    public void saveBooks(List<Book> books){//this try-with-resorce style allows that we do not have to concern about closing stream
        try(ObjectOutputStream oos =new ObjectOutputStream(new FileOutputStream((BOOKS_FILE)))){
                oos.writeObject(books);
            }
        catch(IOException e){
            String message=e.getMessage();
            System.err.println(message);
        }
    }
    @SuppressWarnings("unchecked")//resolve Type safety
    public List<Book> loadBooks(){
        try(ObjectInputStream ois=new ObjectInputStream(new FileInputStream((BOOKS_FILE)))){
          List<Book>list = (List<Book>)ois.readObject();
            return list;
        }catch(IOException e){
            String message=e.getMessage();
            System.err.println(message);
            return new ArrayList<>();
        }catch(ClassNotFoundException e){
            String message=e.getMessage();
            System.err.println(message);
            return new ArrayList<>();
        }
    }

    public void saveMembers(List<Member> members){
        try(ObjectOutputStream oos =new ObjectOutputStream(new FileOutputStream((MEMBERS_FILE)))){
                oos.writeObject(members);
            }
        catch(IOException e){
            String message=e.getMessage();
            System.err.println(message);
        }
    }
    @SuppressWarnings("unchecked")
    public List<Member> loadMembers(){
        try(ObjectInputStream ois=new ObjectInputStream(new FileInputStream((MEMBERS_FILE)))){
            List<Member>list = (List<Member>)ois.readObject();
            return list;
        }catch(IOException e){
            String message=e.getMessage();
            System.err.println(message);
            return new ArrayList<>();
        }catch(ClassNotFoundException e){
            String message=e.getMessage();
            System.err.println(message);
            return new ArrayList<>();
        }
    }
    public void saveLoans(List<LoanRecord> loans){
        try(ObjectOutputStream oos =new ObjectOutputStream(new FileOutputStream((LOANS_FILE)))){
                oos.writeObject(loans);
            }
           
        catch(IOException e){
            String message=e.getMessage();
            System.err.println(message);
        }
    }
    @SuppressWarnings("unchecked")
    public List<LoanRecord>loadLoans(){
        try(ObjectInputStream ois=new ObjectInputStream(new FileInputStream((LOANS_FILE)))){
            List<LoanRecord>list = (List<LoanRecord>)ois.readObject();
            return list;
        }catch(IOException e){
            String message=e.getMessage();
            System.err.println(message);
            return new ArrayList<>();
        }catch(ClassNotFoundException e){
            String message=e.getMessage();
            System.err.println(message);
            return new ArrayList<>();
        }
    }


}