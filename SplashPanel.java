import javax.swing.*;
import java.awt.*;
public class SplashPanel extends JPanel implements Runnable
 {
        int c=50;
        Thread t;
        JWindow window;
        int X,Y;
        public SplashPanel()
        {
                X=500;
                Y=500;
                window = new JWindow();
                window.getContentPane().add(this);
                window.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width)/2-X/2,(Toolkit.getDefaultToolkit().getScreenSize().height)/2-Y/2,X,Y);
                window.setVisible(true);
                t=new Thread(this);
                t.start();
        }
        public static void main(String[] arg) 
        {
                new SplashPanel();
        }
        public void run()
        {
            try{
                for(;c<=X-50;c=c+10)
                {
                    if(c==X/2)
                    c=X/2+40;
                    Thread.sleep(125);
                    repaint();
                }
                Thread.sleep(1000);
                window.dispose();
                //JOptionPane.showMessageDialog(new JFrame(),"Virtual LAB");
                new MainFrame();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        public void paintComponent(Graphics g)
        {
           super.paintComponent(g);
           int r=100;
           try {
                           setBackground(Color.gray);
                            
                           g.setColor(Color.white);
                           g.drawLine(50,Y/2-r/4,X/2,Y/2-r/4);
                           g.drawLine(50,Y/2+r/4,X/2,Y/2+r/4);
                           g.drawLine(X/2+r/2,Y/2,X-50,Y/2);
                           
                           g.setColor(Color.red);
                           g.fillRect(0,Y/2-r,50,2*r);
                           if(c<=X/2)
                           {
                                g.drawLine(50,Y/2-r/4,c,Y/2-r/4);
                                g.fillArc(c,Y/2-r/4-4,8,8,0,360);
                           }
                           else
                           g.drawLine(50,Y/2-r/4,X/2,Y/2-r/4);
                           
                           if(c<=X/2)
                           {
                                g.drawLine(50,Y/2+r/4,c,Y/2+r/4);
                                g.fillArc(c,Y/2+r/4-4,8,8,0,360);
                           }
                           else
                           g.drawLine(50,Y/2+r/4,X/2,Y/2+r/4);
                           
                           if(c>=X/2+r/2)
                           {
                                g.drawLine(X/2+r/2,Y/2,c,Y/2);
                                g.fillArc(c,Y/2-4,8,8,0,360);
                           }
                           g.setColor(Color.green);
                           g.fillArc(X/2-r/2,Y/2-r/2,r,r,270,180); 
                           if(c>X-60)
                           {
                               g.setColor(Color.red);
                               g.fillRect(X-50,Y/2-r,50,2*r);
                               setBackground(Color.black);
                            }
                } 
             catch (Exception e) 
             {
                             e.printStackTrace();
             }
        }
}