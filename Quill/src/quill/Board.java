package quill;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

public class Board extends JPanel {
    private BufferedImage image;
    private int imageWidth = 800;
    private int imageHeight = 600;
    private boolean drawing = false;
    private Graphics2D g2d;
    private BufferedImage drawingLayer;

    public Board() {
        setPreferredSize(new Dimension(imageWidth, imageHeight));
        setOpaque(false); // Make sure the panel background is transparent

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (image != null) {
                    drawing = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                drawing = false;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (drawing && image != null) {
                    // Draw on the drawing layer
                    if (drawingLayer != null) {
                        g2d.setColor(Color.BLACK); // Use pen color or another setting
                        g2d.drawLine(e.getPoint().x, e.getPoint().y, e.getPoint().x, e.getPoint().y);
                        repaint();
                    }
                }
            }
        });
    }

    public void setImage(File file) {
        try {
            image = javax.imageio.ImageIO.read(file);
            imageWidth = image.getWidth();
            imageHeight = image.getHeight();
            setPreferredSize(new Dimension(imageWidth, imageHeight));

            // Initialize drawing layer
            drawingLayer = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
            g2d = drawingLayer.createGraphics();
            g2d.setComposite(AlphaComposite.SrcOver);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2)); // Pen thickness

            // Draw the image
            repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, imageWidth, imageHeight, this);
            if (drawingLayer != null) {
                g.drawImage(drawingLayer, 0, 0, this);
            }
        }
    }
}
