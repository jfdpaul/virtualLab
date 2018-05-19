import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class LabFrame extends JFrame 
{
    JLabel l;       //Information Label
    JMenuBar mb;    //Stores instance of MenuBar
    JScrollPane sp; //Contains DrawingPanel
    WorkSpace ws;
    String name;
    int XX,YY;      //Size of frame
    public LabFrame()
    {
        super("New");
        name="New";
        XX=900;
        YY=900;                 //sets initial size of frame
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(XX,YY);

        ws=new WorkSpace();   //Drawing pane
        mb=new  MenuBar(ws,this);    //Menubar
        sp=new JScrollPane(ws); //ScrollPane
        l=new JLabel("For Assistance Check Help");  //label
        add("North",mb);
        add("Center",sp);
        add("South",l);       //Adding the Components to the frame
        setTitle(name);
        
    }
}