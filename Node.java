import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/*class of Wire Nodes*/
class Node extends JLabel implements Serializable
{
    /**Name of Node*/
    String name;
    /**X Coordinate of Node*/
    int px;
    /**Y Coordinate of Node*/
    int py;
    /**Radius of Node*/
    int r;
    /**Node Voltage Reading*/
    double volt;
    boolean info;
    boolean mp,nPresent,changed;
    /**Color of Node*/
    Color cl;
    int top;
    Node link,nptr;
    Node links[]=new Node[10];
    /**Type of Node Normal/Fixed*/
    Type t;
    /**State of Node High/Low*/
    State st;
    /**This stores the ID of its parent AND-Gate*/
    Gate id;  
    public Node(int count,int x,int y,Type tt,WorkSpace dp)                            //Constructor for Setting Position and Type
    {
        name="N"+count;
        if(tt==Type.FIXED)
            r=30;
        else
            r=10;               //radius of node.
        px=x;   
        py=y;              //coordinates of node.
        mp=false;
        cl=Color.white;    //color of node.
        link=null;        //link of node in list.
        top=-1;
        nPresent=false;   //boolean variable stating if a particular node is already linked to this node or not.
        t=tt;             //sets the Type of Node.
        st=State.LOW;
        super.setBounds(px-r/2,py-r/2,r,r);

        final WorkSpace ws=dp;
        final JPopupMenu pum=new JPopupMenu();
        JMenuItem it=new JMenuItem("Remove");
        pum.add(it);
        final Node i=this;
        it.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    try{
                            if((i.name).equals(ws.auxHead.name))
                            
                                if(i.t==Type.NORMAL)           //Prevents Deletion of Other Nodes
                                {
                                    Node xptr=i;
                                    ws.auxHead=ws.auxHead.link;
                                    remove(i);
                                    i.setVisible(false);
                                    ws.nodeC--;
                                    xptr.disconnect(ws.auxHead,ws.Terminal);
                                    repaint();
                                }
                            else
                            {
                                Node ptr=ws.auxHead.link;
                                while(ptr.link!=null)
                                {
                                        if((i.name).equals(ws.ptr.link.name))
                                        if(ptr.link.t==Type.NORMAL)           //Prevents Deletion of Other Nodes
                                        {
                                            Node xptr=ptr.link;
                                            ptr.link=ptr.link.link;
                                            ws.nodeC--;
                                            xptr.disconnect(ws.auxHead,ws.Terminal);
                                            remove(xptr);
                                            repaint();
                                        }
                                        else break;
                                    }
                                    ptr=ptr.link; 
                            }
                    }//End of try Block
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(new JFrame(),e);
                    }
                }
            });
        it=new JMenuItem("Connect");
        pum.add(it);
        it.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {

                }
            });
        it=new JMenuItem("Disconnect");
        pum.add(it);
        it.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {

                }
            });
        it=new JMenuItem("DisconnectAll");
        pum.add(it);
        it.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {

                }
            });
        /*this.addMouseListener(new MouseAdapter()
            {
                public void mousePressed( MouseEvent me )
                {
                    checkForTriggerEvent( me );
                }

                public void mouseReleased( MouseEvent me )
                {
                    checkForTriggerEvent( me );
                }

                private void checkForTriggerEvent( MouseEvent me )
                {
                    //if ( me.isPopupTrigger() )
                    pum.show( me.getComponent(), me.getX(), me.getY() );
                }
            });*/
    }

    public Node(int count,int x,int y,Type tt,State stt,WorkSpace dp)           //Constructor For Adding Color too
    {
        name="N"+count;
        if(tt==Type.FIXED)
            r=30;
        else
            r=10;   //radius of node.
        px=x;   
        py=y;   //coordinates of node.
        mp=false;
        link=null;     //link of node in list.
        top=-1;
        nPresent=false;   //boolean variable stating if a particular node is already linked to this node or not.
        t=tt;             //sets the Type of Node.
        st=stt;
        switch(st)
        {
            case HIGH:this.cl=Color.red;this.volt=5.0;break;
            case LOW:this.cl=Color.blue;this.volt=0.0;break;
        }
        super.setBounds(px-r/2,py-r/2,r,r);
        final WorkSpace ws=dp;
        final JPopupMenu pum=new JPopupMenu();
        JMenuItem it=new JMenuItem("Remove");
        pum.add(it);
        final Node i=this;
        it.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    try{
                            if((i.name).equals(ws.auxHead.name))
                            
                                if(i.t==Type.NORMAL)           //Prevents Deletion of Other Nodes
                                {
                                    Node xptr=i;
                                    ws.auxHead=ws.auxHead.link;
                                    remove(i);
                                    i.setVisible(false);
                                    ws.nodeC--;
                                    xptr.disconnect(ws.auxHead,ws.Terminal);
                                    repaint();
                                }
                            else
                            {
                                Node ptr=ws.auxHead.link;
                                while(ptr.link!=null)
                                {
                                        if((i.name).equals(ws.ptr.link.name))
                                        if(ptr.link.t==Type.NORMAL)           //Prevents Deletion of Other Nodes
                                        {
                                            Node xptr=ptr.link;
                                            ptr.link=ptr.link.link;
                                            ws.nodeC--;
                                            xptr.disconnect(ws.auxHead,ws.Terminal);
                                            remove(xptr);
                                            repaint();
                                        }
                                        else break;
                                    }
                                    ptr=ptr.link; 
                            }
                    }//End of try Block
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(new JFrame(),e);
                    }
                }
            });
        it=new JMenuItem("Connect");
        pum.add(it);
        it.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {

                }
            });
        it=new JMenuItem("Disconnect");
        pum.add(it);
        it.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {

                }
            });
        it=new JMenuItem("DisconnectAll");
        pum.add(it);
        it.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {

                }
            });
        /*this.addMouseListener(new MouseAdapter()
            {
                public void mousePressed( MouseEvent me )
                {
                    checkForTriggerEvent( me );
                }

                public void mouseReleased( MouseEvent me )
                {
                    checkForTriggerEvent( me );
                }

                private void checkForTriggerEvent( MouseEvent me )
                {
                    //if ( me.isPopupTrigger() )
                    pum.show( me.getComponent(), me.getX(), me.getY() );
                }
            });*/
    }

    public void connect(Node ptr,Node auxHead)                   //function to connect two nodes.
    {
        nPresent=false;
        for(int i=0;i<=top;i++)       //this block checks for overlapping of nodes
        {
            if((this.links[i]).equals(ptr))
                nPresent=true;
        }
        if(nPresent==false&&top<10)
        {
            //condition to prevent nodes from switching state.
            {
                this.links[++(this.top)]=ptr;
                ptr.links[++(ptr.top)]=this;
            }
            //Now the nodes should check their mutually-connected states
            if(this.st==State.HIGH&&ptr.st==State.LOW)
            {
                ptr.st=State.HIGH;

                if(ptr.t==Type.GATE)
                {
                    ptr.id.setLogic();
                }
                ptr.changeConnected();
            }
            else if(this.st==State.LOW&&ptr.st==State.HIGH)
            {
                this.st=State.HIGH;
                if(this.t==Type.GATE)
                {
                    this.id.setLogic();
                }
                this.changeConnected();
            }
        }

        auxHead.reColor();
    }

    public void disconnect(Node auxHead,Node Terminal)
    {
        for(int i=0;i<=this.top;i++)
        {
            Node pptr=this.links[i];
            for(int j=0;j<=pptr.top;j++)
            {
                if((pptr.links[j]).equals(this))
                {
                    for(int k=j;k<pptr.top;k++)
                        pptr.links[k]=pptr.links[k+1];
                    --pptr.top;
                }
            }
        }

        change2Low(auxHead);
        Terminal.link.changeConnected();
        auxHead.reColor();
    }

    public void changeConnected()
    {
        for(int i=0;i<=this.top;i++)
        {
            if(this.links[i].st!=this.st)
            {
                this.links[i].st=this.st;
                if(this.links[i].t==Type.GATE)
                {
                    this.links[i].id.setLogic();
                }
                this.links[i].changeConnected();
            }
        }
    }

    public static void change2Low(Node ptr)
    {
        while(ptr!=null)
        {
            if(ptr.t!=Type.FIXED)
            {
                ptr.st=State.LOW;
                ptr.changeConnected();
            }
            if(ptr.t==Type.GATE)
                ptr.id.setLogic();
            ptr=ptr.link;
        }
    }

    public void reColor()
    {
        Node ptr=this;
        for(;ptr!=null;ptr=ptr.link)
        {
            if(ptr.t!=Type.FIXED)
            {
                if(ptr.st==State.HIGH)
                    ptr.cl=Color.red;
                else if(ptr.st==State.LOW)
                    ptr.cl=Color.blue;
                repaint();  
            }
        }
    }

    public void setXY(int x,int y)//function to add nodes to gates
    {
        px=x;
        py=y;
        setBounds(px-r/2,py-r/2,r,r);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.setColor(this.cl);
        g.fillArc(0,0,this.r,this.r,0,360);    
    }
}    
