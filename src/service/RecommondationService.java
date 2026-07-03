package service;

import java.util.*;
import model.Book;
import model.LoanRecord;


public class RecommondationService{
    private BookService bs;
    private LoanService ls;


    public RecommondationService(BookService bs,LoanService ls){
        this.bs=bs;
        this.ls=ls;

    }
    
    public List<Book> getTopBooks(){
        Map<String,Integer>loanCount = new HashMap<>(); //As my understanding, Map is likely to "Dictionary Type" in Python
        List<Book> result = new ArrayList<>();
        for(LoanRecord loan:ls.getLoans()){//get all loanrecorc
            //entrySet() return Map'S key and value
            String bookID = loan.getBookID();
            int count = loanCount.getOrDefault(bookID,0);
            loanCount.put(bookID,count+1);
        }
        List<Map.Entry<String,Integer>>entries = new ArrayList<>(loanCount.entrySet());//Map.Entry is a one line of Map
            //list by desending order (list by result of the value of b-a)
        entries.sort((a,b)->b.getValue()-a.getValue());

        //put top 3 into result list
        for(int i=0;i<Math.min(3,entries.size());i++){
            String bookID=entries.get(i).getKey(); //get top 3 book's name
            Book book = bs.findByID(bookID);
            if(book!=null)result.add(book);
        }
        return result;
    }
}