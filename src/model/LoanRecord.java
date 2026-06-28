package model;
import java.io.Serializable;
import java.time.LocalDate;

public class LoanRecord implements Serializable{
    private static final long serialVersionUID=1L;
    private String loanID;
    private String bookID;
    private String memberID;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private LoanStatus status; 

    public LoanRecord(String loanID,String bookID,String memberID,LocalDate loanDate){
        this.loanID=loanID;
        this.bookID=bookID;
        this.memberID=memberID;
        this.loanDate=loanDate;
        this.dueDate=this.loanDate.plusDays(14);
        this.returnDate=null;
        this.status=LoanStatus.ACTIVE;
    }
    //Getter 
    public String getLoanID(){return loanID;}
    public String getBookID(){return bookID;}
    public String getMemberID(){return memberID;}
    public LocalDate getLoanDate(){return loanDate;}
    public LocalDate getDueDate(){return dueDate;}
    public LocalDate getReturnDate(){return returnDate;}
    public LoanStatus getStatus(){return status;}

    //Setter
    public void setReturnDate(LocalDate returnDate){this.returnDate=returnDate;}
    public void setStatus(LoanStatus status){this.status=status;}



    
}
