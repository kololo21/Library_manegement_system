package ui.dialog;

import javax.swing.*;
import java.awt.*;
import model.Member;
import service.MemberService;


public class MemberFormDialog extends JDialog{
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;

    public MemberFormDialog(JFrame parent,MemberService ms,Member member){
        super(parent,"Add member",true);//true=We cannnot control parent until this dialog will close
        setSize(400,300);
        setLocationRelativeTo(parent);//display parent window at the center

        Member editingMember = member;
        
        //oneline text input
        JPanel form = new JPanel(new GridLayout(5,2));//5x2 matrix
        nameField = new JTextField(20);//length for 20 words
        form.add(new JLabel("Name"));
        form.add(nameField);

        emailField = new JTextField(20);//length for 20 words
        form.add(new JLabel("Email"));
        form.add(emailField);

        phoneField = new JTextField(20);//length for 20 words
        form.add(new JLabel("Phone"));
        form.add(phoneField);
        

        if(editingMember!=null){
            nameField.setText(editingMember.getName());
            emailField.setText(editingMember.getEmail());
            phoneField.setText(editingMember.getPhone());
        }

        JButton saveButton = new JButton("Save Changes");
        
            saveButton.addActionListener(e->{
            try{
                    //event process when click on
                String input_name=nameField.getText();
                String input_email=emailField.getText();
                String input_phone=phoneField.getText();

            if(editingMember==null){
                //ADD MODE
                //totalLentCopies is 0 because new member is not borrowed
                Member newMember = new Member("M"+(ms.getMembers().size()+1),input_name,input_email,input_phone);
                ms.addMember(newMember);
            }

            else{
                //EDIT MODE
                editingMember.setName(input_name);
                editingMember.setEmail(input_email);
                editingMember.setPhone(input_phone);
                ms.updateMember(editingMember);
            }
            dispose();
            
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Input valid string value.");
            }
            });
        
        form.add(saveButton);
        add(form,BorderLayout.CENTER);
        setVisible(true);
        
    
        
    }
}