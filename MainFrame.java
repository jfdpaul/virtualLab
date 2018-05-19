import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class MainFrame
{
    JFrame f;
    MainMenuBar mb;
    public MainFrame()
    {
       
        f=new JFrame("VIRTUAL LABORATORY");
        f.setForeground(Color.black);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setSize(Toolkit.getDefaultToolkit().getScreenSize().width/10*9,Toolkit.getDefaultToolkit().getScreenSize().height/10*9);
        mb=new MainMenuBar();
        f.add("North",mb);
    }
    public static void main(String args[])
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                new MainFrame();
            }
        });
    }
}