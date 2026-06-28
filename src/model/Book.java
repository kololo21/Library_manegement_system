package model;

import java.io.Serializable;

public class Book implements Serializable{
    private static final long serialVersionUID=1L;
    private String bookID="";
    private String title="";
    private String author="";
    private String genre="";
    private int totalCopies=0;
    private int lentCopies=0;
    
    public Book(String bookID,String title,String author,String genre,int totalCopies,int lentCopies){
        this.bookID=bookID;
        this.title=title;
        this.author=author;
        this.genre=genre;
        this.totalCopies=totalCopies;
        this.lentCopies=lentCopies;
    }
    //Getter and Setter
    public String getBookID(){return bookID;}
    public String getTitle(){return title;}
    public void setTitle(String title){this.title=title;}
    public String getAuthor(){return author;}
    public void setAuthor(String author){this.author=author;}
    public String getGenre(){return genre;}
    public void setGenre(String genre){this.genre=genre;}

    public int getTotalCopies(){return totalCopies;}
    public void setTotalCopies(int totalCopies){this.totalCopies=totalCopies;}
    public int getLentCopies(){return lentCopies;}
    public void setLentCopies(int lentCopies){this.lentCopies=lentCopies;}
    
    //return how many books does the library`s container have
    public int getAvailableCopies(){return totalCopies-lentCopies;}
    //return can user lent book with valid process
    public boolean isAvailable(){
        int check = getAvailableCopies();
        return check>0;
    }
    //equal method:this is a method used for finding a perticular object in the List
    public boolean equals(String comparing_bookID){
        return (this.bookID.equals(comparing_bookID))?true:false;
    }

}