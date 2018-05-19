import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
class Gate extends JLabel implements Serializable
{
    Gate link;
    public void setLogic()
    {
        //Just to Enable Overriding of this function in subclasses.
    }
    public Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }
}