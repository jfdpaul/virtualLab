import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class MainMenuBar extends JMenuBar
{
    JMenu pro,tools,help,info;
    JMenuItem open,nu,pImg,sImg,ttl,ic,abt;
    LabFrame lb;
    public MainMenuBar()
    {
        setBackground(new Color(150,150,200));
        pro=new JMenu("Project");
        info=new JMenu("Information");
        abt=new JMenuItem("About");
        help=new JMenu("Help");
        add(pro);
        add(info);
        add(help);
        open=new JMenuItem("Open");
        nu=new JMenuItem("New");
        
        ttl=new JMenuItem("TTL");
        ic=new JMenuItem("IC");
        pro.add(nu);
        pro.add(open);
        info.add(ttl);
        info.add(ic);
        help.add(abt);
        open.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    try{
                        FileDialog fd=new FileDialog(lb,"Load",FileDialog.LOAD);
                        fd.setVisible(true);
                        String name=fd.getFile();
                        lb=new LabFrame();
                        lb.name=name;
                        lb.setTitle(name);
                        Saver.loadG(lb,name);
                    }
                    catch(Exception e)
                    {JOptionPane.showMessageDialog(lb,e);} 
                }
            });
        nu.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                new LabFrame();
            }
        });
        abt.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                JOptionPane.showMessageDialog(new JFrame(),"Created By:Jonathan Paul");
            }
        });
    }
}