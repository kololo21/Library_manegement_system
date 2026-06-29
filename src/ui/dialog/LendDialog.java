package ui.dialog;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Book;
import model.Member;
import service.LoanService;
import service.MemberService;


public class LendDialog extends JDialog{
    private JList<String> memberList;
    private MemberService ms;
    private LoanService ls;
    private String bookID;

    public LendDialog(JFrame parent,MemberService ms,LoanService ls,String bookID){

        super(parent,"Add Lend",true);//true=We cannnot control parent until this dialog will close
        setSize(400,300);
        setLocationRelativeTo(parent);//display parent window at the center

        JButton lendButton = new JButton("Lend this book");

        //GET MEMBER LIST
        List<Member> members = ms.getMembers();
        String[] names= new String[members.size()];
        for(int i=0;i<members.size();i++){
            names[i]=members.get(i).getName();
        }
        memberList= new JList<>(names);

        //DISPLAY MEMBER LIST
        JScrollPane scroll = new JScrollPane(memberList);
        add(scroll,BorderLayout.CENTER);



        lendButton.addActionListener(e->{
            //event process when click on
            try{
                    //event process when click on
                int index = memberList.getSelectedIndex();
                if(index==-1){
                    JOptionPane.showMessageDialog(this, "Select the book to lend");
                }
                String memberID = members.get(index).getMemberID();
                ls.lendBook(bookID, memberID);
                dispose();
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(null, "Select valid option");
            }
            });
            

        
        add(lendButton,BorderLayout.SOUTH);
        setVisible(true);
        
    
        
    }
}