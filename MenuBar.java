import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class MenuBar extends JMenuBar implements Serializable
{
    boolean nMode,cMode;   //nMode->Node Mode.....cMode->Connection Mode...
    Color back;            //Deternines background color for drawing pane.
    WorkSpace ws;        //Stores instance of DrawingPane to send it values.
    MenuBar mb;             //Store instance of the itself(menubar).
    LabFrame lb;
    NAND xNand;
    NOR xNor;
    NOT xNot;
    /*NOR xNor;
    NAND xNand;
    XOR xXor;
    XNOR xXnor;*/
    //more gate object variables to follow
    public MenuBar(WorkSpace p,LabFrame ll)
    {
        setBackground(new Color(150,150,150));
        lb=ll;
        ws=p;
        mb=this;
        nMode=false;
        cMode=false;
        JMenu pro=new JMenu("Project");
        JMenuItem save=new JMenuItem("Save");
        JMenuItem open=new JMenuItem("Open");
        JMenu tools=new JMenu("Tools");
        JMenuItem pImg=new JMenuItem("Print_Screen");
        JMenuItem sImg=new JMenuItem("Save_Screen_Shot");
        JMenu options=new JMenu("Options");
        JMenu comps=new JMenu("Components");
        JMenu nodes=new JMenu("Node");
        JMenuItem connect=new JMenuItem("Connect");
        JMenuItem create=new JMenuItem("Create");
        JMenu gates=new JMenu("Gates");
        JMenuItem and=new JMenuItem("AND");
        JMenuItem or=new JMenuItem("OR");
        JMenuItem not=new JMenuItem("NOT");
        JMenuItem nand=new JMenuItem("NAND");
        JMenuItem nor=new JMenuItem("NOR");
        JMenuItem xor=new JMenuItem("XOR");
        JMenuItem xnor=new JMenuItem("XNOR");
        JMenuItem abt=new JMenuItem("About");
        JMenu help=new JMenu("Help");
        pro.add(save);
        pro.add(open);
        tools.add(pImg);
        tools.add(sImg);
        help.add(abt);
        nodes.add(create);
        nodes.add(connect);
        comps.add(nodes);
        gates.add(and);
        gates.add(or);
        gates.add(not);
        gates.add(xor);
        gates.add(nand);
        gates.add(nor);
        gates.add(xnor);
        comps.add(gates);
        options.add(comps);
        mb.add(pro);
        mb.add(tools);
        mb.add(options);
        mb.add(help);
        save.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                try{
                    FileDialog fd=new FileDialog(lb,"Save",FileDialog.SAVE);
                    fd.setVisible(true);
                    String name=fd.getFile();
                    Saver.save(ws,name);
                    /*FileOutputStream fos=new FileOutputStream(name);
                    ObjectOutputStream oos=new ObjectOutputStream(fos);
                    oos.writeObject(lb); 
                    oos.flush();
                    oos.close();*/
                    lb.l.setText("Saved");
                }
                catch(Exception e){e.printStackTrace();}
            }
        });
        open.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                try{
                    FileDialog fd=new FileDialog(lb,"Load",FileDialog.LOAD);
                    fd.setVisible(true);
                    String name=fd.getFile();
                    lb.name=name;
                    lb.setTitle(name);
                    Saver.loadG(lb,name);
                    /*FileInputStream fis=new FileInputStream(name);
                    ObjectInputStream ois=new ObjectInputStream(fis);
                    lb=(LabFrame)ois.readObject();
                    ois.close();
                    lb.l.setText("Loaded");*/
                    lb.ws.repaint();
                 }
                catch(Exception e){e.printStackTrace();}   
            }
        });
        connect.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    if(cMode==true)
                    {
                        cMode=false;
                        back=Color.black;
                    }
                    else if(cMode==false)
                    {
                        cMode=true;
                        nMode=false;
                        back=Color.magenta;
                    }
                    ws.repaintDrawingPane(mb);
                }
            });
        create.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    if(nMode==true)
                    {
                        nMode=false;
                        back=Color.black;
                    }
                    else if(nMode==false)
                    {
                        nMode=true;
                        cMode=false;
                        back=Color.cyan;
                    }
                    ws.repaintDrawingPane(mb);
                }
            });
        nand.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    xNand=new NAND(++(ws.nandC),ws);
                    xNand.link=ws.nandHead;
                    ws.nandHead=xNand;
                    ws.add(xNand);
                    /*xAnd.in1Link.link=xAnd.in2Link;
                    xAnd.in2Link.link=xAnd.outLink;
                    xAnd.outLink.link=ws.auxHead;
                    ws.auxHead=xAnd.in1Link;*/
                }
            });
        nor.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                xNor=new NOR(++(ws.norC),ws);
                xNor.link=ws.norHead;
                ws.norHead=xNor;
                ws.add(xNor);
                
            }
        });
        not.addActionListener(new ActionListener()
        {
        public void actionPerformed(ActionEvent ae)
        {
            xNot=new NOT(++(ws.notC),ws);
            xNot.link=ws.notHead;
            ws.notHead=xNot;
            ws.add(xNot);
        
        }
        });
        abt.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                JOptionPane.showMessageDialog(new JFrame(),"Created By:Jonathan Paul");
            }
        });
       /* nand.addActionListener(new ActionListener()
        {
        public void actionPerformed(ActionEvent ae)
        {
        xNand=new NAND();
        xNand.link=dp.nandHead;
        dp.nandHead=xNand;
        xNand.in1Link.link=xNand.in2Link;
        xNand.in2Link.link=xNand.outLink;
        xNand.outLink.link=dp.auxHead;
        dp.auxHead=xNand.in1Link;
        }
        });
        nor.addActionListener(new ActionListener()
        {
        public void actionPerformed(ActionEvent ae)
        {
        xNor=new NOR();
        xNor.link=dp.norHead;
        dp.norHead=xNor;
        xNor.in1Link.link=xNor.in2Link;
        xNor.in2Link.link=xNor.outLink;
        xNor.outLink.link=dp.auxHead;
        dp.auxHead=xNor.in1Link;
        }
        });
        xor.addActionListener(new ActionListener()
        {
        public void actionPerformed(ActionEvent ae)
        {
        xXor=new XOR();
        xXor.link=dp.xorHead;
        dp.xorHead=xXor;
        xXor.in1Link.link=xXor.in2Link;
        xXor.in2Link.link=xXor.outLink;
        xXor.outLink.link=dp.auxHead;
        dp.auxHead=xXor.in1Link;
        }
        });
        xnor.addActionListener(new ActionListener()
        {
        public void actionPerformed(ActionEvent ae)
        {
        xXnor=new XNOR();
        xXnor.link=dp.xnorHead;
        dp.xnorHead=xXnor;
        xXnor.in1Link.link=xXnor.in2Link;
        xXnor.in2Link.link=xXnor.outLink;
        xXnor.outLink.link=dp.auxHead;
        dp.auxHead=xXnor.in1Link;
        }
        });*/
        
    }
}  //End of class MenuBar