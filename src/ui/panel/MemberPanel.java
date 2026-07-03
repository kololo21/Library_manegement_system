package ui.panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

import model.Member;
import service.MemberService;
import ui.dialog.MemberFormDialog;

public class MemberPanel extends JPanel {
    private MemberService ms;
    private JTable table;
    private DefaultTableModel model;//data manegement tool
    private JTextField searchField;


    public MemberPanel(MemberService ms) {
        this.ms = ms;
        //data is gotten from bs.getmembers()
        //DefaultTableModel will manage the data from the table
        String[] columns = {"ID", "Name", "Email","Phone"};

        this.model = new DefaultTableModel(columns,0);
        table = new JTable(model);
        setLayout(new BorderLayout());
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);  // move table to CENTER
        refreshTable();
        
         //Button Event
        JButton addButton = new JButton("Add Member");
        addButton.addActionListener(e->{
            //event process when click on

            //get JFrame(Main Frame) which is a base of memberPanel
            JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
            new MemberFormDialog(parent, ms,null);
            refreshTable();
        });
        JButton editButton = new JButton("Edit member");
        editButton.addActionListener(e->{
            //event process when click on
            int row = table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(this, "Please select a member to edit");
                return;
            }
            String memberID=(String)model.getValueAt(row, 0);//get value at the particular point
            Member member = ms.findByID(memberID);
            JFrame parent =(JFrame)SwingUtilities.getWindowAncestor(this);
            new MemberFormDialog(parent,ms, member);//give member (editor mode)
            refreshTable();
        });
        JButton deleteButton = new JButton("Delete member");
        deleteButton.addActionListener(e->{
            //event process when click on
            int row = table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(this, "Please select a member to delete");
                return;
            }
            int result = JOptionPane.showConfirmDialog(this, "Delete this","Confirm",JOptionPane.YES_NO_OPTION);
            if(result == JOptionPane.YES_OPTION){
                //Process for Yes
                String memberID=(String)model.getValueAt(row, 0);//get value at the particular point
                ms.deleteMember(memberID);
                refreshTable();
            }
            
        });
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel,BorderLayout.SOUTH);
        
        //Searching Bar
        searchField=new JTextField(20);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            //when word is append
            public void insertUpdate(DocumentEvent e){filterTable();}
            //when word is removed
            public void removeUpdate(DocumentEvent e){filterTable();}
            //when style is changed
            public void changedUpdate(DocumentEvent e){filterTable();}

        });

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        add(searchPanel,BorderLayout.NORTH);

    }


    public void filterTable(){
        String keyword = searchField.getText().toLowerCase(); //convert all input to lower case
        model.setRowCount(0);//Table clear

        for(Member member:ms.getMembers()){
            if(member.getName().toLowerCase().contains(keyword)||
            member.getEmail().toLowerCase().contains(keyword)||
            member.getPhone().toLowerCase().contains(keyword)||
            member.getMemberID().toLowerCase().contains(keyword)){
                model.addRow(new Object[]{
                     member.getMemberID(),
                        member.getName(),
                        member.getEmail(),
                        member.getPhone(),
                });
            }

        }
    }


    public void refreshTable(){
        model.setRowCount(0); //all lines clear
        for (Member member:ms.getMembers()){
            model.addRow(new Object[]{
                member.getMemberID(),
                member.getName(),
                member.getEmail(),
                member.getPhone(),
            });
        }

    }
    
}

