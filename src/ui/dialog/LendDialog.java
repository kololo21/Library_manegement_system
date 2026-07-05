package ui.dialog;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import model.Member;
import service.LoanService;
import service.MemberService;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class LendDialog extends JDialog{
    private JList<String> memberList;
    private DefaultListModel<String> listModel;
    private List<Member> shown = new ArrayList<>();   // currently displayed members
    private MemberService ms;

    public LendDialog(JFrame parent,MemberService ms,LoanService ls,String bookID){
        super(parent,"Add Lend",true);
        this.ms = ms;
        setSize(400,300);
        setLocationRelativeTo(parent);

        //DISPLAY MEMBER LIST
        listModel = new DefaultListModel<>();
        memberList = new JList<>(listModel);
        add(new JScrollPane(memberList), BorderLayout.CENTER);

        //SEARCH BAR
        JTextField searchField = new JTextField(20);
        searchField.getDocument().addDocumentListener(new DocumentListener(){
            public void insertUpdate(DocumentEvent e){ populate(searchField.getText()); }
            public void removeUpdate(DocumentEvent e){ populate(searchField.getText()); }
            public void changedUpdate(DocumentEvent e){ populate(searchField.getText()); }
        });
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        add(searchPanel, BorderLayout.NORTH);

        populate("");   // show all members at first

        //LEND BUTTON
        JButton lendButton = new JButton("Lend this book");
        lendButton.addActionListener(e->{
            int index = memberList.getSelectedIndex();
            if(index==-1){
                JOptionPane.showMessageDialog(this, "Select a member to lend to");
                return;
            }
            String memberID = shown.get(index).getMemberID();
            ls.lendBook(bookID, memberID);
            dispose();
        });
        add(lendButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    //rebuild the list, filtered by keyword (name or member ID)
    private void populate(String keyword){
        String k = keyword.toLowerCase();
        listModel.clear();
        shown.clear();
        for(Member m : ms.getMembers()){
            if(m.getName().toLowerCase().contains(k) ||
               m.getMemberID().toLowerCase().contains(k)){
                shown.add(m);
                listModel.addElement(m.getName());
            }
        }
    }
}