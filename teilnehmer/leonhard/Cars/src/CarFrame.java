import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CarFrame extends JFrame
{

    BufferedImage auto;

    public static void main(String[] args)
    {

        CarFrame f = new CarFrame();
        f.setVisible(true);

        JPanel p = new JPanel();
        p.setVisible(true);

        f.pack();

        try
        {
            f.auto = ImageIO.read(new File("./src/europa_karte_de.png"));
        }
        catch (IOException e)
        {
            throw new RuntimeException("Kann Bild nicht laden.");
        }
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(1000, 1000);
    }

}
