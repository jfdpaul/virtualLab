import javax.swing.*;
import java.io.*;
import java.nio.*;
import java.nio.file.*;
/**Class to save states of all avilable components
 * like Nodes,Gates and Modes.
 * The connection between Nodes is problematic because when new nodes are created,
 * their addresses in the computers main memory changes and is not compatible with 
 * the saved data.
 * So only their states need to be saved and not physical location.Thus Serializable Interface is not used.
 */
class Saver
{
    static Path p;
    static String f1="NodeNumber.ctxt";
    static String f2="NodeAttr.ctxt";
    static String f3="nand.ctxt";
    static String f4="nor.ctxt";
    static String f5="not.ctxt";
    static String f6="Connections.ctxt";
    public Saver()
    {

    }

    public static void save(WorkSpace w,String ss)
    {
        final String s=ss;
        final WorkSpace ws=w;
        javax.swing.SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    try
                    {
                        OutputStream os;
                        os=new BufferedOutputStream(Files.newOutputStream(Paths.get(s+".lab")));
                        os.close();
                        p=Paths.get(s);
                        Files.createDirectories(p);
                        /*Saving number of nodes*/
                        os=new BufferedOutputStream(Files.newOutputStream(Paths.get(s+"/"+f1)));
                        os.write((""+ws.nodeC+"\n").getBytes());
                        os.close();
                        /*Saving positions of nodes*/
                        os=new BufferedOutputStream(Files.newOutputStream(Paths.get(s+"/"+f2)));
                        Node ptr=ws.auxHead;
                        while(ptr!=null)
                        {
                            if(ptr.t==Type.NORMAL)
                                os.write(((ptr.name+":"+ptr.px+","+ptr.py+";"+ptr.t+";"+ptr.st+"\n")).getBytes());
                            ptr=ptr.link;
                        }
                        os.close();
                        /*Saving positions of NAND Gates*/
                        os=new BufferedOutputStream(Files.newOutputStream(Paths.get(s+"/"+f3)));
                        NAND nand=ws.nandHead;
                        while(nand!=null)
                        {
                            os.write(((nand.name+":"+nand.px+","+nand.py+";"+nand.in1Link.name+","+nand.in2Link.name+","+nand.outLink.name+",\n")).getBytes()); 
                            nand=nand.link;
                        }
                        os.close();
                        /*Saving positions of NOR Gates*/
                        os=new BufferedOutputStream(Files.newOutputStream(Paths.get(s+"/"+f4)));
                        NOR nor=ws.norHead;
                        while(nor!=null)
                        {
                            os.write(((nor.name+":"+nor.px+","+nor.py+";"+nor.in1Link.name+","+nor.in2Link.name+","+nor.outLink.name+",\n")).getBytes()); 
                            nor=nor.link;
                        }
                        os.close();
                        /*Saving positions of NOT Gates*/
                        os=new BufferedOutputStream(Files.newOutputStream(Paths.get(s+"/"+f5)));
                        NOT not=ws.notHead;
                        while(not!=null)
                        {
                            os.write(((not.name+":"+not.px+","+not.py+";"+not.inLink.name+","+not.outLink.name+",\n")).getBytes()); 
                            not=not.link;
                        }
                        os.close();
                        /*Saving Connections*/
                        os=new BufferedOutputStream(Files.newOutputStream(Paths.get(s+"/"+f6)));
                        ptr=ws.auxHead;
                        while(ptr!=null)
                        {
                            os.write((ptr.name+":").getBytes());
                            for(int i=0;i<=ptr.top;i++)
                            {
                                os.write((ptr.links[i].name+",").getBytes());

                            }
                            os.write(("\n").getBytes());
                            ptr=ptr.link;
                        }
                        os.close();
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(new JFrame(),e);
                    }
                }});
    }

    public static void loadG(LabFrame f,String ss)
    {
        final String sss=ss;
        final LabFrame lf=f;
        javax.swing.SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    try
                    {
                        String s=sss;
                        p=Paths.get(s);
                        LabFrame l=null;
                        if(Files.notExists(p,LinkOption.NOFOLLOW_LINKS))
                            JOptionPane.showMessageDialog(new JFrame(),"No such project found");
                        else
                        {
                            int i;
                            String ext="";
                            for(i=s.length()-1;i>=0;i--)
                            {
                                ext=s.charAt(i)+ext;
                                if(ext.equals(".lab"))
                                    break;
                            }

                            l=new LabFrame();
                            l.setTitle(s.substring(0,i));
                            s=s.substring(0,i);
                            p=Paths.get(s);
                            lf.setVisible(false);
                            lf.getContentPane().removeAll();
                            String line;
                            /*Loading number of nodes*/
                            BufferedReader fr=new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(s+"/"+f1))));
                            while((line=fr.readLine())!=null)
                            {
                                l.ws.nodeC=Integer.parseInt(line.substring(0,line.length()));
                            }
                            fr.close();
                            /*Loading node Positions*/
                            int n=0;
                            Type tt=null;
                            State st=null;
                            int px=0,py=0,p=0;
                            fr=new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(s+"/"+f2))));
                            while((line=fr.readLine())!=null)
                            {
                                for(i=0;i<line.length();i++)
                                {
                                    if(line.charAt(i)==':')
                                    {
                                        n=Integer.parseInt(line.substring(1,i));
                                        break;
                                    }
                                }
                                line=line.substring(i+1,line.length());
                                for(i=0;i<line.length();i++)
                                {
                                    if(line.charAt(i)==',')
                                    {
                                        px=Integer.parseInt(line.substring(0,i));
                                        p=i;
                                    }
                                    if(line.charAt(i)==';')
                                    {
                                        py=Integer.parseInt(line.substring(p+1,i));
                                        break;
                                    }
                                }
                                line=line.substring(i+1,line.length());
                                for(i=0;i<line.length();i++)
                                {
                                    if(line.charAt(i)==';')
                                    {
                                        tt=Type.valueOf(line.substring(0,i));
                                        st=State.valueOf(line.substring(i+1,line.length()));
                                        break;
                                    }
                                }
                                Node nn=new Node(n,px,py,tt,st,l.ws);
                                nn.link=l.ws.auxHead;
                                l.ws.auxHead=nn;
                                l.ws.add(nn);
                            }
                            fr.close();
                            /*Loading the nand gates*/
                            int no=0;
                            fr=new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(s+"/"+f3))));
                            while((line=fr.readLine())!=null)
                            {
                                for(i=0;i<line.length();i++)
                                {
                                    if(line.charAt(i)==':')
                                    {
                                        no=Integer.parseInt(line.substring(4,i));
                                        break;
                                    }
                                }
                                line=line.substring(i+1,line.length());
                                for(i=0;i<line.length();i++)
                                {
                                    if(line.charAt(i)==',')
                                    {
                                        px=Integer.parseInt(line.substring(0,i));
                                        p=i+1;
                                    }
                                    if(line.charAt(i)==';')
                                    {
                                        py=Integer.parseInt(line.substring(p,i));
                                        line=line.substring(i+1,line.length());
                                        break;
                                    }   
                                }
                                int nn[]=new int[3];
                                int k=0;
                                p=0;
                                for(i=0;i<line.length();i++)
                                {
                                    if(line.charAt(i)==',')
                                    {
                                        nn[k++]=Integer.parseInt(line.substring(p+1,i));
                                        p=i+1;

                                    }
                                }
                                NAND nand=new NAND(no,l.ws,nn[0],nn[1],nn[2]);
                                nand.px=px;
                                nand.py=py;
                                nand.link=l.ws.nandHead;
                                l.ws.nandHead=nand;
                                nand.setBounds(px,py,nand.r,nand.r);
                                nand.setNodes();
                                l.ws.add(nand);
                            }
                            fr.close();
                            l.ws.repaint();
                            /*Loading the nor gates*/
                            no=0;
                            fr=new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(s+"/"+f4))));
                            while((line=fr.readLine())!=null)
                            {
                                for(i=0;i<line.length();i++)
                                {
                                    if(line.charAt(i)==':')
                                    {
                                        no=Integer.parseInt(line.substring(3,i));
                                        break;
                                    }
                                }
                                line=line.substring(i+1,line.length());
                                for(i=0;i<line.length();i++)
                                {
                                    if(line.charAt(i)==',')
                                    {
                                        px=Integer.parseInt(line.substring(0,i));
                                        p=i+1;
                                    }
                                    if(line.charAt(i)==';')
                                    {
                                        py=Integer.parseInt(line.substring(p,i));
                                        line=line.substring(i+1,line.length());
                                        break;
                                    }   
                                }
                                int nn[]=new int[3];
                                int k=0;
                                p=0;
                                for(i=0;i<line.length();i++)
                                {
                                    if(line.charAt(i)==',')
                                    {
                                        nn[k++]=Integer.parseInt(line.substring(p+1,i));
                                        p=i+1;

                                    }
                                }
                                NOR nor=new NOR(no,l.ws,nn[0],nn[1],nn[2]);
                                nor.px=px;
                                nor.py=py;
                                nor.link=l.ws.norHead;
                                l.ws.norHead=nor;
                                nor.setBounds(px,py,nor.r,nor.r);
                                nor.setNodes();
                                l.ws.add(nor);
                            }
                            fr.close();
                            l.ws.repaint();
                            /*Loading the not gates*/
                            no=0;
                            fr=new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(s+"/"+f5))));
                            while((line=fr.readLine())!=null)
                            {
                                for(i=0;i<line.length();i++)
                                {
                                    if(line.charAt(i)==':')
                                    {
                                        no=Integer.parseInt(line.substring(3,i));
                                        break;
                                    }
                                }
                                line=line.substring(i+1,line.length());
                                for(i=0;i<line.length();i++)
                                {
                                    if(line.charAt(i)==',')
                                    {
                                        px=Integer.parseInt(line.substring(0,i));
                                        p=i+1;
                                    }
                                    if(line.charAt(i)==';')
                                    {
                                        py=Integer.parseInt(line.substring(p,i));
                                        line=line.substring(i+1,line.length());
                                        break;
                                    }   
                                }
                                int nn[]=new int[2];
                                int k=0;
                                p=0;
                                for(i=0;i<line.length();i++)
                                {
                                    if(line.charAt(i)==',')
                                    {
                                        nn[k++]=Integer.parseInt(line.substring(p+1,i));
                                        p=i+1;

                                    }
                                }
                                NOT not=new NOT(no,l.ws,nn[0],nn[1]);
                                not.px=px;
                                not.py=py;
                                not.link=l.ws.notHead;
                                l.ws.notHead=not;
                                not.setBounds(px,py,not.r,not.r);
                                not.setNodes();
                                l.ws.add(not);
                            }
                            fr.close();
                            l.ws.repaint();
                            /*Connection*/
                            fr=new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(s+"/"+f6))));
                            while((line=fr.readLine())!=null)
                            {
                                String sr="",si="";
                                Node psr=null,psi=null,ptr=null;
                                for(i=0;i<line.length();i++)
                                {
                                    if(line.charAt(i)==':')
                                    {
                                        sr=line.substring(0,i);
                                        line=line.substring(i+1,line.length());
                                        break;
                                    }
                                }
                                p=0;
                                for(i=0;i<line.length();i++)
                                {
                                    if(line.charAt(i)==',')
                                    {
                                        si=line.substring(p,i);
                                        p=i+1;
                                        ptr=l.ws.auxHead;
                                        while(ptr!=null)
                                        {
                                            if((ptr.name).equals(si))
                                                psi=ptr;
                                            if((ptr.name).equals(sr))
                                                psr=ptr;
                                            ptr=ptr.link;
                                        }
                                        if(psr!=null&&psi!=null)
                                            psr.connect(psi,l.ws.auxHead);
                                    }
                                }
                            }
                        }
                        l.l.setText("Loaded");
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(new JFrame(),e);
                    }
                }});
    }

}