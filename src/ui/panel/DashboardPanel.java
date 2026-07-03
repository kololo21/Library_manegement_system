package ui.panel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import javax.swing.*;

import model.Book;
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
    private JLabel recommendLabel1 = new JLabel("-");
    private JLabel recommendLabel2 = new JLabel("-");
    private JLabel recommendLabel3 = new JLabel("-");

    public DashboardPanel(BookService bs,MemberService ms,LoanService ls) {
        this.rs = new RecommondationService(bs, ls);
        this.ms=ms;
        this.ls=ls;

        setLayout((new BorderLayout()));
        JPanel statsJPanel = new JPanel(new GridLayout(2,2));

        statsJPanel.add(new JLabel("Total Books:"+bs.getBooks().size()));   // statsics at top
        statsJPanel.add(new JLabel("Total Members:"+ms.getMembers().size()));   // statsics at top
        statsJPanel.add(new JLabel("Active Loans:"+ls.getActiveLoans().size()));   // statsics at top
        long overdueCount = ls.getActiveLoans().stream().filter(loan->loan.getStatus() == LoanStatus.OVERDUE).count();
        statsJPanel.add(new JLabel("Overdue"+overdueCount));   // statics at top
        add(statsJPanel,BorderLayout.NORTH);
        //model
        String[] columns = {"LoanID", "BookID", "MemberName", "Due Date"};
        model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        

        //Recommand
        JPanel recommandPanel = new JPanel(new GridLayout(4,1));
        recommandPanel.add(new JLabel("---Top 3 Pupular Books---"),BorderLayout.CENTER);
        recommandPanel.add(recommendLabel1,BorderLayout.CENTER);
        recommandPanel.add(recommendLabel2,BorderLayout.CENTER);
        recommandPanel.add(recommendLabel3,BorderLayout.CENTER);

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
        

        //Center Panel(scroll,Recommand,Quit)
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(scroll,BorderLayout.NORTH);
        centerPanel.add(recommandPanel,BorderLayout.CENTER);
        centerPanel.add(quitButton,BorderLayout.EAST);
        add(centerPanel,BorderLayout.CENTER);
        
        refreshTable();
        refreshDashboard();

    }
    public void refreshTable(){
        model.setRowCount(0); //all lines clear
        for (LoanRecord loan:ls.getActiveLoans()){
            if (loan.getStatus() == LoanStatus.OVERDUE) {
                model.addRow(new Object[]{
                    loan.getLoanID(),
                    loan.getBookID(),
                    ms.findByID(loan.getMemberID()).getName(),
                    loan.getDueDate(),
                    
                });
            }
            
        }
    }
    public void refreshDashboard(){
        List<Book> topBooks=rs.getTopBooks();
        recommendLabel1.setText(topBooks.size()>0?topBooks.get(0).getTitle():"-");
        recommendLabel2.setText(topBooks.size()>1?topBooks.get(1).getTitle():"-");
        recommendLabel3.setText(topBooks.size()>2?topBooks.get(2).getTitle():"-");

    }
}

