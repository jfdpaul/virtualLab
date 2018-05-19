import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**class which draws all components for use(Gates,wires,etc.)*/
class WorkSpace extends JPanel implements Serializable,Runnable
{   
    //objects of primary terminals.
    /**Head Node for Temporary Linked List
     */
    Node auxHead;
    /**Temporary Node
     */
    Node xNode;
    /**Stores reference of High Voltage Node source
     */
    Node Terminal;
    /**Source Clock Node 
     */
    Node Clock; 
    /**Iteration NOde
     */
    Node ptr;
    /**Previous Pointer
     */
    Node pptr;
    /**Connection Node
     */
    Node cptr;
    /**Variable Node
     */
    Node xptr;
    /**Pointer storing list of AND gates.
     */
    NAND nandHead;
    /**Pointer storing list of NOT gates.
     */
    NOT notHead;
    /**Pointer storing list of OR gates.
     */ 
    NOR norHead;
    /**Number of present Nodes*/
    int nodeC;
    /**Number of AND Gates*/
    int nandC;
    /**Number of NOR Gates*/
    int norC;
    /**Number of NOT Gates*/
    int notC;
    /*
    NAND nandHead;             //Pointer storing list of NAND gates.
    NOR norHead;               //Pointer storing list of NOR gates.
    XOR xorHead;               //Pointer storing list of XOR gates.
    XNOR xnorHead;             //Pointer storing list of XNOR gates.*/
    Thread t;

    int XX,YY;

    int cnt;
    /**X position of Present Mouse Position*/
    int mx;
    /**Y position of Present Mouse position*/
    int my;
    /**New X position of mouse during movement of objects*/
    int nx;
    /**New Y position of mouse during movement of objects*/
    int ny;
    //int px;
    //int py;

    /**nMode->Mode to Create Nodes
     */
    boolean nMode;
    /**Mode to connect Nodes*/
    boolean cMode;
    /**States Mouse Pressed?*/
    boolean mp;
    /**Tells the object to stop thread*/
    boolean end;
    NAND nandPtr;
    NOT notPtr;
    NOR norPtr;
    /*NAND nandPtr;
    NOR norPtr;
    XOR xorPtr;
    XNOR xnorPtr;*/
    Color back;
    final JPopupMenu pum;
    /**Sets the variables. Also contains the Listeners.*/
    public WorkSpace()                                            //Drawing_Pane_Constructor
    {
        nodeC=0;
        nandC=0;
        notC=0;
        norC=0;
        setPreferredSize(new Dimension(2000,2000));
        XX=900;
        YY=900;
        end=false;
        cnt=0;         //Counts MouseClicks
        auxHead=null;  //Stores Nodes
        back=Color.black;   
        this.setLayout(null);

        xNode=new Node(-1,40,40,Type.FIXED,State.HIGH,this);
        xNode.name="+v";
        xNode.link=auxHead;
        auxHead=xNode;
        add(xNode);

        xNode=new Node(-2,40,100,Type.FIXED,State.LOW,this);   //Initialising initial Terminals
        xNode.name="-v";
        xNode.link=auxHead;
        auxHead=Terminal=xNode;
        add(xNode);

        Clock=new Node(-3,40,160,Type.FIXED,State.HIGH,this);
        Clock.name="cl";
        Clock.link=auxHead;
        auxHead=Clock;
        add(Clock);
        t=new Thread(this);
        t.start();
        setComponentPopupMenu(new JPopupMenu());
        super.addMouseMotionListener(new MouseMotionListener()
            {
                public void mouseDragged(MouseEvent mme)             //MOUSE_DRAGGED
                {
                    final MouseEvent me=mme;
                    try
                    {
                        SwingUtilities.invokeLater(new Runnable()
                            {
                                public void run()
                                {
                                    /*'while' Block to move Nodes being Dragged*/
                                    ptr=auxHead;
                                    while(ptr!=null)
                                    {

                                        if(ptr.mp==true&&cMode==false)
                                        {
                                            nx=me.getX();
                                            ny=me.getY();
                                            ptr.px=ptr.px+nx-mx;
                                            ptr.py=ptr.py+ny-my;
                                            ptr.setBounds(ptr.px-ptr.r/2,ptr.py-ptr.r/2,ptr.r,ptr.r);
                                            mx=nx;
                                            my=ny;
                                        }
                                        ptr=ptr.link;
                                    }
                                    /*Block to mave NAND Gates*/
                                    nandPtr=nandHead;           
                                    while(nandPtr!=null)
                                    {
                                        if(nandPtr.mp==true&&cMode==false)
                                        {
                                            nx=me.getX();
                                            ny=me.getY();
                                            nandPtr.px=nandPtr.px+nx-mx;
                                            nandPtr.py=nandPtr.py+ny-my;
                                            nandPtr.setBounds(nandPtr.px,nandPtr.py,nandPtr.r,nandPtr.r);
                                            nandPtr.setNodes();
                                            mx=nx;
                                            my=ny;

                                        }
                                        nandPtr=nandPtr.link;
                                    }

                                    /*Block to mave NOR Gates*/
                                    norPtr=norHead;           
                                    while(norPtr!=null)
                                    {
                                        if(norPtr.mp==true&&cMode==false)
                                        {
                                            nx=me.getX();
                                            ny=me.getY();
                                            norPtr.px=norPtr.px+nx-mx;
                                            norPtr.py=norPtr.py+ny-my;
                                            norPtr.setBounds(norPtr.px,norPtr.py,norPtr.r,norPtr.r);
                                            norPtr.setNodes();
                                            mx=nx;
                                            my=ny;

                                        }
                                        norPtr=norPtr.link;
                                    }

                                    /*Block to mave NOT Gates*/
                                    notPtr=notHead;           
                                    while(notPtr!=null)
                                    {
                                        if(notPtr.mp==true&&cMode==false)
                                        {
                                            nx=me.getX();
                                            ny=me.getY();
                                            notPtr.px=notPtr.px+nx-mx;
                                            notPtr.py=notPtr.py+ny-my;
                                            notPtr.setBounds(notPtr.px,notPtr.py,notPtr.r,notPtr.r);
                                            notPtr.setNodes();
                                            mx=nx;
                                            my=ny;

                                        }
                                        notPtr=notPtr.link;
                                    }
                                }
                            });
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(new JFrame(),e);
                    }
                    repaint();
                }

                public void mouseMoved(MouseEvent me)                        //MOUSE_MOVED
                {
                    cnt=0;
                    ptr=auxHead;
                    while(ptr!=null)
                    {
                        if(mx>ptr.px-(ptr.r)/2&&mx<(ptr.px+(ptr.r)/2)&&my>ptr.py-(ptr.r)/2&&my<(ptr.py+(ptr.r)/2))
                        {
                            ptr.info=true;
                            repaint();
                        }
                        else
                            ptr.info=false;
                        ptr=ptr.link;
                    }
                }
            });
        final WorkSpace ws=this;
        super.addMouseListener(new MouseListener()
            {
                public void mouseEntered(MouseEvent me){}

                public void mouseExited(MouseEvent me){}

                public void mouseClicked(MouseEvent me)                      //MOUSE_CLICKED
                {
                    mx=me.getX();
                    my=me.getY();
                    cnt+=1;

                    /*Block to Create nodes*/
                    if(nMode==true&&(cnt==1)&&cMode==false)
                    {
                        {
                            xNode=new Node(++nodeC,mx,my,Type.NORMAL,ws);
                            xNode.link=auxHead;
                            auxHead=xNode;
                            add(xNode);
                            repaint();
                            return;
                        }
                    }

                    /*this if(cnt==2) is to delete the nodes*/
                    try{
                        if(cnt==2)
                        {
                            ptr=auxHead;
                            if(mx>ptr.px-(ptr.r)/2&&mx<(ptr.px+(ptr.r)/2)&&my>ptr.py-(ptr.r)/2&&my<(ptr.py+(ptr.r)/2))
                            {
                                if(ptr.t==Type.NORMAL)           //Prevents Deletion of Other Nodes
                                {
                                    xptr=ptr;
                                    auxHead=auxHead.link;
                                    remove(ptr);
                                    ptr.setVisible(false);
                                    nodeC--;
                                    xptr.disconnect(auxHead,Terminal);
                                    repaint();
                                }
                            }
                            else
                                while(ptr.link!=null)
                                {
                                    if(mx>ptr.link.px-(ptr.link.r)/2&&mx<(ptr.link.px+(ptr.link.r)/2)&&my>ptr.link.py-(ptr.link.r)/2&&my<(ptr.link.py+(ptr.link.r)/2))
                                    {
                                        if(ptr.link.t==Type.NORMAL)           //Prevents Deletion of Other Nodes
                                        {
                                            xptr=ptr.link;
                                            ptr.link=ptr.link.link;
                                            nodeC--;
                                            xptr.disconnect(auxHead,Terminal);
                                            remove(xptr);
                                            repaint();
                                        }
                                        else break;
                                    }
                                    ptr=ptr.link;
                            }

                        }     
                    }//End of try Block
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(new JFrame(),e);
                    }
                }          //End of MOUSE_CLICKED

                public void mousePressed(MouseEvent me)                    //MOUSE_PRESSED 
                {
                    mx=me.getX();
                    my=me.getY();
                    /*For connection we require lists:
                     * List of connected nodes 
                     * And List of Gates' Nodes.
                     */
                    ptr=auxHead; //this is head pointer of Connection nodes
                    while(ptr!=null)
                    {

                        if(mx>ptr.px-(ptr.r)/2&&mx<(ptr.px+(ptr.r)/2)&&my>ptr.py-(ptr.r)/2&&my<(ptr.py+(ptr.r)/2))
                        {
                            if(ptr.t==Type.NORMAL)//for movement of Normal nodes 
                                ptr.mp=true;

                            if(cMode==true)
                            {  cptr=ptr;
                            }//storing position of node which is being pressed for connection.
                        }
                        ptr=ptr.link;
                    } 
                    /*Block to tell if AND Gates are pressed or not*/
                    nandPtr=nandHead;
                    while(nandPtr!=null)
                    {
                        if(mx>nandPtr.px&&mx<nandPtr.px+(nandPtr.r)&&my>nandPtr.py&&my<(nandPtr.py+(nandPtr.r)))
                        {
                            nandPtr.mp=true;
                            if(cMode==true)
                            {  cptr=ptr;
                            }//storing position of node which is being pressed for connection.*/
                        }
                        nandPtr=nandPtr.link;
                    }

                    /*Block to tell if OR Gates are pressed or not*/
                    norPtr=norHead;
                    while(norPtr!=null)
                    {
                        if(mx>norPtr.px&&mx<(norPtr.px+(norPtr.r))&&my>norPtr.py&&my<(norPtr.py+(norPtr.r)))
                        {
                            norPtr.mp=true;
                            if(cMode==true)
                            {  cptr=ptr;
                            }//storing position of node which is being pressed for connection.*/
                        }
                        norPtr=norPtr.link;
                    }

                    /*Block to tell if NOT Gates are pressed or not*/
                    notPtr=notHead;
                    while(notPtr!=null)
                    {
                        if(mx>notPtr.px&&mx<(notPtr.px+(notPtr.r))&&my>notPtr.py&&my<(notPtr.py+(notPtr.r)))
                        {
                            notPtr.mp=true;
                            if(cMode==true)
                            {  
                                cptr=ptr;
                            } //storing position of node which is being pressed for connection.
                        }
                        notPtr=notPtr.link;
                    }
                }        //End of mousePressed()

                public void mouseReleased(MouseEvent me)               //MOUSE_RELEASED 
                {
                    mx=me.getX();
                    my=me.getY();
                    /*block to make all mouse pressed false*/
                    ptr=auxHead;
                    while(ptr!=null)
                    {
                        ptr.mp=false;
                        ptr=ptr.link;
                    }
                    /*Block to make all mouse Pressed of AND Gates False*/
                    nandPtr=nandHead;
                    while(nandPtr!=null)
                    {
                        nandPtr.mp=false;
                        nandPtr=nandPtr.link;
                    }
                    /*Block to make all mouse Pressed of NOT Gates False*/
                    notPtr=notHead;
                    while(notPtr!=null)
                    {
                        notPtr.mp=false;
                        notPtr=notPtr.link;
                    }
                    /*Block to make all mouse Pressed of NOR Gates False*/
                    norPtr=norHead;
                    while(norPtr!=null)
                    {
                        norPtr.mp=false;
                        norPtr=norPtr.link;
                    }

                    /*Block to realise Connection b/w nodes*/
                    ptr=auxHead;
                    while(ptr!=null)
                    {

                        if(mx>ptr.px-(ptr.r)/2&&mx<(ptr.px+(ptr.r)/2)&&my>ptr.py-(ptr.r)/2&&my<(ptr.py+(ptr.r)/2)&&(cMode==true)&&!cptr.equals(ptr))
                        {
                            if(cptr!=null)
                                cptr.connect(ptr,auxHead);
                            cptr=null;
                            repaint();
                        }
                        ptr=ptr.link;
                    }
                }
            });    //end of MouseListener
        pum=new JPopupMenu();
        JMenuItem it=new JMenuItem("Node");
        pum.add(it);
        it.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    xNode=new Node(++nodeC,mx,my,Type.NORMAL,ws);
                    xNode.link=auxHead;
                    auxHead=xNode;
                    add(xNode);
                    repaint();
                    return;
                }
            });
        JMenu m=new JMenu("Gates");
        pum.add(m);
        it=new JMenuItem("NAND");
        m.add(it);
        it.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    NAND xNand=new NAND(++(nandC),ws);
                    xNand.link=nandHead;
                    nandHead=xNand;
                    add(xNand);
                }
            });
        it=new JMenuItem("NOT");
        m.add(it);
        it.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    NOT xNot=new NOT(++(notC),ws);
                    xNot.link=notHead;
                    notHead=xNot;
                    add(xNot);
                }
            });
        it=new JMenuItem("NOR");
        m.add(it);
        it.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    NOR xNor=new NOR(++(norC),ws);
                    xNor.link=norHead;
                    norHead=xNor;
                    add(xNor);
                }
            });
        m=new JMenu("ICs");
        pum.add(m);

        addMouseListener(new MouseAdapter()
            {
                public void mouseReleased( MouseEvent me )
                {
                    //if ( pum.isPopupTrigger(me) )
                    pum.show(me.getComponent(), me.getX(), me.getY() );
                }

                public void TriggerEvent( MouseEvent me )
                {
                    if ( me.isPopupTrigger() )
                        pum.show( me.getComponent(), me.getX(), me.getY() );
                }
            });
    }

    public void paintComponent(Graphics g)                          //PAINT_COMPONENT METHOD
    {
        super.paintComponent(g);
        XX=getWidth();
        YY=getHeight();
        setBackground(back);

        ptr=auxHead;
        while(ptr!=null)
        {
            g.setColor(Color.green);
            for(int i=0;i<=ptr.top;i++)
            {
                g.drawLine(ptr.px,ptr.py,ptr.links[i].px,ptr.links[i].py);
            }
            ptr=ptr.link;
        }
    }

    public void repaintDrawingPane(MenuBar b)  //Method to receive values of cMode,nMode and background
    {
        cMode=b.cMode;
        nMode=b.nMode;
        back=b.back;
    }

    public void run()
    {
        while(!end)
        {
            try
            {
                Thread.sleep(2000);
                if(Clock.st==State.HIGH)
                {
                    Clock.st=State.LOW;
                    Clock.cl=Color.blue;
                    Clock.changeConnected();
                    auxHead.reColor();
                }
                else
                {
                    Clock.st=State.HIGH;
                    Clock.cl=Color.red;
                    Clock.changeConnected();
                    auxHead.reColor();
                }
                repaint();
            }
            catch(Exception e){}
        }
    }
}//End of DrawingPane
