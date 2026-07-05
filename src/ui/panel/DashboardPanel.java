package ui.panel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import javax.swing.*;
import java.awt.Component;
import java.awt.FlowLayout;

import model.Book;
import model.Member;
import model.LoanRecord;
import model.LoanStatus;
import service.BookService;
import service.LoanService;
import service.MemberService;
import service.RecommondationService;


public class DashboardPanel extends JPanel {
    private DefaultTableModel model;
    private RecommondationService rs;
    private MemberService ms;
    private LoanService ls;
    private BookService bs;
    private JLabel totalBooksLabel;
    private JLabel totalMembersLabel;
    private JLabel activeLoansLabel;
    private JLabel overdueLabel;
    private JLabel recommendLabel1 = new JLabel("-",JLabel.CENTER);
    private JLabel recommendLabel2 = new JLabel("-",JLabel.CENTER);
    private JLabel recommendLabel3 = new JLabel("-",JLabel.CENTER);

    public DashboardPanel(BookService bs,MemberService ms,LoanService ls) {
        this.rs = new RecommondationService(bs, ls);
        this.ms=ms;
        this.ls=ls;
        this.bs=bs;

        setLayout((new BorderLayout()));
        JPanel statsJPanel = new JPanel(new GridLayout(2,2));

        totalBooksLabel=new JLabel("Total Books:"+bs.getBooks().size());
        statsJPanel.add(totalBooksLabel);   // statsics at top
        totalMembersLabel=new JLabel("Total Members:"+ms.getMembers().size());
        statsJPanel.add(totalMembersLabel);   // statsics at top
        activeLoansLabel=new JLabel("Active Loans:"+ls.getActiveLoans().size());
        statsJPanel.add(activeLoansLabel);   // statsics at top
        long overdueCount = ls.getActiveLoans().stream().filter(loan->loan.getStatus() == LoanStatus.OVERDUE).count();
        overdueLabel=new JLabel("Overdue:"+overdueCount);
        statsJPanel.add(overdueLabel);   // statics at top
        add(statsJPanel,BorderLayout.NORTH);

        //model
        String[] columns = {"LoanID", "BookID", "MemberName", "Due Date"};
        model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        

        //Recommand
        JPanel recommandPanel = new JPanel();
        recommandPanel.setLayout(new BoxLayout(recommandPanel, BoxLayout.Y_AXIS));
        JLabel recTitle = new JLabel("---Top 3 Pupular Books---",JLabel.CENTER);
        recTitle.setFont(new Font("Arial",Font.BOLD,16));
        recTitle.setAlignmentX((Component.CENTER_ALIGNMENT));
        recommendLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        recommendLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        recommendLabel3.setAlignmentX(Component.CENTER_ALIGNMENT);

        recommandPanel.add(recTitle);
        recommandPanel.add(recommendLabel1);
        recommandPanel.add(recommendLabel2);
        recommandPanel.add(recommendLabel3);

        //Quit Button
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e->{
            //event process when click on
 
            int result = JOptionPane.showConfirmDialog(this, "Quit","Confirm",JOptionPane.YES_NO_OPTION);
            if(result == JOptionPane.YES_OPTION){
                //Process for Yes
                System.exit(0);
            }
        });
        
        //OVERDUE TITLE
        JLabel overdueTitle = new JLabel("PEOPLE WHO HAVE OVERDUED BOOKS",JLabel.CENTER);
        //tablePANEL(scroll+overdue)
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(overdueTitle,BorderLayout.NORTH);
        tablePanel.add(scroll,BorderLayout.CENTER);

        //Center Panel(Recommand,Quit)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(recommandPanel);
        centerPanel.add(quitButton);
        add(tablePanel,BorderLayout.CENTER);
        add(centerPanel,BorderLayout.SOUTH);
        refreshTable();

    }
    public void refreshTable(){
        ls.updateOverdueStatus();
        
        //.stream
        long overdueCount = ls.getActiveLoans().stream().filter(loan->loan.getStatus()==LoanStatus.OVERDUE).count();
        totalBooksLabel.setText("Total Books:"+bs.getBooks().size());
        totalMembersLabel.setText("Total Members:"+ms.getMembers().size());
        activeLoansLabel.setText("Active Loans:"+ls.getActiveLoans().size());
        overdueLabel.setText("Overdue:"+overdueCount);
        model.setRowCount(0); //all lines clear
                for (LoanRecord loan:ls.getActiveLoans()){
                    if (loan.getStatus() == LoanStatus.OVERDUE) {
                        Member m = ms.findByID(loan.getMemberID());
                        String name = (m==null)?"(deleted)":m.getName();
                        model.addRow(new Object[]{
                            loan.getLoanID(),
                            loan.getBookID(),
                            name,
                            loan.getDueDate(),
                            
                        });
                    }
                    
                }
   
        List<Book> topBooks=rs.getTopBooks();
        recommendLabel1.setText(topBooks.size()>0?topBooks.get(0).getTitle():"-");
        recommendLabel2.setText(topBooks.size()>1?topBooks.get(1).getTitle():"-");
        recommendLabel3.setText(topBooks.size()>2?topBooks.get(2).getTitle():"-");

    }
}

