package service;

import java.util.*;
import java.time.LocalDate;
import model.*;
import data.DataManager;

public class LoanService{
    private  List<LoanRecord> loans;
    private DataManager dm;
    private BookService bookService;

    public LoanService(DataManager dm,BookService bookService){
        this.dm =dm;
        this.bookService=bookService;
        this.loans=dm.loadLoans();
    }
    public List<LoanRecord> getLoans(){return loans;}
    public void lendBook(String bookID,String memberID){
        Book book =bookService.findByID(bookID);
        if(book==null||!book.isAvailable()){
            return;
        }else{
            LocalDate loanDate = LocalDate.now();
            LoanRecord record =new LoanRecord("L"+(loans.size()+1), bookID, memberID,loanDate);
            loans.add(record);
            dm.saveLoans(loans);
            
            book.setLentCopies(book.getLentCopies()+1);
            bookService.updateBook(book);
        }
    }
    public void returnBook(String loanID) {
        for(LoanRecord loan:loans){
            if(loan.getLoanID().equals(loanID)){
                LocalDate returnDate = LocalDate.now();
                loan.setReturnDate(returnDate);
                loan.setStatus(LoanStatus.RETURNED);
                int index = loans.indexOf(loan);
                loans.set(index,loan);
                Book book=bookService.findByID((loan.getBookID()));
                book.setLentCopies(book.getLentCopies()-1);
                bookService.updateBook(book);
                dm.saveLoans(loans);
            }
        }
    }
    public List<LoanRecord> getActiveLoans(){
        List<LoanRecord> result = new ArrayList<>();
        for(LoanRecord loan : loans){
            if(loan.getStatus().equals(LoanStatus.ACTIVE)||loan.getStatus().equals(LoanStatus.OVERDUE)){
                result.add(loan);
            }
        }
        return result;
    }

    public void updateOverdueStatus(){
        for(LoanRecord loan : loans){
            if(loan.getStatus().equals(LoanStatus.ACTIVE)&&loan.getDueDate().isBefore(LocalDate.now())){
                loan.setStatus(LoanStatus.OVERDUE);
            }
        }
        dm.saveLoans(loans);
    }
}
