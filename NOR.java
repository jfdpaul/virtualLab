import java.awt.*;
import javax.swing.*;
class NOR extends Gate                       //class defining OR Gate.
{
    String name;
    Color cl;
    int px,py,r,nr;
    boolean in1,in2,out,mp;
    int in1x,in1y,in2x,in2y,outx,outy;
    Type t;
    NOR link;
    Node in1Link,in2Link,outLink;
    WorkSpace ws;
    public NOR(int count,WorkSpace p)                                          //Constructor for OR
    {
        setText("NOR");
        ws=p;
        name="NOR"+count;
        cl=Color.white;   //Color of Gate.
        px=450;           
        py=450;           //Coordinates of gate.
        r=40;             //Radius related to gate.
        nr=10;          //Radius related to in-out node.
        in1x=px-r/2;        
        in1y=py+r/3;       //Coordinates of 1st node
        in2x=px-r/2;
        in2y=py+r*2/3;     //Coordinates of 2nd node
        outx=px+3*r/4;
        outy=py+r/2;     //Coordinates of output node
        t=Type.GATE;        //type of gate (enum)
        in1=false;
        in2=false;
        out=false;         //States of gates..
        mp=false;          //Informs if mouse is pressed on gate or not
        in1Link=new Node(++(p.nodeC),in1x,in1y,Type.GATE,State.LOW,p);
        in1Link.id=this;
        ws.add(in1Link);
        in2Link=new Node(++(p.nodeC),in2x,in2y,Type.GATE,State.LOW,p);
        in2Link.id=this;
        ws.add(in2Link);
        outLink=new Node(++(p.nodeC),outx,outy,Type.GATE,State.HIGH,p);
        outLink.id=this;
        ws.add(outLink);
        ImageIcon ii=new ImageIcon(getScaledImage(new ImageIcon("images/nor.jpg").getImage(),r,r),"NAND GATE");
        setIcon(ii);
        setSize(r,r);
        setBounds(px,py,r,r);
        setOpaque(true);
        this.in1Link.link=this.in2Link;
        this.in2Link.link=this.outLink;
        this.outLink.link=ws.auxHead;
        ws.auxHead=this.in1Link;
        //setToolTipText("NOR GATE");
    }
    public NOR(int count,WorkSpace p,int n1,int n2,int n3)                                          //Constructor for OR
    {
        setText("NOR");
        ws=p;
        name="NOR"+count;
        cl=Color.white;   //Color of Gate.
        px=450;           
        py=450;           //Coordinates of gate.
        r=40;             //Radius related to gate.
        nr=10;          //Radius related to in-out node.
        in1x=px-r/2;        
        in1y=py+r/3;       //Coordinates of 1st node
        in2x=px-r/2;
        in2y=py+r*2/3;     //Coordinates of 2nd node
        outx=px+3*r/4;
        outy=py+r/2;     //Coordinates of output node
        t=Type.GATE;        //type of gate (enum)
        in1=false;
        in2=false;
        out=false;         //States of gates..
        mp=false;          //Informs if mouse is pressed on gate or not
        in1Link=new Node(n1,in1x,in1y,Type.GATE,State.LOW,p);
        in1Link.id=this;
        ws.add(in1Link);
        in2Link=new Node(n2,in2x,in2y,Type.GATE,State.LOW,p);
        in2Link.id=this;
        ws.add(in2Link);
        outLink=new Node(n3,outx,outy,Type.GATE,State.HIGH,p);
        outLink.id=this;
        ws.add(outLink);
        ImageIcon ii=new ImageIcon(getScaledImage(new ImageIcon("images/nor.jpg").getImage(),r,r),"NAND GATE");
        setIcon(ii);
        setSize(r,r);
        setBounds(px,py,r,r);
        setOpaque(true);
        this.in1Link.link=this.in2Link;
        this.in2Link.link=this.outLink;
        this.outLink.link=ws.auxHead;
        ws.auxHead=this.in1Link;
        //setToolTipText("NOR GATE");
    }
    public void setNodes()
    {
        in1x=px-nr;        
        in1y=py+r/3;       //Coordinates of 1st node
        in2x=px-nr;
        in2y=py+r*2/3;     //Coordinates of 2nd node
        outx=px+r+nr/2;
        outy=py+r/2;     //Coordinates of output node
        in1Link.setXY(in1x,in1y);
        in2Link.setXY(in2x,in2y);
        outLink.setXY(outx,outy);
    }

    public void setLogic()
    {
        if(!(in1Link.st==State.HIGH||in2Link.st==State.HIGH))
            outLink.st=State.HIGH;
        else
            outLink.st=State.LOW;
        outLink.reColor();
        outLink.changeConnected();
        outLink.reColor();

    }

}   