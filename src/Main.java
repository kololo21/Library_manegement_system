
import javax.swing.SwingUtilities;
import data.DataManager;
import model.Book;
import model.Member;
import service.*;
import ui.MainFrame;

public class Main{
    public static void main(String[] args) {
    DataManager dm = new DataManager();
    BookService bs = new BookService(dm);
    MemberService ms=new MemberService(dm);
    LoanService ls = new LoanService(dm, bs);

    SwingUtilities.invokeLater(()->{
        MainFrame mf = new MainFrame(bs,ms,ls);
    });
    }
}