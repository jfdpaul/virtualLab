import java.awt.*;
import javax.swing.*;
class NOT extends Gate                      //class defining NOT Gate.
{
    String name;
    Color cl;
    int px,py,r,nr;
    boolean in,out,mp;
    int inx,iny,outx,outy;
    Type t;
    NOT link;
    Node inLink,outLink;
    WorkSpace ws;
    public NOT(int count,WorkSpace p)                                          //Constructor for NOT
    {
        ws=p;
        name="NOT"+count;
        cl=Color.white;   //Color of Gate.
        px=450;           
        py=450;           //Coordinates of gate.
        r=40;             //Radius related to gate.
        nr=10;          //Radius related to in-out node.
        inx=px-r/2;        
        iny=py+r/2;       //Coordinates of 1st node
        outx=px+3*r/4;
        outy=py+r/2;     //Coordinates of output node
        t=Type.GATE;        //type of gate (enum)
        in=false;
        out=false;         //States of gates..
        mp=false;          //Informs if mouse is pressed on gate or not
        inLink=new Node(++(p.nodeC),inx,iny,Type.GATE,State.LOW,p);
        inLink.id=this;
        ws.add(inLink);
        outLink=new Node(++(p.nodeC),outx,outy,Type.GATE,State.HIGH,p);
        outLink.id=this;
        ws.add(outLink);
        ImageIcon ii=new ImageIcon(getScaledImage(new ImageIcon("images/not.jpg").getImage(),r,r),"NAND GATE");
        setIcon(ii);
        setSize(r,r);
        setBounds(px,py,r,r);
        setOpaque(true);
        inLink.link=this.outLink;
        this.outLink.link=ws.auxHead;
        ws.auxHead=this.inLink;
        //setToolTipText("NOT GATE");
    }
    public NOT(int count,WorkSpace p,int n1,int n2)                                          //Constructor for NOT
    {
        ws=p;
        name="NOT"+count;
        cl=Color.white;   //Color of Gate.
        px=450;           
        py=450;           //Coordinates of gate.
        r=40;             //Radius related to gate.
        nr=10;          //Radius related to in-out node.
        inx=px-r/2;        
        iny=py+r/2;       //Coordinates of 1st node
        outx=px+3*r/4;
        outy=py+r/2;     //Coordinates of output node
        t=Type.GATE;        //type of gate (enum)
        in=false;
        out=false;         //States of gates..
        mp=false;          //Informs if mouse is pressed on gate or not
        inLink=new Node(n1,inx,iny,Type.GATE,State.LOW,p);
        inLink.id=this;
        ws.add(inLink);
        outLink=new Node(n2,outx,outy,Type.GATE,State.HIGH,p);
        outLink.id=this;
        ws.add(outLink);
        ImageIcon ii=new ImageIcon(getScaledImage(new ImageIcon("images/not.jpg").getImage(),r,r),"NAND GATE");
        setIcon(ii);
        setSize(r,r);
        setBounds(px,py,r,r);
        setOpaque(true);
        inLink.link=this.outLink;
        this.outLink.link=ws.auxHead;
        ws.auxHead=this.inLink;
        //setToolTipText("NOT GATE");
    }
    public void setNodes()
    {
        inx=px-nr;        
        iny=py+r/2;       //Coordinates of 1st node
        outx=px+r+nr/2;
        outy=py+r/2;     //Coordinates of output node
        inLink.setXY(inx,iny);
        outLink.setXY(outx,outy);
    }

    public void setLogic()
    {
        if((inLink.st==State.HIGH))
            outLink.st=State.LOW;
        else
            outLink.st=State.HIGH;
        outLink.reColor();
        outLink.changeConnected();
        outLink.reColor();
    }

}