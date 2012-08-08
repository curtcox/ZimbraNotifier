package login;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextLayout;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A class to demo using Java2D to create on the fly icons
 *
 * @author webbst
 */
public class DynamicIcon {

    private DynamicIcon() {}

    public static BufferedImage getMarkerIcon(String text, int dimension, Color bg, Color fg) {
        return getMarkerIcon(text, dimension, bg, fg, Color.BLACK);
    }

    public static BufferedImage getMarkerIcon(String text, int dimension, Color bg, Color fg, Color outline) {
        if  (text==null || text.isEmpty()) {
            text = " ";
        }
        // new RGB image with transparency channel
        BufferedImage image = new BufferedImage(dimension, dimension, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = createGraphics(image);

        // Set fill color
        g.setColor(bg);
        g.fillOval(0, 0, dimension - 1, dimension - 1);

        addSpot(g,dimension);

        // draw outline of the icon
        g.setColor(outline);
        g.drawOval(0, 0, dimension - 1, dimension - 1);

        // Draw the character
        drawText(g,text,dimension,bg,fg);
        return image;
    }

    public static BufferedImage getGearIcon(int dimension) {
        // new RGB image with transparency channel
        BufferedImage image = new BufferedImage(dimension * 3, dimension * 3, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = createGraphics(image);

        int half = dimension /2;
        int quarter = half /2;
        
        g.translate(dimension * 1.5, dimension * 1.5);
        
        // Set fill color
        g.setColor(Color.BLACK);
        
        for (int i=0; i<10; i++) {
            g.fillRect(quarter, quarter, half, half);
            g.rotate(0.65d);
        }
        
        return image;
    }

    // create new graphics and set anti-aliasing hint
    static Graphics2D createGraphics(BufferedImage image) {
        Graphics2D g = (Graphics2D) image.getGraphics().create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        return g;
    }
    
    // create spot in the upper-left corner using temporary graphics
    // with clip set to the icon outline
    static void addSpot(Graphics2D g, int dimension) {
        Color color = new Color(255, 255, 255, 0);
        GradientPaint spot = new GradientPaint(0, 0, color, dimension, dimension, color);
        Graphics2D tempG = (Graphics2D) g.create();
        tempG.setPaint(spot);
        tempG.setClip(new Ellipse2D.Double(0, 0, dimension - 1, dimension - 1));
        tempG.fillRect(0, 0, dimension, dimension);
        tempG.dispose();
    }
    
    static void drawText(Graphics2D g, String text, int dimension, Color bg, Color fg) {
        int size = 0;
        if (text.length() > 0) {
            size = dimension / text.length();
        }
        Font font = new Font("Arial", Font.BOLD, size);
        g.setFont(font);
        FontRenderContext frc = g.getFontRenderContext();
        GlyphVector glyphVector = font.createGlyphVector(frc, text);
        float x = (float) (dimension - (float) glyphVector.getLogicalBounds().getWidth()) / 2;

        TextLayout mLayout = new TextLayout(text, g.getFont(), frc);
        float y = dimension - (float) ((dimension - mLayout.getBounds().getHeight()) / 2) - 1;

        g.setColor(fg);
        g.drawString(text, x, y);

        g.dispose();
    }
    
    public static JLabel newLabel(String string, int dimension, Color bg, Color fg) {
        return new JLabel(new ImageIcon(getMarkerIcon(string, dimension, bg, fg)));
    }

    public static JLabel newGear(int dimension) {
        return new JLabel(new ImageIcon(getGearIcon(dimension)));
    }
    
    /**
     * Return a range of images of different sizes.
     * @param text text that appears on the icon  
     * @param color text used to determine icon color
     * @return 
     */
    public static List<Image> images(String text, String color) {
        List<Image> images = new ArrayList<Image>();
        int dimension=8;
        Color background = ColorSelector.getBackground(color);
        Color foreground = ColorSelector.getForeground(color);
        for (int i=0; i<4; i++) {
            images.add(getMarkerIcon(text, dimension, background, foreground));
            dimension = dimension * 2;
        }
        return images;
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                final JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                final JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(19,19));
//                panel.add(newLabel("x", 16, Color.GREEN, Color.BLACK));
//                panel.add(newLabel("x", 16, Color.BLUE, Color.WHITE));
//                panel.add(newLabel("87", 24, Color.GREEN, Color.BLACK));
//                panel.add(newLabel("92", 24, Color.BLUE, Color.WHITE));
//                panel.add(newLabel("E", 72, Color.BLUE, Color.WHITE));
//                panel.add(newLabel("72", 72, Color.BLUE, Color.WHITE));
                for (int i=0; i<200; i++) {
                    String text = Integer.toHexString(i);
                    Color fore = ColorSelector.getForeground(text);
                    Color back = ColorSelector.getBackground(text);
                    panel.add(newLabel(text, 32, fore, back));
                    panel.add(new JLabel());
                    panel.add(new JLabel());
                }
//                panel.add(newGear(16));
//                panel.add(newGear(32));
//                panel.add(newGear(64));
                frame.getContentPane().add(panel);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}